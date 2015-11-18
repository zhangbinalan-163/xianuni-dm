-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: xian_uni_dm
-- ------------------------------------------------------
-- Server version	5.6.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `media_resource`
--

DROP TABLE IF EXISTS `media_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '媒体资源名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '媒体资源类型',
  `forbidden` tinyint(1) DEFAULT NULL COMMENT '禁用',
  `uploadDate` datetime DEFAULT NULL COMMENT '资源上传时间',
  `description` varchar(256) DEFAULT NULL COMMENT '资源描述',
  `resourcePath` varchar(256) DEFAULT NULL COMMENT '资源地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_resource`
--

LOCK TABLES `media_resource` WRITE;
/*!40000 ALTER TABLE `media_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `media_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_meeting`
--

DROP TABLE IF EXISTS `organization_meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity` int(4) NOT NULL COMMENT '组织活动类型 1-党政联席会议 2-民主评议 3-民主生活会 4-三会一课',
  `mettingType` varchar(32) DEFAULT NULL COMMENT '会议类型',
  `startTime` datetime DEFAULT NULL COMMENT '会议开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '会议结束时间',
  `location` varchar(64) DEFAULT NULL COMMENT '会议地点',
  `theme` varchar(64) DEFAULT NULL COMMENT '会议主题',
  `compere` varchar(32) DEFAULT NULL COMMENT '主持人',
  `shouldNumberOfPeople` int(11) DEFAULT NULL COMMENT '应到人数',
  `realNumberOfPeople` int(11) DEFAULT NULL COMMENT '实到人数',
  `content` varchar(1024) DEFAULT NULL COMMENT '主要内容',
  `attendancePeople` varchar(256) DEFAULT NULL COMMENT '出勤人员',
  `absencePeople` varchar(256) DEFAULT NULL COMMENT '缺勤人员',
  `filePath` varchar(256) DEFAULT NULL COMMENT '附件地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_meeting`
--

LOCK TABLES `organization_meeting` WRITE;
/*!40000 ALTER TABLE `organization_meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization_meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_dues_pay`
--

DROP TABLE IF EXISTS `party_dues_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_dues_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '组织关系ID',
  `payStartTime` datetime DEFAULT NULL COMMENT '缴费起始日期',
  `payEndTime` datetime DEFAULT NULL COMMENT '缴费截止日期',
  `payTime` datetime DEFAULT NULL COMMENT '缴费日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_dues_pay`
--

LOCK TABLES `party_dues_pay` WRITE;
/*!40000 ALTER TABLE `party_dues_pay` DISABLE KEYS */;
INSERT INTO `party_dues_pay` VALUES (1,1,'2015-07-09 00:00:00','2015-12-09 00:00:00','2015-07-08 00:00:00'),(2,2,'2015-06-09 00:00:00','2015-11-09 00:00:00','2015-06-09 00:00:00');
/*!40000 ALTER TABLE `party_dues_pay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_member`
--

DROP TABLE IF EXISTS `party_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `organizationId` int(11) NOT NULL COMMENT '组织机构ID',
  `type` tinyint(2) DEFAULT NULL COMMENT '人员类型 1-教工 2-学生',
  `memberId` char(32) DEFAULT NULL COMMENT '学工号',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `idCardNo` char(18) DEFAULT NULL COMMENT '身份证号',
  `sex` tinyint(2) DEFAULT NULL COMMENT '性别 1-男 2-女',
  `nation` varchar(16) DEFAULT NULL COMMENT '民族',
  `education` varchar(16) DEFAULT NULL COMMENT '学历',
  `degree` varchar(16) DEFAULT NULL COMMENT '学位',
  `nativePlace` varchar(16) DEFAULT NULL COMMENT '籍贯',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `stats` int(3) DEFAULT NULL COMMENT '党员状态 1-历史党员',
  `origin` tinyint(4) DEFAULT '1' COMMENT '党员来源 1-校内 2-校外',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_member`
--

LOCK TABLES `party_member` WRITE;
/*!40000 ALTER TABLE `party_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `party_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_training`
--

DROP TABLE IF EXISTS `party_training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL COMMENT '培训标题',
  `trainingType` tinyint(3) DEFAULT NULL COMMENT '培训类别',
  `organizationId` int(11) DEFAULT NULL COMMENT '组织关系ID',
  `startTime` datetime DEFAULT NULL COMMENT '培训开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '培训结束时间',
  `period` int(3) DEFAULT NULL COMMENT '课时',
  `trainingObject` varchar(32) DEFAULT NULL COMMENT '培训对象',
  `content` varchar(1024) DEFAULT NULL COMMENT '培训内容',
  `harvest` varchar(1024) DEFAULT NULL COMMENT '培训收获',
  `opinion` varchar(1024) DEFAULT NULL COMMENT '培训意见',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_training`
--

LOCK TABLES `party_training` WRITE;
/*!40000 ALTER TABLE `party_training` DISABLE KEYS */;
/*!40000 ALTER TABLE `party_training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation_transfer`
--

DROP TABLE IF EXISTS `relation_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relation_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transferType` tinyint(3) DEFAULT NULL COMMENT '0-校内转入  1-校外转入',
  `personId` int(11) DEFAULT NULL,
  `toOrgId` int(11) DEFAULT NULL,
  `fromOrgId` int(11) DEFAULT NULL,
  `fromOrgName` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `transferTime` datetime DEFAULT NULL,
  `toOrgName` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation_transfer`
--

LOCK TABLES `relation_transfer` WRITE;
/*!40000 ALTER TABLE `relation_transfer` DISABLE KEYS */;
INSERT INTO `relation_transfer` VALUES (1,1,1,1,NULL,'武汉大学党委','2015-10-10 00:00:00','现在的党组织'),(2,2,2,NULL,1,'现在的党组织','2014-10-10 00:00:00','外面的党组织'),(3,0,3,2,1,'现在的党组织','2016-10-10 00:00:00','党组织');
/*!40000 ALTER TABLE `relation_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_admin`
--

DROP TABLE IF EXISTS `tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '用户名',
  `schoolNumber` varchar(45) NOT NULL COMMENT '学工号',
  `type` bigint(3) DEFAULT '0' COMMENT '管理员类型 0-部门管理员 1-超级管理员',
  `status` bigint(3) DEFAULT '0' COMMENT '状态 0-正常 1-禁用',
  `orgId` int(11) DEFAULT NULL COMMENT '部门管理员管理的部门',
  `password` varchar(45) DEFAULT NULL COMMENT '密码MD5之后',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_admin`
--

LOCK TABLES `tb_admin` WRITE;
/*!40000 ALTER TABLE `tb_admin` DISABLE KEYS */;
INSERT INTO `tb_admin` VALUES (1,'ZHANGBIN','123456',0,0,1,'123456',NULL,NULL);
/*!40000 ALTER TABLE `tb_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_message`
--

DROP TABLE IF EXISTS `tb_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messageTime` datetime DEFAULT NULL COMMENT '消息通知时间',
  `type` tinyint(3) DEFAULT NULL COMMENT '消息类型',
  `title` varchar(64) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_message`
--

LOCK TABLES `tb_message` WRITE;
/*!40000 ALTER TABLE `tb_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_org_reward`
--

DROP TABLE IF EXISTS `tb_org_reward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_org_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orgId` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `rewardTime` datetime DEFAULT NULL,
  `type` bigint(3) DEFAULT '0' COMMENT '0-奖励 1-处罚',
  `level` bigint(3) DEFAULT '0' COMMENT '0-校级 1-地市级  2-省级 3-国家级',
  `desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_org_reward`
--

LOCK TABLES `tb_org_reward` WRITE;
/*!40000 ALTER TABLE `tb_org_reward` DISABLE KEYS */;
INSERT INTO `tb_org_reward` VALUES (1,1,'国家级奖励','2015-11-11 00:00:00',0,3,NULL),(23,1,'test','2015-11-11 00:00:00',0,3,NULL),(24,1,'test','2015-11-11 00:00:00',0,3,NULL),(25,1,'test','2015-11-11 00:00:00',0,3,NULL),(26,1,'test','2015-11-11 00:00:00',0,3,NULL),(27,1,'test','2015-11-11 00:00:00',0,3,NULL),(28,1,'test','2015-11-11 00:00:00',0,3,NULL),(29,1,'test','2015-11-11 00:00:00',0,3,NULL),(30,1,'test','2015-11-11 00:00:00',0,3,NULL),(32,1,'test','2015-11-11 00:00:00',0,3,NULL),(33,1,'test','2015-11-11 00:00:00',0,3,NULL),(34,1,'test','2015-11-11 00:00:00',0,3,NULL),(35,1,'test','2015-11-11 00:00:00',0,3,NULL),(36,1,'test','2015-11-11 00:00:00',0,3,NULL),(37,1,'test','2015-11-11 00:00:00',0,3,NULL),(38,1,'test','2015-11-11 00:00:00',0,3,NULL),(39,1,'test','2015-11-11 00:00:00',0,3,NULL),(40,2,'hehe',NULL,0,3,NULL),(41,2,'hehe',NULL,0,3,NULL),(42,2,'hehe',NULL,0,3,NULL),(43,2,'hehe',NULL,0,3,NULL),(44,2,'hehe',NULL,0,3,NULL),(45,2,'hehe',NULL,0,3,NULL),(46,2,'hehe',NULL,0,3,NULL),(47,2,'hehe',NULL,0,3,NULL),(48,2,'hehe',NULL,0,3,NULL),(49,2,'hehe',NULL,0,3,NULL),(50,2,'hehe',NULL,0,3,NULL),(51,2,'hehe',NULL,0,3,NULL),(52,2,'hehe',NULL,0,3,NULL),(53,2,'hehe',NULL,0,3,NULL),(54,2,'hehe',NULL,0,3,NULL),(55,2,'hehe',NULL,0,3,NULL),(56,2,'hehe',NULL,0,3,NULL),(57,2,'hehe',NULL,0,3,NULL),(58,2,'hehe',NULL,0,3,NULL),(59,2,'hehe',NULL,0,3,NULL),(60,2,'hehe',NULL,0,3,NULL),(61,2,'hehe',NULL,0,3,NULL),(62,2,'hehe',NULL,0,3,NULL),(63,2,'hehe',NULL,0,3,NULL),(64,2,'hehe',NULL,0,3,NULL),(65,2,'hehe',NULL,0,3,NULL),(66,2,'hehe',NULL,0,3,NULL),(67,2,'hehe',NULL,0,3,NULL),(68,2,'hehe',NULL,0,3,NULL),(69,2,'hehe',NULL,0,3,NULL),(70,2,'hehe',NULL,0,3,NULL),(71,2,'hehe',NULL,0,3,NULL),(72,2,'hehe',NULL,0,3,NULL),(73,2,'hehe',NULL,0,3,NULL),(74,2,'hehe',NULL,0,3,NULL),(75,2,'hehe',NULL,0,3,NULL),(76,2,'hehe',NULL,0,3,NULL);
/*!40000 ALTER TABLE `tb_org_reward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_orgnization`
--

DROP TABLE IF EXISTS `tb_orgnization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_orgnization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构名称',
  `status` bigint(3) DEFAULT '0' COMMENT '状态 0-正常 1-已撤销',
  `parent` int(11) DEFAULT '-1' COMMENT '直属上级组织ID',
  `isParent` tinyint(1) DEFAULT NULL COMMENT '是否有子节点',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '最近修改时间',
  `electionTime` datetime DEFAULT NULL COMMENT '最近选举时间',
  `desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_orgnization`
--

LOCK TABLES `tb_orgnization` WRITE;
/*!40000 ALTER TABLE `tb_orgnization` DISABLE KEYS */;
INSERT INTO `tb_orgnization` VALUES (1,'西安文理学院校党委',0,-1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(2,'机关第一党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(3,'机关第二党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(4,'机关第三党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(5,'机关第四党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(6,'机关第五党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(7,'后勤党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(8,'离退休党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(9,'教辅单位党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(10,'人文学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(11,'外国语学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(12,'机械与材料工程学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(13,'化学工程学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(14,'生物与环境工程学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(15,'经济管理学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(16,'信息工程学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(17,'艺术学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(18,'师范学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(19,'政治学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(20,'继续教育学院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(21,'文化艺术教育中心党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(22,'服务地方研究院党总支',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(23,'党政办党支部',0,2,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(24,'组织部党支部',0,2,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(25,'宣传部党支部',0,2,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(26,'纪委党支部',0,2,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(27,'工会党支部',0,2,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(29,'测试的2',0,1,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(30,'测试的11',0,28,1,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(31,'测试的21',0,29,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL),(37,'测试的22',0,29,0,'2013-10-11 00:00:00','2014-10-11 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `tb_orgnization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_party_activist`
--

DROP TABLE IF EXISTS `tb_party_activist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_party_activist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '人员ID',
  `evaluationTime` datetime DEFAULT NULL COMMENT '民主测评时间',
  `evaluationContent` varchar(200) DEFAULT NULL COMMENT '评估内容',
  `meetTime` datetime DEFAULT NULL COMMENT '会面时间',
  `meetContent` varchar(200) DEFAULT NULL COMMENT '会面内容',
  `director` varchar(23) DEFAULT NULL COMMENT '培养人ID',
  `recorded` tinyint(1) DEFAULT NULL COMMENT '是否已备案',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_party_activist`
--

LOCK TABLES `tb_party_activist` WRITE;
/*!40000 ALTER TABLE `tb_party_activist` DISABLE KEYS */;
INSERT INTO `tb_party_activist` VALUES (1,3,'2015-11-10 00:00:00','CONTENT',NULL,NULL,'1,2',1,'2015-11-10 00:00:00'),(2,4,NULL,NULL,NULL,NULL,NULL,NULL,'2015-11-12 00:00:00'),(3,5,NULL,NULL,NULL,NULL,NULL,NULL,'2015-11-09 00:00:00');
/*!40000 ALTER TABLE `tb_party_activist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_party_applier`
--

DROP TABLE IF EXISTS `tb_party_applier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_party_applier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '人员ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `applyTime` datetime DEFAULT NULL COMMENT '申请时间',
  `talkTime` datetime DEFAULT NULL COMMENT '谈话时间',
  `talkContent` varchar(200) DEFAULT NULL COMMENT '谈话内容',
  `talkerId` int(11) DEFAULT NULL COMMENT '谈话人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_party_applier`
--

LOCK TABLES `tb_party_applier` WRITE;
/*!40000 ALTER TABLE `tb_party_applier` DISABLE KEYS */;
INSERT INTO `tb_party_applier` VALUES (1,1,'2014-09-09 00:00:00',NULL,NULL,'hehe',3),(3,2,'2014-09-09 00:00:00','2014-09-09 00:00:00','2014-09-09 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `tb_party_applier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_party_intention`
--

DROP TABLE IF EXISTS `tb_party_intention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_party_intention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '人员ID',
  `trainHour` int(3) DEFAULT NULL COMMENT '培训时间',
  `publiced` tinyint(1) DEFAULT NULL COMMENT '是否已公布',
  `meetTime` datetime DEFAULT NULL COMMENT '会面时间',
  `meetContent` varchar(200) DEFAULT NULL COMMENT '会面内容',
  `politicalChcekContent` varchar(200) DEFAULT NULL COMMENT '审查内容',
  `introducer` int(11) DEFAULT NULL COMMENT '介绍人ID',
  `recorded` tinyint(1) DEFAULT NULL COMMENT '是否已记录',
  `schoolApproval` varchar(200) DEFAULT NULL COMMENT '是否已批准',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_party_intention`
--

LOCK TABLES `tb_party_intention` WRITE;
/*!40000 ALTER TABLE `tb_party_intention` DISABLE KEYS */;
INSERT INTO `tb_party_intention` VALUES (1,11,11,1,NULL,NULL,NULL,NULL,1,NULL,NULL),(2,12,12,1,NULL,NULL,NULL,NULL,0,NULL,NULL),(3,12,12,0,NULL,NULL,NULL,NULL,1,NULL,NULL),(4,14,12,0,NULL,NULL,NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `tb_party_intention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_party_normal`
--

DROP TABLE IF EXISTS `tb_party_normal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_party_normal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '人员ID',
  `applyTime` datetime DEFAULT NULL COMMENT '申请时间',
  `approval` tinyint(1) DEFAULT NULL COMMENT '是否批准',
  `branchApproval` tinyint(1) DEFAULT NULL COMMENT '支部是否批准',
  `schoolApproval` tinyint(1) DEFAULT NULL COMMENT '学校是否批准',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_party_normal`
--

LOCK TABLES `tb_party_normal` WRITE;
/*!40000 ALTER TABLE `tb_party_normal` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_party_normal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_party_prepare`
--

DROP TABLE IF EXISTS `tb_party_prepare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_party_prepare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL COMMENT '人员ID',
  `approval` varchar(200) DEFAULT NULL COMMENT '批准',
  `branchApproval` varchar(200) DEFAULT NULL COMMENT '支部批准',
  `schoolApproval` varchar(200) DEFAULT NULL COMMENT '学校批准',
  `application` tinyint(1) DEFAULT NULL COMMENT '是否已提交申请书',
  `meetTime` datetime DEFAULT NULL COMMENT '会面时间',
  `meetContent` varchar(200) DEFAULT NULL COMMENT '会面内容',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_party_prepare`
--

LOCK TABLES `tb_party_prepare` WRITE;
/*!40000 ALTER TABLE `tb_party_prepare` DISABLE KEYS */;
INSERT INTO `tb_party_prepare` VALUES (1,7,'bucuo','henhao','tongyi',1,'2015-11-11 00:00:00','bucuo','2015-11-10 00:00:00'),(2,8,NULL,NULL,NULL,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_party_prepare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_person`
--

DROP TABLE IF EXISTS `tb_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL COMMENT '姓名',
  `orgId` int(11) NOT NULL COMMENT '组织机构ID',
  `status` int(3) DEFAULT NULL COMMENT '状态 ',
  `type` tinyint(2) DEFAULT '0' COMMENT '类型 0-学生 1-教工',
  `number` varchar(20) DEFAULT NULL COMMENT '学工号',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别 0-男 1-女',
  `nation` tinyint(3) DEFAULT NULL COMMENT '民族',
  `degree` tinyint(2) DEFAULT NULL COMMENT '学位',
  `hometown` varchar(32) DEFAULT NULL COMMENT '籍贯',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `birth` datetime DEFAULT NULL COMMENT '出生日期',
  `profession` tinyint(3) DEFAULT NULL COMMENT '职称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_person`
--

LOCK TABLES `tb_person` WRITE;
/*!40000 ALTER TABLE `tb_person` DISABLE KEYS */;
INSERT INTO `tb_person` VALUES (1,'张斌',1,1,0,'3070702217','371425198809098888',0,1,1,'山东','123456','1988-09-09 00:00:00',1,'2012-10-11 00:00:00',NULL),(2,'hehe',1,1,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2011-10-10 00:00:00',NULL),(3,'hehe',1,2,0,'33333333','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(4,'hehe',2,2,0,'66666666','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(5,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(6,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(7,'hehe',1,4,0,'44433333','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(8,'hehe',1,4,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(9,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(10,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(11,'hehe',1,3,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(12,'hehe',1,3,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(13,'hehe',1,3,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(14,'hehe',1,3,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(15,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(16,'hehe',1,4,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(17,'hehe',2,4,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(18,'hehe',3,4,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(19,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2012-10-11 00:00:00',NULL),(20,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(21,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(22,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(23,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(24,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(25,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(26,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(27,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(28,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(29,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(30,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(31,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(32,'hehe',1,0,0,'22222222','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(33,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(34,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(35,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(36,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(37,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(38,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(39,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(40,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(41,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(42,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(43,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(44,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(45,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(46,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(47,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(48,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(49,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(50,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(51,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(52,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(53,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(54,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(55,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(56,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(57,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(58,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(59,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(60,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(61,'nihao',1,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(62,'nihao',2,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(63,'nihao',12,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL),(64,'nihao',12,0,0,'11111111','371425198809098888',1,1,1,'山东','123456',NULL,1,'2013-01-11 00:00:00',NULL);
/*!40000 ALTER TABLE `tb_person` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-18 20:58:23
