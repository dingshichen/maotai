alter table user modify updated_time datetime not null default current_timestamp comment '最新修改时间';