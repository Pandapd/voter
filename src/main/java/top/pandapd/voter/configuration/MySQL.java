package top.pandapd.voter.configuration;

/**
 * If you form this values, SpringBoot will be check the database:
 * DATABASE NAME: voter
 * And auto create tables if not exist.
 * BUT FIRST: Fill in below values, and CREATE A DATABASE NAMED "voter", then start the project.
 * Every tables will be auto generate.
 */
public class MySQL {
    public static String IP = "";
    public static Integer Port = 0;
    public static String Username = "";
    public static String Password = "";
}
