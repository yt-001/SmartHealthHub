-- 处方表
CREATE TABLE `prescriptions` (
  `id` bigint NOT NULL COMMENT '编号',
  `medical_record_id` bigint DEFAULT NULL COMMENT '关联病历ID',
  `user_id` bigint NOT NULL COMMENT '关联患者ID',
  `doctor_id` bigint NOT NULL COMMENT '关联医生ID',
  `patient_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '患者姓名快照',
  `doctor_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '医生姓名快照',
  `diagnosis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '诊断信息',
  `medication_details` json DEFAULT NULL COMMENT '处方详情(JSON)',
  `notes` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '医嘱/备注',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待取药, 1-已完成, 2-已作废',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_doctor_id` (`doctor_id`),
  KEY `idx_medical_record_id` (`medical_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='处方表';
