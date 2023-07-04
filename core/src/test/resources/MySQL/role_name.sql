CREATE TABLE `std_example` (
                               `std_example_id` bigint NOT NULL COMMENT '标准示例ID',
                               `std_arch_id` bigint NOT NULL COMMENT '标准体系结构ID',
                               `obj_typ_id` bigint NOT NULL COMMENT '对象类型ID',
                               `std_example_nm` varchar(256) NOT NULL COMMENT '标准示例名称',
                               `crt_usr_id` bigint NOT NULL COMMENT '创建用户ID',
                               `crt_tm` datetime NOT NULL COMMENT '创建时间',
                               `latest_update_usr_id` bigint NOT NULL COMMENT '最近更新用户ID',
                               `latest_update_tm` datetime NOT NULL COMMENT '最近更新时间',
                               `is_deleted` int DEFAULT '0' COMMENT '是否已删除',
                               PRIMARY KEY (`std_example_id`,`std_arch_id`,`obj_typ_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标准示例';