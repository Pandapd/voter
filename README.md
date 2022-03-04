# voter

:cn: Simple online vote system based on Spring Boot and Mybatis-Plus| 基于SpringBoot+Mybatis-Plus的简约投票系统

基于项目[Voter](https://github.com/adlered/Voter) 修改

[中文说明](#中文说明)

[English Version](#english-version)

# 预览 Preview

[公开测试网站 | Public Test Website](https://voter.stackoverflow.wiki/)

![DEMO](/images/Demo-Full.gif)


# 中文说明

## 运行

本项目使用Intellij IDEA构建，你可以使用IDEA直接调试本项目。

### 配置MySQL数据库

voter默认使用MySQL中的`voter`库。

```$xslt
在开始使用之前，请在你的MySQL中先创建一个名为"voter"的数据库。
```

#### 建表

> 手动建表，可以执行下列语句：

```$xslt
USE voter;

CREATE TABLE `vote_detail` (
  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '描述',
  `selection` varchar(10000) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '投票选项以及所选票数',
  `type` int(11) DEFAULT '0' COMMENT '类型',
  `limitation` int(11) DEFAULT '-1' COMMENT '限制',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '逻辑删除标记',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `voter_vote` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Pandapd', 0, 2);
```

#### 配置数据库连接

当建立完成数据库后，配置`application.properties`文件来配置数据库连接。

## 工作原理

### 实现一串字符串存储所有投票选项统计

存储在数据库中的`SelectionText`字段如下：

```$xslt
<%1<%0<%Vote for Xiaoli
<%2<%10<%Vote for Zhanghua
<%3<%20<%Vote for Ergou
<%4<%30<%Vote for Guawazi
<%5<%40<%Vote for Benwei
<%6<%50<%Vote for Pandapd
```

规定的投票存储语法如下：

```$xslt
<%选项号<%获得票数<%选项描述
```

通过`top.pandapd.voter.analyzer`中的方法对此字段实现分析与修改。

# English Version

## Run

This project is built using Intellij IDEA, you can use IDEA to debug this project directly.

### Configuring the MySQL database

voter defaults to the `voter` library in MySQL.

```$xslt
Before you start using it, create a database called "voter" in your MySQL.
```

#### Automatically generating tables

voter integrates the ability to automatically generate data tables. Editing the class `MySQL` class enables the project to automatically deploy the data table at startup.

**Note: This class is configured only to automatically generate data tables. If you want to configure read and write database connections for voter, configure the parameters in the application.properties file. **

> Please note: If you want to build a table manually, you can execute the following statement:

```$xslt
USE voter;

CREATE TABLE `vote_detail` (
  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '描述',
  `selection` varchar(10000) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '投票选项以及所选票数',
  `type` int(11) DEFAULT '0' COMMENT '类型',
  `limitation` int(11) DEFAULT '-1' COMMENT '限制',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '逻辑删除标记',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `voter_vote` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Pandapd', 0, 2);
```

#### Configuring database connections

After creating the database, configure the `application.properties` file to configure the database connection.

## working principle

### Implement a string of strings to store all voting option statistics

The `SelectionText` field stored in the database is as follows:

```$xslt
<%1<%0<%Vote for Xiaoli
<%2<%10<%Vote for Zhanghua
<%3<%20<%Vote for Ergou
<%4<%30<%Vote for Guawazi
<%5<%40<%Vote for Benwei
<%6<%50<%Vote for Pandapd
```

The prescribed voting storage syntax is as follows:

```$xslt
<% option number <% get votes <% option description
```

Analyze and modify this field by the method in `top.pandapd.voter.analyzer`.
