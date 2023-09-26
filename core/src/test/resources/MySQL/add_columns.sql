alter table user add column update_time datetime not null default current_timestamp comment '最新修改时间',
    add column end_time datetime not null default current_timestamp comment '最后时间';

alter table std_obj
    add column rspnsbl_dept_id bigint comment '责任部门ID';