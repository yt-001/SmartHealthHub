USE SmartHealthHub;

CREATE TABLE IF NOT EXISTS `pharmacy_locations` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '地点名称',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `opening_hours` varchar(100) DEFAULT NULL COMMENT '营业时间',
  `is_enabled` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用：0 否 1 是',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取药地点表';

CREATE TABLE IF NOT EXISTS `medicine_orders` (
  `id` bigint NOT NULL COMMENT '主键',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `medicine_id` bigint NOT NULL COMMENT '药品ID',
  `medicine_name` varchar(255) NOT NULL COMMENT '药品名称快照',
  `price` decimal(10,2) NOT NULL COMMENT '价格快照',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待支付, 1-已支付, 2-已取药, 3-已取消',
  `pharmacy_location_id` bigint DEFAULT NULL COMMENT '取药地点ID',
  `payment_method` varchar(20) DEFAULT NULL COMMENT '支付方式: wechat, alipay',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药品订单表';

-- 插入测试数据，先检查是否已存在避免重复插入报错
INSERT IGNORE INTO `pharmacy_locations` (`id`, `name`, `address`, `phone`, `opening_hours`, `is_enabled`, `sort_order`, `created_at`, `updated_at`) VALUES
(1800000000000000001, '社区卫生服务中心药房', '建设路88号一楼大厅', '0830-1234567', '08:00-18:00', 1, 1, NOW(), NOW()),
(1800000000000000002, '第二门诊部取药点', '人民南路12号', '0830-7654321', '08:30-17:30', 1, 2, NOW(), NOW());
