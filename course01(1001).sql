/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : course01

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2023-10-01 20:13:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `apid` int NOT NULL AUTO_INCREMENT,
  `auid` int NOT NULL,
  `cid` int NOT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `resume` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tranciption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enrolment_details_semester` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `working_hours` int DEFAULT NULL,
  `overseas` tinyint(1) DEFAULT NULL,
  `willback` tinyint(1) DEFAULT NULL,
  `permanent` tinyint(1) DEFAULT NULL,
  `visa` tinyint(1) DEFAULT NULL,
  `contracts` tinyint(1) DEFAULT NULL,
  `undergraduate` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grades` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `studied` tinyint(1) DEFAULT NULL,
  `qualification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `experience` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `priority` tinyint(1) DEFAULT NULL,
  `qualified` tinyint(1) DEFAULT NULL,
  `rate` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `minGrade` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`apid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of application
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `cartid` int NOT NULL AUTO_INCREMENT,
  `auid` int DEFAULT NULL COMMENT 'user id',
  `cid` int DEFAULT NULL COMMENT 'course id',
  `favors` int DEFAULT NULL COMMENT 'favors',
  `applied` tinyint(1) DEFAULT NULL COMMENT 'applied or not',
  PRIMARY KEY (`cartid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cid` int NOT NULL AUTO_INCREMENT COMMENT 'course id',
  `cname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'course name',
  `term` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'term',
  `capacity` int DEFAULT NULL COMMENT 'course capacity',
  `selected` int DEFAULT NULL COMMENT 'selected number',
  `course_coordinator` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'course_coordinator id',
  `course_director` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'course_director id',
  `details` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'course details',
  `min_grade` int DEFAULT NULL COMMENT 'min grades',
  `visible` tinyint(1) DEFAULT NULL,
  `coursecoordinatorname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `coursecoordinatoremail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `coursedirectorname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `coursedirectoremail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `marker` tinyint(1) DEFAULT NULL,
  `hour` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `convert` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `requirement` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `nid` int NOT NULL AUTO_INCREMENT,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `auid` int DEFAULT NULL,
  PRIMARY KEY (`nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `size` double(50,2) DEFAULT NULL COMMENT '文件大小',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源位置',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否被删除',
  `user_id` int DEFAULT NULL COMMENT '上传用户',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件md5',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `auid` int NOT NULL AUTO_INCREMENT COMMENT '学号',
  `upi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'upi',
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电子邮件',
  `priority` smallint DEFAULT NULL COMMENT '优先级，不同uid的代表不同角色',
  `passwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`auid`,`email`,`upi`)
) ENGINE=InnoDB AUTO_INCREMENT=2023005027 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2023001', '001', 'Richardson', 'jonathan.richardson@aucklanduni.ac.nz', '3', '123456');
INSERT INTO `sys_user` VALUES ('2023002', '002', 'Richardson', 'elisabeth.mathews@aucklanduni.ac.nz', '1', '123456');
INSERT INTO `sys_user` VALUES ('2023003', '003', 'Roberts', 'emily.roberts@aucklanduni.ac.nz', '5', '123456');
INSERT INTO `sys_user` VALUES ('2023004', '004', 'Morgan', 'lucas.morgan@aucklanduni.ac.nz', '5', '123456');
INSERT INTO `sys_user` VALUES ('2023005', '005', 'Mitchell', 'sarah.mitchell@aucklanduni.ac.nz', '6', '123456');
