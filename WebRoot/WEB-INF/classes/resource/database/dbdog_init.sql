/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.7.14 : Database - dbdog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbdog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `dbdog`;


/*Table structure for table `appuserinfo` */

DROP TABLE IF EXISTS `appuserinfo`;

CREATE TABLE `appuserinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `lastlogin_time` datetime DEFAULT NULL,
  `head` varchar(200) DEFAULT NULL,
  `height` int(3) DEFAULT NULL,
  `weight` int(3) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `bind_count` varchar(11) DEFAULT '0',
  `login_imei` varchar(20) DEFAULT '0',
  `email` varchar(200) DEFAULT NULL,
  `zousnqag` varchar(200) DEFAULT '54156',
  `login_status` char(2) DEFAULT '0',
  `login_time` varchar(50) DEFAULT NULL,
  `phone_mac` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `appuserinfo` */

/*Table structure for table `channelinfo` */

DROP TABLE IF EXISTS `channelinfo`;

CREATE TABLE `channelinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_no` varchar(20) DEFAULT '' COMMENT '渠道编号',
  `channel_name` varchar(50) DEFAULT '' COMMENT '渠道名称',
  `company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` text COMMENT '备注',
  `status` char(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `channelinfo` */

/*Table structure for table `checkinfo` */

DROP TABLE IF EXISTS `checkinfo`;

CREATE TABLE `checkinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_name` varchar(50) NOT NULL,
  `version_name` varchar(50) NOT NULL,
  `version_code` varchar(50) NOT NULL,
  `download_path` varchar(200) NOT NULL,
  `function_cap` text,
  `belong_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `checkinfo` */

/*Table structure for table `clicklocationinfo` */

DROP TABLE IF EXISTS `clicklocationinfo`;

CREATE TABLE `clicklocationinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL COMMENT '设备ID',
  `battery` int(3) DEFAULT NULL COMMENT '设备电池电量',
  `longitude` varchar(50) DEFAULT NULL COMMENT 'GPS经度',
  `latitude` varchar(50) DEFAULT NULL COMMENT 'GPS纬度',
  `location_type` char(1) DEFAULT NULL COMMENT '定位类型 1--高德GPS 2--高德LBS 3--高德WIFI 4--Google GPS 5--Google Geolocation',
  `accuracy` float DEFAULT NULL COMMENT '定位精度',
  `upload_time` datetime DEFAULT NULL COMMENT '上传位置时间',
  `change_longitude` varchar(50) DEFAULT NULL COMMENT '对应地图经度',
  `change_latitude` varchar(50) DEFAULT NULL COMMENT '对应地图纬度',
  `belong_project` int(11) DEFAULT NULL COMMENT '所属项目',
  `fall` char(1) DEFAULT '1' COMMENT '是否配戴',
  `step_count` int(11) DEFAULT '0' COMMENT '计步器累计步数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/*Data for the table `clicklocationinfo` */

/*Table structure for table `companyinfo` */

DROP TABLE IF EXISTS `companyinfo`;

CREATE TABLE `companyinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_no` varchar(20) DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `channel_id` varchar(500) DEFAULT NULL COMMENT '渠道编号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` text COMMENT '备注',
  `status` char(1) DEFAULT '1' COMMENT '状态',
  `user_name` varchar(20) DEFAULT '' COMMENT '登录账号用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `companyinfo` */


/*Table structure for table `device_active_history` */

DROP TABLE IF EXISTS `device_active_history`;

CREATE TABLE `device_active_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT '',
  `user_id` varchar(11) DEFAULT '',
  `nick_name` varchar(50) DEFAULT '',
  `status` char(1) DEFAULT '',
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

/*Data for the table `device_active_history` */


/*Table structure for table `device_active_info` */

DROP TABLE IF EXISTS `device_active_info`;

CREATE TABLE `device_active_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_phone` varchar(15) NOT NULL DEFAULT '0' COMMENT '设备电话号码',
  `device_imei` varchar(20) NOT NULL DEFAULT '000000000000000' COMMENT '设备的imei号',
  `device_name` varchar(10) NOT NULL DEFAULT '0' COMMENT '设备名称',
  `user_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '关联的用户id',
  `device_head` varchar(225) DEFAULT '0' COMMENT '设备头像地址',
  `device_sex` varchar(2) NOT NULL DEFAULT '0' COMMENT '设备性别,0男1女',
  `device_age` varchar(20) NOT NULL DEFAULT '2015-12-12 23:59:59' COMMENT '设备年龄,默认6岁',
  `device_height` varchar(5) NOT NULL DEFAULT '170' COMMENT '设备身高,默认170',
  `device_weight` varchar(5) NOT NULL DEFAULT '100' COMMENT '设备体重',
  `device_update_time` datetime NOT NULL COMMENT '设备上传时间',
  `device_disable` varchar(2) DEFAULT '1' COMMENT '设备是否禁用,0表示禁用,1(默认)表示不禁用',
  `listen_type` char(1) DEFAULT '0',
  `count` varchar(11) DEFAULT '1',
  `belong_project` int(11) DEFAULT '1' COMMENT '所属哪个项目ID',
  `target_step` varchar(20) DEFAULT '0',
  `actural_step` varchar(20) DEFAULT '0',
  `temperature` double DEFAULT '0' COMMENT '温度',
  `heatrate` varchar(20) DEFAULT '0' COMMENT '心率',
  `isphoneHelp` char(1) DEFAULT '0' COMMENT '0不需要1需要',
  `isserverHelp` char(1) DEFAULT '0' COMMENT '0不需要1需要',
  `deadline` datetime DEFAULT '2015-03-18 00:00:00',
  `brandname` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `is_sos` char(2) DEFAULT '0' COMMENT '是否开启SOS求救0不开',
  `old_fall` char(2) DEFAULT '0' COMMENT '老人防摔0不开1开',
  `sbp` varchar(50) DEFAULT '0' COMMENT '收缩压',
  `dbp` varchar(50) DEFAULT '0' COMMENT '舒张压',
  `device_type` char(2) DEFAULT '1' COMMENT '设备类型0老人1小孩',
  `flight_mode` char(2) DEFAULT '0' COMMENT '设备飞行模式标志，1开0关',
  `urgent_mode` char(2) DEFAULT '0' COMMENT 'Pet collar 进入寻找模式，1开0关',
  `sensitivity` int(2) DEFAULT '3' COMMENT '计步器的灵敏度设置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `device_active_info` */

/*Table structure for table `device_login` */

DROP TABLE IF EXISTS `device_login`;

CREATE TABLE `device_login` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `device_imei` char(20) NOT NULL COMMENT '设备imei',
  `device_phone` char(20) DEFAULT NULL COMMENT '设别电话号码',
  `device_version` varchar(50) DEFAULT NULL COMMENT '固件版本',
  `device_status` char(2) NOT NULL DEFAULT '0' COMMENT '设备状态(0表示解绑空闲,1表示出厂,2表示绑定，3表示非法IMEI)',
  `date_time` datetime DEFAULT NULL COMMENT '签到时间',
  `belong_project` int(11) DEFAULT NULL COMMENT '项目ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=664 DEFAULT CHARSET=utf8;

/*Data for the table `device_login` */


/*Table structure for table `directive` */

DROP TABLE IF EXISTS `directive`;

CREATE TABLE `directive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) NOT NULL,
  `distrub` char(1) DEFAULT '0',
  `mdistime` varchar(50) DEFAULT NULL,
  `tdistime` varchar(50) DEFAULT NULL,
  `wdistime` varchar(50) DEFAULT NULL,
  `lowelectricity` varchar(3) DEFAULT NULL,
  `isLow` char(1) DEFAULT '0',
  `clock` varchar(255) DEFAULT NULL,
  `sleep` varchar(255) DEFAULT NULL,
  `thdistime` varchar(50) DEFAULT NULL,
  `fdistime` varchar(50) DEFAULT NULL,
  `sdistime` varchar(50) DEFAULT NULL,
  `sudistime` varchar(50) DEFAULT NULL,
  `distrubChange` char(1) DEFAULT '0',
  `alarmChange` char(1) DEFAULT '0',
  `sleepChange` char(1) DEFAULT '0',
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `deep_sleep_time` varchar(50) DEFAULT NULL COMMENT '深度睡眠',
  `low_sleep_time` varchar(50) DEFAULT NULL COMMENT '浅度睡眠',
  `total_sleep_time` varchar(50) DEFAULT NULL COMMENT '总睡眠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `directive` */


/*Table structure for table `feedbackinfo` */

DROP TABLE IF EXISTS `feedbackinfo`;

CREATE TABLE `feedbackinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) NOT NULL DEFAULT '',
  `user_feedback_content` text NOT NULL,
  `date_time` datetime NOT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `feedbackinfo` */


/*Table structure for table `funcinfo` */

DROP TABLE IF EXISTS `funcinfo`;

CREATE TABLE `funcinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `funcCode` varchar(50) DEFAULT NULL,
  `funcName` varchar(50) NOT NULL,
  `funcDesc` varchar(50) DEFAULT NULL,
  `superCode` varchar(20) DEFAULT NULL,
  `levels` int(2) NOT NULL,
  `funcSort` int(2) NOT NULL,
  `statu` varchar(2) NOT NULL,
  `funcDo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

/*Data for the table `funcinfo` */

insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (1,NULL,'',NULL,NULL,0,0,'',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (43,'sysAdmin','系统功能','系统功能','super',1,1,'1','');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (44,'sysInfo','权限管理','权限管理','sysAdmin',2,1,'1','');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (45,'debugTools','调试工具','调试工具','sysAdmin',2,2,'1','');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (46,'roleInfo','角色','角色','sysInfo',2,2,'1','sysAdmin/roleInfo/doRoleInfo.do?method=queryRoleInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (47,'monitorInfo','页面性能工具','页面性能工具','debugTools',2,2,'1','sysAdmin/monitorInfo/doMonitorInfo.do?method=queryMonitorInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (48,'sysLogInfo','系统日志','系统日志','debugTools',2,1,'1','sysAdmin/sysLogInfo/doSysLogInfo.do?method=querySysLogInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (49,'adminInfo','系统用户','系统用户','sysInfo',2,1,'1','sysAdmin/userInfo/doUserInfo.do?method=queryUserInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (50,'userAndDevice','注册绑定','注册绑定','super',1,2,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (51,'appUserInfo','APP用户','APP用户','userAndDevice',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (52,'appUserMsg','成功注册','成功注册','appUserInfo',2,1,'1','dyconfig/appUserInfo/doAppUserInfo.do?method=queryAppUserInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (53,'deviceInfo','设备','设备','userAndDevice',2,2,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (54,'IMEIMsg','IMEI录入','IMEI录入','deviceInfo',2,3,'1','dyconfig/phoneInfo/doPhoneInfo.do?method=queryPhoneInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (55,'deviceMsg','所有激活','所有激活','deviceInfo',2,1,'1','dyconfig/deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (56,'updateAndBackInfo','APP互动','APP互动','super',1,5,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (57,'updateInfo','升级','升级','updateAndBackInfo',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (58,'APPUpdateInfo','APP升级','APP升级','updateInfo',2,1,'1','dyconfig/checkInfo/doCheckInfo.do?method=queryCheckInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (59,'backInfo','反馈','反馈','updateAndBackInfo',2,2,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (60,'feedBackInfo','意见反馈','意见反馈','backInfo',2,1,'1','dyconfig/feedBackInfo/doFeedBackInfo.do?method=queryFeedBackInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (61,'dataAnalysisInfo','分析统计','分析统计','super',1,6,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (62,'saleInfo','销量统计','销量统计','dataAnalysisInfo',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (65,'deviceActiveCount','激活设备统计','激活设备统计','dataCountInfo',2,1,'1','dyconfig/deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveCount');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (66,'appUserCount','APP用户统计','APP用户统计','dataCountInfo',2,2,'1','dyconfig/appUserInfo/doAppUserInfo.do?method=queryAppUserCount');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (67,'deviceDataInfo','设备数据','设备数据','super',1,7,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (68,'relativeCallInfo','亲情号码','亲情号码','deviceDataInfo',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (69,'dataMsgInfo','数据信息','数据信息','relativeCallInfo',2,1,'1','dyconfig/relativeCallInfo/doRelativeCallInfo.do?method=queryRelativeCallInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (70,'locationInfo','定位信息','定位信息','deviceDataInfo',2,2,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (71,'location','定位','定位','locationInfo',2,1,'1','dyconfig/locationInfo/doLocationInfo.do?method=queryLocationInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (72,'deviceShareInfo','设备分享','设备分享','deviceDataInfo',2,3,'1','');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (73,'shareInfo','分享','分享','deviceShareInfo',2,1,'1','dyconfig/shareInfo/doShareInfo.do?method=queryShareInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (74,'deviceMessage','设备消息','设备消息','deviceDataInfo',2,4,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (75,'deviceMessageInfo','消息','消息','deviceMessage',2,1,'1','dyconfig/msgInfo/doMsgInfo.do?method=queryMsgInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (76,'deviceSafeAreaInfo','设备安全区域','设备安全区域','deviceDataInfo',2,5,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (77,'safeAreaInfo','安全区域','安全区域','deviceSafeAreaInfo',2,1,'1','dyconfig/safeArea/doSafeArea.do?method=querySafeArea');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (78,'deviceSettingInfo','设备设置','设备设置','deviceDataInfo',2,6,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (79,'settingInfo','设置','设置','deviceSettingInfo',2,1,'1','dyconfig/settingInfo/doSettingInfo.do?method=querySettingInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (80,'clientManage','客户管理','客户管理','super',1,4,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (81,'clientMassage','客户信息','客户信息','clientManage',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (82,'companyInfo','客户','客户','clientMassage',2,1,'1','dyconfig/companyInfo/doCompanyInfo.do?method=queryCompanyInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (83,'channelManage1','渠道管理','渠道管理','super',1,3,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (84,'channelMassage1','渠道信息','渠道信息','channelManage',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (85,'channelInfo1','渠道商','渠道商','channelMassage',2,1,'1','dyconfig/channelInfo/doChannelInfo.do?method=queryChannelInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (86,'projectManage','项目管理','项目管理','super',1,3,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (87,'projectMassage','项目信息','项目信息','projectManage',2,1,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (88,'projectInfo','项目','项目','projectMassage',2,1,'1','dyconfig/projectInfo/doProjectInfo.do?method=queryProjectInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (92,'analysisArea','区域分析','区域分析','dataAnalysisInfo',2,2,'1',NULL);
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (93,'saleAreaInfo','销售区域','销售区域','analysisArea',2,1,'1','dyconfig/saleInfo/doSaleInfo.do?method=querySaleAreaInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (95,'appLoginInfo','登陆签到','登陆签到','appUserInfo',2,2,'1','dyconfig/userLoginInfo/doUserLoginInfo.do?method=queryUserLoginInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (96,'deviceLogin','登陆签到','登陆签到','deviceInfo',2,2,'1','dyconfig/deviceActiveInfo/doDeviceLogin.do?method=queryDeviceLogin');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (97,'dayAddInfo','日新增','日新增','saleInfo',2,1,'1','dyconfig/saleInfo/doSaleInfo.do?method=queryDayAddInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (99,'appVisit','APP接口性能工具','APP接口性能工具','debugTools',2,3,'1','sysAdmin/monitorInfo/doMonitorInfo.do?method=queryVisit&type=0');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (100,'deviceVisit','设备接口性能工具','设备接口性能工具','debugTools',2,4,'1','sysAdmin/monitorInfo/doMonitorInfo.do?method=queryVisit&type=1');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (101,NULL,'设备上传设备信息给服务器','设备上传设备信息给服务器',NULL,0,0,'','/DEVICE_UP_DEVICEINFO.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (102,NULL,'设备上传定位信息给服务器','设备上传定位信息给服务器',NULL,0,0,'','/DEVICE_UP_LOCATION.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (103,NULL,'设备请求设置信息','设备请求设置信息',NULL,0,0,'','/DEVICE_MSG_SETTING.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (104,NULL,'设备上传设置列表给服务器','设备上传设置列表给服务器',NULL,0,0,'','/DEVICE_UP_SETTING.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (105,NULL,'设备登录服务器','设备登录服务器',NULL,0,0,'','/DEVICE_ONLINE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (106,NULL,'设备请求亲情号码指令','设备请求亲情号码指令',NULL,0,0,'','/MSG_DEVICE_FAMILY_PHONE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (107,NULL,'设备上传所有亲情号码至服务器','设备上传所有亲情号码至服务器',NULL,0,0,'','/UP_DEVICE_FAMILY_PHONE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (108,NULL,'设备操作亲情号码指令至服务器','设备操作亲情号码指令至服务器',NULL,0,0,'','/UP_DEVICE_FAMILY_PHONE_TYPE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (109,NULL,'设备请求服务器APP端倾听指令','设备请求服务器APP端倾听指令',NULL,0,0,'','/GET_DEVICE_LISTEN.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (110,NULL,'设备请求服务器APP端报警指令','设备请求服务器APP端报警指令',NULL,0,0,'','/GET_DEVICE_ALARM_BELL_TYPE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (111,NULL,'时间同步接口','时间同步接口',NULL,0,0,'','/SYNC_TIME.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (112,NULL,'心跳接口','心跳接口',NULL,0,0,'','/HEARTBEAT.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (113,NULL,'防脱落接口','防脱落接口',NULL,0,0,'','/DEVICE_FALL.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (114,NULL,'上课防打扰','上课防打扰',NULL,0,0,'','/DEVICE_DISTURB.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (115,NULL,'低电提醒','低电提醒',NULL,0,0,'','/DEVICE_LOWELECTRICITY.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (116,NULL,'远程闹钟','远程闹钟',NULL,0,0,'','/DEVICE_CLOCK.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (117,NULL,'睡眠提醒','睡眠提醒',NULL,0,0,'','/DEVICE_SLEEP.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (118,NULL,'注册','注册',NULL,0,0,'','/doRegister.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (119,NULL,'登陆','登陆',NULL,0,0,'','/doLogin.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (120,NULL,'用户信息修改','用户信息修改',NULL,0,0,'','/doModifyUser.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (121,NULL,'设备信息修改','设备信息修改',NULL,0,0,'','/doModifyDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (122,NULL,'设备验证','设备验证',NULL,0,0,'','/doVerifyDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (123,NULL,'添加宝贝','添加宝贝',NULL,0,0,'','/doAddDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (124,NULL,'删除宝贝','删除宝贝',NULL,0,0,'','/doDeleteDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (125,NULL,'设备亲情号码','设备亲情号码',NULL,0,0,'','/doAddDeviceFamily.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (126,NULL,'删除亲情号码','删除亲情号码',NULL,0,0,'','/doDeleteDeviceFamily.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (127,NULL,'修改亲情号码','修改亲情号码',NULL,0,0,'','/doModifyDeviceFamily.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (128,NULL,'历史轨迹查询','历史轨迹查询',NULL,0,0,'','/doQueryDeviceTrack.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (129,NULL,'设备安全区域','设备安全区域',NULL,0,0,'','/doModifyDeviceSafeArea.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (130,NULL,'设备远程数据设置','设备远程数据设置',NULL,0,0,'','/doSetDeviceData.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (131,NULL,'设备数据下载','设备数据下载',NULL,0,0,'','/doDownLoadDeviceData.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (132,NULL,'版本升级','版本升级',NULL,0,0,'','/doUpdateApp.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (133,NULL,'意见反馈','意见反馈',NULL,0,0,'','/doFeedback.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (134,NULL,'倾听','倾听',NULL,0,0,'','/doListen.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (135,NULL,'定位','定位',NULL,0,0,'','/doLocation.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (136,NULL,'找宝贝','找宝贝',NULL,0,0,'','/doFindBaby.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (137,NULL,'删除安全区域','删除安全区域',NULL,0,0,'','/doDeleteDeviceSafeArea.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (138,NULL,'获取安全区域','获取安全区域',NULL,0,0,'','/doGetDeviceSafeArea.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (139,NULL,'设备分享','设备分享',NULL,0,0,'','/doShareDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (140,NULL,'消息处理','消息处理',NULL,0,0,'','/doMsgHandler.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (141,NULL,'消息获取','消息获取',NULL,0,0,'','/doGetMsg.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (142,NULL,'LBS定位','LBS定位',NULL,0,0,'','/GET_DEVICE_LOCATION_LBS.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (143,NULL,'取消分享','取消分享',NULL,0,0,'','/doDeleteShareDevice.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (144,NULL,'基站定位','基站定位',NULL,0,0,'','/DEVICE_UP_LOCATION.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (145,NULL,'找回密码接口','找回密码接口',NULL,0,0,'','/doFindPassword.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (146,NULL,'远程关机,驱蚊,爱心接口','远程关机,驱蚊,爱心接口',NULL,0,0,'','/REMOTE.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (147,NULL,'上课防打扰','上课防打扰',NULL,0,0,'','/SET_DISTURB.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (148,NULL,'低电提醒','低电提醒',NULL,0,0,'','/GET_LOWELECTRICITY.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (149,NULL,'远程闹钟','远程闹钟',NULL,0,0,'','/SET_CLOCK.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (150,NULL,'睡眠提醒','睡眠提醒',NULL,0,0,'','/SET_SLEEP.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (151,NULL,'防脱落','防脱落',NULL,0,0,'','/doGetFall.do');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (152,'IMEIManager','IMEI管理','IMEI管理','deviceInfo',2,4,'1','dyconfig/phoneInfo/doPhoneInfo.do?method=queryPhoneIMEIInfo');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (153,'deviceActiveHistory','设备绑定历史','设备绑定历史','deviceInfo',2,5,'1','dyconfig/deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveHistory');
insert  into `funcinfo`(`id`,`funcCode`,`funcName`,`funcDesc`,`superCode`,`levels`,`funcSort`,`statu`,`funcDo`) values (154,'wappUsersMsg','APP用户统计','APP用户统计','wappUsers',2,2,'1','dyconfig/wappUsers/doWappUsers.do');

/*Table structure for table `locationinfo` */

DROP TABLE IF EXISTS `locationinfo`;

CREATE TABLE `locationinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL COMMENT '设备IMEI',
  `battery` int(3) DEFAULT '50' COMMENT '设备电量',
  `longitude` varchar(50) DEFAULT NULL COMMENT 'GPS经度',
  `latitude` varchar(50) DEFAULT NULL COMMENT 'GPS纬度',
  `location_type` char(1) DEFAULT NULL COMMENT '定位类型 1--高德GPS 2--高德LBS 3--高德WIFI 4--Google GPS 5--Google Geolocation',
  `accuracy` float DEFAULT NULL COMMENT '定位精度',
  `upload_time` datetime DEFAULT NULL COMMENT '上传位置时间',
  `change_longitude` varchar(50) DEFAULT NULL COMMENT '对应地图经度',
  `change_latitude` varchar(50) DEFAULT NULL COMMENT '对应地图纬度',
  `belong_project` int(11) DEFAULT '1' COMMENT '所属项目',
  `fall` char(1) DEFAULT '1' COMMENT '是否配戴',
  `step_count` int(11) DEFAULT '0' COMMENT '计步器累计步数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


/*Data for the table `locationinfo` */


/*Table structure for table `mediainfo` */

DROP TABLE IF EXISTS `mediainfo`;

CREATE TABLE `mediainfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_content` varchar(200) DEFAULT NULL,
  `from_id` varchar(20) DEFAULT NULL,
  `to_id` varchar(20) DEFAULT NULL,
  `send_type` char(1) DEFAULT '0',
  `send_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '0',
  `time_length` varchar(11) DEFAULT '0',
  `belong_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;

/*Data for the table `mediainfo` */


/*Table structure for table `monitorinfo` */

DROP TABLE IF EXISTS `monitorinfo`;

CREATE TABLE `monitorinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime default NULL,
  `end_time` datetime default NULL,
  `cost_time` int(11) default NULL,
  `function` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `function_href` varchar(855) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_id` int(11) default -1,
  `user_id` int(11) default -1,  
  `server_time` datetime default NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `monitorinfo` */


/*Table structure for table `msg_info` */

DROP TABLE IF EXISTS `msg_info`;

CREATE TABLE `msg_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_level` char(1) DEFAULT '0' COMMENT '消息级别',
  `from_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '哪个用户发出的id',
  `to_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '给哪个用户发',
  `is_handler` char(1) DEFAULT '0' COMMENT '默认没有处理,1表示处理',
  `msg_content` text NOT NULL COMMENT '消息内容',
  `msg_handler_date` datetime NOT NULL DEFAULT '2015-01-01 00:00:00' COMMENT '消息处理时间',
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1582 DEFAULT CHARSET=utf8;

/*Data for the table `msg_info` */

insert  into `msg_info`(`id`,`msg_level`,`from_id`,`to_id`,`is_handler`,`msg_content`,`msg_handler_date`,`belong_project`) values (1,'0','3','3','1','3@152452512545252@test','2016-03-28 19:00:00',1);
insert  into `msg_info`(`id`,`msg_level`,`from_id`,`to_id`,`is_handler`,`msg_content`,`msg_handler_date`,`belong_project`) values (2,'0','3','3','1','2@152452512545252@test2','2016-03-28 19:00:00',1);

/*Table structure for table `phone_manage` */

DROP TABLE IF EXISTS `phone_manage`;

CREATE TABLE `phone_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `input_time` datetime DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `count_num` varchar(20) DEFAULT NULL,
  `mini_num` varchar(20) DEFAULT NULL,
  `max_num` varchar(20) DEFAULT NULL,
  `type` char(1) DEFAULT '' COMMENT '1为测试，2为量产',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `phone_manage` */

/*Table structure for table `phoneinfo` */

DROP TABLE IF EXISTS `phoneinfo`;

CREATE TABLE `phoneinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL,
  `product_model` varchar(50) DEFAULT NULL,
  `firmware_edition` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `alarm_bell_type` char(1) DEFAULT '',
  `input_time` datetime DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '0',
  `shutdown` char(1) DEFAULT '0',
  `repellent` char(1) DEFAULT '0',
  `heart` varchar(5) DEFAULT '0',
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `phone_manage_id` int(11) DEFAULT NULL COMMENT 'IMEI管理表id',
  `relative_call_status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

/*Data for the table `phoneinfo` */

/*Table structure for table `photoinfo` */

DROP TABLE IF EXISTS `photoinfo`;

CREATE TABLE `photoinfo` (
  `id_o` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(20) NOT NULL,
  `product_picture` varchar(200) NOT NULL,
  PRIMARY KEY (`id_o`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `photoinfo` */

/*Table structure for table `playiteminfo` */

DROP TABLE IF EXISTS `playiteminfo`;

CREATE TABLE `playiteminfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL,
  `product_model` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `control_type` char(1) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `playiteminfo` */

/*Table structure for table `productinfo` */

DROP TABLE IF EXISTS `productinfo`;

CREATE TABLE `productinfo` (
  `id_p` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(200) NOT NULL,
  `product_type` varchar(50) NOT NULL,
  `product_order` int(11) NOT NULL DEFAULT '1',
  `product_intro` text NOT NULL,
  `product_flag` varchar(50) NOT NULL,
  `date_time` datetime NOT NULL,
  `icon` varchar(200) NOT NULL,
  `price` varchar(200) NOT NULL DEFAULT '0',
  `product_address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_p`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `productinfo` */

/*Table structure for table `projectinfo` */

DROP TABLE IF EXISTS `projectinfo`;

CREATE TABLE `projectinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_no` varchar(20) DEFAULT '',
  `project_name` varchar(50) DEFAULT '',
  `channel_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `add_time` datetime DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `projectinfo` */

/*Table structure for table `relativecallinfo` */

DROP TABLE IF EXISTS `relativecallinfo`;

CREATE TABLE `relativecallinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL,
  `product_model` varchar(50) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `family_ssid` varchar(20) DEFAULT NULL,
  `family_passwd` varchar(12) DEFAULT NULL,
  `host_ssid` varchar(12) DEFAULT NULL,
  `relative_type` char(1) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT '',
  `status` char(1) DEFAULT '',
  `user_id` varchar(11) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `relativecallinfo` */

insert  into `relativecallinfo`(`id`,`serie_no`,`product_model`,`phone_number`,`family_ssid`,`family_passwd`,`host_ssid`,`relative_type`,`nick_name`,`status`,`user_id`,`add_time`,`belong_project`) values (1,'862989021010104',NULL,'13566965423',NULL,NULL,NULL,'0','hh','0','3','2015-09-26 15:48:14',1);
insert  into `relativecallinfo`(`id`,`serie_no`,`product_model`,`phone_number`,`family_ssid`,`family_passwd`,`host_ssid`,`relative_type`,`nick_name`,`status`,`user_id`,`add_time`,`belong_project`) values (2,'862989021010104',NULL,'15118109750',NULL,NULL,NULL,'0','妈妈','0','3','2015-09-20 16:23:52',1);
insert  into `relativecallinfo`(`id`,`serie_no`,`product_model`,`phone_number`,`family_ssid`,`family_passwd`,`host_ssid`,`relative_type`,`nick_name`,`status`,`user_id`,`add_time`,`belong_project`) values (3,'123456789012345',NULL,'42348175','waterworld1','123','huawei_01','0','father','0','1','2016-08-11 16:13:30',1);

/*Table structure for table `rolefuncinfo` */

DROP TABLE IF EXISTS `rolefuncinfo`;

CREATE TABLE `rolefuncinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(25) NOT NULL,
  `funcCode` varchar(50) NOT NULL,
  `userCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

/*Data for the table `rolefuncinfo` */

insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (3,'Administrator','sysAdmin','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (4,'Administrator','sysInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (5,'Administrator','roleInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (6,'Administrator','adminInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (7,'Administrator','debugTools','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (8,'Administrator','monitorInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (9,'Administrator','sysLogInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (10,'Administrator','userAndDevice','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (11,'Administrator','appUserInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (12,'Administrator','appUserMsg','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (13,'Administrator','deviceInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (14,'Administrator','IMEIMsg','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (15,'Administrator','deviceMsg','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (18,'Administrator','updateAndBackInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (19,'Administrator','updateInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (20,'Administrator','APPUpdateInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (21,'Administrator','backInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (22,'Administrator','feedBackInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (23,'Administrator','dataAnalysisInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (32,'Administrator','deviceDataInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (33,'Administrator','dataMsgInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (34,'Administrator','relativeCallInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (36,'Administrator','settingInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (37,'Administrator','locationInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (38,'Administrator','safeAreaInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (39,'Administrator','location','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (40,'Administrator','deviceShareInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (41,'Administrator','shareInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (42,'Administrator','deviceMessage','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (43,'Administrator','deviceMessageInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (44,'Administrator','deviceSafeAreaInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (45,'Administrator','deviceSettingInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (46,'操作员','sysAdmin','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (47,'操作员','sysInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (48,'操作员','roleInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (49,'操作员','adminInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (50,'Administrator','clientManage','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (51,'Administrator','clientMassage','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (52,'Administrator','companyInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (56,'Administrator','projectManage','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (57,'Administrator','projectMassage','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (58,'Administrator','projectInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (59,'Administrator','saleInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (63,'Administrator','analysisArea','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (64,'Administrator','saleAreaInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (66,'Administrator','appLoginInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (67,'Administrator','deviceLogin','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (68,'Administrator','dayAddInfo','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (69,'Administrator','appVisit','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (70,'Administrator','deviceVisit','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (71,'Administrator','IMEIManager','admin');
insert  into `rolefuncinfo`(`id`,`roleCode`,`funcCode`,`userCode`) values (72,'Administrator','deviceActiveHistory','admin');

/*Table structure for table `roleinfo` */

DROP TABLE IF EXISTS `roleinfo`;

CREATE TABLE `roleinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(25) NOT NULL,
  `roleDesc` varchar(50) DEFAULT NULL,
  `roleCode` varchar(25) NOT NULL,
  `roleType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `roleinfo` */

insert  into `roleinfo`(`id`,`roleName`,`roleDesc`,`roleCode`,`roleType`) values (1,'Administrator','admin','Administrator','Administrator');
insert  into `roleinfo`(`id`,`roleName`,`roleDesc`,`roleCode`,`roleType`) values (2,'管理员','manager','管理员','管理员');
insert  into `roleinfo`(`id`,`roleName`,`roleDesc`,`roleCode`,`roleType`) values (3,'操作员','operator','操作员','操作员');

/*Table structure for table `safe_area` */

DROP TABLE IF EXISTS `safe_area`;

CREATE TABLE `safe_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL DEFAULT '',
  `device_id` int(11) NOT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `safe_range` int(10) DEFAULT NULL,
  `area_name` varchar(50) DEFAULT NULL,
  `area_effect_time` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `safe_address` varchar(120) DEFAULT '0',
  `status` char(2) DEFAULT '0',
  `belong_project` int(11) DEFAULT '0' COMMENT '所属哪个项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `safe_area` */

/*Table structure for table `saleinfo` */

DROP TABLE IF EXISTS `saleinfo`;

CREATE TABLE `saleinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `imei` varchar(15) DEFAULT NULL,
  `imsi` varchar(15) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `phone_model` varchar(50) DEFAULT NULL,
  `sys_version` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `date_time` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `app_version` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `saleinfo` */


/*Table structure for table `settinginfo` */

DROP TABLE IF EXISTS `settinginfo`;

CREATE TABLE `settinginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serie_no` varchar(50) DEFAULT NULL,
  `volume` int(3) DEFAULT NULL,
  `map` char(2) DEFAULT NULL,
  `fallOn` char(1) DEFAULT NULL,
  `light` char(2) DEFAULT NULL,
  `gps_on` char(2) DEFAULT NULL,
  `light_sensor` char(1) DEFAULT NULL,
  `fall` char(1) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `auto_mute` char(1) DEFAULT NULL,
  `power_off` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Data for the table `settinginfo` */


/*Table structure for table `share_info` */

DROP TABLE IF EXISTS `share_info`;

CREATE TABLE `share_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `to_user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '要分享给哪个用户',
  `device_imei` varchar(20) NOT NULL DEFAULT '0' COMMENT '设备的imei',
  `is_priority` char(1) NOT NULL DEFAULT '1' COMMENT '1表示是主设备,0表示是分享的设备',
  `share_date` datetime NOT NULL DEFAULT '2015-01-01 00:00:00' COMMENT '分享的时间',
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;

/*Data for the table `share_info` */


/*Table structure for table `switch_info` */

DROP TABLE IF EXISTS `switch_info`;

CREATE TABLE `switch_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `visit_s` char(2) DEFAULT NULL COMMENT 'app端访问',
  `moni_s` char(2) NOT NULL DEFAULT '' COMMENT '监听',
  `device_s` char(2) DEFAULT NULL COMMENT '设备端访问',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `switch_info` */

insert  into `switch_info`(`id`,`visit_s`,`moni_s`,`device_s`) values (1,'1','1','1');

/*Table structure for table `sysloginfo` */

DROP TABLE IF EXISTS `sysloginfo`;

CREATE TABLE `sysloginfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(25) NOT NULL,
  `logDate` datetime NOT NULL,
  `logs` varchar(1024) NOT NULL,
  `ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

/*Data for the table `sysloginfo` */


/*Table structure for table `sysloginfo_beifen` */

DROP TABLE IF EXISTS `sysloginfo_beifen`;

CREATE TABLE `sysloginfo_beifen` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(25) NOT NULL,
  `logDate` datetime NOT NULL DEFAULT '2015-12-12 23:59:59',
  `logs` varchar(1024) NOT NULL,
  `ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sysloginfo_beifen` */

/*Table structure for table `test_user` */

DROP TABLE IF EXISTS `test_user`;

CREATE TABLE `test_user` (
  `id` int(11) NOT NULL COMMENT '宠物ID',
  `type` char(2) DEFAULT '1' COMMENT '运动类型，0: 慢走，1:走,2:快走,3:慢跑，4：跑，5：快跑',
  `count` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test_user` */

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userCode` varchar(25) NOT NULL DEFAULT '',
  `userName` varchar(50) NOT NULL,
  `passWrd` varchar(50) NOT NULL,
  `passWrd1` varchar(50) NOT NULL,
  `tag` int(2) NOT NULL DEFAULT '0',
  `createDate` datetime NOT NULL DEFAULT '2015-12-12 23:59:59',
  `updateDate` datetime DEFAULT '2015-12-12 23:59:59',
  `groupCode` varchar(100) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `addUser` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

insert  into `userinfo`(`id`,`userCode`,`userName`,`passWrd`,`passWrd1`,`tag`,`createDate`,`updateDate`,`groupCode`,`remark`,`code`,`addUser`) values (70,'admin','admin','e10adc3949ba59abbe56e057f20f883e','123456',1,'2014-06-23 12:00:00','2015-08-13 19:51:52','Administrator','Administrator','admin','admin');
insert  into `userinfo`(`id`,`userCode`,`userName`,`passWrd`,`passWrd1`,`tag`,`createDate`,`updateDate`,`groupCode`,`remark`,`code`,`addUser`) values (71,'test_01','test_01','e10adc3949ba59abbe56e057f20f883e','123456',1,'2015-08-25 15:52:09','2015-08-25 15:52:09','操作员','','admin','admin');


/*Data for the table `userinfo` */

/*Table structure for table `userlogininfo` */

DROP TABLE IF EXISTS `userlogininfo`;

CREATE TABLE `userlogininfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `imei` varchar(20) DEFAULT '',
  `imsi` varchar(20) DEFAULT '',
  `phone_model` varchar(50) DEFAULT '',
  `phone_version` varchar(50) DEFAULT '',
  `app_version` varchar(50) DEFAULT '',
  `ip` varchar(20) DEFAULT '',
  `province` varchar(50) DEFAULT '',
  `belong_project` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1501 DEFAULT CHARSET=utf8;

/*Data for the table `userlogininfo` */


/*Table structure for table `visit` */

DROP TABLE IF EXISTS `visit`;

CREATE TABLE `visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` char(20) NOT NULL COMMENT '访问者的电话号码',
  `function` char(20) NOT NULL DEFAULT '' COMMENT '访问接口',
  `function_href` varchar(200) NOT NULL DEFAULT '' COMMENT '具体接口',
  `belong_project` int(11) NOT NULL COMMENT '所属项目',
  `start_time` datetime NOT NULL COMMENT '访问时间',
  `type` char(2) NOT NULL DEFAULT '0' COMMENT '类型,默认app端访问接口(0)',
  `end_time` datetime DEFAULT NULL COMMENT '访问结束时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=199229 DEFAULT CHARSET=utf8;

/*Data for the table `visit` */


/*Table structure for table `wakc_standard` */

DROP TABLE IF EXISTS `wakc_standard`;

CREATE TABLE `wakc_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex` char(2) DEFAULT '1' COMMENT '0: mail,1:female	',
  `minShoulder` float DEFAULT NULL COMMENT '肩高的起始范围，单位：cm',
  `maxShoulder` float DEFAULT NULL COMMENT '肩高的截止范围，单位：cm',
  `minWeight` float DEFAULT NULL COMMENT '体重的起始范围，单位：kg',
  `maxWeight` float DEFAULT NULL COMMENT '体重的截止范围，单位：kg',
  `ext1` float DEFAULT NULL COMMENT '预留字段',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  `ext3` float DEFAULT NULL COMMENT '预留字段',
  `ext4` float DEFAULT NULL COMMENT '预留字段',
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  `fci_detail_all_catid` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_AKC_standard_FCI_detail_all1_idx` (`fci_detail_all_catid`),
  CONSTRAINT `fk_AKC_standard_FCI_detail_all1` FOREIGN KEY (`fci_detail_all_catid`) REFERENCES `wfci_detail_all` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `wakc_standard` */

insert  into `wakc_standard`(`id`,`sex`,`minShoulder`,`maxShoulder`,`minWeight`,`maxWeight`,`ext1`,`ext2`,`ext3`,`ext4`,`fci_detail_catid`,`fci_detail_all_catid`) values (1,'0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'FDA00003',NULL);
insert  into `wakc_standard`(`id`,`sex`,`minShoulder`,`maxShoulder`,`minWeight`,`maxWeight`,`ext1`,`ext2`,`ext3`,`ext4`,`fci_detail_catid`,`fci_detail_all_catid`) values (2,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'FDA00003',NULL);

/*Table structure for table `wapplocationinfo` */

DROP TABLE IF EXISTS `wapplocationinfo`;

CREATE TABLE `wapplocationinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `battery` int(3) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `location_type` char(1) DEFAULT NULL,
  `accuracy` int(5) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `change_longitude` varchar(50) DEFAULT NULL,
  `change_latitude` varchar(50) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL,
  `fall` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2316 DEFAULT CHARSET=utf8;

/*Data for the table `wapplocationinfo` */



DROP TABLE IF EXISTS `wappWiFiInfo`;
/*Table structure for table `wappWiFiInfo` APP主设备WIFI源信息 表 */
CREATE TABLE `wappWiFiInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,  
  `device_id` int(11) NOT NULL,    
  `ssid` varchar(80) NOT NULL COMMENT 'wifi ssid',
  `pwd` varchar(20) NOT NULL,
  `wifi_on` char(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `wappusers`;

CREATE TABLE `wappusers` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(80) NOT NULL COMMENT '邮箱地址',
  `passwd` varchar(80) DEFAULT NULL,
  `nickname` varchar(40) DEFAULT NULL COMMENT '昵称',
  `sex` char(2) DEFAULT '1',
  `photo` varchar(60) DEFAULT NULL,
  `status` char(1) DEFAULT '1' COMMENT '状态标志, 0: 禁用 ，1： 不禁用',
  `login_status` char(2) DEFAULT '0' COMMENT '用户APP状态，“0”没有进入app，也没有在后台  “1”用户进入了app,并且在前台， “2”用户进入了app,并且在后台',  
  `act_device_id` int(11) DEFAULT NULL COMMENT '客户已连接并设置为当前关注项圈的设备id号',
  `ext1` varchar(60) DEFAULT NULL COMMENT '预留字段, 0: android app login, 1: ios app login',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `lastlogin_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `belong_project` int(11) DEFAULT '0' COMMENT '所属项目',
  `bind_count` int(11) DEFAULT '0' COMMENT '绑定设备数量',
  `login_imei` varchar(20) DEFAULT NULL COMMENT 'IMEI',
  `login_imsi` varchar(20) DEFAULT NULL COMMENT 'IMEI',
  `zousnqag` varchar(200) DEFAULT NULL,
  `phone_mac` varchar(50) DEFAULT NULL,
  `born_date` date DEFAULT NULL,
  `firstname` varchar(80) DEFAULT NULL,
  `lastname` varchar(80) DEFAULT NULL,
  `addr1` varchar(80) DEFAULT NULL,
  `addr2` varchar(40) DEFAULT NULL COMMENT 'IP地址',
  `city` varchar(80) DEFAULT NULL,
  `state` varchar(80) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `phone` varchar(40) DEFAULT NULL,
  `product_model` varchar(50) DEFAULT NULL,
  `firmware_edition` varchar(50) DEFAULT NULL,
  `user_type` char(2) DEFAULT '0' COMMENT '0: 正常用户 1：测试用户 2:facebook账号',
  `signtext` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `app_token` varchar(50) DEFAULT NULL COMMENT '会话TOKEN',
  `photo_time_stamp` datetime DEFAULT NULL COMMENT '头像文件资料更新日期时间戳，用于后台与APP同步数据用',
  `time_zone` char(50) DEFAULT 'Asia/Shanghai' COMMENT '时区属性，如中国对应为<Asia/Shanghai>',      
  `device_token` char(80) DEFAULT NULL COMMENT '设备ID,android app为android_id',  
  `app_version` char(80) DEFAULT NULL COMMENT 'app版本号',  
  `pet_count` int(11) DEFAULT '0' COMMENT '用户宠物数量',  
  `ios_token` char(80) DEFAULT NULL COMMENT 'ios apns token',    
  `ios_real` char(80) DEFAULT NULL COMMENT 'ios real apns token',   
  `badge` INT(11) DEFAULT 1 COMMENT '如果消息是发给苹果手机，记录对应的badge',
  `lang` char(10) DEFAULT 'en' COMMENT 'app 系统语言',
  `udu` char(2) DEFAULT '0' COMMENT '表示用户全局设置距离的单位为米或者英尺，0: 米 1：英尺',
  `dfg` char(2) DEFAULT '0' COMMENT '表示用户是否为dream ware 用户，0: 不是 1：是',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `wappusers` */
INSERT INTO `wappusers` (`user_id`, `email`, `passwd`, `nickname`, `sex`, `photo`, `status`, `login_status`, `act_device_id`, `ext1`, `create_time`, `lastlogin_time`, `belong_project`, `bind_count`, `login_imei`, `login_imsi`, `zousnqag`, `phone_mac`, `born_date`, `firstname`, `lastname`, `addr1`, `addr2`, `city`, `state`, `zip`, `country`, `phone`, `product_model`, `firmware_edition`, `user_type`, `signtext`, `app_token`, `photo_time_stamp`, `time_zone`) VALUES('-1','empty@163.com','123456','logAdmin','0',NULL,NULL,NULL,NULL,NULL,'2016-09-11 16:34:13','2016-09-12 13:46:14','1','0',NULL,NULL,NULL,NULL,'1992-02-05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,'39330',NULL,'Asia/Shanghai');
insert into `wappusers` (`user_id`, `email`, `passwd`, `nickname`, `sex`, `photo`, `status`, `login_status`, `act_device_id`, `ext1`, `create_time`, `lastlogin_time`, `belong_project`, `bind_count`, `login_imei`, `login_imsi`, `zousnqag`, `phone_mac`, `born_date`, `firstname`, `lastname`, `addr1`, `addr2`, `city`, `state`, `zip`, `country`, `phone`, `product_model`, `firmware_edition`, `user_type`, `signtext`, `app_token`, `photo_time_stamp`, `time_zone`) values('1','461261532@qq.com','123456','yonghu','0',NULL,'1','0',NULL,NULL,'2016-06-01 17:15:21','2016-06-01 17:16:21','1','0',NULL,NULL,NULL,NULL,'1975-09-18',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,'Asia/Shanghai');
insert into `wappusers` (`user_id`, `email`, `passwd`, `nickname`, `sex`, `photo`, `status`, `login_status`, `act_device_id`, `ext1`, `create_time`, `lastlogin_time`, `belong_project`, `bind_count`, `login_imei`, `login_imsi`, `zousnqag`, `phone_mac`, `born_date`, `firstname`, `lastname`, `addr1`, `addr2`, `city`, `state`, `zip`, `country`, `phone`, `product_model`, `firmware_edition`, `user_type`, `signtext`, `app_token`, `photo_time_stamp`, `time_zone`) values('2','wwwqingtian@126.com','123456','test2','0',NULL,'1','0',NULL,NULL,'2016-06-01 17:15:21','2016-06-01 17:15:21','1','0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,'12345678912345678912',NULL,'Asia/Shanghai');
insert into `wappusers` (`user_id`, `email`, `passwd`, `nickname`, `sex`, `photo`, `status`, `login_status`, `act_device_id`, `ext1`, `create_time`, `lastlogin_time`, `belong_project`, `bind_count`, `login_imei`, `login_imsi`, `zousnqag`, `phone_mac`, `born_date`, `firstname`, `lastname`, `addr1`, `addr2`, `city`, `state`, `zip`, `country`, `phone`, `product_model`, `firmware_edition`, `user_type`, `signtext`, `app_token`, `photo_time_stamp`, `time_zone`) values('5','zhouweibin_1992@163.com','123456','zwb','0',NULL,NULL,NULL,NULL,NULL,'2016-09-11 16:34:13','2016-09-12 13:46:14','1','0',NULL,NULL,NULL,NULL,'1992-02-05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,'29730',NULL,'Asia/Shanghai');
insert into `wappusers` (`user_id`, `email`, `passwd`, `nickname`, `sex`, `photo`, `status`, `login_status`, `act_device_id`, `ext1`, `create_time`, `lastlogin_time`, `belong_project`, `bind_count`, `login_imei`, `login_imsi`, `zousnqag`, `phone_mac`, `born_date`, `firstname`, `lastname`, `addr1`, `addr2`, `city`, `state`, `zip`, `country`, `phone`, `product_model`, `firmware_edition`, `user_type`, `signtext`, `app_token`, `photo_time_stamp`, `time_zone`) values('6','wqing@waterworld.com.cn','123456',NULL,'0',NULL,NULL,NULL,NULL,NULL,'2016-09-12 11:49:57','2016-09-12 11:49:57','1','0',NULL,NULL,NULL,NULL,'2015-12-12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,'Asia/Shanghai');

/*Table structure for table `wappuserverify` */

DROP TABLE IF EXISTS `wappuserverify`;

CREATE TABLE `wappuserverify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `verify_code` varchar(10) NOT NULL,
  `create_time` date NOT NULL,
  `belong_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `wdevLogFile`;
CREATE TABLE `wdevLogFile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `log_file` varchar(10) NOT NULL,
  `up_time` datetime NOT NULL,
  `belong_project` int(8) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Data for the table `wappuserverify` */

/*Table structure for table `wappuserverify2` */

DROP TABLE IF EXISTS `wappuserverify2`;

CREATE TABLE `wappuserverify2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `verify_code` varchar(10) NOT NULL,
  `create_time` date NOT NULL,
  `belong_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wappuserverify2` */

/*Table structure for table `wchannelinfo` */

DROP TABLE IF EXISTS `wchannelinfo`;

CREATE TABLE `wchannelinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_no` varchar(20) DEFAULT '' COMMENT '渠道编号',
  `channel_name` varchar(50) DEFAULT '' COMMENT '渠道名称',
  `company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` text COMMENT '备注',
  `status` char(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wchannelinfo` */

/*Table structure for table `wcheckinfo` */

DROP TABLE IF EXISTS `wcheckinfo`;

CREATE TABLE `wcheckinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_name` varchar(50) NOT NULL,
  `version_name` varchar(50) NOT NULL,
  `version_code` varchar(50) NOT NULL,
  `download_path` varchar(200) NOT NULL,
  `function_cap` text,
  `belong_project` int(11) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL COMMENT '版本上传时间',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO wcheckinfo (package_name, version_name, version_code, function_cap, download_path ) VALUES ('devFirm','device firmware', 'v1.0.1', '初始版本', '');
INSERT INTO wcheckinfo (package_name, version_name, version_code, function_cap, download_path ) VALUES ('androidPetDog','android app ver.', 'V1.3.4', '升级全功能阶段版本', 'Paby-1.3.4-20161020.apk');

/*Data for the table `wcheckinfo` */

/*Table structure for table `wcompanyinfo` */

DROP TABLE IF EXISTS `wcompanyinfo`;

CREATE TABLE `wcompanyinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_no` varchar(20) DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `channel_id` varchar(500) DEFAULT NULL COMMENT '渠道编号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` text COMMENT '备注',
  `status` char(1) DEFAULT '1' COMMENT '状态',
  `user_name` varchar(20) DEFAULT '' COMMENT '登录账号用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wcompanyinfo` */

/*Table structure for table `wdevice_active_info` */


DROP TABLE IF EXISTS `device_ip_active_info`;

CREATE TABLE `device_ip_active_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `device_imei` VARCHAR(22) NOT NULL,
  `service_ip` VARCHAR(20) NOT NULL,
  `date_time` VARCHAR(20) NOT NULL,
  `port` INT(5) NOT NULL,
  `h_port` INT(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `wdevice_active_info`;

CREATE TABLE `wdevice_active_info` (
  `device_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_phone` varchar(20) DEFAULT '0' COMMENT '设备电话号码',
  `device_imei` varchar(20) NOT NULL DEFAULT '000000000000000' COMMENT '设备的imei号',
  `device_name` varchar(10) DEFAULT '0' COMMENT '设备名称',
  `device_update_time` datetime DEFAULT '2016-11-11 00:00:00' COMMENT '设备上传时间',
  `device_disable` varchar(2) DEFAULT '1' COMMENT '设备是否禁用,0表示禁用,1(默认)表示不禁用',
  `listen_type` char(1) DEFAULT '0',
  `belong_project` int(11) DEFAULT '1' COMMENT '所属哪个项目ID',
  `isphoneHelp` char(1) DEFAULT '0' COMMENT '0不需要1需要',
  `isserverHelp` char(1) DEFAULT '0' COMMENT '0不需要1需要',
  `deadline` datetime DEFAULT '2015-03-18 00:00:00',
  `brandname` varchar(50) DEFAULT 'paby 1' COMMENT '品牌名称',
  `fall_on` char(2) DEFAULT '0' COMMENT '防脱落 0不开1开',
  `device_type` char(2) DEFAULT '0' COMMENT '设备类型0：第一代发现者；1:第二代守护者；',
  `conn_type` char(2) DEFAULT '3' COMMENT '宠物项圈连接进入系统的方式，0: 通过蓝牙连接手机，通过手机进入系统；1:通过项圈自己的移动通信网络，2：脱机 3：WIFI',
  `is_sos` char(2) DEFAULT '0' COMMENT '是否开启SOS求救0不开',
  `sel_mode` char(2) DEFAULT '0' COMMENT '用户设置对具体设备（如狗狗项圈）的工作模式。0:睡眠模式； 1:遛狗模式；2：脱机模式 3：寻回模式 4：炫酷模式 5：电子围栏模式, 6: 寻回模式及电子围栏模式同时启用',
  `gps_on` char(1) DEFAULT '0' COMMENT 'GPS开关, 0:关，1：开',
  `callback_on` char(1) DEFAULT '0' COMMENT '唤回声音开关, 0:关，1：开',
  `temperature_on` char(1) DEFAULT '0' COMMENT '体温监测开关, 0:关，1：开',
  `heatout_on` char(1) DEFAULT '0' COMMENT '环境温度监测开关, 0:关，1：开',
  `led_on` char(1) DEFAULT '0' COMMENT '灯光控制开关, 0:关，1：开',
  `flight_mode` char(1) DEFAULT '0' COMMENT '飞行模式状态：0--关 1--开',
  `urgent_mode` char(1) DEFAULT '0' COMMENT '寻找模式状态：0--关 1--开',
  `battery` int(3) DEFAULT '0' COMMENT '终端电池电量：0---99',
  `is_lowbat` char(1) DEFAULT '0' COMMENT '电量低于30%，置1；',
  `data_mute` char(1) DEFAULT '0' COMMENT '设备是否自动静音  “0”否 “1”是 ',
  `data_volume` char(3) DEFAULT '0' COMMENT '设备音量大小',
  `data_power` char(1) DEFAULT '0' COMMENT '设备开关机  “0”关  “1”开',
  `bind_count` int(5) DEFAULT '0' COMMENT '共有多少个用户绑定该设备',
  `wifir_interval` char(15) DEFAULT '2000' COMMENT 'WIFI测距默认刷新间隔时间，单位，秒',
  `wifir_flag` char(2) DEFAULT '0' COMMENT 'WIFI测距开关，“0”关  “1”开',
  `time_zone` char(50) DEFAULT 'Asia/Shanghai' COMMENT '时区属性，如中国对应为<Asia/Shanghai>',
  `longitude` char(40) DEFAULT '0' COMMENT '设备最新的经度',
  `latitude` char(40) DEFAULT '0' COMMENT '设备最新的纬度',
  `auto_time_zone` char(2) DEFAULT '1' COMMENT '自动更新时区属性，1:自动更新, 0: 不自动更新',
  `beattim` int(3) DEFAULT '5' COMMENT '设备心跳包时间间隔',
  `iccid` VARCHAR(70) DEFAULT NULL，
  `iccid_auto` int(3) DEFAULT 1 COMMENT '设备实时更新iccid',
  `ulfq` int(5) DEFAULT 100 COMMENT '每多少步运动后设备主动上报位置',
  `dynIccid` VARCHAR(70) DEFAULT NULL COMMENT '动态获取设备SIM卡对应的iccid'，
  `uLTe` int(5) DEFAULT 30 COMMENT '定时多少分钟设备主动上传位置， 0关闭定时上传位置',
  `device_update_time_ts` int(4) DEFAULT 0 COMMENT '整型存放时间日期值',  
  `ttype` varchar(30) not null DEFAULT 'wdevice_active_info' COMMENT '表名',  
  `device_id_tag` varchar(50) DEFAULT '' COMMENT 'device_id tag',  
  `test_status` char(2) DEFAULT '1' COMMENT 'test status',  
  `sn` varchar(16) DEFAULT '0' COMMENT '工厂序列号',  
  PRIMARY KEY (`device_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sales_promotion` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `promotion_code` varchar(16) NOT NULL,
  `card_status` char(1) NOT NULL DEFAULT '1' COMMENT '1表示可以使用0表示不能',
  `type` char(1) NOT NULL DEFAULT '1' COMMENT '1表示为优惠卡2表示为代金券',
  `card_type` char(1) DEFAULT '1' COMMENT '1年2半年3月',
  `discount_rate` varchar(3) DEFAULT '0' COMMENT '折扣率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sim_info`;
CREATE TABLE `sim_info` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `iccid` varchar(64) DEFAULT NULL,
  `friendly_name` varchar(64) DEFAULT NULL,
  `sid` varchar(64) NOT NULL,
  `rate_plan` varchar(200) DEFAULT NULL,
  `card_status` char(2) DEFAULT NULL  COMMENT ' 0：注销， 1： 支付成功',
  `create_time` varchar(25) DEFAULT NULL,
  `update_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS `cancel_sub_info`;
CREATE TABLE `cancel_sub_info` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `imei` varchar(22) DEFAULT NULL,
  `date_time` varchar(25) DEFAULT NULL,
  `card_status` varchar(5) DEFAULT NULL,
  `message` text,
  `customer_id` varchar(50) DEFAULT NULL,
  `subscription_id` varchar(50) DEFAULT NULL,
  `stop_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `cancel_sub_loginfo` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `imei` varchar(22) DEFAULT NULL,
  `date_time` varchar(25) DEFAULT NULL,
  `card_status` varchar(5) DEFAULT NULL,
  `stop_time` varchar(25) DEFAULT NULL,
  `belong_company` char(2) DEFAULT NULL,
  `message` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS `payfor_info`;
CREATE TABLE `payfor_info` (
  `id` INT(16) NOT NULL AUTO_INCREMENT,
  `imei` VARCHAR(22) NOT NULL,
  `create_time` VARCHAR(25) NOT NULL,
  `account` INT(16) NOT NULL,
  `pay_status` VARCHAR(5) NOT NULL  COMMENT ' 200：成功',
  `message` TEXT NOT NULL,
  `currency` VARCHAR(10) DEFAULT NULL COMMENT '单位',
  `description` VARCHAR(10) DEFAULT NULL,
  `source` VARCHAR(50) DEFAULT NULL COMMENT '用户token',
  `card_number` VARCHAR(22) DEFAULT NULL,
  `customer_id` VARCHAR(50) DEFAULT NULL,
  `email` VARCHAR(25) DEFAULT NULL,
  `subscription_id` VARCHAR(50) DEFAULT NULL,
  `plan_count` char(2) DEFAULT NULL COMMENT '0： 一次 ， 1： 连续',
  `iccid` varchar(22) DEFAULT NULL COMMENT 'SIM卡iccid',
  `sub_status` char(2) DEFAULT NULL COMMENT '订阅状态 1: 成功， 0: 失败',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8




/*Data for the table `wdevice_active_info` */

/*Table structure for table `wDeviceExtra` 设备电子围栏属性、时区属性等等*/
DROP TABLE IF EXISTS `wDeviceExtra`;
CREATE TABLE `wDeviceExtra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `esafe_on` char(2) DEFAULT '0' COMMENT '电子围栏总开关，“0”关  “1”开',
  `prev_eid` int(11) DEFAULT NULL COMMENT '前一时间段(暂定为5分钟)的所属电子围栏id',
  `cur_eid` int(11) DEFAULT NULL COMMENT '当前所属电子围栏id',
  `prev_time` datetime DEFAULT NULL COMMENT '对应prev_eid的时间节点',
  `app_timestamp` datetime DEFAULT NULL COMMENT '对应最后一次app在线的时间',
  `app_status` char(2) DEFAULT '0' COMMENT '与设备相关的所有用户APP状态的汇总状态，“0”所有用户都没有进入app，也没有在后台  “1”只要有一个用户进入了app,并且在前台， “2”只要有一个用户进入了app,并且在后台 ',
  `dev_timestamp` datetime DEFAULT NULL COMMENT '对应最后一次设备在线的时间 ',
  `dev_status` char(2) DEFAULT '0' COMMENT '设备状态，“0”设备不在线  “1”设备在线 ',
  `charging_status` char(2) DEFAULT '0' COMMENT '设备充电状态，“0”设备没有充电  “1”设备充电中 ',
  `sig_level` char(2) DEFAULT '0' COMMENT '设备信号强度',
  `acc` char(5) DEFAULT '0' COMMENT '最后一次定位的精度',
  `lct_type` char(3) DEFAULT '0' COMMENT '定位类型， 1表示GPS高德地图   2表示基站定位高德地图  3表示高德地图wifi定位  4表示Google Map GPS   5表示Google Map Geolocation API定位',
  `eco_mode` char(2) DEFAULT '0' COMMENT 'eco 省电模式',
  `debug_mode` char(2) DEFAULT '0' COMMENT '设备调试模式',
  `esafe_wifi` char(2) DEFAULT '0' COMMENT 'WIFI电子围栏总开关，“0”关  “1”开',
  `ssid_wifi` varchar(50) DEFAULT '' COMMENT '选中的WIFI SSID',
  `bssid_wifi` varchar(50) DEFAULT '' COMMENT '选中的WIFI BSSID',
  `estat_wifi` char(2) DEFAULT '0' COMMENT 'WIFI电子围栏报警状态，“0”不报警  “1”报警',
  `sleep_status` int(3) default 0  COMMENT '休眠状态， 1， 休眠中， 0： 非',  
  `sos_led_on` char(2) DEFAULT '0' COMMENT '设备紧急模式是否启用灯开关，“0”关  “1”开',    
  `order_by` int(5) default 0  COMMENT '排序等级',
  `ufirm_stat` int(3) default 0  COMMENT '设备升级状态， 1， 升级中， 0： 非升级中',
  `autopdn_status` int(3) default 0  COMMENT '定时开关机状态， 1， 开启， 0： 关闭',    
  `time_pon` char(10) DEFAULT NULL COMMENT '自动开机时间',    
  `time_poff` char(10) DEFAULT NULL COMMENT '自动关机时间',  
  `is_bwt_sim` char(2) DEFAULT '0' COMMENT '设备是否正在使用公司售出的卡，“0”不是  “1”是',    
  `ttype` varchar(30) not null DEFAULT 'wDeviceExtra' COMMENT '表名',   
  `how_long` int(3) DEFAULT 0 COMMENT '启动时长',
  `action_time_utc` datetime default NULL COMMENT '启动时间',  
  `tm_flag` char(2) DEFAULT '0' COMMENT '设备实时上报开关，“0”不打开  “1”打开',      
  `tm_dur` int(6) DEFAULT 0 COMMENT '设备实时上报温度的固定时间间隔',
  PRIMARY KEY (`id`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/* 设置过的WIFI历史信息表 */
DROP TABLE IF EXISTS `wDevWifi`;
CREATE TABLE `wDevWifi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `ssid_wifi` varchar(50) DEFAULT '' COMMENT 'SSID',
  `bssid_wifi` varchar(50) DEFAULT '' COMMENT 'mac',
  `status` int(3) default 0  COMMENT '是否选中，0：没有选中， 1：选中',
  `up_time` datetime NOT NULL COMMENT '更新时间,用于排序',  
  PRIMARY KEY (`id`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


/* 发现模式启用时长倒计时信息表 */
DROP TABLE IF EXISTS `wDevDiscovery`;
CREATE TABLE `wDevDiscovery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `action_time` datetime NOT NULL COMMENT '启动时间',
  `how_long` int(3) DEFAULT 0 COMMENT '启动时长',
  `stat` int(3) DEFAULT 0 COMMENT '状态,1:启用， 0：不启用',
  `action_time_utc` datetime default NULL COMMENT '启动时间',
  PRIMARY KEY (`id`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/* 下行设备命令执行反馈表 */
DROP TABLE IF EXISTS `wDevCmdRes`;
CREATE TABLE `wDevCmdRes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `cmd` varchar(50) NOT NULL,  
  `action_time` datetime NOT NULL COMMENT '命令时间',
  `flag` char(2) DEFAULT '0' COMMENT '0为命令执行未反馈，1为命令执行完成'
  `onoff_para` varchar(50) default NULL COMMENT '命令执行结果参数'
  `ex_para1` varchar(50) default NULL COMMENT '命令执行结果参数'
  `ex_para2` varchar(50) default NULL COMMENT '命令执行结果参数'
  PRIMARY KEY (`id`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



/*Table structure for table `wdevice_manage` */

DROP TABLE IF EXISTS `wdevice_manage`;

CREATE TABLE `wdevice_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `input_time` datetime DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `count_num` varchar(20) DEFAULT NULL,
  `mini_num` varchar(20) DEFAULT NULL,
  `max_num` varchar(20) DEFAULT NULL,
  `type` char(1) DEFAULT '0' COMMENT '1为测试，2为量产',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wdevice_manage` */

/*Table structure for table `wdog_weight` */

DROP TABLE IF EXISTS `wdog_weight`;

CREATE TABLE `wdog_weight` (
  `sex` char(2) NOT NULL DEFAULT '1',
  `minWeight` float DEFAULT NULL COMMENT '体重的起始范围，单位：磅',
  `maxWeight` float DEFAULT NULL COMMENT '体重的截止范围，单位：磅',
  `min_best_weight` float DEFAULT NULL COMMENT '最优体重的起始范围，单位：磅',
  `max_best_weight` float DEFAULT NULL COMMENT '最优体重的截止范围，单位：磅',
  `ext1` varchar(45) DEFAULT NULL COMMENT '预留字段',
  `ext2` varchar(45) DEFAULT NULL COMMENT '预留字段',
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  `fci_detail_all_catid` varchar(15) NOT NULL,
  KEY `fk_dog_weight_FCI_detail_all1_idx` (`fci_detail_all_catid`),
  CONSTRAINT `fk_dog_weight_FCI_detail_all1` FOREIGN KEY (`fci_detail_all_catid`) REFERENCES `wfci_detail_all` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wdog_weight` */

/*Table structure for table `wefencing` */

DROP TABLE IF EXISTS `wefencing`;
CREATE TABLE `wefencing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `center_gps_lo` double DEFAULT NULL COMMENT '围栏中心点gps位置的经度',
  `center_gps_la` double DEFAULT NULL COMMENT '围栏中心点gps位置的纬度',
  `round_distance` int(11) DEFAULT NULL COMMENT '方圆多少米的范围，取值为200m到2000m，单位为米',
  `device_safe_name` varchar(160) DEFAULT NULL COMMENT '预留字段,安全区域名称',
  `device_safe_effect_time` varchar(100) DEFAULT NULL COMMENT '有效时间',
  `if_active` char(2) DEFAULT '0' COMMENT '是否激活此电子围栏，如果被激活，具体每个宠物上报GPS位置信息的时候需要搜索匹配计算得出匹配到的围栏是否需要发出报警',
  `if_prompt_user` char(2) DEFAULT '0' COMMENT '是否需要给用户报警',
  `device_id` int(11) NOT NULL,
  `center_addr` varchar(160) DEFAULT NULL COMMENT '围栏中心点地址描述',
  `safe_type` char(2) DEFAULT '0' COMMENT '电子围栏性质, 0: 安全区域， 1：危险区域',  
  `flag` int(3) NOT NULL default 0 COMMENT '0: 初始状态，上报位置后一定要报警一次，  1： 在此区域  2： 不在此区域',  
  `photo` varchar(60) DEFAULT NULL COMMENT '照片文件名，纯文件名, 该文件名由系统生成',
  `pst` varchar(60) default NULL COMMENT '照片文件上传时间戳',  
  PRIMARY KEY (`id`),
  KEY `fk_efencing_users_has_pets1_idx` (`device_id`),
  CONSTRAINT `fk_efencing_users_has_pets1` FOREIGN KEY (`device_id`) REFERENCES `wdevice_active_info` (`device_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wefencing` */

/*Table structure for table `wfci_detail` */

DROP TABLE IF EXISTS `wfci_detail`;

CREATE TABLE `wfci_detail` (
  `catid` varchar(15) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `eng_name` varchar(100) DEFAULT NULL,
  `shape_type` char(2) DEFAULT '1' COMMENT '体形:0:大型犬或者1:中型犬或者2:小型犬',
  `func_type` char(2) DEFAULT '2' COMMENT '功能类型，0:畜牧犬;1:工作犬;2:非运动犬;3:玩具犬;4:猎犬',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  `FCI_group_catid` varchar(10) NOT NULL,
  PRIMARY KEY (`catid`),
  KEY `fk_FCI_detail_FCI_group_idx` (`FCI_group_catid`),
  CONSTRAINT `fk_FCI_detail_FCI_group` FOREIGN KEY (`FCI_group_catid`) REFERENCES `wfci_group` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfci_detail` */

insert  into `wfci_detail`(`catid`,`name`,`eng_name`,`shape_type`,`func_type`,`ext2`,`FCI_group_catid`) values ('FD00004','贵宾犬','Poodle','2','4',NULL,'F009');


DROP TABLE IF EXISTS `wfci_detail_cat`;

CREATE TABLE `wfci_detail_cat` (
  `catid` varchar(15) NOT NULL,
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  `fci_group_catid` varchar(10) NOT NULL,
  `desc` varchar(50) DEFAULT NULL COMMENT '具体猫有一部分有细分的类型',
  `desc_cn` varchar(50) DEFAULT NULL COMMENT '中文描述',
  `desc_fr` varchar(50) DEFAULT NULL COMMENT '法文描述',
  `desc_de` varchar(50) DEFAULT NULL COMMENT '德文描述',
  `desc_e1` varchar(50) DEFAULT NULL COMMENT '扩展',
  `desc_e2` varchar(50) DEFAULT NULL,
  `desc_e3` varchar(50) DEFAULT NULL,
  `desc_e4` varchar(50) DEFAULT NULL,
  `desc_e5` varchar(50) DEFAULT NULL,
  `shape_type` char(2) DEFAULT '1' COMMENT '体形:0:',
  `func_type` char(2) DEFAULT '2' COMMENT '功能类型',
  `ext2` int(11) DEFAULT NULL,
  `akc_detail_catid` varchar(15) DEFAULT NULL,
  `photo` varchar(70) DEFAULT NULL COMMENT '宠物种类文件名',
  PRIMARY KEY (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `wfci_detail_cat`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('0',NULL,'F000','Cat',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,NULL);

/*Table structure for table `wfci_detail_all` */

DROP TABLE IF EXISTS `wfci_detail_all`;

CREATE TABLE `wfci_detail_all` (
  `catid` varchar(15) NOT NULL,
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  `fci_group_catid` varchar(10) NOT NULL,
  `desc` varchar(50) DEFAULT NULL COMMENT '具体狗狗有一部分有细分的类型，如贵宾有迷你，玩具，标准等,英文描述',
  `desc_cn` varchar(50) DEFAULT NULL COMMENT '中文描述',
  `desc_fr` varchar(50) DEFAULT NULL COMMENT '法文描述',
  `desc_de` varchar(50) DEFAULT NULL COMMENT '德文描述',
  `desc_e1` varchar(50) DEFAULT NULL COMMENT '扩展',
  `desc_e2` varchar(50) DEFAULT NULL,
  `desc_e3` varchar(50) DEFAULT NULL,
  `desc_e4` varchar(50) DEFAULT NULL,
  `desc_e5` varchar(50) DEFAULT NULL,
  `shape_type` char(2) DEFAULT '1' COMMENT '体形:0:大型犬或者1:中型犬或者2:小型犬',
  `func_type` char(2) DEFAULT '2' COMMENT '功能类型，0:畜牧犬;1:工作犬;2:非运动犬;3:玩具犬;4:猎犬',
  `ext2` int(11) DEFAULT NULL,
  `akc_detail_catid` varchar(15) DEFAULT NULL,
  `photo` varchar(70) DEFAULT NULL COMMENT '宠物种类文件名',
  PRIMARY KEY (`catid`),
  KEY `fk_FCI_detail_all_FCI_detail1_idx` (`fci_detail_catid`),
  CONSTRAINT `fk_FCI_detail_all_FCI_detail1` FOREIGN KEY (`fci_detail_catid`) REFERENCES `wfci_detail` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*Data for the table `wfci_detail_all` */

insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('0',NULL,'F011','other',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,NULL);
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00001',NULL,'F001','Border Collie','边境牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'53.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00002',NULL,'F001','Shetland Sheepdog','喜乐蒂牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'270.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00004',NULL,'F001','Australian Cattle Dog','澳大利亚牧牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'24.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00005',NULL,'F001','Flanders Cattle Dog','佛兰德斯牧牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'117.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00006',NULL,'F001','Ardennes Cattle Dog','阿登牧牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'18.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00007',NULL,'F001','Australian Kelpie ','澳大利亚卡尔比犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'25.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00008',NULL,'F001','Czekslovakian Wolfdog','捷克斯洛伐克狼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'87.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00009',NULL,'F001','Dutch Schapendoes','斯恰潘道斯犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'98.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00010',NULL,'F001','Saarloos Wolfdog','萨卢斯猎狼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'257.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00011',NULL,'F001','Australian Shepherd ','澳大利亚牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'26.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00012',NULL,'F001','Croatian Sheepdog','克罗地亚牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'85.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00013',NULL,'F001','Tatra Shepherd Dog','泰托拉牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'298.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00014',NULL,'F001','Polish Lowland Sheepdog','波兰低地牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'238.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00015',NULL,'F001','Mudi','马地犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'214.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00016',NULL,'F001','Dutch Shepherd Dog','荷兰牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'99.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00017',NULL,'F001','Maremmaand Abruzzes Sheepdog','玛瑞玛安布卢斯牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'206.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00018',NULL,'F001','Bergamasco Shepherd Dog','贝加马斯卡牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'41.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00019',NULL,'F001','German Shepherd Dog','德国牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','0',NULL,NULL,'134.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00020',NULL,'F001','Slovakian Chuvach','斯洛伐克楚维卡犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'277.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00021',NULL,'F001','Long-haired Pyrenean Sheepdog','长毛比利牛斯牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'201.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00022',NULL,'F001','Pyrenean Sheepdog-smoothfaced','平脸比利牛斯牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'252.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00023',NULL,'F001','Portuguese Sheepdog','葡萄牙牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'243.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00024',NULL,'F001','Schipperke','西帕凯牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'262.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00025',NULL,'F001','Pumi','波密犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'249.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00026',NULL,'F001','Puli','波利犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'248.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00027',NULL,'F001','Kuvasz','库瓦茨犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'193.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00028',NULL,'F001','Komondor','可蒙犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'190.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00029',NULL,'F001','Beauceron','法国狼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','0',NULL,NULL,'38.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00030',NULL,'F001','Welsh Corgi Pembroke','彭布罗克威尔士柯基',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'308.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00031',NULL,'F001','Old English Sheepdog','古代英国牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'227.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00032',NULL,'F001','Belgian Shepherd Dog','比利时牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','0',NULL,NULL,'40.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00033',NULL,'F002','Miniature Schnauzer','迷你雪那瑞',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'212.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00034',NULL,'F002','Schnauzer','标准雪纳瑞',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'263.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00035',NULL,'F002','Giant Schnauzer','巨型雪纳瑞',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'142.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00036',NULL,'F002','Dogo Argentino','阿根廷杜高犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'94.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00037',NULL,'F002','Miniature Pinscher','迷你宾莎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'210.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00038',NULL,'F002','Deutscher Boxer','拳师犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'92.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00039',NULL,'F002','Great Swiss Mountain Dog','大型瑞士山地犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'153.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00040',NULL,'F002','Entlebuch Cattle Dog','恩特雷布赫牧牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'107.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00041',NULL,'F002','Appenzell CattleDog','阿彭则牧牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'16.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00042',NULL,'F002','Bernese Mountain Dog','伯恩山犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'42.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00043',NULL,'F002','Cane Corso Italiano','意大利凯因克尔索犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'70.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00044',NULL,'F002','Central Asia Shepherd Dog','中亚牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'74.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00045',NULL,'F002','Anatolian Shepherd Dog','安那托利亚牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'14.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00046',NULL,'F002','Caucasian Shepherd Dog','高加索牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'72.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00047',NULL,'F002','Broholmer','丹麦布罗荷马獒',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'60.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00048',NULL,'F002','Shar Pei','沙皮犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'269.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00049',NULL,'F002','Karst Shepherd Dog','卡斯特牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'187.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00050',NULL,'F002','Mastiff','獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'207.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00051',NULL,'F002','Tosa','土佐犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'303.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00052',NULL,'F002','Majorca Mastiff','马略卡獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'202.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00053',NULL,'F002','Atlas Shepherd Dog','阿特拉斯牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'23.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00054',NULL,'F002','Great Dane','大丹犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'151.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00055',NULL,'F002','Tibetan Mastiff','西藏獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'300.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00056',NULL,'F002','Landseer','兰希尔犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'196.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00057',NULL,'F002','Fila Brasileiro','巴西獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'112.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00058',NULL,'F002','Neapolitan Mastiff','那不勒斯獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'215.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00059',NULL,'F002','Hovawart','霍夫瓦尔特犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'165.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00060',NULL,'F002','Serradaestrela Mountain Dog','埃斯特卑拉山犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'268.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00061',NULL,'F002','Castro Laboreiro Dog','卡斯托莱博瑞罗犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'71.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00062',NULL,'F002','Bullmastiff','斗牛獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'64.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00063',NULL,'F002','Bulldog','斗牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'63.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00064',NULL,'F002','Rottweiler','罗威纳犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'255.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00065',NULL,'F002','Leonberger','兰伯格犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'198.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00066',NULL,'F002','German Boxer','德国拳师犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'131.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00067',NULL,'F002','Pyrenean Mountain Dog','比例牛斯山地犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'251.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00068',NULL,'F002','Doguede Bordeaux','法国波尔多獒',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'95.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00069',NULL,'F002','Alentejo Mastiff','阿兰多獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'6.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00070',NULL,'F002','Pyrenean Mastiff','比利牛斯獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'250.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00071',NULL,'F002','Spanish Mastiff','西班牙獒犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'287.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00072',NULL,'F002','Saint Bernard Dog','圣伯纳犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'258.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00073',NULL,'F002','Newfoundland','纽芬兰犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'216.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00074',NULL,'F002','Black Terrier','黑犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'45.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00075',NULL,'F002','Hollandse Smoushond','荷兰斯姆茨杭德犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'164.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00076',NULL,'F002','Affenpinscher','猴面宾莎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'1.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00078',NULL,'F002','German Pinscher','德国宾莎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','1',NULL,NULL,'133.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00079',NULL,'F002','Dobermann','杜宾犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'93.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00080',NULL,'F002','Austrianshort-haired Pinscher','澳大利亚短毛宾莎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'30.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00081',NULL,'F003','Fox Terrier Wire','刚毛猎狐更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'120.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00082',NULL,'F003','Yorkshire Terrier','约克夏更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'318.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00083',NULL,'F003','Fox Terrier Smooth','平毛猎狐梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'119.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00084',NULL,'F003','Australian Silky Terrier','澳洲丝毛更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'27.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00085',NULL,'F003','English Toy Terrier','英国玩具更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'106.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00086',NULL,'F003','American Staffordshire Terrier','美国斯塔福德郡更犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'12.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00087',NULL,'F003','Staffordshire Bull Terrier','斯塔福郡斗牛更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'291.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00089',NULL,'F003','JackRussell Terrier','杰克拉赛尔更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'181.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00090',NULL,'F003','Norfolk Terrier','罗福更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'217.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00091',NULL,'F003','Japanese Terrier','日本更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'184.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00092',NULL,'F003','Cesky Terrier','捷克更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'75.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00094',NULL,'F003','Dandie Dinmont Terrier','短脚长身更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'90.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00095',NULL,'F003','West Highland White Terrier','西高地白更犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'313.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00096',NULL,'F003','Skye Terrier','斯凯更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'276.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00097',NULL,'F003','Sealyham Terrier','西里汉姆梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'265.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00098',NULL,'F003','Scottish Terrier','苏格兰更',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'264.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00099',NULL,'F003','Norwich Terrier','诺维茨梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'224.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00100',NULL,'F003','Australian Terrier','澳大利亚梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2',NULL,NULL,'28.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00101',NULL,'F003','Cairn Terrier','凯安梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'66.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00102',NULL,'F003','Parson Russell Terrier','帕尔森·罗塞尔梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'229.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00103',NULL,'F003','Irish GlenofImaal Terrier','爱尔兰峡谷梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2',NULL,NULL,'170.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00104',NULL,'F003','Irish Terrier','爱尔兰梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'173.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00105',NULL,'F003','German Hunting Terrier','德国猎梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'132.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00106',NULL,'F003','Welsh Terrier','威尔士',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'312.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00107',NULL,'F003','Manchester Terrier','曼彻斯特',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'205.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00108',NULL,'F003','Lakeland Terrier','湖畔',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'195.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00109',NULL,'F003','Irish Soft Coated Wheaten Terrier','爱尔兰软毛麦色',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'172.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00110',NULL,'F003','Border Terrier','伯德',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'54.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00111',NULL,'F003','Bedlington Terrier','贝灵顿',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'39.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00112',NULL,'F003','Airedale Terrier','万能梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'3.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00113',NULL,'F003','Bull Terrier','牛头梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'62.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00114',NULL,'F004','Dachshunds Standard','标准腊肠犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'88.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00115',NULL,'F005','Thai Ridgeback Dog','泰国脊背犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'299.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00116',NULL,'F005','Canarian Warren Hound','加那利沃伦猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'69.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00117',NULL,'F005','Cirneco dell\'Etna','西西里猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'81.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00118',NULL,'F005','Portuguese Podengo','葡萄牙波登哥犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'241.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00119',NULL,'F005','Peruvian Hairless Dog','秘鲁无毛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'231.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00120',NULL,'F005','Canaan Dog','迦南犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','0',NULL,NULL,'67.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00121',NULL,'F005','Pharaoh Hound','法老王猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'233.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00122',NULL,'F005','Mexican Hairless Dog','墨西哥无毛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'209.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00123',NULL,'F005','Basenji','巴辛吉犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'33.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00124',NULL,'F005','American Akita','美国秋田犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'8.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00125',NULL,'F005','Korea Jindodog','韩国金刀犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'191.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00126',NULL,'F005','Shikoku','四国犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'273.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00127',NULL,'F005','Kishu','纪州犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'189.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00128',NULL,'F005','Kai','甲斐犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'185.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00129',NULL,'F005','Eurasier','欧亚大陆犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'108.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00131',NULL,'F005','Japanese Spitz','日本狐狸犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'183.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00132',NULL,'F005','Hokkaido','北海道犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'163.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00133',NULL,'F005','Shiba','柴犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'271.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00134',NULL,'F005','Akita','秋田犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'4.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00135',NULL,'F005','Chow Chow','松狮犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'80.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00136',NULL,'F005','Volpino Italiano','意大利狐狸犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'306.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00137',NULL,'F005','German Spitz, Including Keeshond And Pomeranian','德国尖嘴犬(博美)',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'306.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00138',NULL,'F005','Icelandic Sheepdog','冰岛牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'169.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00139',NULL,'F005','Finnish Reindeer Herder','芬兰驯鹿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'115.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00140',NULL,'F005','Norwegian Buhund','挪威牧羊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'219.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00141',NULL,'F005','Finnish Lapphund','芬兰拉普猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,NULL,'114.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00142',NULL,'F005','Swedish Lapphund','瑞典拉普猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'295.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00143',NULL,'F005','Swedish Vallhund','瑞典柯基犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','0',NULL,NULL,'296.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00144',NULL,'F005','West Siberian Laika','西西伯利亚莱卡犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'314.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00145',NULL,'F005','East Siberian Laika','东西伯利亚莱犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'100.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00146',NULL,'F005','Russian-European Laika','欧式俄国莱卡犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'256.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00147',NULL,'F005','Norrbottenspets','诺波丹狐狸犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'218.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00148',NULL,'F005','Norwegian Elkhoundblack','黑色挪威猎鹿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'220.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00149',NULL,'F005','Norwegian Lundehund','挪威卢德杭犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'223.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00150',NULL,'F005','Norwegian Elkhoundgrey','灰色挪威猎鹿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'221.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00151',NULL,'F005','Finnish Spitz','芬兰猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'116.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00152',NULL,'F005','Karelian Bear Dog','卡累利亚熊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'186.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00153',NULL,'F005','Swedish Elkhound','瑞典猎鹿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'294.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00154',NULL,'F005','Greenland Dog','格陵兰犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'154.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00155',NULL,'F005','Siberian Husky','西伯利亚哈士奇犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','1',NULL,NULL,'275.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00156',NULL,'F005','Alaskan Malamute','阿拉斯加雪橇犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','1',NULL,NULL,'5.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00157',NULL,'F005','Samoyed','萨摩耶犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','1',NULL,NULL,'260.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00158',NULL,'F006','Dalmatian','大麦叮犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'89.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00159',NULL,'F006','Rhodesian Ridgeback','罗德西亚脊背犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'253.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00160',NULL,'F006','Alpine Dachsbracke','阿尔卑斯达切斯勃拉克犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'7.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00161',NULL,'F006','Bavarian Mountain Scenthound','巴伐利亚山嗅猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'35.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00162',NULL,'F006','Hanoverian Scenthound','汗挪威嗅猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'159.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00163',NULL,'F006','Short-haire Ditalian Hound','短毛意大利猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'274.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00164',NULL,'F006','Anglo-Francais Depetitevénerie','英法小型犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'15.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00165',NULL,'F006','Great Anglo French Whiteand Orange Hound','大英法黄白猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'149.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00166',NULL,'F006','Great Anglo French Whiteand Black Hound','大英法黑白猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'150.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00167',NULL,'F006','Great Anglo French Tricolour Hound','大英法三色猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'148.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00168',NULL,'F006','French Whiteand Orange Hound','法国黄白猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'128.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00169',NULL,'F006','American Foxhound','美国猎狐犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'11.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00170',NULL,'F006','Blackand Tan Coonhound','黑褐猎浣熊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'46.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00171',NULL,'F006','German Hound','德国猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'139.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00172',NULL,'F006','Harrier','猎兔犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'160.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00173',NULL,'F006','Otterhound','奥达猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'228.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00174',NULL,'F006','Beagle-Harrier','比格猎兔犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'37.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00175',NULL,'F006','Grand Griffon Vendeen','大格林芬犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'147.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00176',NULL,'F006','Montenegrin Mountain Hound','蒙特内哥罗山猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'213.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00177',NULL,'F006','Halden Hound','哈尔登猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'157.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00178',NULL,'F006','Hygen Hound','海根猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'168.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00179',NULL,'F006','Slovakian Hound','斯洛伐克猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'278.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00180',NULL,'F006','Transylvanian Hound','特兰西瓦尼亚猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'304.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00181',NULL,'F006','Serbian Tricolor Hound','塞尔维亚三色猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'267.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00182',NULL,'F006','French Whiteand Black Hound','法国黑白猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'127.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00183',NULL,'F006','French Tricolor Hound','法国三色猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'125.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00184',NULL,'F006','Hellenic Hound','希腊猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'162.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00185',NULL,'F006','Spanish Hound','西班牙猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'286.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00186',NULL,'F006','Norwegian Hound','挪威猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'222.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00187',NULL,'F006','Coarse-haire Ditalian Hound','粗毛意大利猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'83.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00188',NULL,'F006','Basset Hound','短腿猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'34.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00189',NULL,'F006','Beagle','比格犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'36.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00190',NULL,'F006','English Foxhound','英国猎狐犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'102.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00191',NULL,'F006','Bosniancoarse-haired Hound','波斯尼亚粗毛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'56.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00192',NULL,'F006','Posavaz Hound','波萨维茨猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'245.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00193',NULL,'F006','Istriancoarse-haired Hound','依斯特拉粗毛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'176.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00194',NULL,'F006','Istrianshort-haired Hound','依斯特拉短毛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'177.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00195',NULL,'F006','Serbian Hound','塞尔维亚猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'266.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00196',NULL,'F006','Hamilton Hound','汉密尔顿猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'158.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00197',NULL,'F006','Schiller Hound','席勒猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'261.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00198',NULL,'F006','Drever','瑞典腊肠犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'97.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00199',NULL,'F006','Smaland Hound','斯莫兰德猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'279.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00200',NULL,'F006','Westphalian Dachsbracke','威斯特伐利亚.达切斯勃拉克犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'315.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00201',NULL,'F006','Blood Hound','寻血猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'47.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00202',NULL,'F006','Tyrolean Hound','提洛尔猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'305.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00203',NULL,'F006','Petit Basset Griffon Vendeen','佩蒂格里芬旺德短腿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'232.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00204',NULL,'F006','Fawn Brittany Griffon','浅黄布列塔尼格里芬犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'110.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00205',NULL,'F006','Austrian Black and Tan Hound','奥地利黑褐猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'29.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00206',NULL,'F006','Styriancoarse-haired Hound','斯提瑞恩粗毛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'292.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00207',NULL,'F006','Small Swiss Hound','小瑞士猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'283.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00208',NULL,'F006','Swiss Hound','瑞士猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'297.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00209',NULL,'F006','Polish Hound','波兰猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'237.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00210',NULL,'F006','Finnish Hound','芬兰猎犬A',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'113.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00211',NULL,'F006','Fawn Brittany Basset','浅黄不列塔尼短腿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'109.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00212',NULL,'F006','Blue Gascony Basset','蓝色加斯科涅短腿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'48.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00213',NULL,'F006','Artesian-Norman Basset','阿提桑诺曼底短腿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'21.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00214',NULL,'F006','Grand Basset Griffon Vendeen','大格里芬旺德短腿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'146.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00215',NULL,'F006','Blue Gascony Griffon','蓝色加斯科涅格里芬犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'49.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00216',NULL,'F006','Small Blue Gascony Hound','加斯科涅小蓝犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'280.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00217',NULL,'F006','Porcelaine','瓷器犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'240.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00218',NULL,'F006','Artois Hound','阿图瓦猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'22.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00219',NULL,'F006','Billy','比利犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'44.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00220',NULL,'F006','Poitevin','佩狄芬犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'235.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00221',NULL,'F006','Great Gascony Hound','大加斯科涅猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'152.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00222',NULL,'F006','Gascon Saintongeois','加斯科大猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'130.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00223',NULL,'F006','Ariégeois','艾瑞格斯犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'20.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00224',NULL,'F006','Medium Griffon Vendeen','中型格里芬狩猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'208.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00225',NULL,'F006','Griffonnivernais','格里芬尼韦奈犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'156.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00226',NULL,'F007','English Pointer','英国指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'103.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00227',NULL,'F007','English Setter','英国雪达蹲猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'104.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00228',NULL,'F007','Gordon Setter','戈登蹲猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'145.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00229',NULL,'F007','Burgos Pointing Dog','博格斯指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'65.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00230',NULL,'F007','Brittany','不列塔尼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'59.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00231',NULL,'F007','German Wire-haired Pointing Dog','德国硬毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'138.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00232',NULL,'F007','Weimaraner','魏玛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'307.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00233',NULL,'F007','Small Munsterlander','小明斯特兰德犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'282.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00234',NULL,'F007','Blue Picardy Spaniel','蓝色匹卡迪档猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'50.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00235',NULL,'F007','Picardy Spaniel','皮卡第猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'234.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00236',NULL,'F007','Spanielde Pont-Audemer','蓬托德梅尔猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'284.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00237',NULL,'F007','St Germain Pointing Dog','圣.日尔曼指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'289.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00238',NULL,'F007','Germanlong-haired Pointing Dog','德国长毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'140.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00239',NULL,'F007','Large Munsterlander','大明斯特兰德犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'197.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00240',NULL,'F007','German Short-haired Pointing Dog','德国短毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'135.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00241',NULL,'F007','French Pointing Dog-Gascognetype','法国盖斯克格尼指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'122.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00242',NULL,'F007','French Pointing Dog-Pyreneantype','法国比利牛斯指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'123.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00243',NULL,'F007','Italian Wire-haired Pointing Dog','意大利硬毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'180.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00244',NULL,'F007','French Spaniel','法国猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'124.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00245',NULL,'F007','Ariege Pointing Dog','艾瑞格指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'19.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00246',NULL,'F007','Bourbonnais Pointing Dog','波旁指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'58.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00247',NULL,'F007','Auvergne Pointing Dog','奥弗涅指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'31.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00248',NULL,'F007','Portuguese Pointing Dog','葡萄牙指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'242.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00249',NULL,'F007','Italian Pointing Dog','意大利指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'179.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00250',NULL,'F007','Pudelpointer','卷毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'246.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00251',NULL,'F007','Staby Hound','斯塔比荷猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'290.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00252',NULL,'F007','Drentse Partridge Dog','荷兰猎鸟犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'96.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00253',NULL,'F007','GermanRough-haired Pointing Dog','德国粗毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'141.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00254',NULL,'F007','Hungarian Wire-haired Pointing Dog','匈牙利硬毛指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'167.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00255',NULL,'F007','Bohemianwire-haired Pointing Griffon','波西米亚硬毛格里芬指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'51.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00256',NULL,'F007','Old Danish Pointing Dog','丹麦老式指示犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'226.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00257',NULL,'F007','Wirehaired Slovakian Pointer','斯洛伐克硬毛猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'317.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00258',NULL,'F007','Irish Redand White Setter','爱尔兰红白蹲猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'171.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00259',NULL,'F008','American Cocker Spaniel','美国可卡犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','4',NULL,NULL,'9.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00260',NULL,'F008','Golden Retriever','金毛寻回犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'143.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00261',NULL,'F008','English Cocker Spaniel','英国可卡犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'101.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00262',NULL,'F008','German Spaniel','德国猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'136.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00263',NULL,'F008','Clumber Spaniel','克伦勃猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'82.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00264',NULL,'F008','Curly Coated Retriever','卷毛寻回猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'86.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00266',NULL,'F008','Flat Coated Retriever','平毛寻猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'118.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00267',NULL,'F008','Labrador Retriever','拉布拉多寻回猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'194.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00268',NULL,'F008','Field Spaniel','田野猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'111.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00269',NULL,'F008','English Springer Spaniel','英国斯宾格猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'105.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00270',NULL,'F008','Welsh Springer Spaniel','威尔士斯宾格猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'310.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00271',NULL,'F008','Sussex Spaniel','苏赛克斯长耳猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'293.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00273',NULL,'F008','Chesapeake Bay Retriever','切萨皮克湾寻猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'76.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00274',NULL,'F008','Nova Scotia Duck Tolling Retriever','新斯科舍猎鸭寻猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'225.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00275',NULL,'F008','Small Dutch Waterfowl Dog','小型荷兰水猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'281.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00276',NULL,'F008','Portuguese Water Dog','葡萄牙水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','1',NULL,NULL,'244.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00277',NULL,'F008','French Water Dog','法国水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'126.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00278',NULL,'F008','Irish Water Spaniel','爱尔兰水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'174.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00279',NULL,'F008','Frisian Water Dog','佛瑞斯安水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'129.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00280',NULL,'F008','Romagna Water Dog','罗曼娜水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'254.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00281',NULL,'F008','American Water Spaniel','美国水猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','4',NULL,NULL,'13.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00282',NULL,'F008','Spanish Water Dog','西班牙水犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'288.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00283',NULL,'F009','Pug','八哥犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'247.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00285',NULL,'F009','Maltese','玛尔济斯',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'203.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00286',NULL,'F009','Continental Toy Spaniel','大陆玩具犬(蝴蝶犬)',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'203.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00287',NULL,'F009','French Bull Dog','法国斗牛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'121.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00288',NULL,'F009','King Charles Spaniel','查尔斯王猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'188.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00289',NULL,'F009','Cavalier King Charles Spaniel','查尔斯王骑士猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'73.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00290',NULL,'F009','Boston Terrier','波士顿梗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'57.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00291',NULL,'F009','Poodle','贵宾犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'239.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00292',NULL,'F009','Kromfohrlander','克龙弗兰德犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'192.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00293',NULL,'F009','Bolognese','波伦亚伴随犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'52.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00294',NULL,'F009','Japanese Chin','日本仲犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'182.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00295',NULL,'F009','Pekingese','北京犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'230.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00296',NULL,'F009','Shih Tzu','西施犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'272.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00297',NULL,'F009','Tibetan Terrier','西藏梗犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'302.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00298',NULL,'F009','Bichon Frise','卷毛比熊犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'43.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00299',NULL,'F009','Chihuahua','吉娃娃犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'77.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00300',NULL,'F009','Lhasa Apso','拉萨犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2',NULL,NULL,'199.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00301',NULL,'F009','Tibetan Spaniel','西藏猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2',NULL,NULL,'301.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00302',NULL,'F009','Little Lion Dog','小狮子狗',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'200.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00303',NULL,'F009','Havanese','哈威那伴随犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'161.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00304',NULL,'F009','Chinese Crested Dog','中国冠毛犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','3',NULL,NULL,'79.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00306',NULL,'F010','Afghan Hound','阿富汗猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'2.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00307',NULL,'F010','Grey Hound','灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'155.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00308',NULL,'F010','Irish Wolf Hound','爱尔兰猎狼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'175.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00309',NULL,'F010','Whippet','惠比特',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'316.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00310',NULL,'F010','Deerhound','猎鹿犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'91.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00311',NULL,'F010','Arabian Grey Hound','阿拉伯灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'17.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00312',NULL,'F010','Borzoi','苏俄猎狼犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','4',NULL,NULL,'55.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00313',NULL,'F010','Italian Grey Hound','意大利灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'178.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00314',NULL,'F010','Hungarian Grey Hound','匈牙利灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'166.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00315',NULL,'F010','Saluki','萨卢基猎犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'259.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00316',NULL,'F010','Spanish Grey Hhound','西班牙灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'285.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00317',NULL,'F010','Azawakh','阿札瓦克犬',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'32.png');
insert  into `wfci_detail_all`(`catid`,`fci_detail_catid`,`fci_group_catid`,`desc`,`desc_cn`,`desc_fr`,`desc_de`,`desc_e1`,`desc_e2`,`desc_e3`,`desc_e4`,`desc_e5`,`shape_type`,`func_type`,`ext2`,`akc_detail_catid`,`photo`) values ('FDA00318',NULL,'F010','Polish Greyhound','波兰灵缇',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2',NULL,NULL,'236.png');

/*Table structure for table `wfci_group` */

DROP TABLE IF EXISTS `wfci_group`;

CREATE TABLE `wfci_group` (
  `catid` varchar(10) NOT NULL,
  `seqno` int(11) DEFAULT NULL COMMENT '组序号',
  `name` varchar(100) DEFAULT NULL COMMENT 'FCI组名',
  `eng_name` varchar(100) DEFAULT NULL,
  `ext1` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfci_group` */

insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F001',1,'牧羊犬和牧牛犬组','牧羊犬和牧牛犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F002',2,'宾莎犬和雪纳瑞类、獒犬、瑞士山地犬和瑞士牧牛犬组','宾莎犬和雪纳瑞类、獒犬、瑞士山地犬和瑞士牧牛犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F003',3,'梗犬组','梗犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F004',4,'腊肠犬组','腊肠犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F005',5,'尖嘴犬和原始犬种组','尖嘴犬和原始犬种组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F006',6,'嗅觉猎犬和相关犬种组','嗅觉猎犬和相关犬种组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F007',7,'短毛大猎犬组','短毛大猎犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F008',8,'寻猎犬、搜寻犬、水猎犬组','寻猎犬、搜寻犬、水猎犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F009',9,'伴侣犬和玩具犬组','伴侣犬和玩具犬组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F010',10,'灵缇（视觉猎犬）组','灵缇（视觉猎犬）组',NULL,NULL);
insert  into `wfci_group`(`catid`,`seqno`,`name`,`eng_name`,`ext1`,`ext2`) values ('F011',11,'其他组','Others',NULL,NULL);

/*Table structure for table `wfci_standard` */

DROP TABLE IF EXISTS `wfci_standard`;

CREATE TABLE `wfci_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex` char(2) DEFAULT '1' COMMENT '公或者母，如果公母的数据没有差异，同时录入两条相同的数据，只是性别不同',
  `minShoulder` float DEFAULT NULL COMMENT '肩高的起始范围，单位：cm',
  `maxShoulder` float DEFAULT NULL COMMENT '肩高的截止范围，单位：cm',
  `minWeight` float DEFAULT NULL COMMENT '体重的起始范围，单位：kg',
  `maxWeight` float DEFAULT NULL COMMENT '体重的截止范围，单位：kg',
  `ext1` float DEFAULT NULL COMMENT '预留字段',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  `ext3` float DEFAULT NULL COMMENT '预留字段',
  `ext4` float DEFAULT NULL COMMENT '预留字段',
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  `fci_detail_all_catid` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FCI_standard_FCI_detail_all1_idx` (`fci_detail_all_catid`),
  CONSTRAINT `fk_FCI_standard_FCI_detail_all1` FOREIGN KEY (`fci_detail_all_catid`) REFERENCES `wfci_detail_all` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `wfci_standard` */

insert  into `wfci_standard`(`id`,`sex`,`minShoulder`,`maxShoulder`,`minWeight`,`maxWeight`,`ext1`,`ext2`,`ext3`,`ext4`,`fci_detail_catid`,`fci_detail_all_catid`) values (1,'0',18,23,2,3,NULL,NULL,NULL,NULL,'FDA00003',NULL);

/*Table structure for table `wfeedbackinfo` */

DROP TABLE IF EXISTS `wfeedbackinfo`;

CREATE TABLE `wfeedbackinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) NOT NULL DEFAULT '',
  `user_feedback_content` text NOT NULL,
  `date_time` datetime NOT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `photo` VARCHAR(100) DEFAULT NULL  COMMENT '反馈图片',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfeedbackinfo` */

/*Table structure for table `wfuncinfo` */

DROP TABLE IF EXISTS `wfuncinfo`;

CREATE TABLE `wfuncinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `funcCode` varchar(50) DEFAULT NULL,
  `funcName` varchar(50) NOT NULL,
  `funcDesc` varchar(50) DEFAULT NULL,
  `superCode` varchar(20) DEFAULT NULL,
  `levels` int(2) NOT NULL,
  `funcSort` int(2) NOT NULL,
  `statu` varchar(2) NOT NULL,
  `funcDo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfuncinfo` */

/*Table structure for table `wmonitorinfo` */

DROP TABLE IF EXISTS `wmonitorinfo`;

CREATE TABLE `wmonitorinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime default NULL,
  `end_time` datetime default NULL,
  `cost_time` int(11) default NULL,
  `function` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `function_href` varchar(855) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_id` int(11) default -1,
  `user_id` int(11) default -1,  
  `server_time` datetime default NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `wmonitorinfo` */

/*Table structure for table `wpet_life` */

DROP TABLE IF EXISTS `wpet_life`;

CREATE TABLE `wpet_life` (
  `min_life_years` float DEFAULT NULL COMMENT '狗狗的寿命',
  `sample_pic_name` varchar(60) DEFAULT NULL COMMENT '狗狗图片文件的名称',
  `ext1` float DEFAULT NULL COMMENT '预留字段',
  `id_ext1` int(11) DEFAULT NULL COMMENT '预留字段',
  `fci_detail_all_catid` varchar(15) DEFAULT NULL,
  `max_life_years` float DEFAULT NULL,
  `fci_detail_catid` varchar(15) DEFAULT NULL,
  KEY `fk_dog_life_FCI_detail_all1_idx` (`fci_detail_all_catid`),
  CONSTRAINT `fk_dog_life_FCI_detail_all1` FOREIGN KEY (`fci_detail_all_catid`) REFERENCES `wfci_detail_all` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpet_life` */

/*Table structure for table `wpet_mgr_info` */

DROP TABLE IF EXISTS `wpet_mgr_info`;

CREATE TABLE `wpet_mgr_info` (
  `device_id` int(11) NOT NULL,
  `reco_move_on` char(2) DEFAULT '0' COMMENT '一天合适的运动距离是否有用户自定义',
  `reco_breath_freq_on` char(2) DEFAULT '0' COMMENT '合适的呼吸频率是否由用户自定义',
  `reco_heat_on` char(2) DEFAULT '0' COMMENT '合适的宠物的体温是否由用户自定义',
  `reco_hb_freq_on` char(2) DEFAULT '0' COMMENT '心率正常值是否由用户自定义',
  `reco_bloodpr_on` char(2) DEFAULT '0' COMMENT '合适的血压是否由用户自定义',
  `reco_move` int(11) DEFAULT NULL COMMENT '被用户自定义的一天累计合适运动距离，如果值为0表示用户默认没有设置此自定义的数据',
  `min_hb_freq` int(11) DEFAULT NULL COMMENT '用户自定义的心率正常值的起始范围',
  `max_hb_freq` int(11) DEFAULT NULL COMMENT '用户自定义的心率正常值的结束范围',
  `minHeat` float DEFAULT NULL COMMENT '用户自定义的体温',
  `maxHeat` float DEFAULT NULL COMMENT '用户自定义的体温',
  `min_br_freq` int(11) DEFAULT NULL COMMENT '用户自定义的呼吸频率',
  `max_br_freq` int(11) DEFAULT NULL COMMENT '用户自定义的呼吸频率',
  PRIMARY KEY (`device_id`),
  KEY `fk_users_has_pets_pets1_idx` (`device_id`),
  CONSTRAINT `fk_pet_mgr_info_pets1` FOREIGN KEY (`device_id`) REFERENCES `wdevice_active_info` (`device_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpet_mgr_info` */


DROP TABLE IF EXISTS `wmsg_info`;
CREATE TABLE `wmsg_info` (
  `msg_id` INT(11) NOT NULL AUTO_INCREMENT,
  `msg_type` VARCHAR(10) DEFAULT NULL COMMENT '消息类型',
  `msg_ind_id` INT(11) NOT NULL COMMENT '消息身份-1:其它0:正常消息1:离开电子围栏2:进入电子围栏4:低电报警5:设备分享请求6:同意设备分享7:拒绝设备分享8:设备断网9:设备连网(即设备上线)10:app用户断网11:app用户连网12:脱落13',
  `msg_date` datetime DEFAULT NULL COMMENT '消息时间， 设备当地时间',
  `msg_content` varchar(200) DEFAULT NULL COMMENT '消息内容',
  `device_id` INT(11) DEFAULT NULL COMMENT '设备id',
  `from_usrid` INT(11) NOT NULL COMMENT '消息发送者',
  `to_usrid` INT(11) NOT NULL COMMENT '消息接受者',
  `fence_id` INT(11) DEFAULT NULL COMMENT '电子围栏id',
  `status` char(2) DEFAULT '0' COMMENT '消息状态,0：未读，1：已读',
  `share_id` INT(11) NOT NULL COMMENT '设备分享id',
  `pet_id` INT(11) NULL COMMENT '设备分享id',
  `push_status` char(2) DEFAULT '0' COMMENT '推送状态,0：未推送，1：已经推送',  
  `hide_flag` char(2) DEFAULT '0' COMMENT '是否显示给app用户,0：不隐藏，1：隐藏',    
  `summary` varchar(200) DEFAULT NULL COMMENT '通知内容',
  `msg_date_utc` datetime DEFAULT NULL COMMENT 'utc时间',
  `badge` INT(11) DEFAULT 1 COMMENT '如果消息是发给苹果手机，记录对应的badge',  
  `order_id` INT(3) DEFAULT 0 NOT NULL COMMENT '排序序号',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Table structure for table `wPetSugMoveState` */
/* 宠物运动建议是否生成状态表 */
DROP TABLE IF EXISTS `wPetSugMoveState`;
CREATE TABLE `wPetSugMoveState` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `up_time` datetime NOT NULL COMMENT '生成时间',
  `status` char(2) DEFAULT '0' COMMENT '状态,0：未生成，1：已生成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `wPetSugMoveDescRepState` */
/* 宠物运动达标数据是否生成状态表 */
DROP TABLE IF EXISTS `wPetSugMoveDescRepState`;
CREATE TABLE `wPetSugMoveDescRepState` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `up_time` datetime NOT NULL COMMENT '生成时间',
  `status` char(2) DEFAULT '0' COMMENT '状态,0：未生成，1：已生成',
  `tz` varchar(20) DEFAULT '' COMMENT 'time zone, 时区',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `wPetSugMoveRepState` */
/* 宠物每天的运动类型分类统计数据是否生成状态表 */
DROP TABLE IF EXISTS `wPetSugMoveRepState`;
CREATE TABLE `wPetSugMoveRepState` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `up_time` datetime NOT NULL COMMENT '生成时间',
  `status` char(2) DEFAULT '0' COMMENT '状态,0：未生成，1：已生成',
  `tz` varchar(20) DEFAULT '' COMMENT 'time zone, 时区',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `wpetSugMove` */
DROP TABLE IF EXISTS `wPetSugMove`;
CREATE TABLE `wPetSugMove` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `move_type` char(2) DEFAULT '1' COMMENT '运动类型，0: 慢走，1:走, 2:快走,3:慢跑,4：跑，5：快跑',
  `move_time` float DEFAULT 0.0 COMMENT '运动时间，单位：秒',
  `up_time` datetime NOT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `wPetMoveMsgStatus` 
运动完成情况消息状态表
*/
DROP TABLE IF EXISTS `wPetMoveMsgStatus`;
CREATE TABLE `wPetMoveMsgStatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `msg_type` char(2) DEFAULT '0' COMMENT '运动消息类型， 0：运动完成建议的数据超过50%, 1: 运动完成建议的数据超过100%',
  `up_time` date NOT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `wPetSugMoveDescRep`;
CREATE TABLE `wPetSugMoveDescRep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `per_move` float DEFAULT 0 COMMENT '完成情况百分比',
  `exec_time` float DEFAULT 0 COMMENT '所有运动的总时长', 
  `route` float DEFAULT 0 COMMENT '执行的行程', 
  `step_number` float DEFAULT 0 COMMENT '执行的步数', 
  `calories` float DEFAULT 0 COMMENT '执行的卡路里',     
  `up_time` datetime NOT NULL COMMENT '对应的时间',
  `dexec_time` float DEFAULT 0 COMMENT '强烈运动的总时长',     
  `lexec_time` float DEFAULT 0 COMMENT '普通运动的总时长',     
  `tot_sleep` float DEFAULT 0 COMMENT '睡眠的总时长',     
  `tot_dsleep` float DEFAULT 0 COMMENT '深度睡眠的总时长',     
  `tot_lsleep` float DEFAULT 0 COMMENT '普通睡眠的总时长',     
  `max_unit_steps` float DEFAULT 0 COMMENT '单位时间14.4分钟内最大的运动步数',     
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table 运动类型分类统计时间表 `wPetSugMoveRep`   此表不再使用 2016-09-28 */
DROP TABLE IF EXISTS `wPetSugMoveRep`;
CREATE TABLE `wPetSugMoveRep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `move_type` char(2) DEFAULT '1' COMMENT '运动类型，0: 慢走，1:走, 2:快走,3:慢跑,4：跑，5：快跑',
  `move_time` float DEFAULT 0.0 COMMENT '运动时间，单位：秒',
  `up_time` datetime NOT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table 运动类型分类统计Activity表 `wPetSugMoveRepActivity` */
DROP TABLE IF EXISTS `wPetSugMoveRepActivity`;
CREATE TABLE `wPetSugMoveRepActivity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `move_type` float DEFAULT 0.0 COMMENT '运动系数，0 ～ 1 ， 1为强烈运动',
  `steps` float DEFAULT 0.0 COMMENT '运动步数',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table 运动类型分类统计Sleep表 `wPetSugMoveRepSleep` */
DROP TABLE IF EXISTS `wPetSugMoveRepSleep`;
CREATE TABLE `wPetSugMoveRepSleep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `sleep_type` float DEFAULT 0.0 COMMENT '睡眠系数，0 ～ 1 ， 1为熟睡',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



	DROP TABLE IF EXISTS `wpet_move_info`;

	CREATE TABLE `wpet_move_info` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `pets_pet_id` int(11) NOT NULL COMMENT '宠物ID',
	  `step_number` float DEFAULT NULL COMMENT '一段运动时间的步数',
	  `route` float DEFAULT NULL COMMENT '行程，单位：米',
	  `calories` double DEFAULT NULL COMMENT '消耗的卡路里',
	  `speed` double DEFAULT NULL COMMENT '步行速度，单位：米/秒',
	  `up_time` datetime DEFAULT NULL COMMENT '最后上报数据的时间',
	  `start_time` datetime DEFAULT NULL COMMENT '步行起始时间',
	  `end_time` datetime DEFAULT NULL COMMENT '步行结束时间',
      PRIMARY KEY (`id`),
	  KEY `fk_wpet_move_info_pets1_idx` (`pets_pet_id`),
	  CONSTRAINT `fk_wpet_move_info_pets1` FOREIGN KEY (`pets_pet_id`) REFERENCES `wpets` (`pet_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	/*Data for the table `wpet_move_info` */

	
	DROP TABLE IF EXISTS `wpet_sleep_info`;

	CREATE TABLE `wpet_sleep_info` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `pets_pet_id` int(11) NOT NULL COMMENT '宠物ID',
	  `step_number` float DEFAULT NULL COMMENT '一段运动时间的步数',
	  `speed` double DEFAULT NULL COMMENT '速度，单位：步数/分钟',
	  `up_time` datetime DEFAULT NULL COMMENT '最后上报数据的时间',
	  `start_time` datetime DEFAULT NULL COMMENT '起始时间',
	  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
      PRIMARY KEY (`id`),
	  KEY `fk_wpet_sleep_info_pets1_idx` (`pets_pet_id`),
	  CONSTRAINT `fk_wpet_sleep_info_pets1` FOREIGN KEY (`pets_pet_id`) REFERENCES `wpets` (`pet_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	
/*Table structure for table `wpets` */

DROP TABLE IF EXISTS `wpets`;

CREATE TABLE `wpets` (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) DEFAULT -1,
  `photo` varchar(60) DEFAULT NULL COMMENT '照片文件名，纯文件名, 该文件名由系统生成',
  `nickname` varchar(40) DEFAULT NULL COMMENT '昵称',
  `born_date` date DEFAULT NULL COMMENT '出生日期',
  `sex` char(2) DEFAULT '1' COMMENT '0:male ,1:female',
  `weight` float DEFAULT NULL COMMENT '体重，单位：公斤',
  `sport_level` char(2) DEFAULT '1' COMMENT '好动程度 0:lazzy, 1:mormal, 2: active ',
  `fat_level` char(2) DEFAULT '2' COMMENT '肥胖程度,0: 消瘦； 1: 偏瘦；  2: 正常； 3: 偏胖； 4: 肥胖',
  `fci_detail_catid` varchar(15) DEFAULT NULL COMMENT '废除此字段不用，yonghu 20150714',
  `fci_detail_all_catid` varchar(15) NOT NULL,
  `ext2` varchar(60) DEFAULT NULL,
  `weight_level_1_catid` varchar(10) NOT NULL,
  `pet_type` char(2) DEFAULT '0' COMMENT '宠物类型,  0：dog；1：cat',
  `photo_time_stamp` datetime DEFAULT NULL COMMENT '头像文件资料更新日期时间戳，用于后台与APP同步数据用',
  `sheight` int(6) DEFAULT NULL COMMENT '肩高,cm,用于测算狗狗步距等',
  `sensitivity` int(3) DEFAULT '3' COMMENT '计步器Sensor灵敏度',
  `user_id` int(11) DEFAULT NULL  COMMENT '添加该宠物资料的用户id',
  `is_healthy` char(2) DEFAULT '1' COMMENT '健康程度 0:abnormal, 1:mormal',
  `time_zone` char(50) DEFAULT 'Asia/Shanghai' COMMENT '时区属性，如中国对应为<Asia/Shanghai>',    
  `is_online` char(2) DEFAULT '0' COMMENT '是否接入系统，0：脱机， 1：在线',
  `weight_type` int(3) DEFAULT 0 COMMENT '用户选择体重操作的方式',  
  PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `wpets` */

insert  into `wpets`(`pet_id`,`device_id`,`photo`,`nickname`,`born_date`,`sex`,`weight`,`sport_level`,`fat_level`,`fci_detail_catid`,`fci_detail_all_catid`,`ext2`,`weight_level_1_catid`,`pet_type`,`photo_time_stamp`,`sheight`,`sensitivity`) values (1,-1,NULL,'maomao','2016-07-18','1',6.5,'2','2',NULL,'FDA00004',NULL,'Z001','0',NULL,NULL,3);

/*Table structure for table `wpets_info` */

DROP TABLE IF EXISTS `wpets_info`;

CREATE TABLE `wpets_info` (
  `pets_pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `last_gps_lo` float DEFAULT NULL COMMENT '最后上报gps位置的经度',
  `last_gps_la` float DEFAULT NULL COMMENT '最后上报gps位置的纬度',
  `up_time` datetime DEFAULT NULL COMMENT '最后上报gps位置的时间',
  `last_bat_level` float NOT NULL COMMENT '最后一次上报信息时候设备的电量，\n                         当顷圈电池低于 30%时,在当前app端页面需要弹出提示框”宠物\n                         顷圈电量不足,请及时充 电”,按确认后,提示框消失 ',
  `hb_freq` int(11) DEFAULT NULL COMMENT '最新心率数据',
  `temperature` float DEFAULT NULL COMMENT '体温',
  `sbp` varchar(50) DEFAULT '0' COMMENT '收缩压',
  `dbp` varchar(50) DEFAULT '0' COMMENT '舒张压',
  `breath_freq` int(11) DEFAULT NULL COMMENT '呼吸频率',
  `movement` float DEFAULT NULL COMMENT '最新上报运动距离数据',
  `energy_consumed` float DEFAULT NULL COMMENT '能量消耗',
  `move_type` char(2) DEFAULT '1' COMMENT '运动类型，0: 慢走，1:走, 2:快走,3:慢跑,4：跑，5：快跑',
  `move_time` float DEFAULT NULL COMMENT '运动时间，单位：秒',
  KEY `fk_pets_info_pets1_idx` (`pets_pet_id`),
  CONSTRAINT `fk_pets_info_pets1` FOREIGN KEY (`pets_pet_id`) REFERENCES `wpets` (`pet_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpets_info` */

/*Table structure for table `wpets_sys_reco` */

DROP TABLE IF EXISTS `wpets_sys_reco`;

CREATE TABLE `wpets_sys_reco` (
  `min_hb_freq` int(11) DEFAULT NULL,
  `max_hb_freq` int(11) DEFAULT NULL,
  `minHeat` float DEFAULT NULL,
  `maxHeat` float DEFAULT NULL,
  `min_br_freq` int(11) DEFAULT NULL,
  `max_br_freq` int(11) DEFAULT NULL,
  `minSbp` int(11) DEFAULT NULL,
  `maxSbp` int(11) DEFAULT NULL,
  `minDbp` int(11) DEFAULT NULL,
  `maxDbp` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpets_sys_reco` */

/*Table structure for table `wpets_sys_reco_move` */

DROP TABLE IF EXISTS `wpets_sys_reco_move`;

CREATE TABLE `wpets_sys_reco_move` (
  `moves_inaday` float DEFAULT NULL COMMENT '系统建议的一天累计合适运动距离， 需要系统根据具体宠物的数据和一定的算法计算，每天服务器在晚上3点可以定时开始计算，检查狗狗中长大了一岁或者1/4岁的情况；还有一种情况是狗狗的主人更新的狗狗的数据，如体重，出生日期，体形等数据是，服务器需要主动更新狗狗的建议运动距离，此种情况针对pets表的update操作需要设置触发器。',
  `device_id` int(11) NOT NULL,
  KEY `fk_pets_sys_reco_pets1_idx` (`device_id`),
  CONSTRAINT `fk_pets_sys_reco_pets1` FOREIGN KEY (`device_id`) REFERENCES `wdevice_active_info` (`device_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpets_sys_reco_move` */

/*Table structure for table `wpetwifirange` */

DROP TABLE IF EXISTS `wpetwifirange`;

CREATE TABLE `wpetwifirange` (
  `pet_id` int(11) NOT NULL COMMENT '宠物标识号',
  `wifi_radius` float DEFAULT NULL COMMENT 'WIFI信号强弱换算成距离，单位米',
  `host_ssid` varchar(16) CHARACTER SET latin1 DEFAULT NULL COMMENT '主人WIFI热点的SSID',
  `up_time` datetime DEFAULT NULL COMMENT '数据上传时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpetwifirange` */

insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (1,3,'waterworld1','2016-08-09 20:24:37');
insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (2,3,'waterworld1','2016-08-10 14:22:11');
insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (3,3,'waterworld1','2016-08-11 16:16:46');
insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (4,3,'waterworld1','2016-08-11 16:32:16');
insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (5,20,'waterworld1','2016-08-11 19:30:04');
insert  into `wpetwifirange`(`pet_id`,`wifi_radius`,`host_ssid`,`up_time`) values (2,60,'waterworld1','2016-08-12 11:41:47');

/*Table structure for table `wpic_addr` */

DROP TABLE IF EXISTS `wpic_addr`;

CREATE TABLE `wpic_addr` (
  `id` int(11) NOT NULL,
  `type` varchar(50) DEFAULT NULL COMMENT '图片的类别, 如type＝“dog_life”, 表示用于dog_life表中用到的图片　',
  `server_addr` varchar(100) DEFAULT NULL COMMENT '服务器端存放图片的地址',
  `app_addr` varchar(100) DEFAULT NULL COMMENT 'app端存放图片的地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wpic_addr` */

/*Table structure for table `wprojectinfo` */

DROP TABLE IF EXISTS `wprojectinfo`;

CREATE TABLE `wprojectinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_no` varchar(20) DEFAULT '',
  `project_name` varchar(50) DEFAULT '',
  `channel_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `add_time` datetime DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wprojectinfo` */

/*Table structure for table `wrolefuncinfo` */

DROP TABLE IF EXISTS `wrolefuncinfo`;

CREATE TABLE `wrolefuncinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(25) NOT NULL,
  `funcCode` varchar(50) NOT NULL,
  `userCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wrolefuncinfo` */

/*Table structure for table `wroleinfo` */

DROP TABLE IF EXISTS `wroleinfo`;

CREATE TABLE `wroleinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(25) NOT NULL,
  `roleDesc` varchar(50) DEFAULT NULL,
  `roleCode` varchar(25) NOT NULL,
  `roleType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wroleinfo` */

/*Table structure for table `wrp_pet_move` */

DROP TABLE IF EXISTS `wrp_pet_move`;

CREATE TABLE `wrp_pet_move` (
  `tot_move` int(11) NOT NULL COMMENT '具体对应日期已累计的运动距离',
  `date` date DEFAULT NULL COMMENT '具体日期',
  `device_id` int(11) NOT NULL,
  `tot_energy` float DEFAULT NULL COMMENT '具体对应日期已累计的能量消耗',
  KEY `fk_rp_pet_move_pets1_idx` (`device_id`),
  CONSTRAINT `fk_rp_pet_move_pets1` FOREIGN KEY (`device_id`) REFERENCES `wdevice_active_info` (`device_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wrp_pet_move` */

/*Table structure for table `wsaleinfo` */

DROP TABLE IF EXISTS `wsaleinfo`;

CREATE TABLE `wsaleinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '-1',
  `imei` varchar(15) DEFAULT NULL,
  `imsi` varchar(15) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `phone_model` varchar(50) DEFAULT NULL,
  `sys_version` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `belong_project` int(11) DEFAULT NULL COMMENT '所属哪个项目',
  `date_time` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `app_version` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wsaleinfo` */

/*Table structure for table `wshare_info` */

DROP TABLE IF EXISTS `wshare_info`;

CREATE TABLE `wshare_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `to_user_id` varchar(20) DEFAULT NULL COMMENT '设备主人ID',
  `device_id` int(11) NOT NULL COMMENT '设备的imeiID',
  `is_priority` char(1) NOT NULL DEFAULT '1' COMMENT '1表示是主设备,0表示是分享的设备',
  `share_date` datetime NOT NULL DEFAULT '2015-01-01 00:00:00' COMMENT '分享的时间',
  `belong_project` int(11) DEFAULT '0' COMMENT '所属哪个项目',
  `status` char(2) DEFAULT '1' COMMENT '状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `wSoundInfo`;

CREATE TABLE `wSoundInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL DEFAULT '-1' COMMENT '设备id',
  `call_file` varchar(80) DEFAULT NULL COMMENT '不带路径的唤回声音文件名',
  `up_time` datetime NOT NULL DEFAULT '2015-01-01 00:00:00' COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `wunshare_info` 解除设备绑定信息表 */
DROP TABLE IF EXISTS `wunshare_info`;
CREATE TABLE `wunshare_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `to_user_id` varchar(20) DEFAULT NULL COMMENT '设备主人ID',
  `device_id` int(11) NOT NULL COMMENT '设备的imeiID',
  `unshare_date` datetime NOT NULL DEFAULT '2015-01-01 00:00:00' COMMENT '分享的时间',
  `belong_project` int(11) DEFAULT '0' COMMENT '所属哪个项目',
  `status` char(2) DEFAULT '1' COMMENT '状态，待定',
  `pet_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Data for the table `wshare_info` */

/*Table structure for table `wsport_level_1` */

DROP TABLE IF EXISTS `wsport_level_1`;

CREATE TABLE `wsport_level_1` (
  `catid` varchar(10) NOT NULL,
  `min_sport_time` float DEFAULT NULL COMMENT '每天运动量的起始范围，单位：分钟',
  `max_sport_time` float DEFAULT NULL COMMENT '每天运动量的截止范围，单位：分钟',
  `desc` varchar(80) DEFAULT NULL COMMENT '对运动量范围的中文语言描述。如“在公寓室内平时的活动量就够了”，此描述对应的sport_start = 0, sport_end = 15.',
  `eng_desc` varchar(80) DEFAULT NULL COMMENT '对运动量范围的英文语言描述',
  `ext1` varchar(50) DEFAULT NULL COMMENT '预留字段',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  `weight_level_1_catid` varchar(10) NOT NULL,
  PRIMARY KEY (`catid`),
  KEY `fk_sport_level_1_weight_level_11_idx` (`weight_level_1_catid`),
  CONSTRAINT `fk_sport_level_1_weight_level_11` FOREIGN KEY (`weight_level_1_catid`) REFERENCES `wweight_level_1` (`catid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wsport_level_1` */

insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S001',20,20,'','',NULL,NULL,'Z001');
insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S002',36,72,'','',NULL,NULL,'Z002');
insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S003',72,144,'','',NULL,NULL,'Z003');
insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S004',144,216,'','',NULL,NULL,'Z004');
insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S005',216,260,'','',NULL,NULL,'Z005');
insert  into `wsport_level_1`(`catid`,`min_sport_time`,`max_sport_time`,`desc`,`eng_desc`,`ext1`,`ext2`,`weight_level_1_catid`) values ('S006',260,500,'','',NULL,NULL,'Z006');

/*Table structure for table `wswitch_info` */

DROP TABLE IF EXISTS `wswitch_info`;

CREATE TABLE `wswitch_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `visit_s` char(2) DEFAULT NULL COMMENT 'app端访问',
  `moni_s` char(2) NOT NULL DEFAULT '' COMMENT '监听',
  `device_s` char(2) DEFAULT NULL COMMENT '设备端访问',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wswitch_info` */

/*Table structure for table `wsysloginfo` */

DROP TABLE IF EXISTS `wsysloginfo`;

CREATE TABLE `wsysloginfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(25) NOT NULL,
  `logDate` datetime NOT NULL,
  `logs` varchar(1024) NOT NULL,
  `ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wsysloginfo` */

/*Table structure for table `wuserinfo` */

DROP TABLE IF EXISTS `wuserinfo`;

CREATE TABLE `wuserinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userCode` varchar(25) NOT NULL DEFAULT '',
  `userName` varchar(50) NOT NULL,
  `passWrd` varchar(50) NOT NULL,
  `passWrd1` varchar(50) NOT NULL,
  `tag` int(2) NOT NULL DEFAULT '0',
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `groupCode` varchar(100) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `addUser` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wuserinfo` */

/*Table structure for table `wvisit` */

DROP TABLE IF EXISTS `wvisit`;

CREATE TABLE `wvisit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` char(20) NOT NULL COMMENT '访问者的电话号码',
  `function` char(20) NOT NULL DEFAULT '' COMMENT '访问接口',
  `function_href` varchar(200) NOT NULL DEFAULT '' COMMENT '具体接口',
  `belong_project` int(11) NOT NULL COMMENT '所属项目',
  `start_time` datetime NOT NULL COMMENT '访问时间',
  `type` char(2) NOT NULL DEFAULT '0' COMMENT '类型,默认app端访问接口(0)',
  `end_time` datetime DEFAULT NULL COMMENT '访问结束时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wvisit` */

/*Table structure for table `wweight_level_1` */

DROP TABLE IF EXISTS `wweight_level_1`;

CREATE TABLE `wweight_level_1` (
  `catid` varchar(10) NOT NULL,
  `minWeight` float DEFAULT NULL COMMENT '体重的起始范围，单位：公斤',
  `maxWeight` float DEFAULT NULL COMMENT '体重的截止范围，单位：公斤',
  `desc` varchar(50) DEFAULT NULL COMMENT '对体重范围的中文语言描述，如“5公斤之内”',
  `eng_esc` varchar(50) DEFAULT NULL,
  `ext1` varchar(50) DEFAULT NULL COMMENT '预留字段',
  `ext2` float DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wweight_level_1` */

insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z001',0,5,NULL,NULL,NULL,NULL);
insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z002',5,10,NULL,NULL,NULL,NULL);
insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z003',10,15,NULL,NULL,NULL,NULL);
insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z004',15,20,NULL,NULL,NULL,NULL);
insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z005',20,25,NULL,NULL,NULL,NULL);
insert  into `wweight_level_1`(`catid`,`minWeight`,`maxWeight`,`desc`,`eng_esc`,`ext1`,`ext2`) values ('Z006',25,500,NULL,NULL,NULL,NULL);


DROP TABLE IF EXISTS `questioninfo`;

CREATE TABLE `questioninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type` varchar(50) NOT NULL COMMENT '问题类型',
  `question_title` varchar(150) NOT NULL COMMENT '问题标题',
  `question_content` text NOT NULL COMMENT '问题内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `wupfirmware` */
DROP TABLE IF EXISTS `wupfirmware`;

CREATE TABLE `wupfirmware` (
  `id` int(7) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(11) NOT NULL COMMENT '固件项目名',
  `version_us` varchar(50) DEFAULT NULL COMMENT '北美固件版本',
  `version_eu` varchar(50) DEFAULT NULL COMMENT '欧洲固件版本',
  `version_cn` varchar(50) DEFAULT NULL COMMENT '国内固件版本',
  `update_time` datetime DEFAULT NULL COMMENT '更新版本时间',
  `nver_us` varchar(50) DEFAULT NULL COMMENT '北美最新固件版本',
  `nver_eu` varchar(50) DEFAULT NULL COMMENT '欧洲固件版本',  
  `nver_usf` varchar(50) DEFAULT NULL COMMENT '北美最新固件版本全',
  `nver_euf` varchar(50) DEFAULT NULL COMMENT '欧洲固件版本全',  
  `fup` varchar(3) DEFAULT '1' COMMENT '是否强制升级',      
  PRIMARY KEY (`id`,`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


/* Procedure structure for procedure `calcSugMove` */

/*!50003 DROP PROCEDURE IF EXISTS  `calcSugMove` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugMove`(IN rpet_id VARCHAR(50), IN rup_time VARCHAR(50), OUT rrecMoveType CHAR(2), OUT rrecMoveTime INT)
BEGIN
DECLARE _t int unsigned default 0; 
DECLARE _is_hlthy int unsigned default 0; 
DECLARE _k float default 1; 
DECLARE _age float;
DECLARE _weight float default 1;  
DECLARE _func_type char(2) default '2';  
DECLARE _shape_type char(2) default '1';  
DECLARE _fat_level char(2) default '2';  
DECLARE _crt_date date;
DECLARE _born_date date;
/*DECLARE _device_id int unsigned default 0; */ 
DECLARE _kindid char(15);  
DECLARE _kindall_id char(15);  
DECLARE _fci_group_id char(10) default '';


/*根据宠物体重信息获取建议运动量*/
SELECT a.weight into _weight FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
SELECT a.is_healthy into _is_hlthy FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
SELECT a.born_date into _born_date FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
select CURRENT_DATE() into _crt_date;
/*select TIMESTAMPDIFF(MONTH, born_date,  CURRENT_DATE()) into _age from wpets where pet_id = rpet_id; */
select ( TO_DAYS(/*_crt_date*/rup_time) - TO_DAYS(_born_date) + 0.0 )/365 into _age;
/* 如果未成年（小于两年），建议室内游戏，早晚各10分钟 */
IF _born_date is null or _is_hlthy = 1 THEN
  /*select null into recMoveType; 未成年，暂时固定运动20分钟*/
  /* type: '1': 表示强烈运动 */
  select '0' into rrecMoveType;		
  select 10 into rrecMoveTime;

ELSE
  select '1' into rrecMoveType;
/*
IF _age < 1.0 THEN
  select '0' into rrecMoveType;
  select 10 into rrecMoveTime;
ELSE */
  SELECT a.fat_level into _fat_level FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
  SELECT a.fci_detail_catid into _kindid FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
  SELECT a.fci_detail_all_catid into _kindall_id FROM wpets a 
	where a.pet_id = rpet_id limit 1; 
  select b.fci_group_catid into _fci_group_id  from wpets a left join 
	wfci_detail_all b on a.fci_detail_all_catid = b.catid where pet_id = rpet_id limit 1; 
	
  /* 宠物狗当前是否患有疾病，如果是，不建议运动，形成运动菜单，流程结束 */
  /* ... */
  
  /* 判断狗狗是否大于8岁,设置当前的运动量系数: K = 0.667*K */
  IF _age > 8.0 or _age < 1.0 THEN
    select '0' into rrecMoveType;  
	set _k = 0.667 * _k;
  END IF;
  /*
  select a.min_sport_time into _t from wsport_level_1 a left join wweight_level_1 b 
	on a.weight_level_1_catid = b.catid 
    where b.minWeight <= _weight and b.maxWeight > _weight;
  */
  
  /* 判断狗狗的体型，如果体型偏廋或者过廋，设置当前的运动量系数: K = 0.8*K；
	否则如果体型偏胖，K=1.3*K;否则如果体型过胖，K=1.5*K. */
  CASE _fat_level
  when '0' then
	set _k = 0.8 * _k;
    select '0' into rrecMoveType;  	
  when '1' then
	set _k = 0.9 * _k;
    select '0' into rrecMoveType;  	
  when '2' then
	set _k = _k;
    select '1' into rrecMoveType;  	
  when '3' then
	set _k = 1.2 * _k;
    select '0' into rrecMoveType;  	
  when '4' then
	set _k = 1.3 * _k;
    select '0' into rrecMoveType;  
  end case;
    
  /* select func_type into _func_type from wfci_detail where catid = _kindid; */
  /* IF _kindall_id is null THEN 
    select shape_type into _shape_type from wfci_detail where catid = _kindid;
  ELSE
    select shape_type into _shape_type from wfci_detail_all where catid = _kindall_id;
  END IF;  */
	
  /* IF STRCMP(_func_type,'0') = 0 THEN  */ /* 畜牧犬 */
  /*	set _k = 1.15 * _k; */
  /* ELSE */
  /*    IF STRCMP(_func_type,'1') = 0 THEN  */ /* 工作犬 */
  /*	  set _k = 1.08 * _k; */
  /*    ELSE */
  /*      IF STRCMP(_func_type,'2') = 0 THEN */ /* 非运动犬 */
  /*	    IF STRCMP(_shape_type,'2') = 0 THEN	 */ /* 小型犬 */
  /* 	      set _k = 0.95 * _k;       */ 
  /*        ELSE */
  /*	      set _k = 1.08 * _k; */
  /*        END IF; */
  /*	  else */
  /*	    IF STRCMP(_shape_type,'3') = 0 THEN	*/ /* 玩具犬 */
  /*	      IF STRCMP(_shape_type,'2') = 0 THEN */	/* 小型犬 */
  /* 	        set _k = 0.95 * _k;      */              
  /*		  else */
  /*		    IF STRCMP(_shape_type,'0') = 0 THEN */	/* 大型犬 */
  /*	          set _k = 1.10 * _k; */
  /*            end if; */
  /*          end if; */
  /*        else */
  /*	      IF STRCMP(_shape_type,'4') = 0 THEN  */	/* 猎犬 */
  /*	        set _k = 1.05 * _k; */
  /*          end if; */
  /*        end if;       */
  /*      END IF;     */
  /*    END IF; */
  /* END IF; */
  IF strcmp(_fci_group_id, '0') = 0 or strcmp(_fci_group_id, 'F003') = 0 or strcmp(_fci_group_id, 'F009') = 0  THEN 
	select 30 into _t;
  ELSE
    select 60 into _t;
  END IF;
   
  set _t =  _t * _k ;

  select _t into rrecMoveTime;
  select rrecMoveType, rrecMoveTime;
/* END IF;   */
END IF;
insert into wPetSugMove (pet_id, move_type, move_time, up_time) select rpet_id, rrecMoveType, rrecMoveTime*60, rup_time; 
insert into wPetSugMoveState (pet_id, up_time, status) select rpet_id, rup_time, '1';


commit;
END */$$
DELIMITER ;



/* Procedure structure for procedure `new_procedure` */

/*!50003 DROP PROCEDURE IF EXISTS  `new_procedure` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `new_procedure`(IN sex_id INT, OUT user_count INT)
BEGIN
IF sex_id=0 THEN
 SELECT COUNT(*) INTO user_count FROM test_user WHERE type='0'; 
else
 SELECT COUNT(*) INTO user_count FROM test_user WHERE type='1'; 
END IF;
END */$$
DELIMITER ;



/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExec` */;

DELIMITER $$

/* rpet_id    宠物id */
/* rup_time   具体日期，例如'2016-09-26' */

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExec`(IN rpet_id VARCHAR(50), IN rup_time VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _fci_group_id char(10) default '';
DECLARE _sug_move_type int default 0;
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _div_move float default 0.000;
DECLARE _div_fast float default 0.000;
DECLARE _div_sleep float default 0.000;
DECLARE _div_dsleep float default 0.000;
DECLARE _age float;

 declare i int default 0;
DECLARE _rec_step_number float default 0.000;
DECLARE _sec_step_number float default 0.000;   /*每天原始运动上报数据(设计为每3.6分钟一条上报数据) */
DECLARE _sec_sleep_type float default 0.000;
DECLARE _sec_move_type float default 0.000;


DECLARE _max_sec_step_number float default 0.000;
DECLARE _rec_start_time VARCHAR(25);
DECLARE _rec_end_time VARCHAR(25);
DECLARE _rec_sleep_type float default 0.000;
DECLARE _rec_speed float default 0.000;

DECLARE _rec_route float default 0.000;
DECLARE _rec_calories float default 0.000;


DECLARE _sec_start_time VARCHAR(25);		/*100格中具体的起始时间 */
DECLARE _sec_end_time VARCHAR(25);

DECLARE _temp float default 0.000;

DECLARE _dexec_time float default 0.000;
DECLARE _lexec_time float default 0.000;
DECLARE _exec_time float default 0.000;
DECLARE _tot_sleep float default 0.000;
DECLARE _tot_dsleep float default 0.000;
DECLARE _tot_lsleep float default 0.000;

DECLARE _tot_route float default 0.000;
DECLARE _tot_calories float default 0.000;
DECLARE _tot_steps float default 0.000;



 DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR select step_number, speed,  start_time, end_time, route, calories from wpet_move_info where pets_pet_id = rpet_id and 
	(start_time > concat(rup_time, " 00:00:00" ) AND end_time <= concat(rup_time, " 23:59:59" ) );		
 
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 SET SQL_SAFE_UPDATES=0;

/* 判断是否已经生成过数据， 如果是，直接返回, ... 
	select count(*) into _cnt from wPetSugMoveDescRepState where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 ) and status = '1';
	IF _cnt > 0 THEN
		return;
	END IF;
	
*/
select TIMESTAMPDIFF(MONTH, born_date,  CURRENT_DATE())/12.0 into _age from wpets where pet_id = rpet_id limit 1; 
select b.fci_group_catid into _fci_group_id  from wpets a left join wfci_detail_all b on a.fci_detail_all_catid = b.catid where pet_id = rpet_id limit 1; 

set _div_sleep = 0.84;
set _div_dsleep = 0.001;

IF _age <= 0.16667 and _age > -0.01 THEN      /* 小于2个月， 不建议运动 */
	set _div_move = 50;
	set _div_fast = 55;
ELSE IF _age > 0.16667 and _age <= 0.25 THEN /* 2到 3个月 大 */
	set _div_move = 50;	/*5; */
	set _div_fast = 55;	/*12; */
ELSE IF _age <= 0.5 and _age > 0.25 THEN /* 3到 6个月 大 */
	set _div_move = 50;	/*12; */
	set _div_fast = 55;	/*40; */
ELSE IF _age <= 0.6667 and _age > 0.5 THEN /* 6到 8个月 大 */
	set _div_move = 50;  /*40; */
	set _div_fast = 55;	 /*100; */
ELSE IF _age <= 1.0 and _age > 0.6667 THEN /* 8到 12个月 大 */
	set _div_move = 35;	 /*50; */
	set _div_fast = 45;	/*120; */
ELSE IF _age <= 8.0 and _age > 1.0 THEN /* 12到 12*8个月 大 */
	set _div_move = 35; /*100; */
	set _div_fast = 45;	/*180; */
ELSE IF _age > 8.0 THEN /* 12 * 8 个月以上 */
	set _div_move = 35;	/*30; */
	set _div_fast = 45; /*70; */
END IF;
END IF;
END IF;
END IF;
END IF;
END IF;
END IF;
	
/*select count(*) into _cnt from wPetSugMove where pet_id = rpet_id and 
	up_time >= CONCAT(rup_time, " 00:00:00" ) AND up_time <= CONCAT(rup_time, " 23:59:59"); 	
*/
delete from wPetSugMove where 	 pet_id = rpet_id and 
	up_time >= CONCAT(rup_time, " 00:00:00" ) AND up_time <= CONCAT(rup_time, " 23:59:59"); 	
/*IF _cnt = 0 THEN */
	call calcSugMove(rpet_id, rup_time, _type, _time);
/*END IF; */

/*	
select count(*) into _cnt from wPetSugMoveDescRep where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );	
IF _cnt = 0 THEN
	insert into wPetSugMoveDescRep (pet_id, per_move, exec_time, route, step_number, calories, up_time)
		values (rpet_id, 0, 0, 0, 0, 0, rup_time);
END IF;
*/

select move_time into _sug_move_time  from wPetSugMove where pet_id = rpet_id and 
	up_time >= CONCAT(rup_time, " 00:00:00" ) AND up_time <= CONCAT(rup_time, " 23:59:59" ) limit 1	;

select move_type into _sug_move_type  from wPetSugMove where pet_id = rpet_id and 
	up_time >= CONCAT(rup_time, " 00:00:00" ) AND up_time <= CONCAT(rup_time, " 23:59:59" )	limit 1;

	
IF ( _sug_move_type = 1 ) THEN
	SELECT _div_move * 1.350 INTO _div_move;
	SELECT _div_fast * 1.350 INTO _div_fast;
END IF;
	
delete from wPetSugMoveDescRep  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );
delete from wPetSugMoveRep  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );

/*
select count(*) into _cnt from wpet_move_info  where 
	speed >  -0.012 and (TIMESTAMPDIFF(DAY, DATE_FORMAT(up_time,'%Y-%m-%d 00:00:00'),  rup_time ) = 0 ) and pets_pet_id=rpet_id;
*/
/*	
IF _cnt = 0 THEN
insert into wPetSugMoveDescRep (pet_id, per_move, exec_time, route, step_number, calories, up_time)
	values (rpet_id, 0, 0, 0, 0, 0, rup_time );
  insert into wPetSugMoveDescRep (pet_id, per_move, dexec_time, route, step_number, calories, up_time)
	select rpet_id, sum( TIMESTAMPDIFF( SECOND, start_time, end_time  ))/_sug_move_time, 
		sum( TIMESTAMPDIFF( SECOND, start_time, end_time  )),
		sum( route), sum(step_number), sum(calories), rup_time 
	from wpet_move_info where speed > _div_fast and (TIMESTAMPDIFF(DAY, DATE_FORMAT(up_time,'%Y-%m-%d 00:00:00'),  rup_time ) = 0 ) and pets_pet_id=rpet_id;

  update wPetSugMoveDescRep set lexec_time= ( select sum( TIMESTAMPDIFF( SECOND, start_time, end_time  )) from 
		wpet_move_info where speed > _div_move and speed < _div_fast and (TIMESTAMPDIFF(DAY, DATE_FORMAT(up_time,'%Y-%m-%d 00:00:00'),  rup_time ) = 0 ) 
		and pets_pet_id=rpet_id ) where pet_id = rpet_id and up_time = rup_time;
		
  update wPetSugMoveDescRep set exec_time = (dexec_time + lexec_time ) where  pet_id = rpet_id and up_time = rup_time;	

  update wPetSugMoveDescRep set tot_sleep= ( select sum( TIMESTAMPDIFF( SECOND, start_time, end_time  )) from 
		wpet_move_info where speed < _div_sleep and (TIMESTAMPDIFF(DAY, DATE_FORMAT(up_time,'%Y-%m-%d 00:00:00'),  rup_time ) = 0 ) 
		and pets_pet_id=rpet_id ) where pet_id = rpet_id and up_time = rup_time;
  
  update wPetSugMoveDescRep set tot_dsleep= ( select sum( TIMESTAMPDIFF( SECOND, start_time, end_time  )) from 
		wpet_move_info where speed < _div_dsleep and (TIMESTAMPDIFF(DAY, DATE_FORMAT(up_time,'%Y-%m-%d 00:00:00'),  rup_time ) = 0 ) 
		and pets_pet_id=rpet_id ) where pet_id = rpet_id and up_time = rup_time;
  
  update wPetSugMoveDescRep set tot_lsleep=(tot_sleep - tot_dsleep) where pet_id=rpet_id and up_time=rup_time;

	
END IF;	
*/  


 
select concat(rup_time,' 00:00:00') into _sec_start_time;

 
 
 
   delete from wPetSugMoveRepActivity where pet_id = rpet_id and TIMESTAMPDIFF(DAY, start_time, rup_time) = 0;
   delete from wPetSugMoveRepSleep where pet_id = rpet_id and TIMESTAMPDIFF(DAY, start_time, rup_time) = 0;
   
 
   /* 打开游标 */
      OPEN rs;  

	SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  
	set _sec_step_number = 0.0;
    FETCH NEXT FROM rs INTO _rec_step_number, _rec_speed, _rec_start_time, _rec_end_time, _rec_route, _rec_calories;      
	  
    while  no_more_records  = 0 and TIMESTAMPDIFF(DAY, rup_time, _sec_start_time ) = 0 do
	      /* 逐个取出当前记录 step_number 字段的最大值， */
		set _sec_step_number = 0.0;		
		set _sec_sleep_type = 0.0;
		set _sec_move_type = 0.0;
		while no_more_records  = 0 and TIMESTAMPDIFF(SECOND, _rec_end_time, _sec_end_time) > 0  and 
			TIMESTAMPDIFF(SECOND, _rec_start_time, _sec_end_time) > 0 do

			set _temp = TIMESTAMPDIFF(SECOND, _rec_start_time, _rec_end_time);
			set _tot_route = _tot_route + _rec_route;
			set _tot_steps = _tot_steps + _rec_step_number;
			set _tot_calories = _tot_calories + _rec_calories;
			
			set _sec_step_number=_rec_step_number + _sec_step_number;
			IF _rec_speed < _div_sleep and _rec_speed > _div_dsleep THEN 
				set _sec_sleep_type = _sec_sleep_type + 0.5 * _temp/( 216 * 8);
				set _tot_sleep = _tot_sleep + _temp;				
				set _tot_lsleep = _tot_lsleep + _temp;													
			ELSE 
				IF _rec_speed < _div_dsleep THEN 
					set _sec_sleep_type = _sec_sleep_type + 1.0 * _temp/( 216 * 8);
					set _tot_sleep = _tot_sleep + _temp;				
					set _tot_dsleep = _tot_dsleep + _temp;									
				END IF;
			END IF;
			

			IF _rec_speed < _div_fast and _rec_speed > _div_move THEN 
				set _sec_move_type = _sec_move_type + 0.5 * _temp/ ( 216 * 8);
				set _lexec_time = _lexec_time + _temp;				
				set _exec_time = _exec_time + _temp;				
			ELSE 
				IF _rec_speed >= _div_fast THEN 
					set _sec_move_type = _sec_move_type + 1.0 * _temp/ ( 216 * 8 );
					set _dexec_time = _dexec_time + _temp;
					set _exec_time = _exec_time + _temp;				
				END IF;
			END IF;
			
			
			FETCH NEXT FROM rs INTO _rec_step_number,_rec_speed,  _rec_start_time, _rec_end_time, _rec_route, _rec_calories;            
		end while;
		 
		 IF ( _sec_step_number < 0.001 ) THEN		/* 关机后， 没有运动数据上报，认为宠物在熟睡 */
		     set _sec_sleep_type = 0.00;
			 set _sec_move_type = 0.0;
		 END IF;
		 
		 insert into wPetSugMoveRepActivity(pet_id, steps, move_type, start_time, end_time) values(rpet_id, _sec_step_number, _sec_move_type, _sec_start_time, _sec_end_time);
		 insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, _sec_sleep_type, _sec_start_time, _sec_end_time);

		 IF _sec_step_number > _max_sec_step_number THEN 
			set _max_sec_step_number  = _sec_step_number;
		 END IF;
		 
		set _sec_start_time = _sec_end_time;
		SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  

	end while;

    /*update wPetSugMoveDescRep set max_unit_steps= _max_sec_step_number where pet_id=rpet_id and up_time=rup_time; */
	
	
     CLOSE rs;	

	 
	 IF strcmp(_fci_group_id, '0') = 0 or strcmp(_fci_group_id, 'F003') = 0 or strcmp(_fci_group_id, 'F009') = 0  THEN 
		insert into wPetSugMoveDescRep (pet_id, per_move, dexec_time, route, step_number, calories, up_time, max_unit_steps, tot_sleep, 
			tot_dsleep, tot_lsleep, lexec_time, exec_time )
			select rpet_id, _exec_time*100 /_sug_move_time, 
				_dexec_time, _tot_route, _tot_steps, _tot_calories, 
				rup_time, _max_sec_step_number, _tot_sleep, _tot_dsleep, 
				_tot_lsleep, _lexec_time, _exec_time;
	 ELSE
		insert into wPetSugMoveDescRep (pet_id, per_move, dexec_time, route, step_number, calories, up_time, max_unit_steps, tot_sleep, 
			tot_dsleep, tot_lsleep, lexec_time, exec_time )
			select rpet_id, _dexec_time*100 /_sug_move_time, 
				_dexec_time, _tot_route, _tot_steps, _tot_calories, 
				rup_time, _max_sec_step_number, _tot_sleep, _tot_dsleep, 
				_tot_lsleep, _lexec_time, _exec_time;
     END IF;	 
	 
			CALL calcSugExecSleep(rpet_id, rup_time);

	 
delete from wPetSugMoveRepState  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );
delete from wPetSugMoveDescRepState  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );	
	
insert into wPetSugMoveRepState (pet_id, up_time, status)
	values (rpet_id, rup_time, '1');	
insert into wPetSugMoveDescRepState (pet_id, up_time, status)
	values (rpet_id, rup_time, '1');
commit;	
END */$$
DELIMITER ;






/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecSe` */;

DELIMITER $$

/* rpet_id    宠物id */
/* rup_time   具体日期，例如'2016-09-26' */

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecSe`(IN rpet_id VARCHAR(50), IN rstime VARCHAR(50), IN retime VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _fci_group_id char(10) default '';
DECLARE _sug_move_type int default 0;
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _div_move float default 0.000;
DECLARE _div_fast float default 0.000;
DECLARE _div_sleep float default 0.000;
DECLARE _div_dsleep float default 0.000;
DECLARE _age float;

 declare i int default 0;
DECLARE _rec_step_number float default 0.000;
DECLARE _sec_step_number float default 0.000;   /*每天原始运动上报数据(设计为每3.6分钟一条上报数据) */
DECLARE _sec_sleep_type float default 0.000;
DECLARE _sec_move_type float default 0.000;


DECLARE _max_sec_step_number float default 0.000;
DECLARE _up_time VARCHAR(25);
DECLARE _rec_start_time VARCHAR(25);
DECLARE _rec_end_time VARCHAR(25);
DECLARE _rec_sleep_type float default 0.000;
DECLARE _rec_speed float default 0.000;

DECLARE _rec_route float default 0.000;
DECLARE _rec_calories float default 0.000;


DECLARE _sec_start_time VARCHAR(25);		/*100格中具体的起始时间 */
DECLARE _sec_end_time VARCHAR(25);

DECLARE _temp float default 0.000;

DECLARE _dexec_time float default 0.000;
DECLARE _lexec_time float default 0.000;
DECLARE _exec_time float default 0.000;
DECLARE _tot_sleep float default 0.000;
DECLARE _tot_dsleep float default 0.000;
DECLARE _tot_lsleep float default 0.000;

DECLARE _tot_route float default 0.000;
DECLARE _tot_calories float default 0.000;
DECLARE _tot_steps float default 0.000;



 DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR select step_number, speed,  start_time, end_time, route, calories from wpet_move_info where pets_pet_id = rpet_id and 
	(start_time >= rstime AND end_time <= retime );		
 
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 SET SQL_SAFE_UPDATES=0;

/* 判断是否已经生成过数据， 如果是，直接返回, ... 
	select count(*) into _cnt from wPetSugMoveDescRepState where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 ) and status = '1';
	IF _cnt > 0 THEN
		return;
	END IF;
	
*/
select TIMESTAMPDIFF(MONTH, born_date,  CURRENT_DATE())/12.0 into _age from wpets where pet_id = rpet_id limit 1; 
select b.fci_group_catid into _fci_group_id  from wpets a left join wfci_detail_all b on a.fci_detail_all_catid = b.catid where pet_id = rpet_id limit 1; 

set _div_sleep = 0.84;
set _div_dsleep = 0.001;

IF _age <= 0.16667 and _age > -0.01 THEN      /* 小于2个月， 不建议运动 */
	set _div_move = 35;
	set _div_fast = 45;
ELSE IF _age > 0.16667 and _age <= 0.25 THEN /* 2到 3个月 大 */
	set _div_move = 35;	/*5; */
	set _div_fast = 45;	/*12; */
ELSE IF _age <= 0.5 and _age > 0.25 THEN /* 3到 6个月 大 */
	set _div_move = 35;	/*12; */
	set _div_fast = 45;	/*40; */
ELSE IF _age <= 0.6667 and _age > 0.5 THEN /* 6到 8个月 大 */
	set _div_move = 35;  /*40; */
	set _div_fast = 45;	 /*100; */
ELSE IF _age <= 1.0 and _age > 0.6667 THEN /* 8到 12个月 大 */
	set _div_move = 35;	 /*50; */
	set _div_fast = 45;	/*120; */
ELSE IF _age <= 8.0 and _age > 1.0 THEN /* 12到 12*8个月 大 */
	set _div_move = 35; /*100; */
	set _div_fast = 45;	/*180; */
ELSE IF _age > 8.0 THEN /* 12 * 8 个月以上 */
	set _div_move = 35;	/*30; */
	set _div_fast = 45; /*70; */
END IF;
END IF;
END IF;
END IF;
END IF;
END IF;
END IF;

select LEFT(rstime, 10) into _up_time;
	
/*select count(*) into _cnt from wPetSugMove where pet_id = rpet_id and 
	up_time = _up_time;
	*/
delete from wPetSugMove where  pet_id = rpet_id and 
	up_time = _up_time;
/*IF _cnt = 0 THEN */
	call calcSugMove(rpet_id, _up_time, _type, _time);
/*END IF; */


/*	
select count(*) into _cnt from wPetSugMoveDescRep where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 );	
IF _cnt = 0 THEN
	insert into wPetSugMoveDescRep (pet_id, per_move, exec_time, route, step_number, calories, up_time)
		values (rpet_id, 0, 0, 0, 0, 0, rup_time);
END IF;
*/

select move_time into _sug_move_time  from wPetSugMove where pet_id = rpet_id and 
	up_time = CONCAT(_up_time, " 00:00:00" ) limit 1;

select move_type into _sug_move_type  from wPetSugMove where pet_id = rpet_id and 
	up_time = CONCAT(_up_time, " 00:00:00" ) limit 1;

	
IF ( _sug_move_type = 1 ) THEN
	SELECT _div_move * 1.350 INTO _div_move;
	SELECT _div_fast * 1.350 INTO _div_fast;
END IF;
	
delete from wPetSugMoveDescRep  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  _up_time ) = 0 );
delete from wPetSugMoveRep  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  _up_time ) = 0 );



 
select rstime into _sec_start_time;

 
 
 
   /*
   delete from wPetSugMoveRepActivity where pet_id = rpet_id and TIMESTAMPDIFF(DAY, start_time, _up_time) = 0;
   delete from wPetSugMoveRepSleep where pet_id = rpet_id and TIMESTAMPDIFF(DAY, start_time, _up_time) = 0;
 
   */
   delete from wPetSugMoveRepActivity where pet_id = rpet_id and 
		start_time >= rstime  AND end_time <= retime; 	
   delete from wPetSugMoveRepSleep where pet_id = rpet_id and 
		start_time >= rstime  AND end_time <= retime; 	
 
   /* 打开游标 */
      OPEN rs;  

	SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  
	set _sec_step_number = 0.0;
    FETCH NEXT FROM rs INTO _rec_step_number, _rec_speed, _rec_start_time, _rec_end_time, _rec_route, _rec_calories;      
	  
    while  no_more_records  = 0  do
	      /* 逐个取出当前记录 step_number 字段的最大值， */
		set _sec_step_number = 0.0;		
		set _sec_sleep_type = 0.0;
		set _sec_move_type = 0.0;
		while no_more_records  = 0 and TIMESTAMPDIFF(SECOND, _rec_end_time, _sec_end_time) > 0  and 
			TIMESTAMPDIFF(SECOND, _rec_start_time, _sec_end_time) > 0 do

			set _temp = TIMESTAMPDIFF(SECOND, _rec_start_time, _rec_end_time);
			set _tot_route = _tot_route + _rec_route;
			set _tot_steps = _tot_steps + _rec_step_number;
			set _tot_calories = _tot_calories + _rec_calories;
			
			set _sec_step_number=_rec_step_number + _sec_step_number;
			IF _rec_speed < _div_sleep and _rec_speed > _div_dsleep THEN 
				set _sec_sleep_type = _sec_sleep_type + 0.5 * _temp/( 216 * 8);
				set _tot_sleep = _tot_sleep + _temp;				
				set _tot_lsleep = _tot_lsleep + _temp;													
			ELSE 
				IF _rec_speed < _div_dsleep THEN 
					set _sec_sleep_type = _sec_sleep_type + 1.0 * _temp/( 216 * 8);
					set _tot_sleep = _tot_sleep + _temp;				
					set _tot_dsleep = _tot_dsleep + _temp;									
				END IF;
			END IF;
			

			IF _rec_speed < _div_fast and _rec_speed > _div_move THEN 
				set _sec_move_type = _sec_move_type + 0.5 * _temp/ ( 216 * 8);
				set _lexec_time = _lexec_time + _temp;				
				set _exec_time = _exec_time + _temp;				
			ELSE 
				IF _rec_speed >= _div_fast THEN 
					set _sec_move_type = _sec_move_type + 1.0 * _temp/ ( 216 * 8 );
					set _dexec_time = _dexec_time + _temp;
					set _exec_time = _exec_time + _temp;				
				END IF;
			END IF;
			
			
			FETCH NEXT FROM rs INTO _rec_step_number,_rec_speed,  _rec_start_time, _rec_end_time, _rec_route, _rec_calories;            
		end while;
		 
		 IF ( _sec_step_number < 0.001 ) THEN		/* 关机后， 没有运动数据上报，认为宠物在熟睡 */
		     set _sec_sleep_type = 0.00;
			 set _sec_move_type = 0.0;
		 END IF;
		 
		 insert into wPetSugMoveRepActivity(pet_id, steps, move_type, start_time, end_time) values(rpet_id, _sec_step_number, _sec_move_type, _sec_start_time, _sec_end_time);
		 insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, _sec_sleep_type, _sec_start_time, _sec_end_time);

		 IF _sec_step_number > _max_sec_step_number THEN 
			set _max_sec_step_number  = _sec_step_number;
		 END IF;
		 
		set _sec_start_time = _sec_end_time;
		SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  

	end while;

    /*update wPetSugMoveDescRep set max_unit_steps= _max_sec_step_number where pet_id=rpet_id and up_time=rup_time; */
	
	
     CLOSE rs;	

	 
	 IF strcmp(_fci_group_id, '0') = 0 or strcmp(_fci_group_id, 'F003') = 0 or strcmp(_fci_group_id, 'F009') = 0  THEN 
		insert into wPetSugMoveDescRep (pet_id, per_move, dexec_time, route, step_number, calories, up_time, max_unit_steps, tot_sleep, 
			tot_dsleep, tot_lsleep, lexec_time, exec_time )
			select rpet_id, _exec_time*100 /_sug_move_time, 
				_dexec_time, _tot_route, _tot_steps, _tot_calories/10, 
				_up_time, _max_sec_step_number, _tot_sleep, _tot_dsleep, 
				_tot_lsleep, _lexec_time, _exec_time;
	 ELSE
		insert into wPetSugMoveDescRep (pet_id, per_move, dexec_time, route, step_number, calories, up_time, max_unit_steps, tot_sleep, 
			tot_dsleep, tot_lsleep, lexec_time, exec_time )
			select rpet_id, _dexec_time*100 /_sug_move_time, 
				_dexec_time, _tot_route, _tot_steps, _tot_calories/10, 
				_up_time, _max_sec_step_number, _tot_sleep, _tot_dsleep, 
				_tot_lsleep, _lexec_time, _exec_time;
     END IF;	 
	 
			CALL calcSugExecSleepSe(rpet_id, rstime, retime);


/*			
delete from wPetSugMoveRepState  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  _up_time ) = 0 );
delete from wPetSugMoveDescRepState  where  pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  _up_time ) = 0 );	
	
insert into wPetSugMoveRepState (pet_id, up_time, status)
	values (rpet_id, _up_time, '1');	
insert into wPetSugMoveDescRepState (pet_id, up_time, status)
	values (rpet_id, _up_time, '1');
*/	
commit;	
END */$$
DELIMITER ;



/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecSleepSe` */;

DELIMITER $$

/* rpet_id    宠物id */
/* rup_time   具体日期，例如'2016-09-26' */

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecSleepSe`(IN rpet_id VARCHAR(50), IN rstime VARCHAR(50), IN retime VARCHAR(50))
BEGIN
DECLARE _up_time VARCHAR(25);
DECLARE _per_move float default 0.0000; 
DECLARE _fci_group_id char(10) default '';
DECLARE _sug_move_type int default 0;
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _div_move float default 0.000;
DECLARE _div_fast float default 0.000;
DECLARE _div_sleep float default 0.000;
DECLARE _div_dsleep float default 0.000;
DECLARE _age float;

 declare i int default 0;
DECLARE _rec_step_number float default 0.000;
DECLARE _sec_step_number float default 0.000;   /*每天原始运动上报数据(设计为每3.6分钟一条上报数据) */
DECLARE _sec_sleep_type float default 0.000;
DECLARE _sec_move_type float default 0.000;


DECLARE _max_sec_step_number float default 0.000;
DECLARE _rec_start_time VARCHAR(25);
DECLARE _rec_end_time VARCHAR(25);
DECLARE _rec_sleep_type float default 0.000;
DECLARE _rec_speed float default 0.000;

DECLARE _rec_route float default 0.000;
DECLARE _rec_calories float default 0.000;


DECLARE _sec_start_time VARCHAR(25);		/*100格中具体的起始时间 */
DECLARE _sec_end_time VARCHAR(25);

DECLARE _temp float default 0.000;

DECLARE _dexec_time float default 0.000;
DECLARE _lexec_time float default 0.000;
DECLARE _exec_time float default 0.000;
DECLARE _tot_sleep float default 0.000;
DECLARE _tot_dsleep float default 0.000;
DECLARE _tot_lsleep float default 0.000;

DECLARE _tot_route float default 0.000;
DECLARE _tot_calories float default 0.000;
DECLARE _tot_steps float default 0.000;



 DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR select step_number, speed,  start_time, end_time from wpet_sleep_info where pets_pet_id = rpet_id and 
	(start_time >= rstime AND end_time <= retime );		
 
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 SET SQL_SAFE_UPDATES=0;

/* 判断是否已经生成过数据， 如果是，直接返回, ... 
	select count(*) into _cnt from wPetSugMoveDescRepState where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 ) and status = '1';
	IF _cnt > 0 THEN
		return;
	END IF;
	
*/

set _div_sleep = 10.84;
set _div_dsleep = 10.01;
	
select LEFT(rstime, 10) into _up_time;
	
select rstime into _sec_start_time;

 
 
 
   delete from wPetSugMoveRepSleep where pet_id = rpet_id and 
	(start_time >= rstime AND end_time <= retime );		
 
   /* 打开游标 */
      OPEN rs;  

	/*SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  */
	set _sec_step_number = 0.0;
    FETCH NEXT FROM rs INTO _rec_step_number, _rec_speed, _rec_start_time, _rec_end_time;      
	  
    while  no_more_records  = 0  do
		  set _temp = TIMESTAMPDIFF(SECOND, _rec_start_time, _rec_end_time);
		  IF ( _temp > 4 * 60 * 60 ) THEN
			set _temp = 0.000;
		  END IF;
		  
	      /* 逐个取出当前记录 step_number 字段的最大值， */
			IF _rec_speed < _div_sleep and _rec_speed > _div_dsleep THEN 
				set _tot_sleep = _tot_sleep + _temp;				
				set _tot_lsleep = _tot_lsleep + _temp;													
				insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, 0.0, _rec_start_time, _rec_end_time);
			ELSE 
				IF _rec_speed < _div_dsleep THEN 
					set _tot_sleep = _tot_sleep + _temp;				
					set _tot_dsleep = _tot_dsleep + _temp;									
					insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, 1.0, _rec_start_time, _rec_end_time);
				END IF;
			END IF;
			

			
			FETCH NEXT FROM rs INTO _rec_step_number,_rec_speed,  _rec_start_time, _rec_end_time;            

	end while;

    /*update wPetSugMoveDescRep set max_unit_steps= _max_sec_step_number where pet_id=rpet_id and up_time=rup_time; */
	
	
     CLOSE rs;	

	 
	 update wPetSugMoveDescRep set tot_sleep = _tot_sleep, tot_lsleep = _tot_lsleep, 
		tot_dsleep = _tot_dsleep where pet_id = rpet_id and up_time = _up_time;
	 
	 
	 
	
END */$$
DELIMITER ;




/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecSleep` */;

DELIMITER $$

/* rpet_id    宠物id */
/* rup_time   具体日期，例如'2016-09-26' */

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecSleep`(IN rpet_id VARCHAR(50), IN rup_time VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _fci_group_id char(10) default '';
DECLARE _sug_move_type int default 0;
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _div_move float default 0.000;
DECLARE _div_fast float default 0.000;
DECLARE _div_sleep float default 0.000;
DECLARE _div_dsleep float default 0.000;
DECLARE _age float;

 declare i int default 0;
DECLARE _rec_step_number float default 0.000;
DECLARE _sec_step_number float default 0.000;   /*每天原始运动上报数据(设计为每3.6分钟一条上报数据) */
DECLARE _sec_sleep_type float default 0.000;
DECLARE _sec_move_type float default 0.000;


DECLARE _max_sec_step_number float default 0.000;
DECLARE _rec_start_time VARCHAR(25);
DECLARE _rec_end_time VARCHAR(25);
DECLARE _rec_sleep_type float default 0.000;
DECLARE _rec_speed float default 0.000;

DECLARE _rec_route float default 0.000;
DECLARE _rec_calories float default 0.000;


DECLARE _sec_start_time VARCHAR(25);		/*100格中具体的起始时间 */
DECLARE _sec_end_time VARCHAR(25);

DECLARE _temp float default 0.000;

DECLARE _dexec_time float default 0.000;
DECLARE _lexec_time float default 0.000;
DECLARE _exec_time float default 0.000;
DECLARE _tot_sleep float default 0.000;
DECLARE _tot_dsleep float default 0.000;
DECLARE _tot_lsleep float default 0.000;

DECLARE _tot_route float default 0.000;
DECLARE _tot_calories float default 0.000;
DECLARE _tot_steps float default 0.000;



 DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR select step_number, speed,  start_time, end_time from wpet_sleep_info where pets_pet_id = rpet_id and 
	(start_time > concat(rup_time, " 00:00:00" ) AND end_time <= concat(rup_time, " 23:59:59" ) );		
 
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 SET SQL_SAFE_UPDATES=0;

/* 判断是否已经生成过数据， 如果是，直接返回, ... 
	select count(*) into _cnt from wPetSugMoveDescRepState where pet_id = rpet_id and 
	(TIMESTAMPDIFF(DAY, up_time,  rup_time ) = 0 ) and status = '1';
	IF _cnt > 0 THEN
		return;
	END IF;
	
*/

set _div_sleep = 10.84;
set _div_dsleep = 10.01;
	
 
select concat(rup_time,' 00:00:00') into _sec_start_time;

 
 
 
   delete from wPetSugMoveRepSleep where pet_id = rpet_id and TIMESTAMPDIFF(DAY, start_time, rup_time) = 0;
   
 
   /* 打开游标 */
      OPEN rs;  

	/*SELECT DATE_ADD(_sec_start_time,INTERVAL 216*8 SECOND) into _sec_end_time;  */
	set _sec_step_number = 0.0;
    FETCH NEXT FROM rs INTO _rec_step_number, _rec_speed, _rec_start_time, _rec_end_time;      
	  
    while  no_more_records  = 0  do
		  set _temp = TIMESTAMPDIFF(SECOND, _rec_start_time, _rec_end_time);
		  IF ( _temp > 4 * 60 * 60 ) THEN
			set _temp = 0.000;
		  END IF;
		  
	      /* 逐个取出当前记录 step_number 字段的最大值， */
			IF _rec_speed < _div_sleep and _rec_speed > _div_dsleep THEN 
				set _tot_sleep = _tot_sleep + _temp;				
				set _tot_lsleep = _tot_lsleep + _temp;													
				insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, 0.0, _rec_start_time, _rec_end_time);
			ELSE 
				IF _rec_speed < _div_dsleep THEN 
					set _tot_sleep = _tot_sleep + _temp;				
					set _tot_dsleep = _tot_dsleep + _temp;									
					insert into wPetSugMoveRepSleep(pet_id, sleep_type, start_time, end_time) values(rpet_id, 1.0, _rec_start_time, _rec_end_time);
				END IF;
			END IF;
			

			
			FETCH NEXT FROM rs INTO _rec_step_number,_rec_speed,  _rec_start_time, _rec_end_time;            

	end while;

    /*update wPetSugMoveDescRep set max_unit_steps= _max_sec_step_number where pet_id=rpet_id and up_time=rup_time; */
	
	
     CLOSE rs;	

	 
	 update wPetSugMoveDescRep set tot_sleep = _tot_sleep, tot_lsleep = _tot_lsleep, 
		tot_dsleep = _tot_dsleep where pet_id = rpet_id and up_time = rup_time;
	 
	 
	 
	
END */$$
DELIMITER ;



/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecListSe` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecListSe`(IN rpet_id VARCHAR(50), 
	IN rend_day VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _day VARCHAR(50) default '';
DECLARE _start_day VARCHAR(50) default ' ';
DECLARE _rec_etime VARCHAR(50) default ' ';
DECLARE _rec_stime VARCHAR(50) default ' ';

    select rend_day into _day;
	SELECT DATE_ADD(_day,INTERVAL -6 DAY) into _start_day;
	SELECT DATE_ADD(DATE_ADD(_day,INTERVAL -1 DAY),INTERVAL 1 SECOND) into _rec_stime; 
    select _day into _rec_etime;

	SELECT DATE_ADD(_day,INTERVAL -6 DAY) into _start_day; 
	
	 while TIMESTAMPDIFF(DAY, _start_day,  _day)  >=0 do	 
		CALL calcSugExecSe(rpet_id, _rec_stime, _rec_etime);
		SELECT DATE_ADD(_day,INTERVAL -1 DAY) into _day; 
		SELECT DATE_ADD(DATE_ADD(_day,INTERVAL -1 DAY),INTERVAL 1 SECOND) into _rec_stime; 
		select _day into _rec_etime;		
	 end while;
END */$$
DELIMITER ;



/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecList` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecList`(IN rpet_id VARCHAR(50), 
	IN rstart_day VARCHAR(50),IN rend_day VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _day VARCHAR(50) default '';
DECLARE _end_day VARCHAR(50) default ' ';

    select rstart_day into _day;
    select rend_day into _end_day;
	
	 while TIMESTAMPDIFF(DAY, _day,  _end_day)  >=0 do
	 
		select count(*) into _cnt from wPetSugMoveDescRep where pet_id=rpet_id and up_time = _day;
		IF  _cnt < 1 THEN
			CALL calcSugExec(rpet_id, _day);
		ELSE IF ( TIMESTAMPDIFF(DAY, _day,  _end_day) = 0 ) THEN
			CALL calcSugExec(rpet_id, _day);
			END IF;
		END IF;
		SELECT DATE_ADD(_day,INTERVAL 1 DAY) into _day; 
	 end while;
END */$$
DELIMITER ;


/*!50003 DROP PROCEDURE IF EXISTS  `autoShareMgr` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `autoShareMgr`(IN puser_id VARCHAR(50))
BEGIN
DECLARE _device_id int; 
DECLARE _temp int; 
DECLARE _i int; 
 DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR SELECT a.device_id FROM wdevice_active_info a left join wDeviceExtra b on a.device_id = b.device_id 
	WHERE LENGTH(a.device_imei) = 15 order by b.order_by desc, b.dev_status desc;		
 
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 
       OPEN rs;  

    FETCH NEXT FROM rs INTO _device_id;      
	set _i = 0;
	  
    while  no_more_records  = 0 and _i < 12  do
			
		SELECT COUNT(*) into _temp FROM wshare_info WHERE user_id = puser_id AND device_id = _device_id AND STATUS = 1;
		if  _temp < 1  THEN
			INSERT INTO wshare_info (user_id, device_id, is_priority, `status` ) VALUES (puser_id, _device_id, 0, 1);
        END IF;
		FETCH NEXT FROM rs INTO _device_id;      
	    set _i = _i + 1;
	  
	end while;

	
     CLOSE rs;	
 

	
END */$$
DELIMITER ;


/*!50003 DROP PROCEDURE IF EXISTS  `testTsProc` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `testTsProc`()
BEGIN
DECLARE _device_id int; 
DECLARE _timestamp1 VARCHAR(50) default '';
DECLARE  no_more_records INT DEFAULT 0;  
 
 DECLARE rs CURSOR FOR SELECT device_id FROM wdevice_active_info;		
 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_records = 1;

 
       OPEN rs;  

    FETCH NEXT FROM rs INTO _device_id;        
    while  no_more_records  = 0 do
	select device_update_time_ts into _timestamp1 from temp_tb_real where device_id = _device_id limit 1;
	update wdevice_active_info set device_update_time_ts = _timestamp1 where device_id = _device_id;
	FETCH NEXT FROM rs INTO _device_id;      
    end while;

	
     CLOSE rs;	
 
END */$$
DELIMITER ;



/* 只是统计一个月的运动数据 */
/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecByMonthSe` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecByMonthSe`(IN rpet_id VARCHAR(50), 
	IN rstart_mon VARCHAR(250),IN rend_mon VARCHAR(250))
label:BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _flag int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _rstart_mon_i VARCHAR(21) default '';
DECLARE _rend_mon_i VARCHAR(21) default '';
DECLARE _end_day VARCHAR(50) default ' ';
DECLARE _tot_step int default 0; 

DECLARE _loop int default 0; 


	SET SQL_SAFE_UPDATES=0;
	
	
	while true do
		select SUBSTRING(rstart_mon, _loop * 20 + 1, 19) into _rstart_mon_i;
		select SUBSTRING(rend_mon, _loop * 20 + 1, 19) into _rend_mon_i;
		
		IF ( LENGTH( _rstart_mon_i ) <> 19 || LENGTH( _rend_mon_i ) <> 19 ) THEN
			leave label;
		END IF;

		delete from wPetSugMoveMonRep where monStr = _rstart_mon_i AND pet_id=rpet_id;
		select sum(step_number) into _tot_step from wpet_move_info  where 
			start_time >= _rstart_mon_i and end_time <= _rend_mon_i 
			and pets_pet_id=rpet_id;
		insert into wPetSugMoveMonRep (pet_id, step_number, monStr) values (rpet_id, _tot_step, _rstart_mon_i);
		
		SELECT (_loop + 1 ) into _loop;
	end while;
END */$$
DELIMITER ;



/*!50003 DROP PROCEDURE IF EXISTS  `calcSugExecByMonth` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcSugExecByMonth`(IN rpet_id VARCHAR(50), 
	IN rstart_mon VARCHAR(50),IN rend_mon VARCHAR(50))
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 0;
DECLARE _flag int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _day VARCHAR(50) default '';
DECLARE _end_day VARCHAR(50) default ' ';
DECLARE _tot_step int default 0; 

	SET SQL_SAFE_UPDATES=0;

    select concat(rstart_mon,'-01 00:00:00') into _day;
    select concat(rend_mon,'-01 00:00:00') into _end_day;

	
	 while TIMESTAMPDIFF(MONTH, _day,  _end_day)  >=0 do
	 
		select count(*) into _cnt from wPetSugMoveMonRep where pet_id=rpet_id and monStr = _day;
		set _flag = 0;
		IF  _cnt < 1 THEN
			set _flag = 1;
		ELSE IF TIMESTAMPDIFF(MONTH, _day,  _end_day)  =0 THEN
			set _flag = 1;			
			delete from wPetSugMoveMonRep where monStr = _day AND pet_id=rpet_id;
			END IF;
		END IF;

		IF _flag = 1 THEN
			select sum(step_number) into _tot_step from wpet_move_info  where 
				(TIMESTAMPDIFF(MONTH, _day,  start_time ) < 1 ) 
                and start_time > _day 				
				and pets_pet_id=rpet_id;
		    insert into wPetSugMoveMonRep (pet_id, step_number, monStr) values (rpet_id, _tot_step, _day);
		END IF;

		SELECT DATE_ADD(_day,INTERVAL 1 MONTH) into _day; 
	 end while;
END */$$
DELIMITER ;


/*!50003 DROP PROCEDURE IF EXISTS  `calcInsTstDev` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `calcInsTstDev`()
BEGIN
DECLARE _per_move float default 0.0000; 
DECLARE _sug_move_time float default 0.000;
DECLARE _cnt int unsigned default 20000;
DECLARE _flag int unsigned default 0;
DECLARE _type char(2) default '2';
DECLARE _time float default 0.000;
DECLARE _day VARCHAR(50) default '';
DECLARE _end_day VARCHAR(50) default ' ';
DECLARE _tot_step int default 0; 



	
	 while _cnt  <=80000 do

		insert  into `wdevice_active_info`(`device_phone`,`device_imei`,`device_name`,
		`device_update_time`,`device_disable`,`listen_type`,`belong_project`,`isphoneHelp`,`isserverHelp`,
		`deadline`,`brandname`,`fall_on`,`device_type`,`conn_type`,`is_sos`,`sel_mode`,`gps_on`,`callback_on`,
		`temperature_on`,`heatout_on`,`led_on`,`flight_mode`,`urgent_mode`,`battery`,`is_lowbat`,`data_mute`,
		`data_volume`,`data_power`,`bind_count`,`wifir_interval`,`wifir_flag`,`time_zone`,`longitude`,`latitude`,
		`auto_time_zone`,`beattim`) values ('0',concat('tset',_cnt),'test',NULL,'1','0',1,'0','0','2015-03-18 00:00:00',NULL,
		'0',NULL,'3',NULL,'1',NULL,'0',NULL,NULL,NULL,NULL,NULL,29,'1','0','0','0',0,'2000','0','Asia/Shanghai','0','0','1',5);
	 
		set _cnt = _cnt + 1;
	 end while;
END */$$
DELIMITER ;


DROP TABLE IF EXISTS `wPetSugMoveMonRep`;
CREATE TABLE `wPetSugMoveMonRep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL COMMENT '宠物ID',
  `step_number` float DEFAULT 0 COMMENT '执行的步数', 
  `monStr`  datetime NOT NULL COMMENT '月度第一天，如2016-10-01',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/* Procedure structure for procedure `new_procedure` */

/*!50003 DROP PROCEDURE IF EXISTS  `new_procedure` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `new_procedure`(IN sex_id INT, OUT user_count INT)
BEGIN
IF sex_id=0 THEN
 SELECT COUNT(*) INTO user_count FROM test_user WHERE type='0'; 
else
 SELECT COUNT(*) INTO user_count FROM test_user WHERE type='1'; 
END IF;
END */$$
DELIMITER ;


/*!50003 DROP PROCEDURE IF EXISTS  `unbindDev` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unbindDev`(IN rimei VARCHAR(50))
BEGIN
DECLARE _pet_id VARCHAR(50) default '';

DELETE FROM wpet_move_info WHERE pets_pet_id IN (SELECT pet_id FROM wpets WHERE device_id IN (SELECT device_id FROM wdevice_active_info WHERE device_imei = rimei));
DELETE FROM wpet_sleep_info WHERE pets_pet_id IN (SELECT pet_id FROM wpets WHERE device_id IN (SELECT device_id FROM wdevice_active_info WHERE device_imei = rimei));
DELETE FROM wpets WHERE device_id IN (SELECT device_id FROM wdevice_active_info WHERE device_imei = rimei);
DELETE FROM wshare_info WHERE device_id IN (SELECT device_id FROM wdevice_active_info WHERE device_imei = rimei);

	
END */$$
DELIMITER ;




/* Procedure structure for procedure `proc1` */

/*!50003 DROP PROCEDURE IF EXISTS  `proc1` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `proc1`(OUT s int)
BEGIN  
 SELECT COUNT(*) INTO s FROM test_user; 
  END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
