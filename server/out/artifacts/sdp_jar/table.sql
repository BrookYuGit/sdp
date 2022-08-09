drop table sdp_history;
drop table sdp_workspace;
drop table sdp_workspace_config;
drop table sdp_project;
drop table sdp_table;
drop table sdp_template;
drop table sdp_sql;


CREATE TABLE sdp_history (
  id int(11) NOT NULL AUTO_INCREMENT,
  workspace_name varchar(45) DEFAULT NULL,
  table_name varchar(45) DEFAULT NULL,
  content text,
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sdp_project (
  id int(11) NOT NULL AUTO_INCREMENT,
  workspace_name varchar(45) DEFAULT NULL,
  name varchar(200) NOT NULL,
  root_path varchar(100) DEFAULT NULL,
  tables text,
  remark text,
  extra_info text,
  PRIMARY KEY (id),
  UNIQUE KEY unique_project (workspace_name,name)
);

CREATE TABLE sdp_table (
  id int(11) NOT NULL AUTO_INCREMENT,
  workspace_name varchar(45) DEFAULT NULL,
  name varchar(200) NOT NULL,
  alias_name varchar(100) DEFAULT NULL,
  remark text,
  extra_info text,
  PRIMARY KEY (id),
  UNIQUE KEY unique_table (workspace_name,name)
);

CREATE TABLE sdp_sql (
  id int(11) NOT NULL AUTO_INCREMENT,
  is_disable int(11) NOT NULL DEFAULT '0',
  workspace_name varchar(45) NOT NULL,
  table_name varchar(45) NOT NULL,
  parameter_catalog varchar(255) NOT NULL DEFAULT '',
  parameter_catalog_type varchar(100) NOT NULL DEFAULT '',
  name varchar(45) NOT NULL DEFAULT '',
  java_type varchar(255) NOT NULL DEFAULT '',
  remarks varchar(256) NOT NULL DEFAULT '',
  parameter_mode varchar(45) NOT NULL DEFAULT 'append',
  parameter_nullable int(11) DEFAULT NULL,
  parameter_is_like int(11) DEFAULT NULL,
  parameter_is_import_excel int(11) DEFAULT NULL,
  parameter_is_export_excel int(11) DEFAULT NULL,
  parameter_sql_value_item varchar(255) DEFAULT NULL,
  parameter_sql_value varchar(255) NOT NULL DEFAULT '',
  parameter_sql_value_ignore int(11) DEFAULT NULL,
  parameter_without_test int(11) DEFAULT NULL,
  parameter_sql text,
  parameter_sql_issimple int(11) NOT NULL DEFAULT '0',
  parameter_sql_return_nolist int(11) NOT NULL DEFAULT '0',
  java_imports varchar(1024) NOT NULL DEFAULT '',
  parameter_overwrite_default_sql int(11) DEFAULT NULL,
  java_return_type varchar(255) NOT NULL DEFAULT '',
  java_body text,
  sort_no int(11) NOT NULL DEFAULT '0',
  remark varchar(200) DEFAULT NULL,
  is_interface int(11) DEFAULT NULL,
  extra_info text,
  PRIMARY KEY (id),
  UNIQUE KEY unique_sql (workspace_name,table_name,parameter_catalog,parameter_catalog_type,name)
);

CREATE TABLE sdp_template (
  id int(11) NOT NULL AUTO_INCREMENT,
  workspace_name varchar(45) DEFAULT NULL,
  project_name varchar(45) DEFAULT NULL,
  name varchar(200) NOT NULL,
  file_type varchar(45) DEFAULT NULL,
  project varchar(200) DEFAULT NULL,
  package_name varchar(200) DEFAULT NULL,
  file_template text,
  no_overwrite int(11) DEFAULT NULL,
  extra_info text,
  remark varchar(200) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_template (workspace_name,project_name, name,file_type,project,package_name)
);

CREATE TABLE sdp_workspace (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  root_path varchar(100) DEFAULT NULL,
  db_host varchar(100) DEFAULT NULL,
  db_port int(11) DEFAULT NULL,
  db_database varchar(200) DEFAULT NULL,
  db_username varchar(45) DEFAULT NULL,
  db_password varchar(200) DEFAULT NULL,
  db_classname varchar(200) DEFAULT NULL,
  remark varchar(200) DEFAULT NULL,
  extra_info text,
  PRIMARY KEY (id),
  UNIQUE KEY unique_workspace (name)
);

CREATE TABLE sdp_workspace_config (
  id int(11) NOT NULL AUTO_INCREMENT,
  workspace_name varchar(45) NOT NULL,
  name varchar(100) NOT NULL,
  value varchar(200) NOT NULL,
  remark varchar(200) DEFAULT NULL,
  extra_info text,
  PRIMARY KEY (id),
  UNIQUE KEY unique_workspace_config (workspace_name, name)
);
