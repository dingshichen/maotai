create table if not exists car (
   id bigint primary key comment '主键ID',
   name varchar(32) not null default '' comment '汽车名称',
   content varchar(32) default '' comment '内容',
   create_time datetime null default current_timestamp comment '生产时间'
) comment '汽车表';