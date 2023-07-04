create table if not exists car (
   id bigint primary key comment '主键ID',
   user_name varchar(32) not null default '' comment '汽车名称',
   number varchar(16) not null comment '编号',
   color varchar(16) comment '颜色',
   cut int not null default 0,
   create_time datetime default current_timestamp comment '生产时间',
   unique key uniq_user_name (user_name),
   index idx_color (color)
) comment '汽车表';

alter table car add key idx_create_time (create_time);
alter table car add index idx_number (number);
alter table car add unique uniq_color_cnt (color, cut);