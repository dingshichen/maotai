create table if not exists car (
       id bigint primary key comment '主键ID',
       user_name varchar(32) not null default '' comment '汽车名称',
       create_time datetime default current_timestamp comment '生产时间'
) comment '汽车表';

create table if not exists User (
       id bigint primary key comment '主键ID',
       name varchar(32) not null default '' comment '用户名称',
       create_time datetime null default current_timestamp comment '创建时间'
) comment '用户表';

alter table user add column age int not null default 0 comment '年龄';

alter table user add column update_time datetime not null default current_timestamp comment '最新修改时间',
                 add column end_time datetime not null comment '最后时间';

alter table user modify update_time datetime not null default current_timestamp comment '最新修改时间';