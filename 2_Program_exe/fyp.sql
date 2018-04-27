-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: fyp
-- ------------------------------------------------------
-- Server version       5.7.19

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
-- Current Database: `fyp`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `fyp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `fyp`;

--
-- Table structure for table `fyp_trans`
--

DROP TABLE IF EXISTS `fyp_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fyp_trans` (
  `trans_id` int(20) NOT NULL AUTO_INCREMENT,
  `trans_fromID` int(5) NOT NULL DEFAULT '0',
  `trans_toID` int(5) NOT NULL DEFAULT '0',
  `trans_fromName` varchar(10) NOT NULL,
  `trans_toName` varchar(10) NOT NULL,
  `trans_fromBalance` int(10) NOT NULL DEFAULT '0',
  `trans_toBalance` int(10) NOT NULL DEFAULT '0',
  `trans_value` int(10) NOT NULL DEFAULT '0',
  `trans_createTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fyp_trans`
--

LOCK TABLES `fyp_trans` WRITE;
/*!40000 ALTER TABLE `fyp_trans` DISABLE KEYS */;
INSERT INTO `fyp_trans` VALUES (1,100,113,'zczhu','roroco',470,30,10,'2017-11-05 20:39:05.682904'),(2,100,113,'zczhu','roroco',460,40,10,'2017-11-08 07:49:35.290869'),(3,100,113,'zczhu','roroco',440,60,20,'2017-11-08 07:50:06.056507'),(4,100,113,'zczhu','roroco',-60,560,500,'2017-11-08 07:51:06.038957'),(5,101,100,'jacklee','zczhu',8400,1940,2000,'2017-11-08 07:52:22.593574'),(6,101,113,'jacklee','roroco',400,8560,8000,'2017-11-08 07:54:21.234214'),(7,100,112,'zczhu','bob',1740,200,200,'2017-11-08 08:03:43.613059'),(8,100,101,'zczhu','jacklee',1730,410,10,'2017-11-08 10:20:49.735198'),(9,113,-1,'roroco','zhuzc',8550,-1,10,'2018-01-09 18:00:54.399130'),(10,100,113,'zczhu','roroco',1630,8650,100,'2018-01-10 21:05:52.199959'),(11,100,113,'zczhu','roroco',1130,8750,100,'2018-01-10 21:41:23.392389'),(12,100,113,'zczhu','roroco',1030,8850,100,'2018-01-10 21:42:26.035789'),(13,100,113,'zczhu','roroco',730,8950,100,'2018-01-10 21:50:09.121864'),(14,113,100,'roroco','zczhu',5950,1729,1000,'2018-01-11 11:40:59.828287'),(15,100,113,'zczhu','roroco',1679,6000,50,'2018-01-11 11:42:45.827239'),(16,100,113,'zczhu','roroco',1579,6100,100,'2018-01-21 16:38:16.411887'),(17,100,113,'zczhu','roroco',1369,6200,100,'2018-01-21 16:45:09.585283'),(18,100,113,'zczhu','roroco',1269,6300,100,'2018-01-21 16:46:42.331280'),(19,100,113,'zczhu','roroco',1159,6310,10,'2018-04-03 21:08:08.949834'),(20,100,113,'zczhu','roroco',1139,6320,10,'2018-04-03 21:09:12.872640'),(21,100,113,'zczhu','roroco',1119,6330,10,'2018-04-03 21:12:13.731573'),(22,100,113,'zczhu','roroco',1109,6340,10,'2018-04-03 21:14:18.494102'),(23,113,113,'roroco','roroco',6330,6350,10,'2018-04-03 21:21:26.622373'),(24,100,113,'zczhu','roroco',1104,6350,5,'2018-04-03 21:24:02.143025'),(25,100,113,'zczhu','roroco',1099,6355,5,'2018-04-03 21:28:47.055313'),(26,100,113,'zczhu','roroco',1094,6360,5,'2018-04-03 21:31:03.884723'),(27,100,113,'zczhu','roroco',1089,6365,5,'2018-04-03 21:33:39.616593'),(28,100,113,'zczhu','roroco',1084,6370,5,'2018-04-03 21:38:33.151447'),(29,100,113,'zczhu','roroco',1083,6371,1,'2018-04-03 21:51:44.001936'),(30,100,113,'zczhu','roroco',1082,6372,1,'2018-04-03 21:54:07.469913'),(31,100,113,'zczhu','roroco',1081,6373,1,'2018-04-03 22:02:14.690133'),(32,100,113,'zczhu','roroco',1080,6374,1,'2018-04-03 22:52:35.013269'),(33,113,100,'roroco','zczhu',6369,1085,5,'2018-04-03 22:58:27.878309'),(34,113,100,'roroco','zczhu',6367,1086,1,'2018-04-03 22:59:17.436330'),(35,113,100,'roroco','zczhu',6362,1091,5,'2018-04-03 23:01:22.479670'),(36,113,100,'roroco','zczhu',6352,1101,10,'2018-04-03 23:23:30.815991'),(37,100,113,'zczhu','roroco',1086,6367,15,'2018-04-03 23:39:26.028248'),(38,100,113,'zczhu','roroco',1084,6369,2,'2018-04-03 23:45:25.280750'),(39,100,113,'zczhu','roroco',1079,6374,5,'2018-04-03 23:47:58.622254'),(40,113,100,'roroco','zczhu',6324,1129,50,'2018-04-04 00:01:00.071491'),(41,5,1,'roroco','zczhu',6314,1139,10,'2018-04-07 22:46:34.740236'),(42,5,1,'roroco','zczhu',6304,1149,10,'2018-04-14 15:12:14.575472'),(43,1,5,'zczhu','roroco',1049,6404,100,'2018-04-14 18:47:00.084910'),(44,1,5,'zczhu','roroco',1039,6414,10,'2018-04-14 18:52:56.771604'),(45,1,5,'zczhu','roroco',1029,6424,10,'2018-04-14 18:56:40.597979'),(46,1,5,'zczhu','roroco',1024,6429,5,'2018-04-14 18:59:18.535391'),(47,1,5,'zczhu','roroco',1019,6434,5,'2018-04-14 19:01:56.109525'),(48,1,5,'zczhu','roroco',1014,6439,5,'2018-04-14 19:02:45.161248'),(49,1,5,'zczhu','roroco',1009,6444,5,'2018-04-14 19:03:38.827836'),(50,5,1,'roroco','zczhu',6434,1019,10,'2018-04-15 10:46:51.226991'),(51,5,1,'roroco','zczhu',6424,1029,10,'2018-04-15 10:48:26.616421'),(52,1,5,'zczhu','roroco',1019,6434,10,'2018-04-15 16:27:17.295098'),(53,1,5,'zczhu','roroco',1009,6444,10,'2018-04-15 16:27:32.850587'),(54,1,5,'zczhu','roroco',999,6454,10,'2018-04-15 16:28:30.566374'),(55,1,5,'zczhu','roroco',989,6464,10,'2018-04-15 16:30:02.342328'),(56,5,1,'roroco','zczhu',6364,1089,100,'2018-04-18 06:06:52.533562');
/*!40000 ALTER TABLE `fyp_trans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fyp_user`
--

DROP TABLE IF EXISTS `fyp_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fyp_user` (
  `user_id` int(5) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL DEFAULT '',
  `user_password` varchar(20) NOT NULL,
  `user_emailAddr` varchar(20) NOT NULL DEFAULT '',
  `user_balance` int(10) NOT NULL DEFAULT '0',
  `user_createTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_lastModifiedTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_valid` char(1) NOT NULL DEFAULT '1',
  `user_bankAccount` varchar(20) NOT NULL DEFAULT '',
  `user_qrExpired` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_ip` varchar(16) DEFAULT '0.0.0.0',
  `user_qrValidNum` varchar(6) NOT NULL DEFAULT 'xxxxxx',
  `user_qrValue` int(10) NOT NULL DEFAULT '-1',
  `user_fcmToken` longtext,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fyp_user`
--

LOCK TABLES `fyp_user` WRITE;
/*!40000 ALTER TABLE `fyp_user` DISABLE KEYS */;
INSERT INTO `fyp_user` VALUES (1,'zczhu','54321','zczhu@cs.hku.hk',1089,'2017-11-05 19:18:47.373362','2018-04-18 06:06:52.523536','1','','2017-10-11 04:41:53.731277','192.168.137.202','xxxxxx',5,'fimUNWvCFJU:APA91bGUItuBfRJ-fUA81UmH0Y5LarHvt_2TY1yvB4kong7sgxI1-Vchq9r60s3lZocCRsOiPfyFiGFqWX0aHoSg-GShmfVOzAviEsGeNWvTppqhuOSEB9Pgi1zQXgoxflwvn5ghmDoU'),(2,'jacklee','jack123456789','jacklee@gmail.com',410,'2017-11-05 19:18:47.376864','2018-04-06 09:34:26.833960','1','','2017-10-11 04:49:58.340760',NULL,'',0,NULL),(3,'mike','54321','mike1999@gmail.com',0,'2017-10-11 07:19:09.374275','2018-04-06 09:34:37.193737','1','','2017-10-11 07:19:09.374275',NULL,'',0,NULL),(4,'bob','bob123','bob123@gmail.com',200,'2017-10-11 07:23:12.599417','2018-04-06 09:34:46.585969','1','','2017-10-11 07:23:12.599417',NULL,'',0,NULL),(5,'roroco','roroco','roroco@gmail.com',6364,'2017-11-02 05:58:53.188171','2018-04-18 06:06:52.523536','1','','2017-11-02 05:58:53.188171','192.168.137.41','xxxxxx',100,'null'),(6,'juju','juju123','juju123@gmail.com',0,'2017-11-02 06:36:40.847267','2018-04-06 09:35:03.530676','1','','2017-11-02 06:36:40.847267',NULL,'',0,NULL),(7,'alibaba','12345','alibaba@163.com',0,'2017-11-02 09:34:10.206764','2018-04-06 09:35:09.019842','1','','2017-11-02 09:34:10.206764',NULL,'',0,NULL),(8,'miaomiao','12345','miaomiao@yahoo.com',0,'2017-11-08 05:13:36.605425','2018-04-06 09:35:14.025416','1','','2017-11-08 05:13:36.605425',NULL,'',0,NULL),(9,'ben123','12345','ben123@gmail.com',0,'2017-11-08 05:19:42.883700','2018-04-06 09:35:21.281099','1','','2017-11-08 05:19:42.883700',NULL,'',0,NULL),(10,'tutu','datuzi','tutu@gmail.com',0,'2017-11-08 05:21:42.999282','2018-04-06 09:35:37.434071','1','','2017-11-08 05:21:42.999282',NULL,'123123',-1,NULL);
/*!40000 ALTER TABLE `fyp_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-20  8:41:50