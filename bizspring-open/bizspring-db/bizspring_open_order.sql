/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : bizspring_open_order

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 22/09/2024 20:42:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_cart
-- ----------------------------
DROP TABLE IF EXISTS `biz_cart`;
CREATE TABLE `biz_cart`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `expire` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `cart_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密钥',
  `member_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cartKey`(`cart_key`) USING BTREE,
  INDEX `ind_Cart_member_id`(`member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_cart
-- ----------------------------
INSERT INTO `biz_cart` VALUES ('1769301995938873346', '2024-03-17 17:57:23', NULL, 0, NULL, 'd1437fed7d004b16c8eff2256ec78eb2', '9951', b'0', b'0', '0');
INSERT INTO `biz_cart` VALUES ('1771830611654385666', '2024-03-24 17:25:11', NULL, 0, NULL, '55321b6f529e84dbf605a20d568a47e2', '9951', b'0', b'0', '0');
INSERT INTO `biz_cart` VALUES ('1805952160682467329', '2024-06-26 21:12:03', NULL, 0, NULL, '7276385cfa60e8811da3ae2d02f1dedc', '9951', b'0', b'0', '0');
INSERT INTO `biz_cart` VALUES ('1805953980502880258', '2024-06-26 21:19:17', NULL, 0, '2024-07-03 21:19:17', 'd8ba8bfec3d48dcd6bad625d8e72fc60', NULL, b'0', b'0', '0');
INSERT INTO `biz_cart` VALUES ('1831178242222182401', '2024-09-04 11:51:29', NULL, 0, NULL, '505d86d15f4ecadf22b6dba1e6271046', '9951', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_cart_item`;
CREATE TABLE `biz_cart_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `checked` int(11) NULL DEFAULT 0,
  `cart_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购物车',
  `sku_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'SKU',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_CartItem_cart_id`(`cart_id`) USING BTREE,
  INDEX `ind_CartItem_sku_id`(`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_cart_item
-- ----------------------------
INSERT INTO `biz_cart_item` VALUES ('1769301996018565121', '2024-03-17 17:57:23', NULL, 0, 1, 1, '1769301995938873346', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_cart_item` VALUES ('1771830612073816066', '2024-03-24 17:25:12', NULL, 0, 1, 1, '1771830611654385666', '1769326584576282625', b'0', b'0', '0');
INSERT INTO `biz_cart_item` VALUES ('1771830709855625218', '2024-03-24 17:25:35', NULL, 0, 1, 1, '1771830611654385666', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_cart_item` VALUES ('1805952161240309762', '2024-06-26 21:12:03', NULL, 0, 1, 1, '1805952160682467329', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_cart_item` VALUES ('1805953980876173313', '2024-06-26 21:19:17', NULL, 0, 1, 1, '1805953980502880258', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_cart_item` VALUES ('1831178242578698241', '2024-09-04 11:51:29', NULL, 0, 1, 1, '1831178242222182401', '1769301318470668289', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_order_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_item`;
CREATE TABLE `biz_order_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `is_delivery` bit(1) NOT NULL COMMENT '是否需要物流',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `price` decimal(21, 6) NOT NULL COMMENT '价格',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `shipped_quantity` int(11) NOT NULL COMMENT '已发货数量',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `specifications` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `thumbnail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `weight` int(11) NULL DEFAULT NULL COMMENT '重量',
  `checked` bit(1) NULL DEFAULT b'0',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单',
  `sku_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SKU',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_OrderItem_orders`(`order_id`) USING BTREE,
  INDEX `ind_OrderItem_sku_id`(`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_order_item
-- ----------------------------
INSERT INTO `biz_order_item` VALUES ('1769302445366935553', '2024-03-17 17:59:10', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 2, 2, 'ip15_5', '[\"Yellow黄色\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1769302445366935554', '1769301318684577793', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1771836269564960770', '2024-03-24 17:47:40', NULL, 1, b'1', 'Apple iPhone 14', 619.000000, 1, 1, 'ip14_1', '[\"Blue蓝色\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1771836269736927234', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1771836269736927233', '2024-03-24 17:47:40', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black黑色\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1771836269736927234', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1772127610027077633', '2024-03-25 13:05:21', NULL, 0, b'1', 'HUAWEI Vision S', 799.000000, 1, 0, 'hvs65', '[]', 'http://qiniuio.bizspring.cn//1/goods/bc584bfc-d35c-4bc5-9ccf-263366c1a590_thumbnail.jpg/678a0e48-1f2c-4e53-9eb5-f4597c1f4505.jpg', NULL, b'0', '1772127610073214977', '1765340697878302722', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1799781575824748546', '2024-06-09 20:32:21', NULL, 0, b'1', 'CHANEL Black Lambskin Women\'s Shoulder Bag', 2874.000000, 1, 0, 'cha01', '[]', 'http://qiniuio.bizspring.cn//1/goods/a9a41e88-3ef0-4e1c-b96c-5b58f69a624e_thumbnail.jpg/16144e93-4bdd-4069-a02e-93fba6b5e6b0.jpg', NULL, b'0', '1799781575824748547', '1773216585831612417', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1804754784055676929', '2024-06-23 13:54:06', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1804754784055676930', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805185019209269250', '2024-06-24 18:23:42', NULL, 0, b'1', 'Advil Liqui-Gels minis Pain Reliever and Fever Reducer', 20.000000, 1, 0, 'm02', '[]', 'http://qiniuio.bizspring.cn//1/goods/d7e98bce-6004-46d8-94e6-1e2f25c1c6c8_thumbnail.jpg/b6fa54dd-9ae3-4e0b-9deb-26fe3fdb4152.jpg', NULL, b'0', '1805185019209269251', '1773282178005962754', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805185151958990850', '2024-06-24 18:24:14', NULL, 1, b'1', 'Apple iPhone 14', 619.000000, 1, 1, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1805185151958990851', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805196467264241666', '2024-06-24 19:09:11', NULL, 1, b'1', 'Apple iPhone 14', 619.000000, 1, 1, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1805196467331350529', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805202154941792257', '2024-06-24 19:31:47', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805202154941792258', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805203638395813890', '2024-06-24 19:37:41', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805203638395813891', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805524535749091330', '2024-06-25 16:52:49', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805524535749091331', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805524893091209218', '2024-06-25 16:54:14', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805524893128957954', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805528242792407041', '2024-06-25 17:07:33', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805528242859515905', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805528775632592898', '2024-06-25 17:09:40', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805528775984914435', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805528775825530881', '2024-06-25 17:09:40', NULL, 1, b'1', 'Mophie Snap+ Juice Pack Mini - Magnetic 5000mAh Po', 0.000000, 1, 1, '2017122620230', '[]', 'http://qiniuio.bizspring.cn//1/goods/44431646-097f-4498-8f99-e66b5a464e15_thumbnail.jpg/f30cc7c7-56e1-4228-a0f1-45b1a33612a0.jpg', NULL, b'0', '1805528775984914435', '63', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805528775984914434', '2024-06-25 17:09:40', NULL, 1, b'1', 'Ultra Slim Wireless Charger by It’s Just Smart', 0.000000, 1, 1, '2017122620231', '[]', 'http://qiniuio.bizspring.cn//1/goods/047ee494-c846-4aa0-b331-c76c0e16ec02_thumbnail.jpg/15dd4925-ba36-4d5b-bfcf-cf0b958b7693.jpg', NULL, b'0', '1805528775984914435', '64', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805529761881235458', '2024-06-25 17:13:35', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805529761927372802', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805959771200892930', '2024-06-26 21:42:17', NULL, 0, b'1', 'Huawei Mate 60 Pro', 1631.000000, 1, 0, 'hw60p_1', '[\"Black\",\"512GB+12GB\"]', 'http://qiniuio.bizspring.cn//1/goods/4f479c2b-0555-45e5-b75d-6bbc706c920a_thumbnail.jpg/522c6e50-c83a-457b-a5bf-8992f59fa204.jpg', NULL, b'0', '1805959771200892931', '1769578255936008193', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805959822774054914', '2024-06-26 21:42:30', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 2, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805959822774054915', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1805990386071711745', '2024-06-26 23:43:56', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1805990386071711746', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1806127251030487042', '2024-06-27 08:47:48', NULL, 0, b'1', 'Apple iPhone 14', 619.000000, 1, 0, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1806127251156316162', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1806271038893473793', '2024-06-27 18:19:09', NULL, 0, b'1', 'Apple iPhone 14', 619.000000, 1, 0, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1806271038893473794', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1806636132739522562', '2024-06-28 18:29:54', NULL, 0, b'1', 'Apple iPhone 14', 619.000000, 1, 0, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1806636132739522563', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1807004594905227265', '2024-06-29 18:54:03', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1807004594905227266', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1807354720714739714', '2024-06-30 18:05:19', NULL, 0, b'1', 'Apple iPhone 14', 619.000000, 1, 0, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1807354720777654273', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1808170197754007553', '2024-07-03 00:05:44', NULL, 0, b'1', 'Benadryl Children\'s Allergy Relief Liquid Medicine', 10.000000, 1, 0, 'm01', '[]', 'http://qiniuio.bizspring.cn//1/goods/ab8fea0c-0298-4d96-b6a1-f00ee433a5de_thumbnail.jpg/0d3b96ee-97c8-4a68-bd2d-8f316165d73b.jpg', NULL, b'0', '1808170197754007554', '1773279264466243586', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830833373746286594', '2024-09-03 13:01:06', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1830833373746286595', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830897501286420481', '2024-09-03 17:15:55', NULL, 0, b'1', 'Benadryl Children\'s Allergy Relief Liquid Medicine', 10.000000, 1, 0, 'm01', '[]', 'http://qiniuio.bizspring.cn//1/goods/ab8fea0c-0298-4d96-b6a1-f00ee433a5de_thumbnail.jpg/0d3b96ee-97c8-4a68-bd2d-8f316165d73b.jpg', NULL, b'0', '1830897501286420482', '1773279264466243586', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830897745495576578', '2024-09-03 17:16:54', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1830897745495576579', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830897764005040130', '2024-09-03 17:16:58', NULL, 0, b'1', 'Huawei Enjoy 70 Pro', 395.990000, 1, 0, 'he70p_1', '[\"Black\",\"256GB+8GB\"]', 'http://qiniuio.bizspring.cn//1/goods/c901228c-71f1-46f0-9009-9550de594d0b_thumbnail.jpg/210c42c3-85d4-47f2-9fc0-20c9d0207819.jpg', NULL, b'0', '1830897764005040131', '1769326584576282625', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830917944286367745', '2024-09-03 18:37:09', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1830917944286367746', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1830917962732916738', '2024-09-03 18:37:14', NULL, 0, b'1', 'Huawei Enjoy 70 Pro', 395.990000, 1, 0, 'he70p_1', '[\"Black\",\"256GB+8GB\"]', 'http://qiniuio.bizspring.cn//1/goods/c901228c-71f1-46f0-9009-9550de594d0b_thumbnail.jpg/210c42c3-85d4-47f2-9fc0-20c9d0207819.jpg', NULL, b'0', '1830917962732916739', '1769326584576282625', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831193076707872769', '2024-09-04 12:50:26', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1831193076707872770', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831193168051425282', '2024-09-04 12:50:48', NULL, 0, b'1', 'Huawei Mate 60 Pro', 1631.000000, 1, 0, 'hw60p_1', '[\"Black\",\"512GB+12GB\"]', 'http://qiniuio.bizspring.cn//1/goods/4f479c2b-0555-45e5-b75d-6bbc706c920a_thumbnail.jpg/522c6e50-c83a-457b-a5bf-8992f59fa204.jpg', NULL, b'0', '1831193168051425283', '1769578255936008193', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831285718498136065', '2024-09-04 18:58:34', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1831285718498136066', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831285794406649858', '2024-09-04 18:58:52', NULL, 0, b'1', 'Apple iPhone 14', 619.000000, 1, 0, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1831285794406649859', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831287243647733762', '2024-09-04 19:04:37', NULL, 1, b'1', 'Apple iPhone 14', 619.000000, 1, 1, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1831287243647733763', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831287307539566593', '2024-09-04 19:04:52', NULL, 0, b'1', 'Huawei Mate 60 Pro', 1631.000000, 1, 0, 'hw60p_1', '[\"Black\",\"512GB+12GB\"]', 'http://qiniuio.bizspring.cn//1/goods/4f479c2b-0555-45e5-b75d-6bbc706c920a_thumbnail.jpg/522c6e50-c83a-457b-a5bf-8992f59fa204.jpg', NULL, b'0', '1831287307539566594', '1769578255936008193', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831289626503139329', '2024-09-04 19:14:05', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1831289626503139330', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1831289735596986370', '2024-09-04 19:14:31', NULL, 1, b'1', 'Apple iPhone 14', 619.000000, 1, 1, 'ip14_1', '[\"Blue\"]', 'http://qiniuio.bizspring.cn//1/goods/4d122441-59a4-4f43-ba11-13a0b76aaad5_thumbnail.jpg/94279568-64c6-4c8f-aa3b-d3fc502db7dd.jpg', NULL, b'0', '1831289735596986371', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1835963059597996034', '2024-09-17 16:44:39', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1835963059597996035', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1835990038854426626', '2024-09-17 18:31:51', NULL, 1, b'1', 'Apple iPhone 15', 649.950000, 1, 1, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1835990038854426627', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1835992347428708354', '2024-09-17 18:41:01', NULL, 0, b'1', 'Huawei Mate 60 Pro', 1631.000000, 1, 0, 'hw60p_1', '[\"Black\",\"512GB+12GB\"]', 'http://qiniuio.bizspring.cn//1/goods/4f479c2b-0555-45e5-b75d-6bbc706c920a_thumbnail.jpg/522c6e50-c83a-457b-a5bf-8992f59fa204.jpg', NULL, b'0', '1835992347428708355', '1769578255936008193', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1836016823629438978', '2024-09-17 20:18:17', NULL, 0, b'1', 'Chanel Soleil Tan De Chanel Bronzing Makeup Base', 72.000000, 1, 0, 'cha02', '[]', 'http://qiniuio.bizspring.cn//1/goods/daacd6a5-1a6d-465e-972a-a35d69aea1e8_thumbnail.jpg/dc5e86df-fee6-40f9-8419-d1af55ad8ab9.jpg', NULL, b'0', '1836016823629438979', '1773220908288376833', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1837440074155003905', '2024-09-21 18:33:46', NULL, 0, b'1', 'aaaaa', 111111.000000, 1, 0, '111', '[]', 'http://qiniuio.bizspring.cn//1/goods/f63bdcd0-0571-4158-9c61-f835cddfbc6b_thumbnail.jpg/1c1ac485-8dd2-42f6-baa3-3d34b1b62ab1.jpg', NULL, b'0', '1837440074155003906', '1837437976109625346', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1837501763260981249', '2024-09-21 22:38:54', NULL, 0, b'1', 'Apple iPhone 15', 649.950000, 1, 0, 'ip15_1', '[\"Black\"]', 'http://qiniuio.bizspring.cn//1/goods/b30b88ca-1258-4368-8ad5-3259c8afdee1_thumbnail.jpg/fe51c895-5284-4368-a153-99758e61ac4a.jpg', NULL, b'0', '1837501763260981250', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_item` VALUES ('1837825254292127746', '2024-09-22 20:04:20', NULL, 0, b'1', 'aaaaa', 111111.000000, 1, 0, '111', '[]', 'http://qiniuio.bizspring.cn//1/goods/f63bdcd0-0571-4158-9c61-f835cddfbc6b_thumbnail.jpg/1c1ac485-8dd2-42f6-baa3-3d34b1b62ab1.jpg', NULL, b'0', '1837825254292127747', '1837437976109625346', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_order_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_log`;
CREATE TABLE `biz_order_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情',
  `type` int(11) NOT NULL COMMENT '类型',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_OrderLog_orders`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_order_log
-- ----------------------------
INSERT INTO `biz_order_log` VALUES ('1769302445496958978', '2024-03-17 17:59:10', NULL, 0, NULL, 0, '1769302445366935554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1769302475146493954', '2024-03-17 17:59:17', NULL, 0, NULL, 4, '1769302445366935554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1771836269862756354', '2024-03-24 17:47:41', NULL, 0, NULL, 0, '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1771836335600082945', '2024-03-24 17:47:56', NULL, 0, NULL, 4, '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1772127610261958658', '2024-03-25 13:05:21', NULL, 0, NULL, 0, '1772127610073214977', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1772128530488061954', '2024-03-25 13:09:01', NULL, 0, NULL, 4, '1772127610073214977', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1799781575917023233', '2024-06-09 20:32:21', NULL, 0, NULL, 0, '1799781575824748547', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1799782011113811970', '2024-06-09 20:34:04', NULL, 0, NULL, 4, '1799781575824748547', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1804754784126980098', '2024-06-23 13:54:06', NULL, 0, NULL, 0, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1804754803324309506', '2024-06-23 13:54:10', NULL, 0, NULL, 4, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1804755511494791170', '2024-06-23 13:56:59', NULL, 0, NULL, 1, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1804755646815621122', '2024-06-23 13:57:32', NULL, 0, NULL, 3, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1804756037645062145', '2024-06-23 13:59:05', NULL, 0, NULL, 5, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805185019276378113', '2024-06-24 18:23:42', NULL, 0, NULL, 0, '1805185019209269251', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805185033516040193', '2024-06-24 18:23:45', NULL, 0, NULL, 4, '1805185019209269251', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805185152000933889', '2024-06-24 18:24:14', NULL, 0, NULL, 0, '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805185161115156481', '2024-06-24 18:24:16', NULL, 0, NULL, 4, '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805185287472758785', '2024-06-24 18:24:46', NULL, 0, NULL, 3, '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805186616081465346', '2024-06-24 18:30:03', NULL, 0, NULL, 5, '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805187413653536770', '2024-06-24 18:33:13', NULL, 0, NULL, 3, '1772127610073214977', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805187457278492674', '2024-06-24 18:33:23', NULL, 0, NULL, 3, '1769302445366935554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805187554506653698', '2024-06-24 18:33:46', NULL, 0, NULL, 3, '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805188664256577538', '2024-06-24 18:38:11', NULL, 0, NULL, 5, '1769302445366935554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805192062649450497', '2024-06-24 18:51:41', NULL, 0, NULL, 8, '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805192069674909698', '2024-06-24 18:51:43', NULL, 0, NULL, 8, '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805195821031047170', '2024-06-24 19:06:37', NULL, 0, NULL, 5, '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805196467390070786', '2024-06-24 19:09:11', NULL, 0, NULL, 0, '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805196475774484481', '2024-06-24 19:09:13', NULL, 0, NULL, 4, '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805196591390474241', '2024-06-24 19:09:41', NULL, 0, NULL, 3, '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805198268566814722', '2024-06-24 19:16:21', NULL, 0, NULL, 5, '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805202155055038466', '2024-06-24 19:31:47', NULL, 0, NULL, 0, '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805202166841032705', '2024-06-24 19:31:50', NULL, 0, NULL, 4, '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805202225502568450', '2024-06-24 19:32:04', NULL, 0, NULL, 3, '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805202399083839490', '2024-06-24 19:32:46', NULL, 0, NULL, 5, '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805203638462922753', '2024-06-24 19:37:41', NULL, 0, NULL, 0, '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805203648659275778', '2024-06-24 19:37:44', NULL, 0, NULL, 4, '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805203720520286209', '2024-06-24 19:38:01', NULL, 0, NULL, 3, '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805203911944126466', '2024-06-24 19:38:46', NULL, 0, NULL, 5, '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805524535946223617', '2024-06-25 16:52:49', NULL, 0, NULL, 0, '1805524535749091331', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805524585493536770', '2024-06-25 16:53:01', NULL, 0, NULL, 4, '1805524535749091331', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805524893175095297', '2024-06-25 16:54:14', NULL, 0, NULL, 0, '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805524905711869954', '2024-06-25 16:54:17', NULL, 0, NULL, 4, '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805525699223855106', '2024-06-25 16:57:26', NULL, 0, NULL, 3, '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805525915075321858', '2024-06-25 16:58:18', NULL, 0, NULL, 5, '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805528242993733634', '2024-06-25 17:07:33', NULL, 0, NULL, 0, '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805528253756317697', '2024-06-25 17:07:35', NULL, 0, NULL, 4, '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805528776081383426', '2024-06-25 17:09:40', NULL, 0, NULL, 0, '1805528775984914435', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805528809690341377', '2024-06-25 17:09:48', NULL, 0, NULL, 4, '1805528775984914435', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805528882461515777', '2024-06-25 17:10:05', NULL, 0, NULL, 3, '1805528775984914435', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529043682172930', '2024-06-25 17:10:44', NULL, 0, NULL, 5, '1805528775984914435', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529109184618498', '2024-06-25 17:10:59', NULL, 0, NULL, 3, '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529276449267714', '2024-06-25 17:11:39', NULL, 0, NULL, 5, '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529761977704449', '2024-06-25 17:13:35', NULL, 0, NULL, 0, '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529777593098241', '2024-06-25 17:13:39', NULL, 0, NULL, 4, '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805529958929637378', '2024-06-25 17:14:22', NULL, 0, NULL, 3, '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805530107538022402', '2024-06-25 17:14:57', NULL, 0, NULL, 5, '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805959771788095489', '2024-06-26 21:42:17', NULL, 0, NULL, 0, '1805959771200892931', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805959822904078337', '2024-06-26 21:42:30', NULL, 0, NULL, 0, '1805959822774054915', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805961962309496834', '2024-06-26 21:51:00', NULL, 0, NULL, 4, '1805959771200892931', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805961962561155073', '2024-06-26 21:51:00', NULL, 0, NULL, 4, '1805959822774054915', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1805990386138820609', '2024-06-26 23:43:56', NULL, 0, NULL, 0, '1805990386071711746', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1806127251286339585', '2024-06-27 08:47:48', NULL, 0, NULL, 0, '1806127251156316162', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1806271039254183938', '2024-06-27 18:19:09', NULL, 0, NULL, 0, '1806271038893473794', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1806636132827602945', '2024-06-28 18:29:54', NULL, 0, NULL, 0, '1806636132739522563', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1807004594968141826', '2024-06-29 18:54:03', NULL, 0, NULL, 0, '1807004594905227266', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1807354721159335938', '2024-06-30 18:05:19', NULL, 0, NULL, 0, '1807354720777654273', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1808170197804339201', '2024-07-03 00:05:44', NULL, 0, NULL, 0, '1808170197754007554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1808307393450430466', '2024-07-03 09:10:54', NULL, 0, NULL, 4, '1808170197754007554', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830833373821784065', '2024-09-03 13:01:06', NULL, 0, NULL, 0, '1830833373746286595', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830835423599120385', '2024-09-03 13:09:15', NULL, 0, NULL, 4, '1830833373746286595', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830836906302361602', '2024-09-03 13:15:08', NULL, 0, NULL, 7, '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830836963730771969', '2024-09-03 13:15:22', NULL, 0, NULL, 7, '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897501307392002', '2024-09-03 17:15:55', NULL, 0, NULL, 0, '1830897501286420482', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897522178248705', '2024-09-03 17:16:00', NULL, 0, NULL, 4, '1830897501286420482', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897745524936705', '2024-09-03 17:16:54', NULL, 0, NULL, 0, '1830897745495576579', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897764084731905', '2024-09-03 17:16:58', NULL, 0, NULL, 0, '1830897764005040131', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897774352388097', '2024-09-03 17:17:01', NULL, 0, NULL, 4, '1830897745495576579', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830897774482411522', '2024-09-03 17:17:01', NULL, 0, NULL, 4, '1830897764005040131', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830917944340893697', '2024-09-03 18:37:09', NULL, 0, NULL, 0, '1830917944286367746', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830917962783248386', '2024-09-03 18:37:14', NULL, 0, NULL, 0, '1830917962732916739', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830917969183756290', '2024-09-03 18:37:15', NULL, 0, NULL, 4, '1830917962732916739', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1830917969343139841', '2024-09-03 18:37:15', NULL, 0, NULL, 4, '1830917944286367746', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831193076724649985', '2024-09-04 12:50:26', NULL, 0, NULL, 0, '1831193076707872770', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831193083636862978', '2024-09-04 12:50:28', NULL, 0, NULL, 4, '1831193076707872770', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831193168080785410', '2024-09-04 12:50:48', NULL, 0, NULL, 0, '1831193168051425283', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831193209642143745', '2024-09-04 12:50:58', NULL, 0, NULL, 4, '1831193168051425283', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831285718569439234', '2024-09-04 18:58:34', NULL, 0, NULL, 0, '1831285718498136066', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831285728778375169', '2024-09-04 18:58:36', NULL, 0, NULL, 4, '1831285718498136066', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831285794465370113', '2024-09-04 18:58:52', NULL, 0, NULL, 0, '1831285794406649859', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831285800320618498', '2024-09-04 18:58:53', NULL, 0, NULL, 4, '1831285794406649859', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831287243681288194', '2024-09-04 19:04:37', NULL, 0, NULL, 0, '1831287243647733763', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831287249473622017', '2024-09-04 19:04:39', NULL, 0, NULL, 4, '1831287243647733763', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831287307615064065', '2024-09-04 19:04:52', NULL, 0, NULL, 0, '1831287307539566594', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831287313566781441', '2024-09-04 19:04:54', NULL, 0, NULL, 4, '1831287307539566594', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831289626524110850', '2024-09-04 19:14:05', NULL, 0, NULL, 0, '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831289633583124482', '2024-09-04 19:14:07', NULL, 0, NULL, 4, '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831289735714426881', '2024-09-04 19:14:31', NULL, 0, NULL, 0, '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831289740684677122', '2024-09-04 19:14:33', NULL, 0, NULL, 4, '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831919460170301441', '2024-09-06 12:56:49', NULL, 0, NULL, 3, '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1831925079874961409', '2024-09-06 13:19:09', NULL, 0, NULL, 5, '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835963059728019457', '2024-09-17 16:44:39', NULL, 0, NULL, 0, '1835963059597996035', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835985799210000386', '2024-09-17 18:15:00', NULL, 0, NULL, 4, '1835963059597996035', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835990038929924097', '2024-09-17 18:31:51', NULL, 0, NULL, 0, '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835990046479671298', '2024-09-17 18:31:53', NULL, 0, NULL, 4, '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835992347466457090', '2024-09-17 18:41:01', NULL, 0, NULL, 0, '1835992347428708355', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1835992354571608065', '2024-09-17 18:41:03', NULL, 0, NULL, 4, '1835992347428708355', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836016823704936450', '2024-09-17 20:18:17', NULL, 0, NULL, 0, '1836016823629438979', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836016831414067202', '2024-09-17 20:18:19', NULL, 0, NULL, 4, '1836016823629438979', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836039216880082945', '2024-09-17 21:47:16', NULL, 0, NULL, 3, '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836039251285958658', '2024-09-17 21:47:24', NULL, 0, NULL, 3, '1835963059597996035', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836045262063370242', '2024-09-17 22:11:17', NULL, 0, NULL, 5, '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836045396843134977', '2024-09-17 22:11:49', NULL, 0, NULL, 3, '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1836045485187760129', '2024-09-17 22:12:10', NULL, 0, NULL, 5, '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837420768570609666', '2024-09-21 17:17:04', NULL, 0, NULL, 3, '1831287243647733763', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837420916113641473', '2024-09-21 17:17:39', NULL, 0, NULL, 5, '1831287243647733763', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837440074247278594', '2024-09-21 18:33:46', NULL, 0, NULL, 0, '1837440074155003906', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837440736246861826', '2024-09-21 18:36:24', NULL, 0, NULL, 4, '1837440074155003906', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837440878706397186', '2024-09-21 18:36:58', NULL, 0, NULL, 7, '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837501763307118593', '2024-09-21 22:38:54', NULL, 0, NULL, 0, '1837501763260981250', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837501770248691713', '2024-09-21 22:38:56', NULL, 0, NULL, 4, '1837501763260981250', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837825254950633474', '2024-09-22 20:04:21', NULL, 0, NULL, 0, '1837825254292127747', b'0', b'0', '0');
INSERT INTO `biz_order_log` VALUES ('1837825264832413698', '2024-09-22 20:04:23', NULL, 0, NULL, 4, '1837825254292127747', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_order_payment
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_payment`;
CREATE TABLE `biz_order_payment`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款账号',
  `amount` decimal(21, 6) NOT NULL COMMENT '付款金额',
  `bank` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款银行',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `method` int(11) NOT NULL COMMENT '方式',
  `payer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款人',
  `payment_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sn`(`sn`) USING BTREE,
  INDEX `ind_OrderPayment_orders`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单支付' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_order_payment
-- ----------------------------
INSERT INTO `biz_order_payment` VALUES ('1771836335377784834', '2024-03-24 17:47:56', NULL, 0, NULL, 1268.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10211', '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1772128530232209410', '2024-03-25 13:09:01', NULL, 0, NULL, 749.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10213', '1772127610073214977', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1799782010790850561', '2024-06-09 20:34:04', NULL, 0, NULL, 2274.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10215', '1799781575824748547', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1804754803135565825', '2024-06-23 13:54:10', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10217', '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805185033390211074', '2024-06-24 18:23:45', NULL, 0, NULL, 20.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10219', '1805185019209269251', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805185161022881794', '2024-06-24 18:24:16', NULL, 0, NULL, 619.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10221', '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805196475661238274', '2024-06-24 19:09:13', NULL, 0, NULL, 619.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10223', '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805202166560014337', '2024-06-24 19:31:50', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10225', '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805203648520863746', '2024-06-24 19:37:44', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10227', '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805524585304793089', '2024-06-25 16:53:01', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10229', '1805524535749091331', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805524905623789570', '2024-06-25 16:54:17', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10231', '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805528253554991106', '2024-06-25 17:07:35', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10233', '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805528809581289474', '2024-06-25 17:09:48', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10235', '1805528775984914435', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805529777442103298', '2024-06-25 17:13:39', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10237', '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805961962175279105', '2024-06-26 21:51:00', NULL, 0, NULL, 1631.000000, NULL, NULL, 0, NULL, NULL, '10239', '1805959771200892931', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1805961962468880385', '2024-06-26 21:51:00', NULL, 0, NULL, 1299.900000, NULL, NULL, 0, NULL, NULL, '10241', '1805959822774054915', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1808307393072943106', '2024-07-03 09:10:54', NULL, 0, NULL, 10.000000, NULL, NULL, 0, NULL, 'Paypal', '10243', '1808170197754007554', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830835423418765313', '2024-09-03 13:09:15', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'Paypal', '10245', '1830833373746286595', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830897522111139841', '2024-09-03 17:16:00', NULL, 0, NULL, 10.000000, NULL, NULL, 0, NULL, 'Paypal', '10247', '1830897501286420482', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830897774230753282', '2024-09-03 17:17:00', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, NULL, '10249', '1830897745495576579', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830897774423691265', '2024-09-03 17:17:01', NULL, 0, NULL, 345.990000, NULL, NULL, 0, NULL, NULL, '10251', '1830897764005040131', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830917969066315778', '2024-09-03 18:37:15', NULL, 0, NULL, 345.990000, NULL, NULL, 0, NULL, NULL, '10253', '1830917962732916739', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1830917969246670849', '2024-09-03 18:37:15', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, NULL, '10255', '1830917944286367746', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831193083469090817', '2024-09-04 12:50:28', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'Paypal', '10257', '1831193076707872770', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831193209554063361', '2024-09-04 12:50:58', NULL, 0, NULL, 1631.000000, NULL, NULL, 0, NULL, 'Paypal', '10259', '1831193168051425283', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831285728711266306', '2024-09-04 18:58:36', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'Paypal', '10261', '1831285718498136066', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831285800190595073', '2024-09-04 18:58:53', NULL, 0, NULL, 619.000000, NULL, NULL, 0, NULL, 'Paypal', '10263', '1831285794406649859', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831287249389735938', '2024-09-04 19:04:39', NULL, 0, NULL, 619.000000, NULL, NULL, 0, NULL, 'Paypal', '10265', '1831287243647733763', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831287313491283970', '2024-09-04 19:04:54', NULL, 0, NULL, 1631.000000, NULL, NULL, 0, NULL, 'Paypal', '10267', '1831287307539566594', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831289633486655490', '2024-09-04 19:14:07', NULL, 0, NULL, 649.950000, NULL, NULL, 0, NULL, 'Paypal', '10269', '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1831289740508516354', '2024-09-04 19:14:33', NULL, 0, NULL, 619.000000, NULL, NULL, 0, NULL, 'Paypal', '10271', '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1835985798887038977', '2024-09-17 18:15:00', NULL, 0, NULL, 659.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10273', '1835963059597996035', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1835990046286733314', '2024-09-17 18:31:53', NULL, 0, NULL, 659.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10275', '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1835992354433196033', '2024-09-17 18:41:03', NULL, 0, NULL, 1631.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10277', '1835992347428708355', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1836016831246295042', '2024-09-17 20:18:19', NULL, 0, NULL, 72.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10279', '1836016823629438979', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1837440735940677634', '2024-09-21 18:36:24', NULL, 0, NULL, 111121.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10281', '1837440074155003906', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1837501769959284737', '2024-09-21 22:38:56', NULL, 0, NULL, 659.950000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10283', '1837501763260981250', b'0', b'0', '0');
INSERT INTO `biz_order_payment` VALUES ('1837825263796420610', '2024-09-22 20:04:23', NULL, 0, NULL, 111121.000000, NULL, NULL, 0, NULL, 'alipay(Wap)', '10285', '1837825254292127747', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_order_shipping
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_shipping`;
CREATE TABLE `biz_order_shipping`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `express_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司',
  `express_company_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司代码',
  `express_company_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司网址',
  `freight` decimal(21, 6) NULL DEFAULT NULL COMMENT '物流运费',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `shipping_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送方式',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `tracking_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `zip_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sn`(`sn`) USING BTREE,
  INDEX `ind_OrderShipping_orders`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单发货' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_order_shipping
-- ----------------------------
INSERT INTO `biz_order_shipping` VALUES ('1804756036634234881', '2024-06-23 13:59:05', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10066', '1323434442', '', '1804754784055676930', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805185906694635521', '2024-06-24 18:27:14', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10068', '47324378249321', '', '1805185151958990851', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805192306833440769', '2024-06-24 18:52:39', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10072', '434243343', '', '1771836269736927234', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805198044666478594', '2024-06-24 19:15:27', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10074', '4848495948', '', '1805196467331350529', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805202398572134401', '2024-06-24 19:32:46', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10076', '47584748', '', '1805202154941792258', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805203911575027714', '2024-06-24 19:38:46', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10078', '6584658', '', '1805203638395813891', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805525914643308546', '2024-06-25 16:58:18', NULL, 0, 'test', '北京市,北京市,东城区', 'tese', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10080', '6748468', '', '1805524893128957954', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805529276038225921', '2024-06-25 17:11:39', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10084', '4342432', '', '1805528242859515905', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1805530107206672385', '2024-06-25 17:14:57', NULL, 0, 'test', '北京市,北京市,东城区', 'test', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '16619915737', 'Express', '10086', '543534254', '', '1805529761927372802', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1831925076196556801', '2024-09-06 13:19:08', NULL, 0, '1', '北京市,北京市,东城区', '1', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '1', 'Express', '10088', '1', '1', '1831289735596986371', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1836045261459390465', '2024-09-17 22:11:17', NULL, 0, '1', '北京市,北京市,东城区', '1', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '1', 'Express', '10067', '1', '', '1835990038854426627', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1836045484638306305', '2024-09-17 22:12:10', NULL, 0, '1', '北京市,北京市,东城区', '1', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 0.000000, '', '1', 'Express', '10069', '1', '', '1831289626503139330', b'0', b'0', '0');
INSERT INTO `biz_order_shipping` VALUES ('1837420915320918018', '2024-09-21 17:17:38', NULL, 0, '1', '北京市,北京市,东城区', '121', 'sf-express', 'shunfeng', 'http://www.sf-express.com', 1.000000, '', '121', 'Express', '10071', '1232111', '', '1831287243647733763', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_order_shipping_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_shipping_item`;
CREATE TABLE `biz_order_shipping_item`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `is_delivery` bit(1) NOT NULL COMMENT '是否需要物流',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'SKU名称',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'SKU编号',
  `specifications` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `order_shipping_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单发货',
  `sku_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SKU',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_OrderShippingItem_orderShipping_id`(`order_shipping_id`) USING BTREE,
  INDEX `ind_OrderShippingItem_sku_id`(`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发货项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_order_shipping_item
-- ----------------------------
INSERT INTO `biz_order_shipping_item` VALUES ('1804756036395159554', '2024-06-23 13:59:04', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1804756036634234881', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805185626301218817', '2024-06-24 18:26:07', NULL, 0, b'1', 'Apple iPhone 14', 1, 'ip14_1', '[\"Blue\"]', '1805185906694635521', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805187869901537282', '2024-06-24 18:35:02', NULL, 0, b'1', 'Apple iPhone 15', 2, 'ip15_5', '[\"Yellow黄色\"]', '1805187970246066178', '1769301318684577793', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805192274398887937', '2024-06-24 18:52:32', NULL, 0, b'1', 'Apple iPhone 14', 1, 'ip14_1', '[\"Blue蓝色\"]', '1805192306833440769', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805192274424053762', '2024-06-24 18:52:32', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black黑色\"]', '1805192306833440769', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805196884010287106', '2024-06-24 19:10:51', NULL, 0, b'1', 'Apple iPhone 14', 1, 'ip14_1', '[\"Blue\"]', '1805198044666478594', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805202398442110978', '2024-06-24 19:32:45', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805202398572134401', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805203911415644161', '2024-06-24 19:38:46', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805203911575027714', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805525914509090817', '2024-06-25 16:58:18', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805525914643308546', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805529042583265282', '2024-06-25 17:10:44', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805529042784591874', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805529042650374146', '2024-06-25 17:10:44', NULL, 0, b'1', 'Mophie Snap+ Juice Pack Mini - Magnetic 5000mAh Po', 1, '2017122620230', '[]', '1805529042784591874', '63', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805529042650374147', '2024-06-25 17:10:44', NULL, 0, b'1', 'Ultra Slim Wireless Charger by It’s Just Smart', 1, '2017122620231', '[]', '1805529042784591874', '64', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805529275975311361', '2024-06-25 17:11:39', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805529276038225921', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1805530107110203394', '2024-06-25 17:14:57', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1805530107206672385', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1831925075651297282', '2024-09-06 13:19:08', NULL, 0, b'1', 'Apple iPhone 14', 1, 'ip14_1', '[\"Blue\"]', '1831925076196556801', '1769307917968936961', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1836045261274841089', '2024-09-17 22:11:17', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1836045261459390465', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1836045484499894273', '2024-09-17 22:12:10', NULL, 0, b'1', 'Apple iPhone 15', 1, 'ip15_1', '[\"Black\"]', '1836045484638306305', '1769301318470668289', b'0', b'0', '0');
INSERT INTO `biz_order_shipping_item` VALUES ('1837420914821795842', '2024-09-21 17:17:38', NULL, 0, b'1', 'Apple iPhone 14', 1, 'ip14_1', '[\"Blue\"]', '1837420915320918018', '1769307917968936961', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_orders
-- ----------------------------
DROP TABLE IF EXISTS `biz_orders`;
CREATE TABLE `biz_orders`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `amount` decimal(21, 6) NOT NULL COMMENT '订单金额',
  `amount_paid` decimal(21, 6) NOT NULL COMMENT '已付金额',
  `region_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区名称',
  `complete_date` datetime(0) NULL DEFAULT NULL COMMENT '完成日期',
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `expire` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `freight` decimal(21, 6) NOT NULL COMMENT '运费',
  `is_allocated_stock` bit(1) NULL DEFAULT NULL COMMENT '是否已分配库存',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附言',
  `offset_amount` decimal(21, 6) NOT NULL COMMENT '调整金额',
  `payment_pattern_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式名称',
  `payment_pattern_type` int(11) NULL DEFAULT NULL COMMENT '支付方式类型',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `price` decimal(21, 6) NOT NULL COMMENT '价格',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `shipped_quantity` int(11) NOT NULL COMMENT '已发货数量',
  `shipping_method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送方式名称',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `status` int(11) NOT NULL COMMENT '状态',
  `weight` int(11) NOT NULL COMMENT '重量',
  `zip_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `region_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `member_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员',
  `payment_pattern_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `shipping_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送方式',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sn`(`sn`) USING BTREE,
  INDEX `ind_Orders_region_id`(`region_id`) USING BTREE,
  INDEX `ind_Orders_member_id`(`member_id`) USING BTREE,
  INDEX `ind_Orders_paymentPattern_id`(`payment_pattern_id`) USING BTREE,
  INDEX `ind_Orders_shippingMethod_id`(`shipping_method_id`) USING BTREE,
  INDEX `ind_Orders_store_id`(`store_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_orders
-- ----------------------------
INSERT INTO `biz_orders` VALUES ('1769302445366935554', '2024-03-17 17:59:10', '2024-06-24 18:37:33', 0, 'test', 1299.900000, 1299.900000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 1299.900000, 2, 2, 'Express', '10224', 3, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1771836269736927234', '2024-03-24 17:47:40', '2024-06-24 19:06:37', 0, 'test', 1268.950000, 1268.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 1268.950000, 4, 2, 'Express', '10225', 2, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1772127610073214977', '2024-03-25 13:05:21', '2024-06-24 18:33:13', 0, '333', 749.000000, 749.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 799.000000, 1, 0, 'Express', '10226', 2, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1799781575824748547', '2024-06-09 20:32:21', '2024-06-09 20:34:04', 0, '333', 2274.000000, 2274.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 2874.000000, 1, 0, 'Express', '10227', 1, 0, '333', '3', '9951', '1', '1', '5', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1804754784055676930', '2024-06-23 13:54:06', '2024-06-24 18:51:43', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 3, 1, 'Express', '10228', 6, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805185019209269251', '2024-06-24 18:23:42', '2024-06-24 18:23:45', 0, '333', 20.000000, 20.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 20.000000, 1, 0, 'Express', '10229', 1, 0, '333', '3', '9951', '1', '1', '1154265608284581889', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805185151958990851', '2024-06-24 18:24:14', '2024-06-24 18:51:41', 0, 'test', 619.000000, 619.000000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 619.000000, 3, 1, 'Express', '10230', 6, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805196467331350529', '2024-06-24 19:09:11', '2024-06-24 19:16:21', 0, 'test', 619.000000, 619.000000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 619.000000, 3, 1, 'Express', '10231', 2, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805202154941792258', '2024-06-24 19:31:47', '2024-06-24 19:32:46', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 3, 1, 'Express', '10232', 2, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805203638395813891', '2024-06-24 19:37:41', '2024-09-03 13:15:08', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 1, 1, 'Express', '10233', 4, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805524535749091331', '2024-06-25 16:52:49', '2024-06-25 16:53:01', 0, '333', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10234', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805524893128957954', '2024-06-25 16:54:14', '2024-06-25 16:58:18', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'tese', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 3, 1, 'Express', '10235', 2, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805528242859515905', '2024-06-25 17:07:33', '2024-06-25 17:11:39', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 1, 1, 'Express', '10236', 3, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805528775984914435', '2024-06-25 17:09:40', '2024-06-25 17:10:44', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 3, 3, 'Express', '10237', 3, 0, '-5e-18', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805529761927372802', '2024-06-25 17:13:35', '2024-09-03 13:15:22', 0, 'test', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'test', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '16619915737', 649.950000, 1, 1, 'Express', '10238', 4, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805959771200892931', '2024-06-26 21:42:17', '2024-06-26 21:51:00', 0, '333', 1631.000000, 1631.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 1631.000000, 1, 0, 'Express', '10239', 1, 0, '333', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805959822774054915', '2024-06-26 21:42:30', '2024-06-26 21:51:00', 0, '333', 1299.900000, 1299.900000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 1299.900000, 2, 0, 'Express', '10240', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1805990386071711746', '2024-06-26 23:43:56', NULL, 0, '333', 649.950000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-06-27 23:43:56', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10241', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1806127251156316162', '2024-06-27 08:47:48', NULL, 0, '333', 619.000000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-06-28 08:47:47', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 619.000000, 1, 0, 'Express', '10242', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1806271038893473794', '2024-06-27 18:19:09', NULL, 0, '333', 619.000000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-06-28 18:19:09', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 619.000000, 1, 0, 'Express', '10243', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1806636132739522563', '2024-06-28 18:29:54', NULL, 0, '333', 619.000000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-06-29 18:29:54', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 619.000000, 1, 0, 'Express', '10244', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1807004594905227266', '2024-06-29 18:54:03', NULL, 0, '333', 649.950000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-06-30 18:54:03', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10245', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1807354720777654273', '2024-06-30 18:05:19', NULL, 0, '333', 619.000000, 0.000000, '北京市,北京市,东城区', NULL, '333', '2024-07-01 18:05:19', 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 619.000000, 1, 0, 'Express', '10246', 0, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1808170197754007554', '2024-07-03 00:05:44', '2024-07-03 09:10:54', 0, '333', 10.000000, 10.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 10.000000, 1, 0, 'Express', '10247', 1, 0, '333', '3', '9951', '1', '1', '1154265608284581889', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830833373746286595', '2024-09-03 13:01:06', '2024-09-03 13:09:15', 0, '333', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10248', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830897501286420482', '2024-09-03 17:15:55', '2024-09-03 17:16:00', 0, '333', 10.000000, 10.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 10.000000, 1, 0, 'Express', '10249', 1, 0, '333', '3', '9951', '1', '1', '1154265608284581889', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830897745495576579', '2024-09-03 17:16:54', '2024-09-03 17:17:00', 0, '333', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10250', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830897764005040131', '2024-09-03 17:16:58', '2024-09-03 17:17:01', 0, '333', 345.990000, 345.990000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 395.990000, 1, 0, 'Express', '10251', 1, 0, '333', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830917944286367746', '2024-09-03 18:37:09', '2024-09-03 18:37:15', 0, '333', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10252', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1830917962732916739', '2024-09-03 18:37:14', '2024-09-03 18:37:15', 0, '333', 345.990000, 345.990000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 395.990000, 1, 0, 'Express', '10253', 1, 0, '333', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831193076707872770', '2024-09-04 12:50:26', '2024-09-04 12:50:28', 0, '333', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 649.950000, 1, 0, 'Express', '10254', 1, 0, '333', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831193168051425283', '2024-09-04 12:50:48', '2024-09-04 12:50:58', 0, '333', 1631.000000, 1631.000000, '北京市,北京市,东城区', NULL, '333', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '3334', 1631.000000, 1, 0, 'Express', '10255', 1, 0, '333', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831285718498136066', '2024-09-04 18:58:34', '2024-09-04 18:58:36', 0, '312', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, 'fdsa', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12323', 649.950000, 1, 0, 'Express', '10256', 1, 0, '12321', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831285794406649859', '2024-09-04 18:58:52', '2024-09-04 18:58:53', 0, '312', 619.000000, 619.000000, '北京市,北京市,东城区', NULL, 'fdsa', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12323', 619.000000, 1, 0, 'Express', '10257', 1, 0, '12321', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831287243647733763', '2024-09-04 19:04:37', '2024-09-21 17:17:39', 0, '1', 619.000000, 619.000000, '北京市,北京市,东城区', NULL, '121', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '121', 619.000000, 1, 1, 'Express', '10258', 3, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831287307539566594', '2024-09-04 19:04:52', '2024-09-04 19:04:54', 0, '312', 1631.000000, 1631.000000, '北京市,北京市,东城区', NULL, 'fdsa', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12323', 1631.000000, 1, 0, 'Express', '10259', 1, 0, '12321', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831289626503139330', '2024-09-04 19:14:05', '2024-09-17 22:12:10', 0, '1', 649.950000, 649.950000, '北京市,北京市,东城区', NULL, '1', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '1', 649.950000, 1, 1, 'Express', '10260', 3, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1831289735596986371', '2024-09-04 19:14:31', '2024-09-06 13:19:09', 0, '1', 619.000000, 619.000000, '北京市,北京市,东城区', NULL, '1', NULL, 0.000000, b'0', '', 0.000000, 'Online Payment', 0, '1', 619.000000, 1, 1, 'Express', '10261', 3, 0, '1', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1835963059597996035', '2024-09-17 16:44:39', '2024-09-17 21:47:24', 0, '312', 659.950000, 659.950000, '北京市,北京市,东城区', NULL, 'fdsa', NULL, 10.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12323', 649.950000, 1, 0, 'Express', '10262', 8, 0, '12321', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1835990038854426627', '2024-09-17 18:31:51', '2024-09-21 18:36:58', 0, '1', 659.950000, 659.950000, '北京市,北京市,东城区', NULL, '1', NULL, 10.000000, b'0', '', 0.000000, 'Online Payment', 0, '1', 649.950000, 1, 1, 'Express', '10263', 4, 0, '', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1835992347428708355', '2024-09-17 18:41:01', '2024-09-17 18:41:03', 0, '12', 1631.000000, 1631.000000, '北京市,北京市,东城区', NULL, '12', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12', 1631.000000, 1, 0, 'Express', '10264', 1, 0, '12', '3', '9951', '1', '1', '2', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1836016823629438979', '2024-09-17 20:18:17', '2024-09-17 20:18:19', 0, '12', 72.000000, 72.000000, '北京市,北京市,东城区', NULL, '12', NULL, 0.000000, b'0', NULL, 0.000000, 'Online Payment', 0, '12', 72.000000, 1, 0, 'Express', '10265', 1, 0, '12', '3', '9951', '1', '1', '5', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1837440074155003906', '2024-09-21 18:33:46', '2024-09-21 18:36:24', 0, '12', 111121.000000, 111121.000000, '北京市,北京市,东城区', NULL, '12', NULL, 10.000000, b'1', NULL, 0.000000, 'Online Payment', 0, '12', 111111.000000, 1, 0, 'Express', '10266', 1, 0, '12', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1837501763260981250', '2024-09-21 22:38:54', '2024-09-21 22:38:56', 0, '12', 659.950000, 659.950000, '北京市,北京市,东城区', NULL, '12', NULL, 10.000000, b'1', NULL, 0.000000, 'Online Payment', 0, '12', 649.950000, 1, 0, 'Express', '10267', 1, 0, '12', '3', '9951', '1', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_orders` VALUES ('1837825254292127747', '2024-09-22 20:04:20', '2024-09-22 20:04:23', 0, '12', 111121.000000, 111121.000000, '北京市,北京市,东城区', NULL, '12', NULL, 10.000000, b'1', NULL, 0.000000, 'Online Payment', 0, '12', 111111.000000, 1, 0, 'Express', '10268', 1, 0, '12', '3', '9951', '1', '1', '1', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_payment_config
-- ----------------------------
DROP TABLE IF EXISTS `biz_payment_config`;
CREATE TABLE `biz_payment_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `propertys` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性',
  `mode_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '支付方式ID',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `modeId`(`mode_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_payment_config
-- ----------------------------
INSERT INTO `biz_payment_config` VALUES ('3', '2023-10-28 00:00:00', '2023-12-03 20:12:03', 1, 2, '{\"alipayPublicKey\":\"abcd\",\"displayName\":\"支付宝\",\"appPrivateKey\":\"abcd\",\"appId\":\"abcd\",\"fee\":\"0\",\"logo\":\"http://qiniuio.bizspring.cn/payment_demo/aa348738-a710-4330-915d-a2d2475681e6.png\",\"description\":null,\"feeType\":\"SCALE\"}', 'alipayWapPayment', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_payment_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_payment_log`;
CREATE TABLE `biz_payment_log`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `amount` decimal(21, 6) NOT NULL COMMENT '支付金额',
  `expire` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `is_success` bit(1) NULL DEFAULT NULL COMMENT '是否成功',
  `payment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
  `payment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式名称',
  `re_pay_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重新支付URL',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父支付',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_payment_log
-- ----------------------------
INSERT INTO `biz_payment_log` VALUES (1769302474391519233, '2024-03-17 17:59:17', '2024-03-17 17:59:16', 0, 1299.900000, '2024-03-18 17:59:17', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10146', 1769302445366935554, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1771836334832525313, '2024-03-24 17:47:56', '2024-03-24 17:47:56', 0, 1268.950000, '2024-03-25 17:47:56', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10147', 1771836269736927234, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1772128529473040386, '2024-03-25 13:09:01', '2024-03-25 13:09:00', 0, 749.000000, '2024-03-26 13:09:01', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10148', 1772127610073214977, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1799782010006515714, '2024-06-09 20:34:04', '2024-06-09 20:34:04', 0, 2274.000000, '2024-06-10 20:34:04', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10149', 1799781575824748547, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1804754802690969602, '2024-06-23 13:54:10', '2024-06-23 13:54:10', 0, 649.950000, '2024-06-24 13:54:10', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10150', 1804754784055676930, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805185032928837634, '2024-06-24 18:23:45', '2024-06-24 18:23:45', 0, 20.000000, '2024-06-25 18:23:45', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10151', 1805185019209269251, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805185160662171650, '2024-06-24 18:24:16', '2024-06-24 18:24:15', 0, 619.000000, '2024-06-25 18:24:16', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10152', 1805185151958990851, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805196475262779394, '2024-06-24 19:09:13', '2024-06-24 19:09:13', 0, 619.000000, '2024-06-25 19:09:13', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10153', 1805196467331350529, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805202165851176961, '2024-06-24 19:31:50', '2024-06-24 19:31:50', 0, 649.950000, '2024-06-25 19:31:50', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10154', 1805202154941792258, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805203648134987778, '2024-06-24 19:37:43', '2024-06-24 19:37:43', 0, 649.950000, '2024-06-25 19:37:43', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10155', 1805203638395813891, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805524584755339266, '2024-06-25 16:53:01', '2024-06-25 16:53:00', 0, 649.950000, '2024-06-26 16:53:01', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10156', 1805524535749091331, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805524905179193345, '2024-06-25 16:54:17', '2024-06-25 16:54:17', 0, 649.950000, '2024-06-26 16:54:17', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10157', 1805524893128957954, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805528253043286017, '2024-06-25 17:07:35', '2024-06-25 17:07:35', 0, 649.950000, '2024-06-26 17:07:35', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10158', 1805528242859515905, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805528809111527426, '2024-06-25 17:09:48', '2024-06-25 17:09:48', 0, 649.950000, '2024-06-26 17:09:48', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10159', 1805528775984914435, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1805529777110753282, '2024-06-25 17:13:39', '2024-06-25 17:13:38', 0, 649.950000, '2024-06-26 17:13:39', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10160', 1805529761927372802, NULL, NULL, NULL);
INSERT INTO `biz_payment_log` VALUES (1837825262747844610, '2024-09-22 20:04:22', '2024-09-22 20:04:22', 0, 111121.000000, '2024-09-23 20:04:22', b'1', 'alipayWapPayment', 'alipay(Wap)', 'test', '10095', 1837825254292127747, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for biz_sn
-- ----------------------------
DROP TABLE IF EXISTS `biz_sn`;
CREATE TABLE `biz_sn`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `last_value` bigint(20) NOT NULL COMMENT '末值',
  `type` int(11) NOT NULL COMMENT '类型',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '序列号' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_sn
-- ----------------------------
INSERT INTO `biz_sn` VALUES ('1', '2023-10-28 00:00:00', '2023-11-10 00:54:14', 10, 10010, 0, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('10', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 10000, 9, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('2', '2023-10-28 00:00:00', '2024-09-22 20:04:20', 270, 10269, 1, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('3', '2023-10-28 00:00:00', '2024-09-22 20:04:23', 287, 10286, 2, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('4', '2023-10-28 00:00:00', '2024-09-21 17:17:38', 72, 10072, 3, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('5', '2023-10-28 00:00:00', '2024-09-22 20:04:22', 97, 10096, 4, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('6', '2023-10-28 00:00:00', '2023-11-01 11:53:01', 3, 10002, 5, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('7', '2023-10-28 00:00:00', '2024-09-04 19:14:32', 193, 10192, 6, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('8', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 10000, 7, b'0', b'0', '0');
INSERT INTO `biz_sn` VALUES ('9', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 10000, 8, b'0', b'0', '0');

SET FOREIGN_KEY_CHECKS = 1;
