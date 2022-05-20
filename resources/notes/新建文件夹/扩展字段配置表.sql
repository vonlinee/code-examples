DELETE FROM `t_sac_field_mapping_config` WHERE source_table_code = 't_sac_basis_log'

insert into t_sac_field_mapping_config(id,source_table_code,bill_type,bill_type_name,business_type,business_type_name,filed_type,db_field_code,filed_code,filed_name,MAPPING_FILED_CODE,OEM_ID,GROUP_ID,CREATOR,CREATED_NAME,CREATED_DATE,MODIFIER,MODIFY_NAME,LAST_UPDATED_DATE,UPDATE_CONTROL_ID) values(uuid(),'t_sac_basis_log','-1','全部','-1','全部','2','EXTENDS_JSON','sendStatus','发送状态','sendStatus','1','1','SYS','SYS',now(),'SYS','SYS',now(),uuid());
insert into t_sac_field_mapping_config(id,source_table_code,bill_type,bill_type_name,business_type,business_type_name,filed_type,db_field_code,filed_code,filed_name,MAPPING_FILED_CODE,OEM_ID,GROUP_ID,CREATOR,CREATED_NAME,CREATED_DATE,MODIFIER,MODIFY_NAME,LAST_UPDATED_DATE,UPDATE_CONTROL_ID) values(uuid(),'t_sac_basis_log','-1','全部','-1','全部','2','EXTENDS_JSON','errors','错误信息','errors','1','1','SYS','SYS',now(),'SYS','SYS',now(),uuid());
insert into t_sac_field_mapping_config(id,source_table_code,bill_type,bill_type_name,business_type,business_type_name,filed_type,db_field_code,filed_code,filed_name,MAPPING_FILED_CODE,OEM_ID,GROUP_ID,CREATOR,CREATED_NAME,CREATED_DATE,MODIFIER,MODIFY_NAME,LAST_UPDATED_DATE,UPDATE_CONTROL_ID) values(uuid(),'t_sac_basis_log','-1','全部','-1','全部','2','EXTENDS_JSON','response','响应内容','response','1','1','SYS','SYS',now(),'SYS','SYS',now(),uuid());
insert into t_sac_field_mapping_config(id,source_table_code,bill_type,bill_type_name,business_type,business_type_name,filed_type,db_field_code,filed_code,filed_name,MAPPING_FILED_CODE,OEM_ID,GROUP_ID,CREATOR,CREATED_NAME,CREATED_DATE,MODIFIER,MODIFY_NAME,LAST_UPDATED_DATE,UPDATE_CONTROL_ID) values(uuid(),'t_sac_basis_log','-1','全部','-1','全部','2','EXTENDS_JSON','message','发送信息','message','1','1','SYS','SYS',now(),'SYS','SYS',now(),uuid());
insert into t_sac_field_mapping_config(id,source_table_code,bill_type,bill_type_name,business_type,business_type_name,filed_type,db_field_code,filed_code,filed_name,MAPPING_FILED_CODE,OEM_ID,GROUP_ID,CREATOR,CREATED_NAME,CREATED_DATE,MODIFIER,MODIFY_NAME,LAST_UPDATED_DATE,UPDATE_CONTROL_ID) values(uuid(),'t_sac_basis_log','-1','全部','-1','全部','2','EXTENDS_JSON','requestBody','短信接口参数','requestBody','1','1','SYS','SYS',now(),'SYS','SYS',now(),uuid());
