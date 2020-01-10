/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.17 : Database - 360hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`360hotel` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `360hotel`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `AdmId` varchar(10) NOT NULL,
  `aName` varchar(8) DEFAULT NULL,
  `aPassword` varchar(20) DEFAULT NULL,
  `aSex` varchar(2) DEFAULT NULL,
  `limit` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`AdmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `administrator` */

insert  into `administrator`(`AdmId`,`aName`,`aPassword`,`aSex`,`limit`) values ('001','经理','36088888','男','administrator'),('3601','前台1','2919966','女','front'),('3602','前台2','2919966','女','front'),('3603','备用','2919966','女','front');

/*Table structure for table `apartment` */

DROP TABLE IF EXISTS `apartment`;

CREATE TABLE `apartment` (
  `roomNum` char(4) NOT NULL,
  `price` int(5) NOT NULL,
  `state` tinyint(1) NOT NULL,
  PRIMARY KEY (`roomNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `apartment` */

insert  into `apartment`(`roomNum`,`price`,`state`) values ('6001',108,0),('6002',108,0),('6003',108,0),('6005',108,0),('6006',108,0),('6007',108,0),('6008',108,0),('6009',108,0),('6010',108,0),('6011',108,0),('6012',128,0),('6013',128,0),('6014',98,0),('6015',98,0),('6016',98,0),('6017',98,0),('6018',98,0),('6019',98,0),('6020',98,0),('6021',98,0),('6022',98,0),('6023',168,0),('8001',98,0),('8002',98,0),('8003',78,0),('8004',98,0),('8005',98,0),('8006',98,0),('8007',98,0),('8008',98,0),('8009',98,0),('8010',98,0),('8011',108,0),('8012',108,0),('8013',98,0),('8014',108,0),('8015',108,0),('8016',108,0),('8017',108,0),('8018',108,0),('8019',108,0),('8020',108,0),('8022',108,0),('8023',108,0);

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `billId` int(11) NOT NULL AUTO_INCREMENT,
  `roomNum` char(4) NOT NULL,
  `inTime` datetime NOT NULL,
  `mineral` int(2) DEFAULT NULL,
  `pulsation` int(2) DEFAULT NULL,
  `greenTea` int(2) DEFAULT NULL,
  `tea` int(2) DEFAULT NULL,
  `noodles` int(2) DEFAULT NULL,
  `WLJJDB` int(2) DEFAULT NULL,
  PRIMARY KEY (`billId`),
  KEY `ck_bill_apartment` (`roomNum`),
  KEY `ck_bill_customer` (`inTime`),
  CONSTRAINT `ck_bill_apartment` FOREIGN KEY (`roomNum`) REFERENCES `apartment` (`roomNum`),
  CONSTRAINT `ck_bill_customer` FOREIGN KEY (`inTime`) REFERENCES `customer` (`inTime`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

/*Data for the table `bill` */

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `inTime` datetime NOT NULL,
  `cName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cardID` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cSex` varchar(2) DEFAULT NULL,
  `roomNum` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `chargeAndDeposit` int(5) DEFAULT NULL,
  `paymentMethod` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`inTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

/*Table structure for table `expense` */

DROP TABLE IF EXISTS `expense`;

CREATE TABLE `expense` (
  `kinds` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `price` int(5) NOT NULL,
  PRIMARY KEY (`kinds`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `expense` */

insert  into `expense`(`kinds`,`name`,`price`) values ('greenTea','绿茶',4),('HourRoom','钟点房',50),('mineral','矿泉水',2),('noodles','泡面',5),('pulsation','脉动',5),('tea','茶叶',5),('WLJJDB','王老吉/加多宝',5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
