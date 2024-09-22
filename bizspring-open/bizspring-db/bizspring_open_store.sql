/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : bizspring_open_store

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 22/09/2024 20:42:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_default_freight
-- ----------------------------
DROP TABLE IF EXISTS `biz_default_freight`;
CREATE TABLE `biz_default_freight`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `continue_price` decimal(21, 6) NOT NULL COMMENT '续重价格',
  `continue_weight` int(11) NOT NULL COMMENT '续重量',
  `first_price` decimal(21, 6) NOT NULL COMMENT '首重价格',
  `first_weight` int(11) NOT NULL COMMENT '首重量',
  `shipping_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配送方式',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_DefaultFreight_shippingMethod_id`(`shipping_method_id`) USING BTREE,
  INDEX `ind_DefaultFreight_store_id`(`store_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '默认运费配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_default_freight
-- ----------------------------
INSERT INTO `biz_default_freight` VALUES ('1693528094475005954', '2023-08-21 15:38:57', '2024-09-21 17:18:31', 3, 2.000000, 2, 2.000000, 2, '1', '1', b'0', b'0', '0');
INSERT INTO `biz_default_freight` VALUES ('1693852620098351105', '2023-08-22 13:08:30', NULL, 0, 55.000000, 55, 55.000000, 55, '1635489434109624321', '1', b'0', b'0', '0');
INSERT INTO `biz_default_freight` VALUES ('1693853119262470145', '2023-08-22 13:10:29', NULL, 0, 5.000000, 5, 5.000000, 5, '1635489432205410305', '1', b'0', b'0', '0');
INSERT INTO `biz_default_freight` VALUES ('1711693628852924417', '2023-10-10 18:42:18', NULL, 0, 20.000000, 1, 20.000000, 1, '2', '1', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_express_company
-- ----------------------------
DROP TABLE IF EXISTS `biz_express_company`;
CREATE TABLE `biz_express_company`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网址',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递公司' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_express_company
-- ----------------------------
INSERT INTO `biz_express_company` VALUES ('1', '2023-10-28 00:00:00', '2024-03-12 15:30:30', 2, 1, 'shunfeng', 'sf-express', 'http://www.sf-express.com', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1531553012028698626', '2023-05-31 16:27:48', '2024-03-12 15:29:27', 5, 4, '1', 'EMS', 'https://www.ems.com.cn', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1533651626976788482', '2023-06-06 11:26:56', '2024-03-12 15:30:11', 3, 3, '3', 'JDL', 'https://www.jdl.com/', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1635489019733360642', '2023-03-14 11:52:23', '2024-03-12 15:30:01', 4, 8, NULL, 'yundaex', 'http://www.yundaex.com/', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1635489186045902850', '2023-03-14 11:53:03', '2024-03-12 15:29:48', 4, 7, NULL, 'ZTO', 'https://www.zto.com/', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1635489187274833922', '2023-03-14 11:53:03', '2024-03-12 15:29:36', 5, 6, NULL, 'STO', 'https://www.sto.cn', b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('1705074725006589954', '2023-09-22 12:21:08', '2024-03-12 15:28:55', 1, 9, NULL, 'Merchant Delive', NULL, b'0', b'0', '0');
INSERT INTO `biz_express_company` VALUES ('2', '2023-10-28 00:00:00', '2024-03-12 15:30:20', 2, 2, 'yuantong', 'YTO', 'http://www.yto.net.cn', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_goods_category_store
-- ----------------------------
DROP TABLE IF EXISTS `biz_goods_category_store`;
CREATE TABLE `biz_goods_category_store`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `stores_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺',
  `goods_categories_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品分类',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  `version` bigint(20) NOT NULL COMMENT '版本',
  PRIMARY KEY (`stores_id`, `goods_categories_id`, `id`) USING BTREE,
  INDEX `ind_Store_GoodsCategory_goodsCategories_id`(`goods_categories_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_goods_category_store
-- ----------------------------
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '1', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('1673609581590261762', NULL, NULL, '1', '1', b'0', b'0', '0', 1);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '10', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '100', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '101', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '102', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '103', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '104', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '105', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '106', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '11', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '12', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '13', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '14', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '55', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '56', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '57', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '58', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '59', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '60', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '61', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '62', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '63', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '64', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '65', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '66', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '67', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '68', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '69', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '7', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('1673613291292766210', NULL, NULL, '1', '7', b'0', b'0', '0', 1);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '70', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '71', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '72', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '73', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '74', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '75', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '76', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '77', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '78', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '79', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '8', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '80', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '81', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '82', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '83', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '84', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '85', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '86', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '87', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '88', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '89', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '9', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '90', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '91', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '92', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '93', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '94', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '95', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '96', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '97', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '98', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1', '99', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '1154265608284581889', '394', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('1673583197308411906', NULL, NULL, '1154265608284581889', '394', b'0', b'0', '0', 1);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '107', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '108', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '109', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '110', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '111', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '112', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '113', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '114', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '115', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '116', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '117', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '118', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '119', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '120', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '121', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '122', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '123', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '124', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '125', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '126', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '127', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '128', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '129', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '130', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '131', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '132', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '133', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '134', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '135', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '136', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '137', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '138', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '139', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '140', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '141', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '142', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '143', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '144', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '145', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '146', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '147', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '148', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '149', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '15', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '150', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '151', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '152', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '153', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '154', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '155', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '156', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '157', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '158', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '159', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '16', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '160', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '161', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '162', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '163', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '164', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '165', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '166', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '167', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '168', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '169', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '17', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '170', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '18', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '19', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '2', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '20', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '21', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '2', '22', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '171', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '172', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '173', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '174', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '175', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '176', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '177', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '178', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '179', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '180', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '181', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '182', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '183', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '184', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '185', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '186', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '187', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '188', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '189', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '190', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '191', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '192', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '193', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '194', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '195', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '196', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '197', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '198', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '199', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '200', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '201', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '202', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '203', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '204', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '205', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '206', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '207', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '208', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '209', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '210', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '211', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '212', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '213', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '214', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '215', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '216', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '217', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '218', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '219', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '220', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '221', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '222', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '223', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '224', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '225', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '226', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '23', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '24', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '25', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '26', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '27', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '28', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '29', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '3', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '3', '30', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '227', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '228', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '229', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '230', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '231', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '232', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '233', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '234', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '235', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '236', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '237', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '238', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '239', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '240', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '241', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '242', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '243', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '244', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '245', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '246', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '247', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '248', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '249', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '250', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '251', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '252', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '253', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '254', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '255', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '256', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '257', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '258', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '259', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '260', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '261', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '262', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '263', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '264', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '265', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '266', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '267', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '268', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '269', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '270', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '271', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '272', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '273', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '274', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '275', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '276', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '277', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '278', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '279', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '280', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '281', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '31', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '32', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '33', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '34', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '35', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '36', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '37', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '38', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '4', '4', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '282', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '283', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '284', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '285', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '286', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '287', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '288', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '289', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '290', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '291', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '292', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '293', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '294', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '295', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '296', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '297', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '298', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '299', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '300', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '301', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '302', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '303', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '304', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '305', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '306', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '307', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '308', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '309', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '310', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '311', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '312', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '313', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '314', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '315', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '316', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '317', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '318', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '319', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '320', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '321', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '322', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '323', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '324', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '325', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '326', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '327', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '328', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '329', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '330', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '331', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '332', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '333', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '334', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '335', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '336', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '337', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '338', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '39', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '40', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '41', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '42', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '43', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '44', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '45', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '46', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '5', '5', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '339', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '340', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '341', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '342', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '343', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '344', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '345', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '346', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '347', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '348', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '349', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '350', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '351', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '352', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '353', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '354', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '355', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '356', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '357', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '358', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '359', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '360', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '361', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '362', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '363', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '364', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '365', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '366', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '367', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '368', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '369', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '370', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '371', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '372', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '373', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '374', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '375', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '376', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '377', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '378', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '379', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '380', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '381', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '382', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '383', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '384', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '385', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '386', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '387', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '388', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '389', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '390', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '391', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '392', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '393', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '394', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '395', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '47', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '48', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '49', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '50', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '51', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '52', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '53', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '54', b'0', b'0', '0', 0);
INSERT INTO `biz_goods_category_store` VALUES ('', NULL, NULL, '6', '6', b'0', b'0', '0', 0);

-- ----------------------------
-- Table structure for biz_payment_pattern
-- ----------------------------
DROP TABLE IF EXISTS `biz_payment_pattern`;
CREATE TABLE `biz_payment_pattern`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `pattern` int(11) NOT NULL COMMENT '方式',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `timeout` int(11) NULL DEFAULT NULL COMMENT '超时时间',
  `type` int(11) NOT NULL COMMENT '类型',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_payment_pattern
-- ----------------------------
INSERT INTO `biz_payment_pattern` VALUES ('1', '2023-10-28 00:00:00', '2024-03-22 17:42:35', 4, 2, '<p><br></p>', '', NULL, 0, 'Online Payment', 1440, 0, b'0', b'0', '0');
INSERT INTO `biz_payment_pattern` VALUES ('1534151245057904641', '2023-06-07 20:32:15', '2024-03-22 17:42:26', 6, 1, '<p>33366</p>', '33366', NULL, 1, 'Cash on delivery', 33366, 1, b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_region_freight
-- ----------------------------
DROP TABLE IF EXISTS `biz_region_freight`;
CREATE TABLE `biz_region_freight`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `continue_price` decimal(21, 6) NOT NULL COMMENT '续重价格',
  `continue_weight` int(11) NOT NULL COMMENT '续重量',
  `first_price` decimal(21, 6) NOT NULL COMMENT '首重价格',
  `first_weight` int(11) NOT NULL COMMENT '首重量',
  `shipping_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配送方式',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺',
  `region_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_RegionFreight_shippingMethod_id`(`shipping_method_id`) USING BTREE,
  INDEX `ind_RegionFreight_store_id`(`store_id`) USING BTREE,
  INDEX `ind_RegionFreight_region_id`(`region_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区运费配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_region_freight
-- ----------------------------
INSERT INTO `biz_region_freight` VALUES ('1693837414865547265', '2023-08-22 12:08:04', '2023-10-27 15:57:21', 13, 0.000000, 1, 10.000000, 1, '1', '1', '3', b'0', b'0', '0');
INSERT INTO `biz_region_freight` VALUES ('1693853445164085250', '2023-08-22 13:11:46', NULL, 0, 10.000000, 1000, 20.000000, 1000, '1635489432205410305', '1', '803', b'0', b'0', '0');
INSERT INTO `biz_region_freight` VALUES ('1703696404943454210', '2023-09-18 17:04:11', '2023-10-10 18:43:45', 3, 8.000000, 1, 10.000000, 1, '1', '1', '353', b'0', b'0', '0');
INSERT INTO `biz_region_freight` VALUES ('1711693552671780865', '2023-10-10 18:41:59', NULL, 0, 20.000000, 1, 20.000000, 1, '2', '1', '3', b'0', b'0', '0');
INSERT INTO `biz_region_freight` VALUES ('1836038906145107969', '2024-09-17 21:46:02', '2024-09-21 17:18:02', 3, 12.000000, 12, 12.000000, 12, '1', '1', '5', b'0', b'0', '0');
INSERT INTO `biz_region_freight` VALUES ('1837421081671225346', '2024-09-21 17:18:18', NULL, 0, 1.000000, 1, 1.000000, 1, '1', '1', '7', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_shipping_method
-- ----------------------------
DROP TABLE IF EXISTS `biz_shipping_method`;
CREATE TABLE `biz_shipping_method`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `default_express_company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认快递公司',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_ShippingMethod_defaultExpressCompany_id`(`default_express_company_id`) USING BTREE,
  INDEX `orders`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配送方式' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_shipping_method
-- ----------------------------
INSERT INTO `biz_shipping_method` VALUES ('1', '2023-10-28 00:00:00', '2024-03-22 17:43:30', 4, 1, '', NULL, 'Express', '2', b'0', b'0', '0');
INSERT INTO `biz_shipping_method` VALUES ('2', '2023-10-28 00:00:00', '2024-03-22 17:43:34', 5, 2, NULL, NULL, 'Arrive in time', '1705074725006589954', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_shipping_method_payment_pattern
-- ----------------------------
DROP TABLE IF EXISTS `biz_shipping_method_payment_pattern`;
CREATE TABLE `biz_shipping_method_payment_pattern`  (
  `shipping_methods_id` bigint(20) NOT NULL COMMENT '配送方式',
  `payment_patterns_id` bigint(20) NOT NULL COMMENT '支付方式',
  PRIMARY KEY (`shipping_methods_id`, `payment_patterns_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_shipping_method_payment_pattern
-- ----------------------------
INSERT INTO `biz_shipping_method_payment_pattern` VALUES (2, 1);

-- ----------------------------
-- Table structure for biz_store
-- ----------------------------
DROP TABLE IF EXISTS `biz_store`;
CREATE TABLE `biz_store`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'E-mail',
  `end_date` datetime(0) NOT NULL COMMENT '到期日期',
  `introduction` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简介',
  `is_enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '搜索关键词',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `zip_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商家',
  `store_category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺分类',
  `aftersales_config_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '售后设置',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `ind_Store_merchant_id`(`merchant_id`) USING BTREE,
  INDEX `ind_Store_storeCategory_id`(`store_category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_store
-- ----------------------------
INSERT INTO `biz_store` VALUES ('1', '2023-10-28 00:00:00', '2024-03-27 07:45:13', 57, 'Beijin', 'biz1@bizspring.cn', '2041-12-29 00:00:00', '11226', b'1', '11226', 'http://qiniuio.bizspring.cn//1/profile/ee7b0126-8cd4-473e-b993-da1baec41e8c_source.jpg/0c74cf32-4e78-43c2-9274-f6426c3cd833.tmp', '16619915737', 'Apple Official Store', '400-1111116', 3, 1, '410106', '1711617632132882434', '1', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('1154265608284581889', '2023-07-25 13:42:21', '2024-03-15 13:21:26', 8, NULL, '1111@qq.com', '2029-07-24 13:45:46', NULL, b'1', '', '', '13500000000', 'Official Medicine Store', NULL, 3, 0, NULL, '1712337155416883202', '2', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('1154296527179526145', '2023-07-25 15:45:13', '2024-03-19 23:48:56', 5, NULL, '111123@qq.com', '2029-07-25 00:00:00', NULL, b'1', NULL, NULL, '13500000001', 'Xiaomi Official Store', NULL, 3, 0, NULL, '1154296371893809153', '1', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('1511276570783166465', '2023-04-05 17:36:27', '2024-03-15 13:30:31', 9, NULL, '111222333@163.com', '2029-04-05 17:36:27', NULL, b'1', NULL, NULL, '12361231231', 'LOREAL Store', NULL, 3, 1, NULL, '1154276382306510000', '5', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('1767782610807951361', '2024-03-13 13:19:53', '2024-03-27 07:45:58', 3, NULL, 'OpenAI@163.com', '2029-03-13 13:19:53', NULL, b'1', NULL, NULL, '16619915737', 'OpenAI Official Store', NULL, 2, 0, NULL, '2', '1', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('2', '2023-10-28 00:00:00', '2024-03-13 18:38:48', 4, 'Beijin Haidian', 'biz2@bizspring.cn', '2030-01-01 00:00:00', NULL, b'1', NULL, 'http://qiniuio.bizspring.cn//1/profile/1980f9f2-ce04-4385-8ad3-454ac387efb7_source.jpg/71e56799-f126-4f6e-a66e-48e2163885e3.tmp', '13822222222', 'HUAWEI Official Store', '400-2222222', 3, 0, '410100', '3', '2', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('3', '2023-10-28 00:00:00', '2024-03-15 13:36:28', 6, 'Beijing Haidian', 'biz4@bizspring.cn', '2030-01-01 00:00:00', NULL, b'1', NULL, 'http://qiniuio.bizspring.cn//1/admin/efb4fb13-12f9-440c-808c-7203914ed463.png', '13833333333', 'Fashion Clothing store', '400-3333333', 3, 1, '410100', '5', '3', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('4', '2023-10-28 00:00:00', '2024-03-19 23:50:52', 3, 'Beijin Haidian', 'biz04@bizspring.cn', '2030-01-01 00:00:00', NULL, b'0', NULL, '', '13844444444', 'Beauty Store', '400-4444444', 3, 0, '410100', '5', '5', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('5', '2023-10-28 00:00:00', '2024-03-15 13:28:57', 10, 'Beijin Haidian', 'biz5@bizspring.cn', '2030-01-01 00:00:00', NULL, b'1', NULL, 'http://qiniuio.bizspring.cn//1/admin/7d558fd2-cb62-40d8-b693-80ceafabcd13.jpg', '13855555555', 'Chanel Official Store', '400-5555555', 3, 1, '410100', '6', '5', NULL, b'0', b'0', '0');
INSERT INTO `biz_store` VALUES ('6', '2023-10-28 00:00:00', '2024-03-19 23:48:04', 4, 'Beijing Haidian', 'biz6@bizspring.cn', '2030-01-01 00:00:00', NULL, b'1', NULL, '', '13866666666', 'Produce Store', '400-6666666', 3, 0, '410100', '7', '1651566808955662337', NULL, b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_store_category
-- ----------------------------
DROP TABLE IF EXISTS `biz_store_category`;
CREATE TABLE `biz_store_category`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_store_category
-- ----------------------------
INSERT INTO `biz_store_category` VALUES ('1', '2023-10-28 00:00:00', '2024-03-15 13:25:16', 8, 8, 'Electronics', b'0', b'0', '0');
INSERT INTO `biz_store_category` VALUES ('1651566808955662337', '2023-04-27 20:39:47', '2024-03-13 14:56:32', 3, 1, 'Produce', b'0', b'0', '0');
INSERT INTO `biz_store_category` VALUES ('2', '2023-10-28 00:00:00', '2024-03-13 14:56:29', 4, 2, 'Medicine', b'0', b'0', '0');
INSERT INTO `biz_store_category` VALUES ('3', '2023-10-28 00:00:00', '2024-03-13 14:56:26', 3, 3, 'Costume', b'0', b'0', '0');
INSERT INTO `biz_store_category` VALUES ('5', '2023-10-28 00:00:00', '2024-03-15 13:25:49', 4, 5, 'Beauty & Personal Care', b'0', b'0', '0');

-- ----------------------------
-- Table structure for biz_store_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `biz_store_goods_category`;
CREATE TABLE `biz_store_goods_category`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `level` int(11) NOT NULL COMMENT '层级',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '树路径',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级分类',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺',
  `lock_flag` bit(1) NULL DEFAULT b'0' COMMENT '0-正常，9-锁定',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_StoreGoodsCategory_parent_id`(`parent_id`) USING BTREE,
  INDEX `ind_StoreGoodsCategory_store_id`(`store_id`) USING BTREE,
  INDEX `orders`(`sort`) USING BTREE,
  INDEX `level`(`level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺商品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_store_goods_category
-- ----------------------------
INSERT INTO `biz_store_goods_category` VALUES ('1', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 0, '手机通讯', ',', NULL, '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('10', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '车载蓝牙', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('11', '2023-10-28 00:00:00', '2023-07-11 13:45:10', 1, 6, 1, '手机支架', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('1154267112584294401', '2023-07-25 13:48:20', NULL, 0, 1, 0, '一般有钱', ',', NULL, '1154265608284581889', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('1154309384554196994', '2023-07-25 16:36:18', NULL, 0, 1, 0, '手机', ',', NULL, '1154296527179526145', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('12', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '手机饰品', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('13', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 0, '电脑整机', ',', NULL, '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('14', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 0, '家居电器', ',', NULL, '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('15', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '笔记本', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('16', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '平板电脑', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('17', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '一体机', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('1711680895264747522', '2023-10-10 17:51:42', NULL, 0, NULL, 1, '手机测试分类', ',1,', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('18', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '超极本', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('19', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '游戏本', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('2', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 0, '手机配件', ',', NULL, '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('20', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '台式机', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('21', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '服务器', ',13,', '13', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('22', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '平板电视', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('23', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '家用空调', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('24', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '家庭音影', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('25', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '迷你音箱', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('26', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '洗衣机', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('27', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '饮水机', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('28', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '冰箱', ',14,', '14', '2', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('29', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 0, '特色女装', ',', NULL, '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('3', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '智能手机', ',1,', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('30', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 0, '精品男装', ',', NULL, '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('31', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 0, '潮流女鞋', ',', NULL, '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('32', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 0, '品质男鞋', ',', NULL, '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('33', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 0, '功能箱包', ',', NULL, '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('34', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '精致礼服', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('35', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '蕾丝衣裙', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('36', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '毛呢大衣', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('37', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '职业套装', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('38', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '精品婚纱', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('39', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '连衣裙', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('4', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '老人手机', ',1,', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('40', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '羽绒服', ',29,', '29', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('41', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '西服套装', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('42', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '修身夹克', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('43', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '长袖衬衫', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('44', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '毛呢大衣', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('45', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '时尚风衣', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('46', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '工装外套', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('47', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '针织开衫', ',30,', '30', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('48', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '高跟皮鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('49', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '平底单鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('5', '2023-10-28 00:00:00', '2023-07-11 13:44:57', 1, 3, 1, '对讲机', ',1,', '1', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('50', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '通勤网鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('51', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '正装皮鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('52', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '时尚凉鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('53', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '运动跑鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('54', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '商务板鞋', ',31,', '31', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('55', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '商务皮鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('56', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '休闲布鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('57', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '时尚板鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('58', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '复古跑鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('59', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '户外休闲', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('6', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '手机电池', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('60', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '家居拖鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('61', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '品牌凉鞋', ',32,', '32', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('62', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '女士钱包', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('63', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '女斜挎包', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('64', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '女拉杆箱', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('65', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '男士钱包', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('66', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '男手提包', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('67', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '男士背包', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('68', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '皮具套装', ',33,', '33', '3', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('69', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 0, '黄金首饰', ',', NULL, '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('7', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '移动电源', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('70', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 0, '钻石彩宝', ',', NULL, '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('71', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '黄金项链', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('72', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '黄金戒指', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('73', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '黄金手链', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('74', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '黄金耳钉', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('75', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '婚嫁套饰', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('76', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '足金饰品', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('77', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '黄金摆饰', ',69,', '69', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('78', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '结婚钻戒', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('79', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '钻石项链', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('8', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '手机耳机', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('80', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '钻石耳饰', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('81', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '钻石吊坠', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('82', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '天然钻石', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('83', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '钻石饰品', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('84', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '天然裸钻', ',70,', '70', '4', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('85', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 0, '面部护理', ',', NULL, '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('86', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 1, 1, '补水保湿', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('87', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 2, 1, '乳液面霜', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('88', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 3, 1, '防晒隔离', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('89', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '敏感修护', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('9', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 4, 1, '拍照设备', ',2,', '2', '1', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('90', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 5, 1, '护肤套装', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('91', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 6, 1, '面部精华', ',85,', '85', '5', b'0', b'0', '0');
INSERT INTO `biz_store_goods_category` VALUES ('92', '2023-10-28 00:00:00', '2023-10-28 00:00:00', 0, 7, 1, '清爽控油', ',85,', '85', '5', b'0', b'0', '0');

SET FOREIGN_KEY_CHECKS = 1;
