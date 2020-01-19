/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/12/2019 17:15:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for command
-- ----------------------------
DROP TABLE IF EXISTS `command`;
CREATE TABLE `command` (
   `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `NAME` varchar(16) DEFAULT NULL COMMENT '指令名称',
   `DESCRIPTION` varchar(32) DEFAULT NULL COMMENT '描述',
   `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of command
-- ----------------------------
BEGIN;
INSERT INTO `command` VALUES (1, '段子', '精彩段子', '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command` VALUES (2, '新闻', '今日头条', '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command` VALUES (3, '娱乐', '娱乐新闻', '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command` VALUES (4, '电影', '近日上映大片', '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command` VALUES (5, '彩票', '中奖号码', '2019-12-09 23:51:31', '2019-12-09 23:51:31');
COMMIT;

-- ----------------------------
-- Table structure for command_content
-- ----------------------------
DROP TABLE IF EXISTS `command_content`;
CREATE TABLE `command_content` (
   `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `CONTENT` varchar(2048) DEFAULT NULL COMMENT '内容',
   `COMMAND_ID` int(11) DEFAULT NULL COMMENT '指令主键',
   `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of command_content
-- ----------------------------
BEGIN;
INSERT INTO `command_content` VALUES (1, '如果你的月薪是3000块钱，请记得分成五份，一份用来买书，一份给家人，一份给女朋友买化妆品和衣服，一份请朋友们吃饭，一份作为同事的各种婚丧嫁娶的份子钱。剩下的2999块钱藏起来，不要告诉任何人', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (2, '如果你得罪了老板，失去的只是一份工作；如果你得罪了客户，失去的不过是一份订单；是的，世上只有一个人可以得罪：你给她脸色看，你冲她发牢骚，你大声顶撞她，甚至当 着她的面摔碗，她都不会记恨你，原因很简单，因为她是你的母亲。', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (3, '有位非常漂亮的女同事，有天起晚了没有时间化妆便急忙冲到公司。结果那天她被记旷工了……吃惊]', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (4, '悟空和唐僧一起上某卫视非诚勿扰,悟空上台,24盏灯全灭。理由:1.没房没车只有一根破棍. 2.保镖职业危险.3.动不动打妖精,对女生不温柔. 4.坐过牢,曾被压五指山下500年。唐僧上台，哗!灯全亮。 理由:1.公务员； 2.皇上兄弟，后台最硬 3.精通梵文等外语 4.长得帅 5.最关键一点：有宝马！', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (5, 'Save your heart for someone who cares. 为了某个在乎你的人，请节约你的真心！', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (6, '有一个人叫真咯嗦，娶了个老婆叫要你管，生了个儿子叫麻烦。有一天麻烦不见了！夫妻俩就去报案。警察问爸爸：请问这位男士你叫啥名字？爸爸说:真咯嗦。警察很生气，然后 他又问妈妈叫啥名字。妈妈说:要你管。警察非常生气的说:你们要干什么?夫妻俩说：找麻烦。', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (7, '一年奔波，尘缘遇了谁；一句珍重，天涯别了谁；一点灵犀，凭栏忆了谁；一种相思，闲愁予了谁；一江明月，豪情酬了谁；一场冬雪，烟波忘了谁；一壶浊酒，相逢醉了谁；一世浮生，轻狂撩了谁；一封短信，才情念了谁；一番思量，谁是谁的谁 ；一枚围脖，转发回复谁.....', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (8, '白羊：记不清人；金牛：悔未上床；双子：他（她）是青蛙（恐龙）；巨蟹：善哉善哉；狮子：一脚蹬开；处女：倒大霉了；天秤：感觉很好；天蝎：你死我活；射手：有多少次；摩羯：气死我也；水瓶：奶奶个熊；双鱼：还是想他（她）{点开大图更精彩}', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (9, '记者：说真的，你真的会给给孩子换尿布吗？ 姚明：要不你躺下，我给你换一个！实话告诉你，我用一只脚就能给孩子换尿布，喂奶啥的，都行。 记者：我不信！ 姚明：真的，连灯都不用开。 记者：不可能！你说怎么换？ 姚明：一只脚把媳妇揣醒就得。', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (10, '一生只谈三次恋爱最好，一次懵懂，一次刻骨，一次一生。谈的太多会比较，无法确定；经历太多会麻木，不再相信爱情，行尸走肉，最后与不爱的人结婚，无法发自内心的爱对方，日常表现的应付，对方则抱怨你不够关心和不顾家，最后这失败的爱情，让你在遗憾和凑合中走完一生。', 1, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (11, '7月17日，马来西亚一架载有298人的777客机在乌克兰靠近俄罗斯边界坠毁。另据国际文传电讯社消息，坠毁机型为一架波音777客机，机载约280名乘客和15个机组人员。乌克兰空管部门随后证实马航MH17航班坠毁。乌克兰内政部幕僚表示，这一航班在顿涅茨克地区上空被击落。马来西亚航空公司确认，该公司从阿姆斯特丹飞往吉隆坡的MH17航班失联，并称最后与该客机取得联系的地点在乌克兰上空。图为马航客机坠毁现场。', 2, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (12, '昨日，邓超在微博分享了自己和孙俪的书法。夫妻同样写幸福，但差距很大。邓超自己都忍不住感慨字丑：左边媳妇写的。右边是我写的。看完我再也不幸福了。', 3, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (13, '《忍者神龟》[2]真人电影由美国派拉蒙影业发行，《洛杉矶之战》导演乔纳森·里贝斯曼执导。 片中四只神龟和老鼠老师都基于漫画和卡通重新绘制，由动作捕捉技术实现。其中皮特·普劳泽克饰演达芬奇(武器：武士刀)，诺尔·费舍饰演米开朗基罗(武器：双节棍)，阿伦·瑞奇森饰演拉斐尔(武器：铁叉)，杰瑞米·霍华德饰演多拉泰罗(武器：武士棍)。该片计划于2014年8月8日在北美上映。', 4, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `command_content` VALUES (14, '查啥呀查，你不会中奖的！', 5, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
COMMIT;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
   `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `COMMAND` tinyint(4) DEFAULT NULL COMMENT '指令类型',
   `DESCRIPTION` varchar(32) DEFAULT NULL COMMENT '描述',
   `CONTENT` varchar(2048) DEFAULT NULL COMMENT '内容',
   `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '是否已删除',
   `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
BEGIN;
INSERT INTO `message` VALUES (1, 0, '精彩内容', '精彩内容', 0, '2019-12-09 23:51:31', '2019-12-09 23:51:31');
INSERT INTO `message` VALUES (2, 0, '精彩段子', '如果你的月薪是3000块钱，请记得分成五份，一份用来买书，一份给家人，一份给女朋友买化妆品和衣服，一份请朋友们吃饭，一份作为同事的各种婚丧嫁娶的份子钱。剩下的2999块钱藏起来，不要告诉任何人', 0, '2019-12-09 23:51:31', '2019-12-26 17:15:06');
INSERT INTO `message` VALUES (3, 1, '今日头条', '7月17日，马来西亚一架载有298人的777客机在乌克兰靠近俄罗斯边界坠毁。另据国际文传电讯社消息，坠毁机型为一架波音777客机，机载约280名乘客和15个机组人员。乌克兰空管部门随后证实马航MH17航班坠毁。乌克兰内政部幕僚表示，这一航班在顿涅茨克地区上空被击落。马来西亚航空公司确认，该公司从阿姆斯特丹飞往吉隆坡的MH17航班失联，并称最后与该客机取得联系的地点在乌克兰上空。图为马航客机坠毁现场。', 0, '2019-12-09 23:51:31', '2019-12-26 17:15:09');
INSERT INTO `message` VALUES (4, 2, '娱乐新闻', '昨日，邓超在微博分享了自己和孙俪的书法。夫妻同样写幸福，但差距很大。邓超自己都忍不住感慨字丑：左边媳妇写的。右边是我写的。看完我再也不幸福了。', 0, '2019-12-09 23:51:31', '2019-12-26 17:15:12');
INSERT INTO `message` VALUES (5, 3, '近日上映大片', '《忍者神龟》[2]真人电影由美国派拉蒙影业发行，《洛杉矶之战》导演乔纳森·里贝斯曼执导。 片中四只神龟和老鼠老师都基于漫画和卡通重新绘制，由动作捕捉技术实现。其中皮特·普劳泽克饰演达芬奇(武器：武士刀)，诺尔·费舍饰演米开朗基罗(武器：双节棍)，阿伦·瑞奇森饰演拉斐尔(武器：铁叉)，杰瑞米·霍华德饰演多拉泰罗(武器：武士棍)。该片计划于2014年8月8日在北美上映。', 0, '2019-12-09 23:51:31', '2019-12-26 17:15:16');
INSERT INTO `message` VALUES (6, 4, '中奖号码', '查啥呀查，你不会中奖的！', 0, '2019-12-09 23:51:31', '2019-12-26 17:15:19');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
