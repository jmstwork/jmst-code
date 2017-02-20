prompt PL/SQL Developer import file
prompt Created on 2013年11月19日 by zdy_zdy
set feedback off
set define off
prompt Creating MQ_MESSAGE_LIB...
create table MQ_MESSAGE_LIB
(
  mqmsg_id               VARCHAR2(50) not null,
  service_id             VARCHAR2(100),
  domain_id              VARCHAR2(100),
  orderdispatchtype_code VARCHAR2(100),
  execunit_id            VARCHAR2(100),
  message_detail         CLOB,
  arrive_time            DATE,
  send_time              DATE,
  send_flg               NUMBER(1),
  error_info             CLOB
)
prompt Creating DICT_ORDER_ITEM...
create table DICT_ORDER_ITEM
(
  code             NVARCHAR2(10) not null,
  name             NVARCHAR2(50) not null,
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  delete_by        NVARCHAR2(50),
  delete_time      DATE,
  delete_flg       NVARCHAR2(1),
  update_count     NUMBER(10),
  item_version     NUMBER(10),
  opt_status       NVARCHAR2(1),
  release_status   NVARCHAR2(1),
  uni_key          NVARCHAR2(50) not null
)
;
alter table DICT_ORDER_ITEM
  add constraint PK_UNI_KEY_DICT_ORDER_ITEM primary key (UNI_KEY);

prompt Creating IAM_SYS_INFO...
create table IAM_SYS_INFO
(
  sys_id           VARCHAR2(50 CHAR) not null,
  sys_name         VARCHAR2(50 CHAR) not null,
  cate_code        VARCHAR2(50 CHAR) not null,
  sup_id           VARCHAR2(50 CHAR) not null,
  sys_url          VARCHAR2(100),
  sys_desc         VARCHAR2(200 CHAR),
  delete_flg       NUMBER(1) default 0,
  create_by        VARCHAR2(50 CHAR),
  create_time      DATE,
  last_update_by   VARCHAR2(50 CHAR),
  last_update_time DATE,
  delete_by        VARCHAR2(50 CHAR),
  delete_time      DATE,
  update_count     NUMBER(5) default 1
)
;
alter table IAM_SYS_INFO
  add constraint SYSINFO_KEY primary key (SYS_ID);

prompt Creating SYSTEM_CODE...
create table SYSTEM_CODE
(
  category_cd      VARCHAR2(10) not null,
  cd_div           VARCHAR2(20) not null,
  cd_value         VARCHAR2(200) not null,
  cd_en_value      VARCHAR2(200),
  disp_order       NUMBER(3),
  create_by        VARCHAR2(50),
  create_time      DATE,
  last_update_by   VARCHAR2(50),
  last_update_time DATE,
  update_count     NUMBER(5),
  delete_by        VARCHAR2(50),
  delete_time      DATE,
  delete_flg       NUMBER(1)
)
;
comment on table SYSTEM_CODE
  is 'code表';
comment on column SYSTEM_CODE.category_cd
  is 'code分类';
comment on column SYSTEM_CODE.cd_div
  is 'code区分';
comment on column SYSTEM_CODE.cd_value
  is 'code值';
comment on column SYSTEM_CODE.cd_en_value
  is '英文值';
comment on column SYSTEM_CODE.disp_order
  is '序列';
comment on column SYSTEM_CODE.create_by
  is '创建者';
comment on column SYSTEM_CODE.create_time
  is '创建时间';
comment on column SYSTEM_CODE.last_update_by
  is '更新者';
comment on column SYSTEM_CODE.last_update_time
  is '更新时间';
comment on column SYSTEM_CODE.update_count
  is '更新次数';
comment on column SYSTEM_CODE.delete_by
  is '删除者';
comment on column SYSTEM_CODE.delete_time
  is '删除时间';
comment on column SYSTEM_CODE.delete_flg
  is '删除标记';
alter table SYSTEM_CODE
  add constraint SYSTEM_CODE_IDX primary key (CATEGORY_CD, CD_DIV);

prompt Creating TSE_USERS...
create table TSE_USERS
(
  user_id      VARCHAR2(32) not null,
  user_account VARCHAR2(30),
  user_name    VARCHAR2(40),
  user_passwd  VARCHAR2(100),
  user_desc    VARCHAR2(100),
  enabled      NUMBER(1),
  super_user   NUMBER(1)
)
;
alter table TSE_USERS
  add constraint KSE_USERS primary key (USER_ID);

prompt Loading DICT_ORDER_ITEM...
prompt Table is empty
prompt Loading IAM_SYS_INFO...
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S045', 'SS', '1', 'C001', null, '22', 1, '213208', to_date('25-01-2013 08:10:55', 'dd-mm-yyyy hh24:mi:ss'), null, null, '213208', to_date('09-05-2013 13:31:23', 'dd-mm-yyyy hh24:mi:ss'), 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S999', 'IE平台', '2', 'C001', null, 'IE平台', 0, '223207', to_date('29-03-2013 15:34:24', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S046', '营养膳食管理系统', '1', 'C024', null, '不允许改删', 0, '213208', to_date('25-01-2013 08:14:50', 'dd-mm-yyyy hh24:mi:ss'), '213208', to_date('27-09-2013 10:26:22', 'dd-mm-yyyy hh24:mi:ss'), '213208', to_date('02-04-2013 14:53:37', 'dd-mm-yyyy hh24:mi:ss'), 2);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S040', '应急系统', '2', 'C001', null, null, 0, 'administrator', to_date('23-11-2012 13:32:37', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S043', 'eee', '3', 'C001', 'http://127.0.0.1', null, 1, '233200', to_date('23-11-2012 15:59:42', 'dd-mm-yyyy hh24:mi:ss'), null, null, '213208', to_date('08-01-2013 14:47:08', 'dd-mm-yyyy hh24:mi:ss'), 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S1000', '系统44', '1', 'C012', null, null, 1, '213208', to_date('09-05-2013 13:27:57', 'dd-mm-yyyy hh24:mi:ss'), null, null, '213208', to_date('09-05-2013 13:32:43', 'dd-mm-yyyy hh24:mi:ss'), 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S044', '测试', '1', 'C001', 'http://127.0.0.1', null, 1, '233200', to_date('26-11-2012 13:24:17', 'dd-mm-yyyy hh24:mi:ss'), '233200', to_date('26-11-2012 13:25:03', 'dd-mm-yyyy hh24:mi:ss'), '213208', to_date('08-01-2013 14:47:14', 'dd-mm-yyyy hh24:mi:ss'), 2);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S041', 'RFID(监控管理系统)', '1', 'C010', null, null, 0, '213208', to_date('08-01-2013 14:48:27', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S039', 'EMPI2', '2', 'C011', 'http://123.com', null, 0, '213208', to_date('05-09-2012 16:40:48', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S037', '内窥镜系统', '3', 'C017', 'http://www.b', '不允许改删', 0, '223200', to_date('27-07-2012 17:29:19', 'dd-mm-yyyy hh24:mi:ss'), null, null, '223207', to_date('14-08-2012 16:21:27', 'dd-mm-yyyy hh24:mi:ss'), 8);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S038', '影像中心（EIP）', '2', 'C002', 'http://www.baidu.com', '不允许改删', 0, '223200', to_date('27-07-2012 17:29:32', 'dd-mm-yyyy hh24:mi:ss'), '223200', to_date('17-09-2012 08:40:31', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('14-08-2012 16:22:59', 'dd-mm-yyyy hh24:mi:ss'), 13);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S001', 'HIS门急诊', '1', 'C001', 'http://172.18.73.123:8080/app/mainMenu', '不允许改删。', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '213208', to_date('12-12-2012 11:32:02', 'dd-mm-yyyy hh24:mi:ss'), '223200', to_date('10-08-2012 10:46:29', 'dd-mm-yyyy hh24:mi:ss'), 21);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S002', 'HIS住院', '1', 'C001', 'http://126.com', '不允许改删。', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:29:49', 'dd-mm-yyyy hh24:mi:ss'), 'SYSTEM', to_date('08-08-2012 11:35:33', 'dd-mm-yyyy hh24:mi:ss'), 6);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S003', 'RIS', '2', 'C002', 'http://163.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:38:06', 'dd-mm-yyyy hh24:mi:ss'), null, null, 3);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S004', 'ECT/PETCT', '2', 'C003', 'http://1.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:26:16', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S005', 'UIS', '2', 'C002', 'http://2.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:38:19', 'dd-mm-yyyy hh24:mi:ss'), null, null, 2);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S006', '心血管信息管理系统（CVIS）', '2', 'C004', 'http://www.baidu.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:42:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, 12);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S007', '朗珈病理信息管理系统', '2', 'C005', 'http://www.baidu.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:42:50', 'dd-mm-yyyy hh24:mi:ss'), null, null, 2);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S008', 'LIS检验', '2', 'C006', 'http://ww.bsss', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:43:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 3);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S009', 'LIS微生物', '2', 'C007', 'http://www.bau', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:43:10', 'dd-mm-yyyy hh24:mi:ss'), null, null, 4);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S010', 'LIS形态学', '2', 'C008', 'http://www.baidu.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:43:20', 'dd-mm-yyyy hh24:mi:ss'), '223200', to_date('15-08-2012 09:46:02', 'dd-mm-yyyy hh24:mi:ss'), 46);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S011', '佰利亚血库管理系统', '2', 'C009', 'http://3com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:43:35', 'dd-mm-yyyy hh24:mi:ss'), null, null, 3);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S012', 'AIMS.NET手术麻醉信息管理系统', '2', 'C010', 'http://4com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:43:50', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S013', '体检', '1', 'C001', 'http://5.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S014', '预约中心', '1', 'C001', 'http://6.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S015', 'CDR', '1', 'C001', 'http://7.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:44:05', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S016', 'IDS', '2', 'C001', 'http://8.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S017', 'EMPI', '1', 'C001', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 11:09:48', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S018', '大医通合理用药系统', '2', 'C011', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S019', '处方点评', '2', 'C011', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S020', 'EMR', '1', 'C012', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S021', 'ERP（EBS）', '1', 'C013', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S022', '分诊大屏', '1', 'C014', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S023', '消毒供应中心质量追溯系统', '1', 'C015', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S024', '移动护士站', '1', 'C015', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S025', 'ICU重症信息监护系统', '2', 'C010', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S026', '院内感染管理系统', '2', 'C007', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S027', '血透系统', '2', 'C016', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S028', '公共服务系统', '3', 'C001', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S029', '统一身份与权限管理', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S030', '访问控制服务', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S031', '文档库（XDS）管理', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S032', '警告通知服务', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S033', '规则服务', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S034', '审计服务', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S035', '通讯接口', '3', 'C001', 'http://9.com', '不允许改删', 1, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S036', '会诊系统', '1', 'C001', 'http://9.com', '不允许改删', 0, 'administrator', to_date('26-07-2012 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), '223207', to_date('16-08-2012 10:26:30', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1);
insert into IAM_SYS_INFO (sys_id, sys_name, cate_code, sup_id, sys_url, sys_desc, delete_flg, create_by, create_time, last_update_by, last_update_time, delete_by, delete_time, update_count)
values ('S042', 'SPDIS', '2', 'C012', 'http://123.com', null, 0, '213208', to_date('19-09-2012 11:05:06', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, 0);
commit;
prompt 48 records loaded
prompt Loading SYSTEM_CODE...
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'ORACLE', 'ORACLE', 'jdbc:oracle:thin:@host:1521:SID', 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'MYSQL', 'MYSQL', 'jdbc:mysql://hostname:3306/database', 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'POSTGRE', 'POSTGRE', 'jdbc:postgresql://hostname/database', 3, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'GENERICODBC', 'ODBC', 'jdbc:odbc:dsn_source;', 4, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'MSSQL', 'SQLSERVER', 'jdbc:jtds:sqlserver://hostname:1433/database', 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'ORACLE', 'oracle.jdbc.driver.OracleDriver', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'MYSQL', 'com.mysql.jdbc.Driver', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'POSTGRE', 'org.postgresql.Driver', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'GENERICODBC', 'sun.jdbc.odbc.JdbcOdbcDriver', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'MSSQL', 'net.sourceforge.jtds.jdbc.Driver', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C004', '0', '国际标准', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C004', '1', '国内标准', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C004', '2', '行业标准', null, 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C004', '3', '医院字典', null, 3, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C009', 'c', '现在', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C009', 'h', '历史', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C009', 'a', '待发布', null, 4, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C009', 'r', '驳回', null, 3, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C009', 'd', '待审批', null, 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C003', '0', '不可编辑', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C003', '1', '可编辑', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C008', 'NVARCHAR2', '字符型', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C008', 'NUMBER', '数值型', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C008', 'DATE', '时间型', null, 3, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C008', 'NUMBER(1)', '布尔型', null, 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C006', 'DB2', 'DB2', 'jdbc:db2://127.0.0.1:5000/DBNAME', 5, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C007', 'DB2', 'com.ibm.db2.jcc.DB2Driver', null, 5, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C005', '0', '待审批', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C005', '1', '待发布', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C005', '2', '已发布', null, 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '0', '医技', null, 6, null, null, null, null, null, null, null, 1);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '1', '护', null, 0, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '2', '研究', null, 2, null, null, null, null, null, null, null, 1);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '3', '工人', null, 3, null, null, null, null, null, null, null, 1);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '4', '医', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '5', '管理', null, 4, null, null, null, null, null, null, null, 1);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C002', '6', '其它技', null, 5, null, null, null, null, null, null, null, 1);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C001', '0', '未提交', null, 1, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C001', '1', '待审批', null, 2, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C001', '2', '已审批', null, 3, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C001', '3', '已驳回', null, 4, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C001', '4', '已发布', null, 5, null, null, null, null, null, null, null, 0);
insert into SYSTEM_CODE (category_cd, cd_div, cd_value, cd_en_value, disp_order, create_by, create_time, last_update_by, last_update_time, update_count, delete_by, delete_time, delete_flg)
values ('C005', '3', '待审批/待发布', null, 3, null, null, null, null, null, null, null, 0);
commit;
prompt 43 records loaded
prompt Loading TSE_USERS...
insert into TSE_USERS (user_id, user_account, user_name, user_passwd, user_desc, enabled, super_user)
values ('yang_jianbo', 'yang_jianbo', '杨建波', '1', '1', 1, 0);
insert into TSE_USERS (user_id, user_account, user_name, user_passwd, user_desc, enabled, super_user)
values ('zheng_dongyang', 'zheng_dongyang', '郑东洋', '1', '1', 1, 0);
insert into TSE_USERS (user_id, user_account, user_name, user_passwd, user_desc, enabled, super_user)
values ('miao_zhenxing', 'miao_zhenxing', '苗振兴', '1', '1', 1, 0);
insert into TSE_USERS (user_id, user_account, user_name, user_passwd, user_desc, enabled, super_user)
values ('na_yuqi', 'na_yuqi', '那玉琦', '1', '1', 1, 0);
insert into TSE_USERS (user_id, user_account, user_name, user_passwd, user_desc, enabled, super_user)
values ('jiang_junjun', 'jiang_junjun', '姜', '1', '1', 1, 0);
commit;
prompt 5 records loaded
set feedback on
set define on
prompt Done.
