-- ------------------------------------------------------
-- SmartHealthHub 药品相关表（MySQL 8.x）
-- ------------------------------------------------------
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 药品分类（支持父子层级）
DROP TABLE IF EXISTS `medicine_categories`;
CREATE TABLE `medicine_categories` (
  `id` bigint NOT NULL COMMENT '主键，雪花ID',
  `parent_id` bigint DEFAULT NULL COMMENT '父分类ID（为空表示一级分类）',
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `description` varchar(200) DEFAULT NULL COMMENT '分类描述',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序',
  `is_enabled` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用：0 否 1 是',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_medicine_categories_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品分类表（支持父子层级）';

-- 药品主表（含客户端所需基础与详情字段）
DROP TABLE IF EXISTS `medicines`;
CREATE TABLE `medicines` (
  `id` bigint NOT NULL COMMENT '主键，雪花ID',
  `name` varchar(100) NOT NULL COMMENT '药品名称（展示名）',
  `common_name` varchar(100) DEFAULT NULL COMMENT '通用名称',
  `brand_name` varchar(100) DEFAULT NULL COMMENT '品牌/商品名',
  `description` varchar(500) DEFAULT NULL COMMENT '简介摘要（用于卡片文案）',
  `cover_image_url` varchar(255) DEFAULT NULL COMMENT '封面图片URL',
  `images` json DEFAULT NULL COMMENT '图片列表JSON数组',
  `specs` json DEFAULT NULL COMMENT '规格列表JSON数组（如“10包/盒”）',
  `tags` json DEFAULT NULL COMMENT '标签JSON数组',
  `recommendation_level` varchar(50) DEFAULT NULL COMMENT '推荐级别文案（如“首选推荐”）',
  `is_prescription` tinyint NOT NULL DEFAULT '0' COMMENT '是否处方药：0 否（OTC） 1 是',
  `price` decimal(10,2) NOT NULL COMMENT '现价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `sales` int NOT NULL DEFAULT '0' COMMENT '月售/销量',
  `rating` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '好评率百分比（0-100）',
  `indications` text COMMENT '适应症',
  `functions` text COMMENT '功能主治',
  `dosage` text COMMENT '用法用量',
  `adverse_reactions` text COMMENT '不良反应',
  `contraindications` text COMMENT '禁忌',
  `precautions` text COMMENT '注意事项',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0 隐藏 1 显示',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除：0 正常 1 已删',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_medicines_name` (`name`),
  KEY `idx_medicines_status` (`status`),
  KEY `idx_medicines_is_prescription` (`is_prescription`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品信息主表（含客户端详情字段）';

-- 药品分类关联表
DROP TABLE IF EXISTS `medicine_category_relations`;
CREATE TABLE `medicine_category_relations` (
  `id` bigint NOT NULL COMMENT '主键，雪花ID',
  `medicine_id` bigint NOT NULL COMMENT '药品ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_medicine_category` (`medicine_id`,`category_id`),
  KEY `idx_mcr_medicine_id` (`medicine_id`),
  KEY `idx_mcr_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品与分类的关联表';

-- 预置分类（一级 + 二级）
LOCK TABLES `medicine_categories` WRITE;
/*!40000 ALTER TABLE `medicine_categories` DISABLE KEYS */;
INSERT INTO `medicine_categories` (`id`,`parent_id`,`name`,`description`,`sort_order`,`is_enabled`,`created_at`,`updated_at`) VALUES
(3001,NULL,'中药调理','传统中医药调理类',1,1,NOW(),NOW()),
(3002,NULL,'西药推荐','常见西药与处方药',2,1,NOW(),NOW()),
(3003,NULL,'营养保健','维生素与营养补充',3,1,NOW(),NOW()),
-- 中药调理子类
(3011,3001,'气血亏虚','气血不足、面色萎黄、心悸失眠',1,1,NOW(),NOW()),
(3012,3001,'脾胃虚弱','消化不良、食少便溏、倦怠乏力',2,1,NOW(),NOW()),
(3013,3001,'清热解毒','咽喉肿痛、口舌生疮等',3,1,NOW(),NOW()),
-- 西药推荐子类
(3021,3002,'感冒流感','退热止痛、抗病毒',1,1,NOW(),NOW()),
(3022,3002,'消炎镇痛','非甾体类、抗生素等',2,1,NOW(),NOW()),
(3023,3002,'消化系统','胃药、止泻止吐等',3,1,NOW(),NOW()),
-- 营养保健子类
(3031,3003,'维生素补充','维C/维D等基础补充',1,1,NOW(),NOW()),
(3032,3003,'增强免疫','提高机体免疫力',2,1,NOW(),NOW()),
(3033,3003,'骨骼健康','补钙、维生素D、骨密度提升',3,1,NOW(),NOW());
/*!40000 ALTER TABLE `medicine_categories` ENABLE KEYS */;
UNLOCK TABLES;

-- 模拟50个药品数据
LOCK TABLES `medicines` WRITE;
/*!40000 ALTER TABLE `medicines` DISABLE KEYS */;
INSERT INTO `medicines`
(`id`,`name`,`common_name`,`brand_name`,`description`,`cover_image_url`,`images`,`specs`,`tags`,`recommendation_level`,`is_prescription`,`price`,`original_price`,`sales`,`rating`,`indications`,`functions`,`dosage`,`adverse_reactions`,`contraindications`,`precautions`,`status`,`is_deleted`,`created_at`,`updated_at`)
VALUES
(50001,'八珍颗粒','八珍颗粒',NULL,'补气养血，面色萎黄、食欲不振','https://picsum.photos/seed/50001/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50001a/800/600','https://picsum.photos/seed/50001b/800/600'),
 JSON_ARRAY('10包/盒','15包/盒'), JSON_ARRAY('补气','养血'),'经典方剂',0,45.00,52.00,1200,98.50,
 '用于气血两虚所致的面色萎黄、头晕心悸、食欲不振。','补气养血，健脾安神。','口服，一次1包，一日2次。','偶见胃部不适、轻微皮疹。','对成分过敏者禁用。','孕妇及哺乳期慎用，遵医嘱。',1,0,NOW(),NOW()),
(50002,'归脾丸','归脾丸',NULL,'益气健脾，养血安神','https://picsum.photos/seed/50002/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50002a/800/600'),
 JSON_ARRAY('200丸/瓶'), JSON_ARRAY('安神','健脾'),'家庭常备',0,32.50,38.00,950,97.20,
 '用于心脾两虚所致的失眠、健忘、心悸。','益气健脾，养血安神。','口服，一次10丸，一日2次。','罕见过敏反应。','糖尿病患者慎用。','服用期间忌辛辣油腻。',1,0,NOW(),NOW()),
(50003,'香砂六君丸','香砂六君丸',NULL,'健脾和胃，消化不良','https://picsum.photos/seed/50003/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50003a/800/600'),
 JSON_ARRAY('6g*10袋'), JSON_ARRAY('健脾','和胃'),'家庭常备',0,28.00,33.00,620,96.10,
 '用于脾虚气滞所致的消化不良、腹胀。','健脾益气，理气和胃。','口服，一次1袋，一日2次。','偶见腹部轻微不适。','对成分过敏者禁用。','儿童应在医师指导下使用。',1,0,NOW(),NOW()),
(50004,'板蓝根颗粒','板蓝根颗粒',NULL,'清热解毒，咽喉肿痛','https://picsum.photos/seed/50004/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50004a/800/600'),
 JSON_ARRAY('10g*10袋'), JSON_ARRAY('清热','解毒'),'快速缓解',0,18.90,22.00,3000,95.00,
 '用于风热感冒、咽喉肿痛。','清热解毒，凉血。','口服，一次1袋，一日3次。','可能出现胃部不适。','肝肾功能不全者慎用。','避免与滋补性中药同服。',1,0,NOW(),NOW()),
(50005,'连花清瘟胶囊','连花清瘟胶囊',NULL,'抗病毒，缓解感冒症状','https://picsum.photos/seed/50005/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50005a/800/600'),
 JSON_ARRAY('24粒/盒'), JSON_ARRAY('清热','宣肺'),'热点',0,32.00,36.00,4100,93.80,
 '用于流感引起的发热、咳嗽、乏力。','清瘟解毒，宣肺泄热。','口服，一次2粒，一日3次。','偶见胃肠道不适。','孕妇禁用。','服药期间注意休息与补水。',1,0,NOW(),NOW()),
(50006,'感冒灵颗粒','感冒灵颗粒',NULL,'缓解感冒头痛、鼻塞','https://picsum.photos/seed/50006/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50006a/800/600'),
 JSON_ARRAY('10g*12袋'), JSON_ARRAY('感冒','止痛'),'快速起效',0,22.80,26.00,2850,94.30,
 '用于普通感冒的头痛、鼻塞、咽痛。','疏风散寒，解表止痛。','口服，一次1袋，一日3次。','偶见嗜睡。','高血压患者慎用。','严遵用量，勿与酒精同服。',1,0,NOW(),NOW()),
(50007,'布洛芬缓释胶囊','布洛芬',NULL,'解热镇痛，缓解炎症','https://picsum.photos/seed/50007/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50007a/800/600'),
 JSON_ARRAY('0.3g*20粒'), JSON_ARRAY('镇痛','退热'),'首选推荐',0,29.90,35.00,5200,92.60,
 '用于轻中度疼痛、发热。','解热镇痛，抗炎。','口服，一次1粒，一日2次，饭后服用。','可能出现胃黏膜刺激。','消化道溃疡者禁用。','避免与其他NSAIDs同用。',1,0,NOW(),NOW()),
(50008,'对乙酰氨基酚片','对乙酰氨基酚',NULL,'退热止痛，安全普及','https://picsum.photos/seed/50008/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50008a/800/600'),
 JSON_ARRAY('0.5g*20片'), JSON_ARRAY('退热','止痛'),'家庭常备',0,16.50,20.00,6100,95.70,
 '用于发热、头痛、牙痛。','解热镇痛。','口服，一次1片，一日不超过4次。','过量可致肝损伤。','肝病患者禁用。','严控用量，避免与酒精同服。',1,0,NOW(),NOW()),
(50009,'阿莫西林胶囊','阿莫西林',NULL,'广谱抗生素，细菌感染','https://picsum.photos/seed/50009/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50009a/800/600'),
 JSON_ARRAY('0.25g*24粒'), JSON_ARRAY('抗感染'),'医生指导',1,23.80,28.00,2400,91.20,
 '用于敏感菌引起的感染。','抑菌杀菌。','口服，一次2粒，一日3次，遵医嘱。','可能出现过敏反应。','青霉素过敏者禁用。','严格遵医嘱完成疗程。',1,0,NOW(),NOW()),
(50010,'奥美拉唑肠溶胶囊','奥美拉唑',NULL,'抑酸护胃，缓解烧心反酸','https://picsum.photos/seed/50010/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50010a/800/600'),
 JSON_ARRAY('20mg*14粒'), JSON_ARRAY('护胃','抑酸'),'首选推荐',1,35.00,42.00,1800,93.20,
 '用于胃食管反流、消化性溃疡。','抑制胃酸分泌。','口服，一次1粒，一日1次，早餐前服用。','偶见腹胀、头痛。','严重肝病者慎用。','疗程结束后如仍不适请就医。',1,0,NOW(),NOW()),
(50011,'蒙脱石散','蒙脱石',NULL,'止泻止吐，肠胃不适','https://picsum.photos/seed/50011/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50011a/800/600'),
 JSON_ARRAY('3g*10袋'), JSON_ARRAY('止泻','护肠'),'家庭常备',0,19.80,23.00,3200,96.00,
 '用于急性腹泻、肠胃炎。','吸附毒素，保护黏膜。','口服，一次1袋，一日2-3次。','偶见便秘。','肠梗阻者禁用。','多饮水，清淡饮食。',1,0,NOW(),NOW()),
(50012,'维生素C片','维C片',NULL,'抗氧化，提升免疫','https://picsum.photos/seed/50012/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50012a/800/600'),
 JSON_ARRAY('100mg*100片'), JSON_ARRAY('维生素','免疫'),'基础补充',0,12.90,15.00,5400,97.50,
 '用于维生素C缺乏。','补充维生素C。','口服，一次1-2片，一日1次。','过量可致胃部不适。','肾结石患者慎用。','配合均衡饮食。',1,0,NOW(),NOW()),
(50013,'维生素D滴剂','维D',NULL,'促进钙吸收，骨骼健康','https://picsum.photos/seed/50013/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50013a/800/600'),
 JSON_ARRAY('400IU*60粒'), JSON_ARRAY('骨骼','维生素'),'骨骼健康',0,29.50,35.00,2100,96.80,
 '用于维生素D补充。','促进钙吸收。','口服，按说明滴服。','过量可能致高钙血症。','高钙血症者禁用。','遵循用量建议。',1,0,NOW(),NOW()),
(50014,'钙片','碳酸钙',NULL,'补钙强骨，预防骨质疏松','https://picsum.photos/seed/50014/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50014a/800/600'),
 JSON_ARRAY('600mg*60片'), JSON_ARRAY('补钙','骨骼'),'骨骼健康',0,39.90,46.00,2600,95.40,
 '用于钙补充。','补钙强骨。','口服，一次1片，一日1-2次。','偶见便秘。','高钙血症者禁用。','搭配维生素D效果更佳。',1,0,NOW(),NOW()),
(50015,'葡萄糖酸锌口服液','葡萄糖酸锌',NULL,'促进食欲，提升免疫','https://picsum.photos/seed/50015/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50015a/800/600'),
 JSON_ARRAY('10ml*12支'), JSON_ARRAY('补锌','免疫'),'儿童适用',0,26.50,30.00,1900,94.10,
 '用于锌缺乏。','补锌。','口服，一次1支，一日1-2次。','偶见恶心。','肝病患者慎用。','按年龄与体重调整用量。',1,0,NOW(),NOW()),
(50016,'鱼油软胶囊','深海鱼油',NULL,'心血管健康，调节血脂','https://picsum.photos/seed/50016/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50016a/800/600'),
 JSON_ARRAY('1000mg*60粒'), JSON_ARRAY('心血管','营养'),'成人保健',0,59.00,68.00,1100,92.80,
 '用于心血管健康维护。','提供Omega-3。','口服，一次1粒，一日1-2次。','可能出现鱼腥回味。','海鲜过敏者慎用。','随餐服用减少不适。',1,0,NOW(),NOW()),
(50017,'辅酶Q10','辅酶Q10',NULL,'提升心肌能量，抗氧化','https://picsum.photos/seed/50017/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50017a/800/600'),
 JSON_ARRAY('30mg*60粒'), JSON_ARRAY('心肌','抗氧化'),'成人保健',0,88.00,99.00,900,93.10,
 '用于心肌能量支持。','抗氧化。','口服，一次1粒，一日1次。','少见不适。','孕期慎用。','遵医嘱使用。',1,0,NOW(),NOW()),
(50018,'益生菌粉','益生菌',NULL,'调节肠道菌群，改善消化','https://picsum.photos/seed/50018/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50018a/800/600'),
 JSON_ARRAY('2g*20袋'), JSON_ARRAY('肠道','消化'),'基础补充',0,49.00,56.00,2300,95.80,
 '用于肠道功能紊乱。','调节菌群。','口服，一次1袋，一日1次。','偶见腹胀。','免疫低下者慎用。','冷水冲服，避免高温。',1,0,NOW(),NOW()),
(50019,'乳酸菌片','乳酸菌',NULL,'改善肠道环境，助消化','https://picsum.photos/seed/50019/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50019a/800/600'),
 JSON_ARRAY('0.5g*60片'), JSON_ARRAY('肠道','益生'),'基础补充',0,24.50,28.00,1750,95.20,
 '用于消化不良。','助消化。','口服，一次2片，一日2次。','偶见腹泻。','严重腹泻者慎用。','配合清淡饮食。',1,0,NOW(),NOW()),
(50020,'感冒药水','复方药水',NULL,'缓解咳嗽、咽痛','https://picsum.photos/seed/50020/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50020a/800/600'),
 JSON_ARRAY('120ml/瓶'), JSON_ARRAY('止咳','化痰'),'快速起效',0,21.00,26.00,2600,93.50,
 '用于感冒引起的咳嗽。','止咳化痰。','口服，按说明书每次10ml，一日3次。','可能嗜睡。','驾驶人士慎用。','服用期间避免酒精。',1,0,NOW(),NOW()),
(50021,'感冒清片','复方清热片',NULL,'清热解毒，退热止痛','https://picsum.photos/seed/50021/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50021a/800/600'),
 JSON_ARRAY('24片/盒'), JSON_ARRAY('清热','退热'),'家庭常备',0,17.80,22.00,2100,94.60,
 '用于风热感冒。','清热解毒。','口服，一次2片，一日3次。','偶见胃部不适。','肝肾功能不全者慎用。','遵守用量。',1,0,NOW(),NOW()),
(50022,'维生素B族','维生素B复合',NULL,'缓解疲劳，提升代谢','https://picsum.photos/seed/50022/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50022a/800/600'),
 JSON_ARRAY('60片/瓶'), JSON_ARRAY('维生素','代谢'),'基础补充',0,36.00,42.00,1400,96.40,
 '用于维生素B缺乏。','促进代谢。','口服，一次1片，一日1次。','偶见恶心。','孕期慎用。','随餐服用更佳。',1,0,NOW(),NOW()),
(50023,'清喉利咽颗粒','清喉利咽',NULL,'咽喉肿痛，声音嘶哑','https://picsum.photos/seed/50023/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50023a/800/600'),
 JSON_ARRAY('6g*10袋'), JSON_ARRAY('清喉','利咽'),'热点',0,25.50,30.00,1850,94.90,
 '用于咽喉炎症。','清喉利咽。','口服，一次1袋，一日2次。','偶见口干。','儿童慎用。','避免辛辣刺激。',1,0,NOW(),NOW()),
(50024,'藿香正气水','藿香正气',NULL,'暑湿感冒，腹泻呕吐','https://picsum.photos/seed/50024/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50024a/800/600'),
 JSON_ARRAY('10ml*12支'), JSON_ARRAY('解暑','和中'),'家庭常备',0,19.00,22.00,2750,93.40,
 '用于暑湿感冒、腹泻。','解表化湿，理气和中。','口服，一次10ml，一日2次。','偶见皮疹。','酒精过敏者慎用。','避光保存。',1,0,NOW(),NOW()),
(50025,'清凉油','复方清凉油',NULL,'提神止痒，缓解蚊虫叮咬','https://picsum.photos/seed/50025/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50025a/800/600'),
 JSON_ARRAY('10g/盒'), JSON_ARRAY('清凉','止痒'),'夏季常备',0,12.00,15.00,3800,97.10,
 '用于蚊虫叮咬止痒。','清凉止痒。','外用，涂抹患处。','可能皮肤刺激。','皮肤破损处禁用。','避免接触眼睛。',1,0,NOW(),NOW()),
(50026,'维生素E软胶囊','维E',NULL,'抗氧化，美容护肤','https://picsum.photos/seed/50026/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50026a/800/600'),
 JSON_ARRAY('100mg*60粒'), JSON_ARRAY('抗氧化','美容'),'成人保健',0,42.00,49.00,1250,95.00,
 '用于维生素E补充。','抗氧化。','口服，一次1粒，一日1次。','少见不适。','孕期慎用。','遵医嘱使用。',1,0,NOW(),NOW()),
(50027,'清肺化痰丸','清肺化痰',NULL,'咳嗽痰多，胸闷不适','https://picsum.photos/seed/50027/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50027a/800/600'),
 JSON_ARRAY('200丸/瓶'), JSON_ARRAY('清肺','化痰'),'家庭常备',0,33.00,38.00,1600,94.20,
 '用于痰多咳嗽。','清肺化痰。','口服，一次20丸，一日2次。','偶见胃部不适。','哮喘患者慎用。','遵医嘱使用。',1,0,NOW(),NOW()),
(50028,'肠炎宁片','肠炎宁',NULL,'缓解腹泻腹痛','https://picsum.photos/seed/50028/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50028a/800/600'),
 JSON_ARRAY('24片/盒'), JSON_ARRAY('止泻','解痉'),'快速起效',0,27.00,32.00,1500,93.90,
 '用于急性肠炎。','止泻解痉。','口服，一次2片，一日3次。','偶见便秘。','肠梗阻者禁用。','清淡饮食。',1,0,NOW(),NOW()),
(50029,'消炎止痛膏','复方贴膏',NULL,'肌肉酸痛、扭伤拉伤','https://picsum.photos/seed/50029/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50029a/800/600'),
 JSON_ARRAY('10片/盒'), JSON_ARRAY('止痛','消炎'),'外用首选',0,35.00,42.00,2100,95.30,
 '用于肌肉酸痛、扭伤。','消炎止痛。','外用，贴敷患处。','可能皮肤刺激。','皮肤敏感者慎用。','每日更换。',1,0,NOW(),NOW()),
(50030,'开塞露','液体石蜡',NULL,'缓解便秘，通便','https://picsum.photos/seed/50030/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50030a/800/600'),
 JSON_ARRAY('20ml*10支'), JSON_ARRAY('通便','润肠'),'家庭常备',0,26.00,30.00,1750,96.30,
 '用于便秘。','润肠通便。','外用，直肠注入。','可能腹部不适。','肠梗阻者禁用。','遵医嘱。',1,0,NOW(),NOW()),
(50031,'维生素AD滴剂','维A维D',NULL,'促进生长发育','https://picsum.photos/seed/50031/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50031a/800/600'),
 JSON_ARRAY('400IU*60粒'), JSON_ARRAY('儿童','发育'),'儿童适用',0,31.00,36.00,2200,96.50,
 '用于维A/维D补充。','促进发育。','口服，按说明滴服。','过量可致不适。','高维生素症者禁用。','遵医嘱。',1,0,NOW(),NOW()),
(50032,'钙镁片','钙镁',NULL,'骨骼与肌肉支持','https://picsum.photos/seed/50032/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50032a/800/600'),
 JSON_ARRAY('60片/瓶'), JSON_ARRAY('补钙','补镁'),'骨骼健康',0,48.00,56.00,1300,95.10,
 '用于钙镁补充。','骨骼支持。','口服，一次1片，一日1次。','偶见便秘。','高钙症者禁用。','配合运动。',1,0,NOW(),NOW()),
(50033,'止咳糖浆','复方糖浆',NULL,'缓解咳嗽、咽痒','https://picsum.photos/seed/50033/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50033a/800/600'),
 JSON_ARRAY('120ml/瓶'), JSON_ARRAY('止咳','润喉'),'家庭常备',0,23.00,28.00,2400,94.70,
 '用于咳嗽。','止咳润喉。','口服，按说明服用。','可能嗜睡。','驾驶人士慎用。','避免酒精。',1,0,NOW(),NOW()),
(50034,'抗过敏片','氯雷他定',NULL,'缓解过敏性鼻炎、皮疹','https://picsum.photos/seed/50034/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50034a/800/600'),
 JSON_ARRAY('10mg*20片'), JSON_ARRAY('抗过敏'),'首选推荐',0,28.00,33.00,2600,94.80,
 '用于过敏症状。','抗组胺。','口服，一次1片，一日1次。','偶见嗜睡。','孕期慎用。','遵医嘱。',1,0,NOW(),NOW()),
(50035,'清热消炎片','复方清热',NULL,'发热、咽痛、口腔溃疡','https://picsum.photos/seed/50035/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50035a/800/600'),
 JSON_ARRAY('24片/盒'), JSON_ARRAY('清热','消炎'),'常备',0,26.00,30.00,2100,93.60,
 '用于上呼吸道感染。','清热消炎。','口服，一次2片，一日3次。','偶见胃部不适。','肝病者慎用。','遵守用量。',1,0,NOW(),NOW()),
(50036,'康复新液','康复新液',NULL,'促进黏膜修复','https://picsum.photos/seed/50036/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50036a/800/600'),
 JSON_ARRAY('10ml*12支'), JSON_ARRAY('修复','护黏膜'),'医生指导',1,42.00,49.00,900,92.90,
 '用于口腔溃疡等黏膜损伤。','促进修复。','口服或外用，遵医嘱。','偶见过敏。','对成分过敏者禁用。','遵医嘱。',1,0,NOW(),NOW()),
(50037,'金银花露','金银花',NULL,'清热解毒，夏季饮用','https://picsum.photos/seed/50037/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50037a/800/600'),
 JSON_ARRAY('500ml/瓶'), JSON_ARRAY('清凉','解毒'),'夏季常备',0,9.90,12.00,5200,97.80,
 '用于暑热烦渴。','清热解毒。','口服，适量饮用。','过量可能胃部不适。','糖尿病者慎用。','适量为宜。',1,0,NOW(),NOW()),
(50038,'藿香正气胶囊','藿香正气',NULL,'暑湿感冒、腹泻','https://picsum.photos/seed/50038/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50038a/800/600'),
 JSON_ARRAY('12粒/盒'), JSON_ARRAY('解暑','和中'),'常备',0,19.50,22.00,1900,93.70,
 '用于暑湿感冒。','理气和中。','口服，一次2粒，一日2次。','偶见皮疹。','酒精过敏者慎用。','避光保存。',1,0,NOW(),NOW()),
(50039,'复方甘草片','复方甘草',NULL,'止咳化痰，缓解气喘','https://picsum.photos/seed/50039/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50039a/800/600'),
 JSON_ARRAY('100片/瓶'), JSON_ARRAY('止咳','化痰'),'家庭常备',0,16.80,20.00,2400,95.30,
 '用于咳嗽、气喘。','止咳化痰。','口服，一次2片，一日3次。','可能嗜睡。','驾驶人士慎用。','遵守用量。',1,0,NOW(),NOW()),
(50040,'胃苏颗粒','胃苏',NULL,'缓解胃胀、反酸','https://picsum.photos/seed/50040/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50040a/800/600'),
 JSON_ARRAY('10g*10袋'), JSON_ARRAY('护胃','抑酸'),'常备',0,27.50,32.00,1350,94.20,
 '用于胃胀、反酸。','健胃消食。','口服，一次1袋，一日2次。','偶见头痛。','肝病者慎用。','遵守用量。',1,0,NOW(),NOW()),
(50041,'止泻胶囊','复方止泻',NULL,'急性腹泻快速缓解','https://picsum.photos/seed/50041/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50041a/800/600'),
 JSON_ARRAY('12粒/盒'), JSON_ARRAY('止泻','护肠'),'快速起效',0,24.00,29.00,1700,93.90,
 '用于急性腹泻。','止泻。','口服，一次2粒，一日3次。','偶见便秘。','肠梗阻者禁用。','清淡饮食。',1,0,NOW(),NOW()),
(50042,'清凉润喉片','润喉片',NULL,'咽干咽痛，清凉舒适','https://picsum.photos/seed/50042/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50042a/800/600'),
 JSON_ARRAY('24片/盒'), JSON_ARRAY('润喉','清凉'),'常备',0,14.50,18.00,2100,96.00,
 '用于咽干、咽痛。','润喉。','含服，一次1片，每隔2小时。','偶见口干。','儿童慎用。','避免辛辣。',1,0,NOW(),NOW()),
(50043,'健胃消食片','健消片',NULL,'饭后消食，缓解胀气','https://picsum.photos/seed/50043/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50043a/800/600'),
 JSON_ARRAY('60片/瓶'), JSON_ARRAY('健胃','消食'),'常备',0,19.90,23.00,2400,95.60,
 '用于消化不良。','健胃消食。','口服，一次2片，一日2次。','偶见口干。','胃溃疡者慎用。','配合饮食规律。',1,0,NOW(),NOW()),
(50044,'护肝片','护肝',NULL,'保护肝功能，解毒排毒','https://picsum.photos/seed/50044/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50044a/800/600'),
 JSON_ARRAY('60片/瓶'), JSON_ARRAY('护肝','解毒'),'成人保健',0,49.00,58.00,900,92.40,
 '用于肝脏保健。','护肝解毒。','口服，一次2片，一日1次。','偶见恶心。','肝病者慎用。','遵医嘱。',1,0,NOW(),NOW()),
(50045,'止痛贴','外用止痛贴',NULL,'肩颈腰腿疼痛缓解','https://picsum.photos/seed/50045/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50045a/800/600'),
 JSON_ARRAY('10片/盒'), JSON_ARRAY('止痛','外用'),'外用首选',0,33.00,38.00,2000,95.00,
 '用于肌肉骨骼疼痛。','外用止痛。','外用，贴敷疼痛部位。','皮肤刺激。','皮肤破损处禁用。','每日更换。',1,0,NOW(),NOW()),
(50046,'润喉糖','草本润喉',NULL,'缓解咽痒咽干','https://picsum.photos/seed/50046/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50046a/800/600'),
 JSON_ARRAY('20粒/盒'), JSON_ARRAY('润喉','清凉'),'常备',0,9.90,12.00,3500,97.20,
 '用于咽部不适。','草本润喉。','含服，适量。','偶见口干。','糖尿病者慎用。','适量为宜。',1,0,NOW(),NOW()),
(50047,'维生素K2','维K2',NULL,'促进钙沉积，骨骼强化','https://picsum.photos/seed/50047/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50047a/800/600'),
 JSON_ARRAY('60粒/瓶'), JSON_ARRAY('骨骼','维生素'),'骨骼健康',0,69.00,79.00,800,94.90,
 '用于骨骼健康支持。','促进钙沉积。','口服，一次1粒，一日1次。','少见不适。','孕期慎用。','遵医嘱。',1,0,NOW(),NOW()),
(50048,'蜂胶软胶囊','蜂胶',NULL,'增强免疫，抗氧化','https://picsum.photos/seed/50048/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50048a/800/600'),
 JSON_ARRAY('60粒/瓶'), JSON_ARRAY('免疫','抗氧化'),'成人保健',0,89.00,99.00,600,93.30,
 '用于免疫提升。','抗氧化。','口服，一次1粒，一日1次。','少见不适。','蜂类过敏者禁用。','遵医嘱。',1,0,NOW(),NOW()),
(50049,'VC泡腾片','维C泡腾',NULL,'补充维C，清爽口感','https://picsum.photos/seed/50049/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50049a/800/600'),
 JSON_ARRAY('10片/管'), JSON_ARRAY('维生素','免疫'),'基础补充',0,16.80,20.00,2600,96.80,
 '用于维C补充。','抗氧化。','口服，溶于水后饮用。','偶见胃部不适。','肾结石者慎用。','遵守用量。',1,0,NOW(),NOW()),
(50050,'关节软骨素','氨糖软骨素',NULL,'关节保健，缓解磨损','https://picsum.photos/seed/50050/360/240',
 JSON_ARRAY('https://picsum.photos/seed/50050a/800/600'),
 JSON_ARRAY('60粒/瓶'), JSON_ARRAY('关节','保健'),'成人保健',0,109.00,129.00,700,92.60,
 '用于关节保健。','保护软骨。','口服，一次1粒，一日1次。','少见不适。','孕期慎用。','遵医嘱。',1,0,NOW(),NOW());
/*!40000 ALTER TABLE `medicines` ENABLE KEYS */;
UNLOCK TABLES;

-- 分类关联示例（为每个药品挂一个二级分类）
LOCK TABLES `medicine_category_relations` WRITE;
/*!40000 ALTER TABLE `medicine_category_relations` DISABLE KEYS */;
INSERT INTO `medicine_category_relations` (`id`,`medicine_id`,`category_id`,`created_at`) VALUES
(80001,50001,3011,NOW()),(80002,50002,3011,NOW()),(80003,50003,3012,NOW()),(80004,50004,3013,NOW()),
(80005,50005,3021,NOW()),(80006,50006,3021,NOW()),(80007,50007,3022,NOW()),(80008,50008,3021,NOW()),
(80009,50009,3022,NOW()),(80010,50010,3023,NOW()),(80011,50011,3023,NOW()),(80012,50012,3031,NOW()),
(80013,50013,3033,NOW()),(80014,50014,3033,NOW()),(80015,50015,3032,NOW()),(80016,50016,3032,NOW()),
(80017,50017,3032,NOW()),(80018,50018,3032,NOW()),(80019,50019,3032,NOW()),(80020,50020,3021,NOW()),
(80021,50021,3021,NOW()),(80022,50022,3031,NOW()),(80023,50023,3013,NOW()),(80024,50024,3021,NOW()),
(80025,50025,3022,NOW()),(80026,50026,3032,NOW()),(80027,50027,3022,NOW()),(80028,50028,3023,NOW()),
(80029,50029,3022,NOW()),(80030,50030,3023,NOW()),(80031,50031,3031,NOW()),(80032,50032,3033,NOW()),
(80033,50033,3021,NOW()),(80034,50034,3022,NOW()),(80035,50035,3013,NOW()),(80036,50036,3023,NOW()),
(80037,50037,3013,NOW()),(80038,50038,3021,NOW()),(80039,50039,3021,NOW()),(80040,50040,3023,NOW()),
(80041,50041,3023,NOW()),(80042,50042,3013,NOW()),(80043,50043,3012,NOW()),(80044,50044,3032,NOW()),
(80045,50045,3022,NOW()),(80046,50046,3013,NOW()),(80047,50047,3033,NOW()),(80048,50048,3032,NOW()),
(80049,50049,3031,NOW()),(80050,50050,3033,NOW());
/*!40000 ALTER TABLE `medicine_category_relations` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
