USE SmartHealthHub;

-- 清空旧数据以保证数据整洁
TRUNCATE TABLE pharmacy_locations;
TRUNCATE TABLE medicine_orders;

-- 插入取药地点模拟数据 (10条)
INSERT INTO pharmacy_locations (id, name, address, phone, opening_hours, is_enabled, sort_order, created_at, updated_at) VALUES 
(1, '泸州市龙马潭区红星社区卫生服务中心', '泸州市龙马潭区红星路一段128号', '0830-1234567', '08:00-18:00', 1, 1, NOW(), NOW()),
(2, '泸州市江阳区南城社区卫生服务中心', '泸州市江阳区连江路一段88号', '0830-2345678', '08:30-17:30', 1, 2, NOW(), NOW()),
(3, '泸州市纳溪区永宁街道社区卫生服务中心', '泸州市纳溪区云溪西路15号', '0830-3456789', '09:00-17:00', 1, 3, NOW(), NOW()),
(4, '泸州市中医医院药房', '泸州市江阳区江阳南路11号', '0830-4567890', '24小时营业', 1, 4, NOW(), NOW()),
(5, '泸州大山坪社区卫生服务站', '泸州市江阳区大山坪路北段22号', '0830-5678901', '08:00-20:00', 1, 5, NOW(), NOW()),
(6, '泸州天佑大药房（龙马潭店）', '泸州市龙马潭区春雨路66号', '0830-6789012', '08:00-22:00', 1, 6, NOW(), NOW()),
(7, '泸州康贝大药房（江阳店）', '泸州市江阳区白招牌路99号', '0830-7890123', '08:30-21:30', 1, 7, NOW(), NOW()),
(8, '泸州百信药房（纳溪店）', '泸州市纳溪区人民路45号', '0830-8901234', '09:00-21:00', 1, 8, NOW(), NOW()),
(9, '泸州市民生大药房', '泸州市龙马潭区蜀泸大道888号', '0830-9012345', '08:00-22:00', 1, 9, NOW(), NOW()),
(10, '泸州市城西社区卫生服务中心', '泸州市江阳区康城路一段1号', '0830-0123456', '08:30-18:00', 1, 10, NOW(), NOW());

-- 插入药品订单模拟数据 (32条)
INSERT INTO medicine_orders (id, order_no, user_id, medicine_id, medicine_name, price, quantity, total_amount, status, pharmacy_location_id, payment_method, payment_time, created_at, updated_at) VALUES
(101, 'ORD202601220001', 1, 50001, '八珍颗粒', 45.00, 1, 45.00, 1, 1, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 1 DAY), NOW()),
(102, 'ORD202601220002', 1, 50002, '感冒灵颗粒', 15.00, 2, 30.00, 2, 2, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 2 DAY), NOW()),
(103, 'ORD202601220003', 2, 50003, '阿莫西林胶囊', 25.00, 1, 25.00, 0, 3, NULL, NULL, DATE_SUB(NOW(), INTERVAL 3 DAY), NOW()),
(104, 'ORD202601220004', 2, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 3, 4, NULL, NULL, DATE_SUB(NOW(), INTERVAL 4 DAY), NOW()),
(105, 'ORD202601220005', 3, 50005, '连花清瘟胶囊', 28.00, 3, 84.00, 1, 5, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
(106, 'ORD202601220006', 3, 50001, '八珍颗粒', 45.00, 1, 45.00, 2, 6, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 6 DAY), NOW()),
(107, 'ORD202601220007', 4, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 0, 7, NULL, NULL, DATE_SUB(NOW(), INTERVAL 1 HOUR), NOW()),
(108, 'ORD202601220008', 4, 50003, '阿莫西林胶囊', 25.00, 2, 50.00, 1, 8, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 2 HOUR), NOW()),
(109, 'ORD202601220009', 5, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 2, 9, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 3 HOUR), NOW()),
(110, 'ORD202601220010', 5, 50005, '连花清瘟胶囊', 28.00, 1, 28.00, 3, 10, NULL, NULL, DATE_SUB(NOW(), INTERVAL 4 HOUR), NOW()),
(111, 'ORD202601220011', 1, 50001, '八珍颗粒', 45.00, 2, 90.00, 1, 1, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 5 HOUR), NOW()),
(112, 'ORD202601220012', 2, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 2, 2, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 6 HOUR), NOW()),
(113, 'ORD202601220013', 3, 50003, '阿莫西林胶囊', 25.00, 1, 25.00, 0, 3, NULL, NULL, DATE_SUB(NOW(), INTERVAL 10 MINUTE), NOW()),
(114, 'ORD202601220014', 4, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 1, 4, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 20 MINUTE), NOW()),
(115, 'ORD202601220015', 5, 50005, '连花清瘟胶囊', 28.00, 2, 56.00, 2, 5, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 30 MINUTE), NOW()),
(116, 'ORD202601220016', 1, 50001, '八珍颗粒', 45.00, 1, 45.00, 3, 6, NULL, NULL, DATE_SUB(NOW(), INTERVAL 40 MINUTE), NOW()),
(117, 'ORD202601220017', 2, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 1, 7, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 50 MINUTE), NOW()),
(118, 'ORD202601220018', 3, 50003, '阿莫西林胶囊', 25.00, 3, 75.00, 2, 8, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 1 DAY), NOW()),
(119, 'ORD202601220019', 4, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 0, 9, NULL, NULL, DATE_SUB(NOW(), INTERVAL 2 DAY), NOW()),
(120, 'ORD202601220020', 5, 50005, '连花清瘟胶囊', 28.00, 1, 28.00, 1, 10, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 3 DAY), NOW()),
(121, 'ORD202601220021', 1, 50001, '八珍颗粒', 45.00, 1, 45.00, 2, 1, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 4 DAY), NOW()),
(122, 'ORD202601220022', 2, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 3, 2, NULL, NULL, DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
(123, 'ORD202601220023', 3, 50003, '阿莫西林胶囊', 25.00, 1, 25.00, 1, 3, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 6 DAY), NOW()),
(124, 'ORD202601220024', 4, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 2, 4, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
(125, 'ORD202601220025', 5, 50005, '连花清瘟胶囊', 28.00, 1, 28.00, 0, 5, NULL, NULL, DATE_SUB(NOW(), INTERVAL 8 DAY), NOW()),
(126, 'ORD202601220026', 1, 50001, '八珍颗粒', 45.00, 1, 45.00, 1, 6, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 9 DAY), NOW()),
(127, 'ORD202601220027', 2, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 2, 7, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 10 DAY), NOW()),
(128, 'ORD202601220028', 3, 50003, '阿莫西林胶囊', 25.00, 1, 25.00, 3, 8, NULL, NULL, DATE_SUB(NOW(), INTERVAL 11 DAY), NOW()),
(129, 'ORD202601220029', 4, 50004, '布洛芬缓释胶囊', 35.00, 1, 35.00, 1, 9, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 12 DAY), NOW()),
(130, 'ORD202601220030', 5, 50005, '连花清瘟胶囊', 28.00, 1, 28.00, 2, 10, 'Alipay', NOW(), DATE_SUB(NOW(), INTERVAL 13 DAY), NOW()),
(131, 'ORD202601220031', 1, 50001, '八珍颗粒', 45.00, 1, 45.00, 0, 1, NULL, NULL, DATE_SUB(NOW(), INTERVAL 14 DAY), NOW()),
(132, 'ORD202601220032', 2, 50002, '感冒灵颗粒', 15.00, 1, 15.00, 1, 2, 'WeChat', NOW(), DATE_SUB(NOW(), INTERVAL 15 DAY), NOW());
