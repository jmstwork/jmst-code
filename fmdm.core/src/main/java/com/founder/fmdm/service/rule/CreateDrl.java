package com.founder.fmdm.service.rule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.founder.fmdm.service.sysmgt.ConstantsDef; 
import com.founder.fmdm.entity.RlmgModelDetail;
import com.founder.fmdm.entity.RlmgRuleDetail;
import com.founder.fmdm.entity.RlmgRuleData;
import com.founder.fmdm.service.rule.dto.RuleListDto;
import com.founder.fmdm.service.rule.RlmgModelService;
@Component
public class CreateDrl {
		String packageHead = "com.founder.fmdm.service.rule";
		@Autowired
		RuleManagerService ruleManagerService;
		@Autowired
		RlmgModelService rlmgModelService;
		
//		static Logger logger = Logger.getLogger(MQSessionForEmergen.class);
		
		public StringBuffer createRools(String ruleIds){
			String[] ruleIdItem = ruleIds.split(",");
			StringBuffer rools = new StringBuffer();
			if(ruleIdItem == null){
				return rools;
			}
			//消息头
			rools.append("package "+packageHead+"\n");
			List<String> rlmgModeMap = new ArrayList<String>();
			for(int i=0; i<ruleIdItem.length; i++){
				//获取规则
				List<RuleListDto> ruleListDtoList = ruleManagerService.selectOneRuleData(ruleIdItem[i]);
				if(ruleListDtoList.size()==0){
					continue;
				}
				RuleListDto ruleListDto = ruleListDtoList.get(0);
				//获取规则模型详细
				List<RlmgModelDetail> rlmgModelDetailList = rlmgModelService.selectInitItems(ruleListDto.getModelId());
				if(!rlmgModeMap.contains(ruleListDto.getModelId())){
					rlmgModeMap.add(ruleListDto.getModelId());
					rools.append("declare " +ruleListDto.getModelEnName()+"\n");
					//注册实体类  问题：实体类创建重复
					for(int j =0 ;j<rlmgModelDetailList.size();j++){
						RlmgModelDetail rlmgModelDetail = (RlmgModelDetail) rlmgModelDetailList.get(j);
						rools.append(rlmgModelDetail.getFieldEnName()+":"+rlmgModelDetail.getFieldType()+"\n");
					}
					rools.append("end \n");
				}
				rools.append(this.initCreateRools(ruleIdItem[i]));
			}
			
//			logger.info("Create Rule DRL: "+rools);
			return rools;
		}
		
		/**
		 * 生成DRL
		 * @param ruleId
		 * @throws IOException 
		 */
		public StringBuffer initCreateRools(String ruleId){
			
			//获取规则
			List<RuleListDto> ruleListDtoList = ruleManagerService.selectOneRuleData(ruleId);
			//获取规则详细
			List<RlmgRuleDetail> rlmgRuleDetailList = ruleManagerService.selectById(ruleId);
			//结果
			List<RlmgRuleDetail> rlmgRuleDetailStatus = new ArrayList();
			//描述
			List<RlmgRuleDetail> rlmgRuleDetailMessage = new ArrayList();
			//条件
			List<RlmgRuleDetail> rlmgRuleDetailCause = new ArrayList();
			if(rlmgRuleDetailList!=null && rlmgRuleDetailList.get(0)!=null){
				for(int i =0;i<rlmgRuleDetailList.size();i++){
					RlmgRuleDetail rlmgRuleDetailNew = rlmgRuleDetailList.get(i);
					if(rlmgRuleDetailNew.getItemType() == 0){
						rlmgRuleDetailMessage.add(rlmgRuleDetailNew);
					}else if(rlmgRuleDetailNew.getItemType() == 1){
						rlmgRuleDetailCause.add(rlmgRuleDetailNew);
					}else if(rlmgRuleDetailNew.getItemType() == 2){
						rlmgRuleDetailStatus.add(rlmgRuleDetailNew);
					}
				}
			}
			//获取规则数据，Integer是规则详细ID，Object是数据
			Map<Integer, Object> ruleDataMap = ruleManagerService.selectRuleDataByIdForCreatRule(ruleId);
			//规则
			if(ruleListDtoList.size()==0){
				return null;
			}
			StringBuffer rools = new StringBuffer();
			RuleListDto ruleListDto = ruleListDtoList.get(0);
			
			//规则实体
			Iterator it = ruleDataMap.entrySet().iterator(); 
			int ruleCount=0;
			while(it.hasNext()){
				Map.Entry m=(Map.Entry)it.next(); 
				Map<String,RlmgRuleData> ruleDatamap = (Map<String, RlmgRuleData>) m.getValue();
				//规则名称=规则名称+规则ID+ruleCount
				rools.append("rule \""+ruleListDto.getRuleName()+"_"+ruleListDto.getRuleGroupId()+"_"+ruleCount+"\"\n");
				//规则组名称=科室ID+规则组名称
				rools.append("agenda-group \""+ ruleListDto.getUnitId() + "_" +ruleListDto.getRulegroupEnName()+"\"\n");
				//条件
				rools.append("when \n");
				rools.append("$item:"+ruleListDto.getModelEnName()+"(");
				StringBuffer roolsWhen = new StringBuffer();
				if(rlmgRuleDetailCause.size()>1){
					roolsWhen.append("(");
				}
				for(int i=0;i<rlmgRuleDetailCause.size();i++){
					
					RlmgRuleDetail rlmgRuleDetailCauseNew = rlmgRuleDetailCause.get(i);
					RlmgRuleData rlmgRuleData = ruleDatamap.get(rlmgRuleDetailCauseNew.getRuleItemId());
					if(rlmgRuleData!=null){
						String value = rlmgRuleData.getDataValue();
						String causeItem = null;
						causeItem = this.exchangeValue2(rlmgRuleDetailCauseNew.getItemExpression(),value);
//						causeItem = this.exchangeValue(rlmgRuleDetailCauseNew.getItemExpression(),value);
						if(i==rlmgRuleDetailCause.size()-1){
							roolsWhen.append(causeItem+")");
						}else{
							roolsWhen.append(causeItem+")&&(");
						}
					}
				}
				roolsWhen = this.exTrim(roolsWhen);
				if("()".equals(roolsWhen.toString())){
					roolsWhen = new StringBuffer("(true)");
				}
				if(rlmgRuleDetailCause.size()>1){
					rools.append(roolsWhen+")");
				}
				else{
					rools.append(roolsWhen);
				}
				//结果
				rools.append("\nthen\n");
				for(int i=0;i<rlmgRuleDetailStatus.size();i++){
					RlmgRuleDetail rlmgRuleDetailStatusNew = rlmgRuleDetailStatus.get(i);
					RlmgRuleData rlmgRuleData = ruleDatamap.get(rlmgRuleDetailStatusNew.getRuleItemId());
					if(rlmgRuleData!=null){
						String value = rlmgRuleData.getDataValue();
						if(value!=null && value!=""){
							String causeItem = rlmgRuleDetailStatusNew.getItemExpression().replaceFirst("\\{\\d\\}", ""+value+"");
							causeItem = "set"+toUpperCaseFirstOne(causeItem);
							causeItem = causeItem.replaceFirst("\\=", "(");
							rools.append("$item."+causeItem+");\n");
						}
					}
				}
				rools.append("end\n");
				ruleCount++;
			}
			rools = exTrim(rools);
			
			return rools;
		}
		
		
		/**
		 *  给规则赋值
		 * @param rule
		 * @param value
		 * @param ruleId
		 * @return
		 */
		private String exchangeValue(String rule,String value){
			//RlmgModelService rlmgModelService = new RlmgModelServiceImpl();
			//获取规则模型Map
			//Map<Object,Object> rlmgModelDetail = rlmgModelService.selectModelDetailByRuleId(ruleId);
			if(value!=null){
				String[] valueItem = value.split(",",-1);
				//String[] ruleItems = this.splitRools(rule);
				for(int i =0 ; i<valueItem.length;i++){
					if(valueItem[i]!=null && !valueItem[i].isEmpty()){
						rule = rule.replaceFirst("\\{\\d\\}",valueItem[i]);
					}
				}
			}
			return rule;
		}
		private String exchangeValue2(String rule,String value){
			if(value!=null){
				value = value.trim();
				String[] valueItem = value.split(",",-1);
				//String[] valueItem = valueParser(value);
//				String[] ruleItems = splitRools(rule);
				
				/*支持规则的十二种运算*/
//				logger.debug("rule expression:"+rule);
				String[] ruleItems = splitRoolsAllOper(rule);
//				logger.debug("rule split length:"+ruleItems.length);
//				ArrayList<String> newRuleItems = new ArrayList<String>();
				String[] newRuleItems = new String[ruleItems.length];
//				System.out.println(rule);
				for(int i =0 ; i<valueItem.length;i++){
					if(valueItem[i]!=null && !valueItem[i].isEmpty()){
						if(i==0){
							newRuleItems[4*i]=ruleItems[4*i];
							newRuleItems[4*i+1]=ruleItems[4*i+1];
							newRuleItems[4*i+2]=ruleItems[4*i+2];
							newRuleItems[4*i+2]=newRuleItems[4*i+2].replaceFirst("\\{\\d\\}",valueItem[i]);
						}
						else{
							newRuleItems[4*i-1]=ruleItems[4*i-1];
							newRuleItems[4*i]=ruleItems[4*i];
							newRuleItems[4*i+1]=ruleItems[4*i+1];
							newRuleItems[4*i+2]=ruleItems[4*i+2];
							newRuleItems[4*i+2]=newRuleItems[4*i+2].replaceFirst("\\{\\d\\}",valueItem[i]);
						}
					}else if(i!=0){
						newRuleItems[4*i-1]="";
						newRuleItems[4*i]="";
						newRuleItems[4*i+1]="";
						newRuleItems[4*i+2]="";
					}else if(i==0){
						newRuleItems[4*i]="";
						newRuleItems[4*i+1]="";
						newRuleItems[4*i+2]="";
					}
				}
				rule = combRuleItems(newRuleItems);
			}
			else{
//				logger.info("The values of the rule are empty！");
				rule="";
			}
			return rule;
		}
		
		/**
		 * 合并规则数组，并去除()
		 * @param ruleItems
		 * @return
		 */
		private String combRuleItems(String[] ruleItems){
			StringBuffer rule = new StringBuffer("");
			for(int i = 0 ; i<ruleItems.length;i++){
				if(ruleItems[i]!=null && !ruleItems[i].isEmpty()){
					if(rule.toString().isEmpty() && (ConstantsDef.STRING_OPER_OR.equals(ruleItems[i]) || ConstantsDef.STRING_OPER_AND.equals(ruleItems[i])))
						continue;
					rule.append(ruleItems[i]);
				}
			}
			return rule.toString();
		}
		
		private StringBuffer exTrim(StringBuffer rules){
			String rule = rules.toString();
			rule.trim();
			if(rule.contains("&&()")){
				rule = rule.replaceAll("\\&\\&\\(\\)", "");
			}else if(rule.contains("||()")){
				rule = rule.replaceAll("\\|\\|\\(\\)", "");
			}else if(rule.contains("()||")){
				rule = rule.replaceAll("\\(\\)\\|\\|", "");
			}else if(rule.contains("()&&")){
				rule = rule.replaceAll("\\(\\)\\&\\&", "");
			}
			StringBuffer rule2 = new StringBuffer(rule);
			return rule2;
		}
		
		/**
		 * 拆分表达式,支持drools的十二种运算符
		 * @param rools
		 * @return
		 */
		public static String[] splitRoolsAllOper(String rools){
			
			String operator = ConstantsDef.RULE_EXPRESSION_OPERATE;
			String[] operatorArr = operator.split(ConstantsDef.RULE_OPERATE_SPLIT);
			
			List<String> strList = new ArrayList<String>();
			String[] strArr = rools.split(ConstantsDef.STRING_OPER_AND);
			for(int i=0;i<strArr.length;i++){
				String[] subStrArr = strArr[i].split("\\|\\|",-1);
				for(int j=0;j<subStrArr.length;j++){
					strList.add(subStrArr[j].trim());
					if(j<subStrArr.length-1)
						strList.add(ConstantsDef.STRING_OPER_OR);
				}
				if(i<strArr.length-1)
					strList.add(ConstantsDef.STRING_OPER_AND);
			}
			
			List<String> valueList = new ArrayList<String>();
			
			for(int i=0;i<strList.size();i++){
				if(ConstantsDef.STRING_OPER_AND.equals(strList.get(i))){
					valueList.add(ConstantsDef.STRING_OPER_AND);
					continue;
				}else if(ConstantsDef.STRING_OPER_OR.equals(strList.get(i))){
					valueList.add(ConstantsDef.STRING_OPER_OR);
					continue;
				}
				for(String oper : operatorArr){
					if(strList.get(i).contains(oper)){
						String[] valueArr = strList.get(i).trim().split(oper);
						for(int k=0;k<valueArr.length;k++){
							valueList.add(valueArr[k]);
							if(k<valueArr.length-1)
								valueList.add(oper);
						}
						break;
					}
				}
			}
			return valueList.toArray(new String[0]);
		}
		
		/**
		 * 拆分表达式
		 * @param rools
		 * @return
		 */
		public static String[] splitRools(String rools){
			String s3="&|<>=!*";
			StringTokenizer st = new StringTokenizer(rools,s3,true);
			List<String> starray = new ArrayList<String>();
			int i=0;
			while(st.hasMoreTokens()){
				String value = st.nextToken().trim();
				if(value.equals("&")||value.equals("|")||value.equals("=")){
					starray.add(value+st.nextToken().trim());
				}
				else if(value.equals(">")||value.equals("<")){
					String ifequal = st.nextToken().trim();
					if(ifequal.equals("=")){
						starray.add(value+ifequal);
					}
					else{
						starray.add(value);
						i++;
						starray.add(ifequal);
					}
				}else if(value.equals("*")){
					starray.add(" contains ");
				}
				else{
					starray.add(value);
				}
				i++;
			}
			return starray.toArray(new String[0]);
		}
		
		/**
		 *  首字母大写转换
		 * @param s
		 * @return
		 */
		public static String toUpperCaseFirstOne(String s)
	    {
	        if(Character.isUpperCase(s.charAt(0)))
	            return s;
	        else
	            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	    }
		
		/**
		 * 拆分数据
		 * @param value
		 * @return
		 * @throws IOException
		 */
		public String[] valueParser(String value){
			CSVParser csvp = new CSVParser();
			String[] valueItem = null;
			if(value == null){
				return valueItem;
			}
			try {
				valueItem = csvp.parseLineMulti(value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return valueItem;
		}
		
		/**
		 * 组合数据
		 * @param valueItem
		 * @return
		 */
		public String valueWriter(String[] valueItem){
			CSVWriter cw = new CSVWriter();
			String writer = cw.writeNext2(valueItem,true);
			return writer;
		}
		
		
		
}
