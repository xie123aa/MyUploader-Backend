drop table if exists fileMeta;

create table fileMeta(
  id bigint auto_increment ,
  name varchar(100) not null ,
  md5 varchar(32) ,
  path varchar(100) not null ,
  upload_time datetime(3) not null ,
  primary key (id)
);
