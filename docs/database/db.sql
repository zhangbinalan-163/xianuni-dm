/**建库**/
CREATE SCHEMA 'xian_uni_dm' DEFAULT CHARACTER SET utf8 ;

/**党组织信息**/
CREATE TABLE 'party_orgnization' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'name' varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构名称',
  'status' bigint(3) DEFAULT '0' COMMENT '状态 0-正常 1-已撤销',
  'parent' int(11) DEFAULT '-1' COMMENT '直属上级组织ID',
  'isParent' bigint(3) DEFAULT '0' COMMENT '是否有子节点',
  'createTime' datetime DEFAULT NULL COMMENT '创建时间',
  'updateTime' datetime DEFAULT NULL COMMENT '最近修改时间',
  'electionTime' datetime DEFAULT NULL COMMENT '最近选举时间',
  'desc' varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO 'party_orgnization' VALUES (1,'西安文理学院校党委',0,-1,1,NULL,NULL,NULL,NULL),(2,'机关第一党总支',0,1,1,NULL,NULL,NULL,NULL),(3,'机关第二党总支',0,1,1,NULL,NULL,NULL,NULL),(4,'机关第三党总支',0,1,1,NULL,NULL,NULL,NULL),(5,'机关第四党总支',0,1,1,NULL,NULL,NULL,NULL),(6,'机关第五党总支',0,1,1,NULL,NULL,NULL,NULL),(7,'后勤党总支',0,1,1,NULL,NULL,NULL,NULL),(8,'离退休党总支',0,1,1,NULL,NULL,NULL,NULL),(9,'教辅单位党总支',0,1,1,NULL,NULL,NULL,NULL),(10,'人文学院党总支',0,1,1,NULL,NULL,NULL,NULL),(11,'外国语学院党总支',0,1,1,NULL,NULL,NULL,NULL),(12,'机械与材料工程学院党总支',0,1,1,NULL,NULL,NULL,NULL),(13,'化学工程学院党总支',0,1,1,NULL,NULL,NULL,NULL),(14,'生物与环境工程学院党总支',0,1,1,NULL,NULL,NULL,NULL),(15,'经济管理学院党总支',0,1,1,NULL,NULL,NULL,NULL),(16,'信息工程学院党总支',0,1,1,NULL,NULL,NULL,NULL),(17,'艺术学院党总支',0,1,1,NULL,NULL,NULL,NULL),(18,'师范学院党总支',0,1,1,NULL,NULL,NULL,NULL),(19,'政治学院党总支',0,1,1,NULL,NULL,NULL,NULL),(20,'继续教育学院党总支',0,1,1,NULL,NULL,NULL,NULL),(21,'文化艺术教育中心党总支',0,1,1,NULL,NULL,NULL,NULL),(22,'服务地方研究院党总支',0,1,1,NULL,NULL,NULL,NULL),(23,'党政办党支部',0,2,0,NULL,NULL,NULL,NULL),(24,'组织部党支部',0,2,0,NULL,NULL,NULL,NULL),(25,'宣传部党支部',0,2,0,NULL,NULL,NULL,NULL),(26,'纪委党支部',0,2,0,NULL,NULL,NULL,NULL),(27,'工会党支部',0,2,0,NULL,NULL,NULL,NULL),(29,'测试的2',0,1,1,NULL,NULL,NULL,NULL),(30,'测试的11',0,28,1,NULL,NULL,NULL,NULL),(31,'测试的21',0,29,0,NULL,NULL,NULL,NULL),(37,'测试的22',0,29,0,NULL,NULL,NULL,NULL);

