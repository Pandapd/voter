package top.pandapd.voter.analyzer;

import top.pandapd.voter.configuration.MySQL;

import javax.annotation.PostConstruct;
import java.sql.*;

//@Controller
// 去除数据检查
public class DatabaseCheck {
    @PostConstruct
    public void DatabaseCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String IP = "jdbc:mysql://" + MySQL.IP + ":" + MySQL.Port + "/voter?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true";
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection(IP, MySQL.Username, MySQL.Password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM vote_detail");
            } catch (SQLException SQLE) {
                System.out.println("[TIP] Did you configured the MySQL properties on CLASS \"top.pandapd.voter.configuration.MySQL\" ? If you didn't create database and tables for voter before, check the CLASS.");
            }
            try {
                while (resultSet.next()) {
                    resultSet.getString("VID");
                }
            } catch (SQLException SQLE) {
                SQLE.printStackTrace();
            } catch (NullPointerException NPE) {
                System.out.println("[VOTER] AUTO-MAKING Tables on MySQL Database ..");
                try {
                    String SQL = "CREATE TABLE `vote_detail` (\n" +
                        "  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',\n" +
                        "  `title` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '标题',\n" +
                        "  `description` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '描述',\n" +
                        "  `selection` varchar(10000) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '投票选项以及所选票数',\n" +
                        "  `type` int(11) DEFAULT '0' COMMENT '类型',\n" +
                        "  `limitation` int(11) DEFAULT '-1' COMMENT '限制',\n" +
                        "  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '逻辑删除标记',\n" +
                        "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                        "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                        "  PRIMARY KEY (`VID`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
                    statement.executeUpdate(SQL);
                    SQL = "INSERT INTO `vote_detail` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Pandapd', 0, 2);";
                    statement.executeUpdate(SQL);
                    System.out.println("[voter] It seems like successful to create default tables. You should check your Database and access the default vote on page \"/vote/1\"");
                } catch (NullPointerException NPE2) {
                    System.out.println("[voter] Create table failed! Please create a database named \"voter\" first!");
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException SQLE) {
                System.out.println("[voter] Did you configured the MySQL properties on CLASS \"top.pandapd.voter.configuration.MySQL\" ? If you didn't create database and tables for voter before, check the CLASS.");
            } catch (NullPointerException NPE) {
            }
        } catch (ClassNotFoundException CNFE) {
            CNFE.printStackTrace();
        }
    }
}
