package com.founder.fmdm.service.sysmgt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.core.AppSettings;
import com.founder.fmdm.dao.rule.LogProcessor;
import com.founder.fmdm.dao.sysmgt.SystemLogDao;
import com.founder.fmdm.dao.sysmgt.WarningSettingDao;
import com.founder.fmdm.entity.IamSysInfo;
import com.founder.fmdm.entity.NfSetting;
import com.founder.fmdm.entity.NfSettingDetail;
import com.founder.fmdm.entity.RlmgRulegroup;
import com.founder.fmdm.entity.SystemCode;
import com.founder.fmdm.entity.SystemLog;
import com.founder.fmdm.entity.TDictPerson;
import com.founder.fmdm.service.sysmgt.dto.DepartmentDto;
import com.founder.fmdm.service.sysmgt.dto.WarningSettingDto;
import com.ibm.mq.MQException;

@Service
public class WarningSettingServiceImpl implements WarningSettingService
{

    @Autowired
    WarningSettingDao warningSettingDao;

    @Autowired
    SystemLogDao sysLogDao;

   /* @Autowired
    MQSession mqSessionSend;*/

    /*@Autowired
    MQqmAndqConfig mqQMAndQConfig;*/

    @Autowired
    LogProcessor logProcessor;

    static Logger logger = Logger.getLogger(WarningSettingServiceImpl.class);
    private final static String HOSPITAL_CODES = "hospital.codes";
    @Override
    @Transactional(rollbackFor = MQException.class)
    // 保存警告设定信息--对应新增和修改！
    public int wsOperate(WarningSettingDto wsDto, String opt)
    {
        NfSetting ws = new NfSetting();
        NfSettingDetail nsd = new NfSettingDetail();
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth != null)
        {
            ws.setDeleteFlg(0);
            String id = String.valueOf(UUID.randomUUID());
            // 修改比较复杂，单独处理日志
            SystemLog sysLog = new SystemLog();
            sysLog.setLogId(String.valueOf(UUID.randomUUID()));
            String optContent = null;
            StringBuffer operCont = new StringBuffer();
            sysLog.setCreateBy(auth.getName());
            sysLog.setCreateTime(new Date());
            sysLog.setOperorId(auth.getName());
            sysLog.setOperDt(new Date());
            sysLog.setOperModu("00606");
            if (ConstantsDef.OPERATION_ADD_SAVE.equalsIgnoreCase(opt))
            {
                ws.setSettingId(id);
                ws.setCreateBy(auth.getName());
                ws.setCreateTime(new Date()); 
                sysLog.setOperObj("00606001");
                sysLog.setOperCont("新增警告设定[" + ("请选择".equalsIgnoreCase(wsDto.getUnitName())||wsDto.getUnitName()==null?"空":wsDto.getUnitName()) + ","
                        + wsDto.getUserName() + ","
                        + wsDto.getRulegroupName() + ","
                        + wsDto.getUserTypeName() + "]");
//                sysLogDao.insert(sysLog);
            }
            else
            {
                if (ConstantsDef.OPERATION_DELETE.equalsIgnoreCase(opt))
                {
                   
                    // 优先判断是否一次性删除多条
                    if (wsDto.getSettingId().split(":").length > 0)
                    {
                        String[] arr = wsDto.getSettingId().split(":");
                        for (int i=0;i<arr.length;i++)
                        {
                            ws = (NfSetting) warningSettingDao.selectNfSettingById(arr[i]);
                            sysLog.setLogId(String.valueOf(UUID.randomUUID()));
                            sysLog.setOperObj("00606003");
                           /* sysLog.setOperCont("删除警告设定[" + ("请选择".equalsIgnoreCase(wsDto.getDepartName().split(":")[i])||wsDto.getDepartName().split(":")[i]==null?"空":wsDto.getDepartName().split(":")[i]) + ","
                                    + wsDto.getUserName().split(":")[i] + ","
                                    + wsDto.getRulegroupName().split(":")[i] + ","
                                    + wsDto.getUserTypeName().split(":")[i] + "]");
                            sysLogDao.insert(sysLog);*/
                            
                            // 先发消息，再更新数据库，反了会查不到数据
                            this.sendMQMessage(getXmlMessage(ws, opt));
                            if (ws.getLastUpdateBy() == null
                                && ws.getLastUpdateTime() == null)
                            {
                                ws.setLastUpdateBy(auth.getName());
                                ws.setLastUpdateTime(new Date());
                            }
                            ws.setDeleteFlg(1);
                            ws.setDeleteBy(auth.getName());
                            ws.setDeleteTime(new Date());
                            warningSettingDao.updateNfSetting(ws);
                            // 删除详细表信息
                            List<NfSettingDetail> list = warningSettingDao.selectDetailByCondition(
                                    ws.getSettingId(), null, null);
                            if (list.size() > 0)
                            {
                                for (NfSettingDetail entity : list)
                                {
                                    entity.setDeleteFlg(1);
                                    entity.setDeleteBy(auth.getName());
                                    entity.setDeleteTime(new Date());
                                    if ("".equalsIgnoreCase(entity.getLastUpdateBy())
                                        || entity.getLastUpdateBy() == null)
                                    {
                                        entity.setLastUpdateBy(auth.getName());
                                    }
                                    if ("".equalsIgnoreCase(String.valueOf(nsd.getLastUpdateTime()))
                                        || nsd.getLastUpdateTime() == null)
                                    {
                                        entity.setLastUpdateTime(new Date());
                                    }
                                    warningSettingDao.updateNfSettingDetail(entity);
                                }
                            }
                        }
                        return 1;
                    }
                    else
                    {
                        ws = (NfSetting) warningSettingDao.selectNfSettingById(wsDto.getSettingId());
                        ws.setDeleteFlg(1);
                        if ("".equalsIgnoreCase(ws.getLastUpdateBy())
                            && "".equalsIgnoreCase(ws.getLastUpdateTime().toString()))
                        {
                            ws.setLastUpdateBy(auth.getName());
                            ws.setLastUpdateTime(new Date());
                        }
                        ws.setDeleteFlg(1);
                        ws.setDeleteBy(auth.getName());
                        ws.setDeleteTime(new Date());
                        warningSettingDao.updateNfSetting(ws);
                        return ConstantsDef.OPERATION_SUCCESS;
                    }

                }
                else
                {
                    ws = (NfSetting) warningSettingDao.selectNfSettingById(wsDto.getSettingId());
                    ws.setLastUpdateBy(auth.getName());
                    ws.setLastUpdateTime(new Date());

                    String emailAdd = null;
                    String emailEdit = null;
                    String emailDelete = null;
                    String telAdd = null;
                    String telEdit = null;
                    String telDelete = null;
                    StringBuffer sysAdd = new StringBuffer();
                    StringBuffer sysDelete = new StringBuffer();
                    // 更新详细表NF_SETTING_DETAIL
                    // 1. 更新email
                    List<NfSettingDetail> list = warningSettingDao.selectDetailByCondition(
                            wsDto.getSettingId(), "2", null);
                    if (list.size() > 0)
                    {
                        nsd = (NfSettingDetail) list.get(0);
                        // 1.1 数据库和页面都存在——————更新
                        if (wsDto.getEmail() != null
                            && !"".equalsIgnoreCase(nsd.getSettingValue()))
                        {
                            // 存在差异才更新
                            if (!wsDto.getEmail().equalsIgnoreCase(
                                    nsd.getSettingValue()))
                            {
                                emailEdit = "[\""+nsd.getSettingValue()+"\"为\""+wsDto.getEmail()+"\"]";
                                //在修改SettingValue值之前记录日志信息
                                nsd.setSettingValue(wsDto.getEmail());
                                nsd.setDeleteFlg(0);
                                nsd.setLastUpdateBy(auth.getName());
                                nsd.setLastUpdateTime(new Date());
                                warningSettingDao.updateNfSettingDetail(nsd);
                                
                            }
                        }
                        else
                        {
                            // 1.2数据库存在，页面不存在——————删除
                            nsd.setDeleteFlg(1);
                            nsd.setDeleteBy(auth.getName());
                            nsd.setDeleteTime(new Date());
                            if ("".equalsIgnoreCase(nsd.getLastUpdateBy())
                                || nsd.getLastUpdateBy() == null)
                            {
                                nsd.setLastUpdateBy(auth.getName());
                            }
                            if ("".equalsIgnoreCase(String.valueOf(nsd.getLastUpdateTime()))
                                || nsd.getLastUpdateTime() == null)
                            {
                                nsd.setLastUpdateTime(new Date());
                            }
                            warningSettingDao.updateNfSettingDetail(nsd);
                            emailDelete = nsd.getSettingValue();
                        }

                    }
                    else
                    {
                        if (wsDto.getEmail() != null
                            && !"".equalsIgnoreCase(wsDto.getEmail()))
                        {
                            // 1.3 数据库无，页面有——————新增
                            nsd = new NfSettingDetail();
                            nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                            nsd.setSettingId(wsDto.getSettingId());
                            nsd.setCreateBy(auth.getName());
                            nsd.setCreateTime(new Date());
                            nsd.setDeleteFlg(0);
                            nsd.setNfType("2");
                            nsd.setSettingValue(wsDto.getEmail());
                            warningSettingDao.insertNfSettingDetail(nsd);
                            emailAdd = wsDto.getEmail();
                        }
                    }

                    // 2.更新system
                    list = warningSettingDao.selectDetailByCondition(
                            wsDto.getSettingId(), "3", null);
                    if (list.size() > 0)
                    {
                        id = list.get(0).getSettingId();
                        Map<String, String> map = new HashMap<String, String>();
                        for (int i = 0; i < list.size(); i++)
                        {
                            nsd = (NfSettingDetail) list.get(i);
                            map.put(nsd.getSettingValue(),
                                    nsd.getSettingValue());
                        }
                        if (!"".equalsIgnoreCase(wsDto.getSysIds()))
                        {
                            String[] arr = wsDto.getSysIds().split(":");
                            for (int j = 0; j < arr.length; j++)
                            {
                                if (map.containsKey(arr[j]))
                                {
                                    map.remove(arr[j]);
                                    /*
                                     * //2.1如果存在且delete_flg是1，标注为删除，要恢复为0，表示启用
                                     * list =
                                     * warningSettingDao.selectDetailByCondition
                                     * (wsDto.getSettingId(), "system",arr[j]);
                                     * if(list.size()>0){ nsd = list.get(0);
                                     * nsd.setDeleteFlg(0);
                                     * nsd.setLastUpdateBy(auth.getName());
                                     * nsd.setLastUpdateTime(new Date());
                                     * entityDao.update(nsd); }
                                     */
                                }
                                else
                                {
                                    // 2.2. 不包含，插入
                                    nsd = new NfSettingDetail();
                                    nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                                    nsd.setSettingId(id);
                                    nsd.setCreateBy(auth.getName());
                                    nsd.setCreateTime(new Date());
                                    nsd.setDeleteFlg(0);
                                    nsd.setNfType("3");
                                    nsd.setSettingValue(arr[j]);
                                    warningSettingDao.insertNfSettingDetail(nsd);
                                    sysAdd.append("[").append(wsDto.getSysIds()).append("],");
                                }
                            }
                        }

                        // 2.3 map中剩下的，说明是被删除的
                        Iterator<String> it = map.keySet().iterator();
                        while (it.hasNext())
                        {
                            Object key = it.next();
                            list = warningSettingDao.selectDetailByCondition(
                                    wsDto.getSettingId(), "3", key.toString());
                            if (list.size() > 0)
                            {
                                nsd = list.get(0);
                                nsd.setDeleteFlg(1);
                                nsd.setDeleteBy(auth.getName());
                                nsd.setDeleteTime(new Date());
                                if ("".equalsIgnoreCase(nsd.getLastUpdateBy())
                                    || nsd.getLastUpdateBy() == null)
                                {
                                    nsd.setLastUpdateBy(auth.getName());
                                }
                                if ("".equalsIgnoreCase(String.valueOf(nsd.getLastUpdateTime()))
                                    || nsd.getLastUpdateTime() == null)
                                {
                                    nsd.setLastUpdateTime(new Date());
                                }
                                warningSettingDao.updateNfSettingDetail(nsd);
                                sysDelete.append("[").append(key).append("],");
                            }
                        }
                    }
                    else
                    {
                        if (wsDto.getSysIds() != null
                            && !"".equalsIgnoreCase(wsDto.getSysIds()))
                        {
                            // 数据库不存在，页面存在————————新增
                            String arr[] = wsDto.getSysIds().split(":");
                            for (int i = 0; i < arr.length; i++)
                            {
                                nsd = new NfSettingDetail();
                                nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                                nsd.setSettingId(wsDto.getSettingId());
                                nsd.setCreateBy(auth.getName());
                                nsd.setCreateTime(new Date());
                                nsd.setDeleteFlg(0);
                                nsd.setNfType("3");
                                nsd.setSettingValue(arr[i]);
                                warningSettingDao.insertNfSettingDetail(nsd);
                                sysAdd.append(arr[i]).append(",");
                            }
                        }
                    }

                    // 3. 更新tel
                    list = warningSettingDao.selectDetailByCondition(
                            wsDto.getSettingId(), "1", null);
                    if (list.size() > 0)
                    {
                        nsd = list.get(0);
                        if (wsDto.getTel() != null
                            && !"".equalsIgnoreCase(wsDto.getTel()))
                        {
                            // 3.1 页面和数据库都存在————————修改
                            StringBuffer sb = new StringBuffer();

                            if (nsd.getFlgMon() != null)
                            {
                                sb.append("1:");
                            }
                            if (nsd.getFlgTus() != null)
                            {
                                sb.append("2:");
                            }
                            if (nsd.getFlgWed() != null)
                            {
                                sb.append("3:");
                            }
                            if (nsd.getFlgThu() != null)
                            {
                                sb.append("4:");
                            }
                            if (nsd.getFlgFri() != null)
                            {
                                sb.append("5:");
                            }
                            if (nsd.getFlgSat() != null)
                            {
                                sb.append("6:");
                            }
                            if (nsd.getFlgSun() != null)
                            {
                                sb.append("7:");
                            }
                            String receviceDays = "";
                            if (sb != null)
                            {
                                receviceDays = (String) sb.toString().subSequence(
                                        0, sb.toString().length() - 1);
                            }
                            if (!receviceDays.equalsIgnoreCase(wsDto.getReceiveDays())
                                || !wsDto.getTel().equalsIgnoreCase(
                                        nsd.getSettingValue()))
                            {
                                if(!wsDto.getTel().equalsIgnoreCase(
                                        nsd.getSettingValue())){
                                    telEdit = "[\""+nsd.getSettingValue()+"\"为\""+ wsDto.getTel() + "\"]";
                                }
                                if(!receviceDays.equalsIgnoreCase(wsDto.getReceiveDays())){
                                    if(!wsDto.getTel().equalsIgnoreCase(
                                            nsd.getSettingValue())){
                                        telEdit =  telEdit + ",";
                                    }
                                    telEdit = telEdit == null ? "" : telEdit;
                                    telEdit = telEdit+ "通知时段[\""+receviceDays+"\"为\""+ wsDto.getReceiveDays() + "\"]";;
                                }
                                //保障在修改SettingValue值之前记录
                                nsd.setSettingValue(wsDto.getTel());
                                nsd.setDeleteFlg(0);
                                nsd.setLastUpdateBy(auth.getName());
                                nsd.setLastUpdateTime(new Date());
                                if (wsDto.getReceiveDays().contains("1"))
                                {
                                    nsd.setFlgMon("0");
                                }
                                else
                                {
                                    nsd.setFlgMon("");
                                }

                                if (wsDto.getReceiveDays().contains("2"))
                                {
                                    nsd.setFlgTus("0");
                                }
                                else
                                {
                                    nsd.setFlgTus("");
                                }
                                if (wsDto.getReceiveDays().contains("3"))
                                {
                                    nsd.setFlgWed("0");
                                }
                                else
                                {
                                    nsd.setFlgWed("");
                                }
                                if (wsDto.getReceiveDays().contains("4"))
                                {
                                    nsd.setFlgThu("0");
                                }
                                else
                                {
                                    nsd.setFlgThu("");
                                }
                                if (wsDto.getReceiveDays().contains("5"))
                                {
                                    nsd.setFlgFri("0");
                                }
                                else
                                {
                                    nsd.setFlgFri("");
                                }
                                if (wsDto.getReceiveDays().contains("6"))
                                {
                                    nsd.setFlgSat("0");
                                }
                                else
                                {
                                    nsd.setFlgSat("");
                                }
                                if (wsDto.getReceiveDays().contains("7"))
                                {
                                    nsd.setFlgSun("0");
                                }
                                else
                                {
                                    nsd.setFlgSun("");
                                }
                                
                                warningSettingDao.updateNfSettingDetail(nsd);
                            }
                        }
                        else
                        {
                            // 3.2 数据库存在，页面不存在————————删除
                            nsd.setDeleteFlg(1);
                            nsd.setDeleteBy(auth.getName());
                            nsd.setDeleteTime(new Date());
                            nsd.setFlgMon("");
                            nsd.setFlgTus("");
                            nsd.setFlgWed("");
                            nsd.setFlgThu("");
                            nsd.setFlgFri("");
                            nsd.setFlgSat("");
                            nsd.setFlgSun("");
                            if ("".equalsIgnoreCase(nsd.getLastUpdateBy())
                                || nsd.getLastUpdateBy() == null)
                            {
                                nsd.setLastUpdateBy(auth.getName());
                            }
                            if ("".equalsIgnoreCase(String.valueOf(nsd.getLastUpdateTime()))
                                || nsd.getLastUpdateTime() == null)
                            {
                                nsd.setLastUpdateTime(new Date());
                            }
                            warningSettingDao.updateNfSettingDetail(nsd);
                            telDelete = nsd.getSettingValue();
                        }
                    }
                    else
                    {
                        if (wsDto.getTel() != null
                            && !"".equalsIgnoreCase(wsDto.getTel()))
                        {
                            // 3.3 数据库不存在，页面存在———————————新增
                            nsd = new NfSettingDetail();
                            nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                            nsd.setSettingId(wsDto.getSettingId());
                            nsd.setDeleteFlg(0);
                            nsd.setCreateBy(auth.getName());
                            nsd.setCreateTime(new Date());
                            nsd.setNfType("1");
                            nsd.setSettingValue(wsDto.getTel());
                            String[] arr = wsDto.getReceiveDays().split(":");
                            for (int i = 0; i < arr.length; i++)
                            {
                                if ("1".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgMon("0");
                                }
                                if ("2".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgTus("0");
                                }
                                if ("3".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgWed("0");
                                }
                                if ("4".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgThu("0");
                                }
                                if ("5".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgFri("0");
                                }
                                if ("6".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgSat("0");
                                }
                                if ("7".equalsIgnoreCase(arr[i]))
                                {
                                    nsd.setFlgSun("0");
                                }
                            }
                            warningSettingDao.insertNfSettingDetail(nsd);
                            telAdd = wsDto.getTel() + "],通知时段["
                                + wsDto.getReceiveDays();
                        }
                    }

                   
                    sysLog.setOperModu("00606");
                    sysLog.setOperObj("00606002");
                    boolean writeLog = false;
                    if ((ws.getUnitId()!=null&&ws.getUnitId().equalsIgnoreCase(wsDto.getUnitId()))||ws.getUnitId()==null)
                    {
                        operCont.append("编辑警告设定[" + ("请选择".equalsIgnoreCase(wsDto.getUnitName())||wsDto.getUnitName()==null?"空":wsDto.getUnitName()) + ","
                            + wsDto.getUserName() + ","
                            + wsDto.getRulegroupName() + ","
                            + wsDto.getUserTypeName() + "],");
                    }
                    else
                    {
                        operCont.append("编辑警告设定[" + ("请选择".equalsIgnoreCase(wsDto.getUnitName())||wsDto.getUnitName()==null?"空":wsDto.getUnitName()) + ","
                            + wsDto.getUserName() + ","
                            + wsDto.getRulegroupName() + ","
                            + wsDto.getUserTypeName() + "]的[科室ID:\""
                            + ws.getUnitId() + "\"为\"" + wsDto.getUnitId()
                            + "\",");
                        writeLog = true;
                    }

                    boolean tel = telAdd != null || telEdit != null
                        || telDelete != null;
                    boolean email = emailAdd != null || emailEdit != null
                        || emailDelete != null;
                    boolean sys = sysAdd.length() > 0 || sysDelete.length() > 0;
                    if (tel)
                    {
                        operCont.append("编辑通知方式包括：");
                        if (telAdd != null)
                        {
                            operCont.append("新增手机");
                            operCont.append(telAdd);
                            operCont.append(",");
                        }
                        if (telEdit != null)
                        {
                            operCont.append("编辑手机");
                            operCont.append(telEdit);
                            operCont.append(",");
                        }
                        if (telDelete != null)
                        {
                            operCont.append("删除手机");
                            operCont.append(telDelete);
                            operCont.append(",");
                        }
                        writeLog = true;
                    }

                    if (email)
                    {
                        if (!tel)
                        {
                            operCont.append("编辑通知方式包括：");
                        }

                        if (emailAdd != null)
                        {
                            operCont.append("新增邮箱");
                            operCont.append(emailAdd);
                            operCont.append(",");
                        }
                        if (emailEdit != null)
                        {
                            operCont.append("编辑邮箱");
                            operCont.append(emailEdit);
                            operCont.append(",");
                        }
                        if (emailDelete != null)
                        {
                            operCont.append("删除邮箱");
                            operCont.append(emailDelete);
                            operCont.append(",");
                        }
                        writeLog = true;
                    }

                    if (sys)
                    {
                        if (!(tel || email))
                        {
                            operCont.append("编辑通知方式包括：");
                        }

                        if (sysAdd.length() > 0)
                        {
                            operCont.append("新增系统");
                            operCont.append(sysAdd.subSequence(0,
                                    sysAdd.length() - 1));
                            operCont.append(",");
                        }
                        if (sysDelete.length() > 0)
                        {
                            operCont.append("删除系统");
                            operCont.append(sysDelete.subSequence(0,
                                    sysDelete.length() - 1));
                            operCont.append(",");
                        }
                        writeLog = true;
                    }
                    if(writeLog){
                        optContent = (String) operCont.substring(0, operCont.length()-1);
                        sysLog.setOperCont(optContent);
                        //sysLogDao.insert(sysLog);
                    }
                }
            }
            ws.setUnitId(wsDto.getUnitId());
            ws.setUserNo(wsDto.getUserNo());
            ws.setUserType(wsDto.getUserType());
            ws.setRulegroupId(wsDto.getGroupCode());
            if (ConstantsDef.OPERATION_ADD_SAVE.equalsIgnoreCase(opt))
            {
                // 插入详细表
                nsd.setSettingId(id);
                nsd.setCreateBy(auth.getName());
                nsd.setCreateTime(new Date());
                nsd.setDeleteFlg(0);
                if (wsDto.getEmail() != null
                    && !"".equalsIgnoreCase(wsDto.getEmail()))
                {
                    nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                    nsd.setNfType("2");
                    nsd.setSettingValue(wsDto.getEmail());
                    warningSettingDao.insertNfSettingDetail(nsd);
                }
                if (wsDto.getSysIds() != null
                    && !"".equalsIgnoreCase(wsDto.getSysIds()))
                {
                    String arr[] = wsDto.getSysIds().split(":");
                    for (int i = 0; i < arr.length; i++)
                    {
                        nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                        nsd.setNfType("3");
                        nsd.setSettingValue(arr[i]);
                        warningSettingDao.insertNfSettingDetail(nsd);
                    }
                }
                if (wsDto.getTel() != null
                    && !"".equalsIgnoreCase(wsDto.getTel()))
                {
                    nsd.setNfType("1");
                    nsd.setSettingValue(wsDto.getTel());
                    String[] arr = wsDto.getReceiveDays().split(":");
                    for (int i = 0; i < arr.length; i++)
                    {
                        nsd.setSettingDetailId(String.valueOf(UUID.randomUUID()));
                        if ("1".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgMon("0");
                        }
                        if ("2".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgTus("0");
                        }
                        if ("3".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgWed("0");
                        }
                        if ("4".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgThu("0");
                        }
                        if ("5".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgFri("0");
                        }
                        if ("6".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgSat("0");
                        }
                        if ("7".equalsIgnoreCase(arr[i]))
                        {
                            nsd.setFlgSun("0");
                        }
                    }
                    warningSettingDao.insertNfSettingDetail(nsd);
                }
                warningSettingDao.insertNfSetting(ws);
            }else
            {
            	warningSettingDao.updateNfSetting(ws);
            }
            //this.sendMQMessage(getXmlMessage(ws, opt));
            return ConstantsDef.OPERATION_SUCCESS;
        }

        return ConstantsDef.OPERATION_FAILURE;
    }

    @Override
    public List<WarningSettingDto> selectWSList(WarningSettingDto wsDto,
            String orderBy, SelectOptions options)
    {
        return warningSettingDao.selectWsList(wsDto.getUnitId(), wsDto.getUnitName(),wsDto.getUserNo(), wsDto.getGroupCode(), wsDto.getTel(),
        		wsDto.getEmail(), wsDto.getSettingId(), wsDto.getUserType(), orderBy, options);
    }

    @Override
    public List<IamSysInfo> selectSysNameBySysId(String sysId)
    {
        return warningSettingDao.selectSysNameBySysId(sysId);
    }

    /***
     * 已启用账户列表
     * 
     * @param userListDto
     * @return
     */
    public List<TDictPerson> selectUserListDictPerson(TDictPerson dictPerson,
    		SelectOptions options)
    {
        String userNo = dictPerson.getCode() == null ? ""
                : dictPerson.getCode();
        String userName = dictPerson.getName() != null ? dictPerson.getName()
                : "";
        return warningSettingDao.selectUserListDictPerson(userNo, userName,
        		options);
    }

    @Override
    public List<SystemCode> selectCodeList(String cdDiv)
    {
        return warningSettingDao.selectCodeList(cdDiv);
    }

    @Override
    public void sendMQMessage(String msg)
    {
        // 设值消息头信息
        Map<String, Object> mapHead = new HashMap<String, Object>();
//        mapHead.put("service_id", "BS913");
//        mapHead.put("domain_id", 0);
//        mapHead.put("order_dispatch_type_code", 0);
//        mapHead.put("exec_unit_id", 0);
        mapHead.put("service_id", "BS913");
        mapHead.put("domain_id", 0);
        mapHead.put("exec_unit_id", 0);
        mapHead.put("hospital_id", AppSettings.getConfig(HOSPITAL_CODES));
        mapHead.put("apply_unit_id", 0);
        mapHead.put("send_sys_id", 0);
        mapHead.put("order_exec_id", 0);
        mapHead.put("extend_sub_id", 0);
        
     /*   MQClient mqclient = mqSessionSend.createClient();
        mqclient.send(mqQMAndQConfig.getSendQMName(),
                mqQMAndQConfig.getWarningSetingSendQName(), msg, mapHead);*/
        logger.info(msg);
        logger.info("BS913警告设定消息已经发送到队列IN.BS913.LQ中!");
    }

    @Override
    public String getXmlMessage(NfSetting entity, String opt)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            if (ConstantsDef.OPERATION_EDIT.equalsIgnoreCase(opt))
            {
                opt = "update";
            }
            else if (ConstantsDef.OPERATION_ADD.equalsIgnoreCase(opt))
            {
                opt = "insert";
            }
            // 查询规则组英文名
            List<RlmgRulegroup> groupList = warningSettingDao.selectRulegroupEnName(entity.getRulegroupId());
            String rulegroupEnName = null;
            for (RlmgRulegroup rulegroup : groupList)
            {
                rulegroupEnName = rulegroup.getRulegroupEnName();
            }
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
            sb.append("<msg>").append("\n   ");
            sb.append("<head>").append("\n      ");
            sb.append("<msgId>").append("BS913").append("</msgId>").append(
                    "\n      ");
            sb.append("<msgName>").append("警告通知对象设定数据").append("</msgName>").append(
                    "\n      ");
            sb.append("<sourceSysCode>").append("S028").append(
                    "</sourceSysCode>").append("\n      ");
            sb.append("<targetSysCode>").append("").append("</targetSysCode>").append(
                    "\n      ");
            DateFormat DATE_FORMAT_MSG = new SimpleDateFormat("yyyyMMddHHmmss");
            sb.append("<createTime>").append(DATE_FORMAT_MSG.format(new Date())).append(
                    "</createTime>").append("\n   ");
            sb.append("</head>").append("\n   ");
            sb.append("<body>").append("\n      ");
            sb.append("<row action=\"").append(opt == null ? "" : opt).append(
                    "\">").append("\n         ");
            sb.append("<userId>").append(
                    entity.getUserNo() == null ? "" : entity.getUserNo()).append(
                    "</userId>").append("\n         ");
            sb.append("<ruleGroup>").append(
                    rulegroupEnName == null ? "" : rulegroupEnName).append(
                    "</ruleGroup>").append("\n         ");
            sb.append("<userType>").append(
                    entity.getUserType() == null ? "" : entity.getUserType()).append(
                    "</userType>").append("\n         ");
            sb.append("<method>").append("\n            ");
            // 从详细表中取tel
            List<NfSettingDetail> telList = warningSettingDao.selectDetailByCondition(
                    entity.getSettingId(), "1", null);
            // 从详细表中取email
            List<NfSettingDetail> emailList = warningSettingDao.selectDetailByCondition(
                    entity.getSettingId(), "2", null);
            // 从详细表中取所有systemIds
            List<NfSettingDetail> systemList = warningSettingDao.selectDetailByCondition(
                    entity.getSettingId(), "3", null);
            String telephone = "";
            if (telList.size() > 0)
            {
                telephone = telList.get(0).getSettingValue();
                sb.append("<telephone value=\"" + telephone + "\">").append(
                        "\n               ");
                StringBuffer weekSb = new StringBuffer();
                String weekDays = "";
                if (telList.size() > 0)
                {
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgMon()))
                    {
                        weekSb.append("1:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgTus()))
                    {
                        weekSb.append("2:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgWed()))
                    {
                        weekSb.append("3:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgThu()))
                    {
                        weekSb.append("4:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgFri()))
                    {
                        weekSb.append("5:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgSat()))
                    {
                        weekSb.append("6:");
                    }
                    if ("0".equalsIgnoreCase(telList.get(0).getFlgSun()))
                    {
                        weekSb.append("7:");
                    }
                    if (!"".equalsIgnoreCase(weekSb.toString()))
                    {
                        weekDays = weekSb.toString().substring(0,
                                weekSb.toString().length() - 1);
                    }
                }
                sb.append("<weekdays>").append(weekDays).append("</weekdays>").append(
                        "\n            ");
                if (telList.size() > 0 && emailList.size() <= 0
                    && systemList.size() <= 0)
                {
                    sb.append("</telephone>").append("\n         ");
                }
                else
                {
                    sb.append("</telephone>").append("\n            ");
                }
            }
            String email = "";
            if (emailList.size() > 0)
            {
                email = emailList.get(0).getSettingValue();
                sb.append("<email>").append(email);
                if (emailList.size() > 0 && systemList.size() <= 0)
                {
                    sb.append("</email>").append("\n         ");
                }
                else
                {
                    sb.append("</email>").append("\n            ");
                }
            }
            if (systemList.size() > 0)
            {
                sb.append("<systems>").append("\n               ");
                for (int i = 0; i < systemList.size(); i++)
                {
                    if (i == systemList.size() - 1)
                    {
                        sb.append("<systemId>").append(
                                systemList.get(i).getSettingValue() == null ? ""
                                        : systemList.get(i).getSettingValue()).append(
                                "</systemId>").append("\n            ");
                    }
                    else
                    {
                        sb.append("<systemId>").append(
                                systemList.get(i).getSettingValue() == null ? ""
                                        : systemList.get(i).getSettingValue()).append(
                                "</systemId>").append("\n               ");
                    }
                }
                sb.append("</systems>").append("\n         ");
            }
            sb.append("</method>").append("\n         ");
            sb.append("<deptId>").append(
                    entity.getUnitId() == null ? "" : entity.getUnitId()).append(
                    "</deptId>").append("\n      ");
            sb.append("</row>").append("\n   ");
            sb.append("</body>").append("\n");
            sb.append("</msg>");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.toString(), null);
        }
        return sb.toString();
    }

    @Override
    public List<DepartmentDto> selectDeptDataFromDictDepartment(String deptCode)
    {
        return warningSettingDao.selectDeptDataFromDictDepartment(deptCode);
    }

    /*
     * @Override public List<RlmgRulegroup> selectInitGros(String rule){ return
     * warningSettingDao.selectInitGros(deptCode); }
     */
}
