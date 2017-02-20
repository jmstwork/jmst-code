CREATE TABLE TERM_EXAMINE_COMMENTS
  (
    TERM_UNI_KEY      VARCHAR2 (20) ,
    TERM_UPDATE_COUNT INTEGER ,
    EXAMINE_COMMENT NVARCHAR2 (500) ,
    update_count     INTEGER NOT NULL ,
    create_by        VARCHAR2 (50) ,
    create_time      DATE ,
    last_update_by   VARCHAR2 (50) ,
    last_update_time DATE ,
    delete_by        VARCHAR2 (50) ,
    delete_time      DATE ,
    delete_flg       INTEGER
  ) ;
COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN TERM_EXAMINE_COMMENTS.delete_flg
IS
  '删除标记' ;
  CREATE TABLE datasource_set
    (
      datasource_id    VARCHAR2 (50) NOT NULL ,
      datasource_name  VARCHAR2 (100) NOT NULL ,
      jdbc_specific    VARCHAR2 (20) NOT NULL ,
      jdbc_url         VARCHAR2 (300) NOT NULL ,
      jdbc_user_name   VARCHAR2 (50) NOT NULL ,
      jdbc_password    VARCHAR2 (32) NOT NULL ,
      system_id        VARCHAR2 (32) NOT NULL ,
      update_count     INTEGER NOT NULL ,
      create_by        VARCHAR2 (50) ,
      create_time      DATE ,
      last_update_by   VARCHAR2 (50) ,
      last_update_time DATE ,
      delete_by        VARCHAR2 (50) ,
      delete_time      DATE ,
      delete_flg       INTEGER
    ) ;
  COMMENT ON COLUMN datasource_set.datasource_id
IS
  'id' ;
  COMMENT ON COLUMN datasource_set.datasource_name
IS
  '数据源名称' ;
  COMMENT ON COLUMN datasource_set.jdbc_specific
IS
  '数据库类型' ;
  COMMENT ON COLUMN datasource_set.jdbc_url
IS
  '数据库连接地址' ;
  COMMENT ON COLUMN datasource_set.jdbc_user_name
IS
  '用户名' ;
  COMMENT ON COLUMN datasource_set.jdbc_password
IS
  '密码' ;
  COMMENT ON COLUMN datasource_set.system_id
IS
  '提供系统ID' ;
  COMMENT ON COLUMN datasource_set.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN datasource_set.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN datasource_set.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN datasource_set.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN datasource_set.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN datasource_set.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN datasource_set.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN datasource_set.delete_flg
IS
  '删除标记' ;
  ALTER TABLE datasource_set ADD CONSTRAINT datasource_set_PK PRIMARY KEY
  (
    datasource_id
  )
  ;
  CREATE TABLE dict_field
    (
      field_id         VARCHAR2 (50 CHAR) NOT NULL ,
      dict_id          VARCHAR2 (50 CHAR) NOT NULL ,
      field_name       VARCHAR2 (50 CHAR) ,
      field_desc       VARCHAR2 (100 CHAR) ,
      filed_type       VARCHAR2 (50 CHAR) ,
      LENGTH           INTEGER ,
      PRECISION        INTEGER ,
      is_must          VARCHAR2 (1) ,
      is_primary       VARCHAR2 (1) ,
      is_show          VARCHAR2 (1) ,
      is_filter        VARCHAR2 (1) ,
      default_value    VARCHAR2 (50 CHAR) ,
      update_count     INTEGER NOT NULL ,
      create_by        VARCHAR2 (50) ,
      create_time      DATE ,
      last_update_by   VARCHAR2 (50) ,
      last_update_time DATE ,
      delete_by        VARCHAR2 (50) ,
      delete_time      DATE ,
      delete_flg       INTEGER
    ) ;
  COMMENT ON COLUMN dict_field.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN dict_field.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN dict_field.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN dict_field.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN dict_field.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN dict_field.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN dict_field.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN dict_field.delete_flg
IS
  '删除标记' ;
  ALTER TABLE dict_field ADD CONSTRAINT dict_field_PK PRIMARY KEY
  (
    field_id
  )
  ;
  CREATE TABLE dict_main
    (
      dict_id          VARCHAR2 (50 CHAR) NOT NULL ,
      dict_name        VARCHAR2 (100 CHAR) ,
      is_edit          VARCHAR2 (1) ,
      is_same          VARCHAR2 (1) ,
      status           VARCHAR2 (2) ,
      type_cd          VARCHAR2 (50 CHAR) ,
      table_name       VARCHAR2 (100 CHAR) ,
      item_version     INTEGER ,
      service_id       VARCHAR2 (50 CHAR) ,
      sys_id           VARCHAR2 (50 CHAR) ,
      lock_status      INTEGER DEFAULT 0 NOT NULL ,
      update_count     INTEGER NOT NULL ,
      create_by        VARCHAR2 (50) ,
      create_time      DATE ,
      last_update_by   VARCHAR2 (50) ,
      last_update_time DATE ,
      delete_by        VARCHAR2 (50) ,
      delete_time      DATE ,
      delete_flg       INTEGER
    ) ;
  COMMENT ON COLUMN dict_main.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN dict_main.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN dict_main.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN dict_main.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN dict_main.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN dict_main.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN dict_main.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN dict_main.delete_flg
IS
  '删除标记' ;
  ALTER TABLE dict_main ADD CONSTRAINT dict_main_PK PRIMARY KEY
  (
    dict_id
  )
  ;
  CREATE TABLE sync_dict
    (
      sync_id             VARCHAR2 (50) NOT NULL ,
      dict_id             VARCHAR2 (50) NOT NULL ,
      dict_name           VARCHAR2 (100) NOT NULL ,
      dict_table_name     VARCHAR2 (100) NOT NULL ,
      dict_status         INTEGER NOT NULL ,
      sync_status         INTEGER NOT NULL ,
      sync_interval       INTEGER ,
      datasource_id       VARCHAR2 (50) NOT NULL ,
      start_time          DATE NOT NULL ,
      end_time            DATE NOT NULL ,
      sync_result         INTEGER NOT NULL ,
      log_txt             VARCHAR2 (4000) ,
      from_datasource_sql VARCHAR2 (4000) NOT NULL ,
      to_datasource_sql   VARCHAR2 (4000) NOT NULL ,
      update_count        INTEGER NOT NULL ,
      create_by           VARCHAR2 (50) ,
      create_time         DATE ,
      last_update_by      VARCHAR2 (50) ,
      last_update_time    DATE ,
      delete_by           VARCHAR2 (50) ,
      delete_time         DATE ,
      delete_flg          INTEGER
    ) ;
  COMMENT ON COLUMN sync_dict.dict_name
IS
  '术语名称' ;
  COMMENT ON COLUMN sync_dict.dict_table_name
IS
  '术语表名' ;
  COMMENT ON COLUMN sync_dict.dict_status
IS
  '字典状态 0：未启用；1：已启用' ;
  COMMENT ON COLUMN sync_dict.sync_status
IS
  '同步状态 0：未开始；1：等待中；2：执行中' ;
  COMMENT ON COLUMN sync_dict.sync_interval
IS
  '同步间隔时间' ;
  COMMENT ON COLUMN sync_dict.start_time
IS
  '同步开始时间' ;
  COMMENT ON COLUMN sync_dict.end_time
IS
  '同步结束时间' ;
  COMMENT ON COLUMN sync_dict.sync_result
IS
  '同步结果 0：失败；1；成功' ;
  COMMENT ON COLUMN sync_dict.log_txt
IS
  '失败原因' ;
  COMMENT ON COLUMN sync_dict.from_datasource_sql
IS
  '源数据源SQL' ;
  COMMENT ON COLUMN sync_dict.to_datasource_sql
IS
  '目标数据源SQL' ;
  COMMENT ON COLUMN sync_dict.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN sync_dict.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN sync_dict.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN sync_dict.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN sync_dict.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN sync_dict.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN sync_dict.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN sync_dict.delete_flg
IS
  '删除标记' ;
  ALTER TABLE sync_dict ADD CONSTRAINT sync_dict_PK PRIMARY KEY
  (
    dict_id
  )
  ;
  CREATE TABLE sync_log
    (
      log_id           VARCHAR2 (50) NOT NULL ,
      dict_name        VARCHAR2 (100) NOT NULL ,
      start_time       DATE NOT NULL ,
      end_time         DATE NOT NULL ,
      sync_result      INTEGER NOT NULL ,
      log_txt          VARCHAR2 (4000) ,
      update_count     INTEGER NOT NULL ,
      create_by        VARCHAR2 (50) ,
      create_time      DATE ,
      last_update_by   VARCHAR2 (50) ,
      last_update_time DATE ,
      delete_by        VARCHAR2 (50) ,
      delete_time      DATE ,
      delete_flg       INTEGER ,
      dict_id          VARCHAR2 (50) NOT NULL
    ) ;
  COMMENT ON COLUMN sync_log.log_id
IS
  '逻辑ID' ;
  COMMENT ON COLUMN sync_log.dict_name
IS
  '术语名称' ;
  COMMENT ON COLUMN sync_log.start_time
IS
  '同步开始时间' ;
  COMMENT ON COLUMN sync_log.end_time
IS
  '同步结束时间' ;
  COMMENT ON COLUMN sync_log.sync_result
IS
  '同步结果 0：失败；1；成功' ;
  COMMENT ON COLUMN sync_log.log_txt
IS
  '失败原因' ;
  COMMENT ON COLUMN sync_log.update_count
IS
  '更新次数' ;
  COMMENT ON COLUMN sync_log.create_by
IS
  '创建者' ;
  COMMENT ON COLUMN sync_log.create_time
IS
  '创建时间' ;
  COMMENT ON COLUMN sync_log.last_update_by
IS
  '更新者' ;
  COMMENT ON COLUMN sync_log.last_update_time
IS
  '更新时间' ;
  COMMENT ON COLUMN sync_log.delete_by
IS
  '删除者' ;
  COMMENT ON COLUMN sync_log.delete_time
IS
  '删除时间' ;
  COMMENT ON COLUMN sync_log.delete_flg
IS
  '删除标记' ;
  ALTER TABLE sync_log ADD CONSTRAINT sync_log_PK PRIMARY KEY
  (
    log_id
  )
  ;
  ALTER TABLE dict_field ADD CONSTRAINT dict_field_dict_main_FK FOREIGN KEY
  (
    dict_id
  )
  REFERENCES dict_main
  (
    dict_id
  )
  ;
  ALTER TABLE sync_dict ADD CONSTRAINT sync_dict_datasource_set_FK FOREIGN KEY
  (
    datasource_id
  )
  REFERENCES datasource_set
  (
    datasource_id
  )
  ;
  ALTER TABLE sync_log ADD CONSTRAINT sync_log_sync_dict_FK FOREIGN KEY
  (
    dict_id
  )
  REFERENCES sync_dict
  (
    dict_id
  )
  ;
