package com.apyhs.demo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    @Test
    public void test1() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("mymg");
            System.out.println("Connect to database successfully");
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("localhost",27017);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);

            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient(addrs, credentials);

            //连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");
            System.out.println("Connect to database successfully");
        }
        catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @Test
    public void test3() {
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        //与数据最大连接数50
        build.connectionsPerHost(50);
        //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        build.connectTimeout(60000);
        build.maxWaitTime(120000);
        MongoClientOptions options = build.build();
        MongoClient client = new MongoClient(new ServerAddress("127.0.0.1", 27017), options);

        MongoDatabase mongoDatabase = client.getDatabase("mymg");
        System.out.println("Connect to database successfully");

        client.close();
    }

}
