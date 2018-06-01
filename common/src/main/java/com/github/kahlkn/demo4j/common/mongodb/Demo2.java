package com.github.kahlkn.demo4j.common.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

public class Demo2 {

    private MongoClient client;

    @Before
    public void init() {
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        //与数据最大连接数50
        build.connectionsPerHost(50);
        //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        build.connectTimeout(60000);
        build.maxWaitTime(120000);
        MongoClientOptions options = build.build();
        client = new MongoClient(new ServerAddress("127.0.0.1", 27017), options);
    }

    @Test
    public void createCollection() {
        MongoDatabase mymg = client.getDatabase("mymg");
        mymg.createCollection("data");
        System.out.println("集合创建成功");
    }

    @Test
    public void insertDocument() {
        // 获取数据库test,不存在的话，会自动建立该数据库
        MongoDatabase mymg = client.getDatabase("mymg");

        //获取data集合，不存在的话，会自动建立该集合（相当于关系数据库中的数据表）
        MongoCollection<Document> data = mymg.getCollection("data");

        // 创建一个 document 对象
        Document document = new Document();
        document.append("name", "小明3");
        document.append("address", "上海3");
        document.append("age", "20");

        // 向 data 插入之前创建的 document 对象
        data.insertOne(document);
        // data.insertMany();

        // MongoClient使用完后必须要close释放资源
        // A database connection with internal connection pooling.
        // For most applications, you should have one Mongo instance for the entire JVM.
        // 所以在应用中，就没必要close了，除非写在 这个 bean 的析构方法中
        client.close();
    }

    @Test
    public void findAll() {
        MongoDatabase mymg = client.getDatabase("mymg");
        MongoCollection<Document> data = mymg.getCollection("data");

        FindIterable<Document> documents = data.find();
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    @Test
    public void findFilters() {
        MongoDatabase mymg = client.getDatabase("mymg");
        MongoCollection<Document> data = mymg.getCollection("data");

        // FindIterable<Document> documents = data.find(Filters.eq("name", "小明1"));
        // FindIterable<Document> documents = data.find(Filters.exists("age"));
        FindIterable<Document> documents = data.find(Filters.in("age", "20", "21"));
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    @Test
    public void updateCollection() {
        MongoDatabase mymg = client.getDatabase("mymg");
        MongoCollection<Document> data = mymg.getCollection("data");

        data.updateOne(Filters.eq("name", "小明3"), new Document("$set", new Document("age", "200")));

        FindIterable<Document> documents = data.find();
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    @Test
    public void deleteCollection() {
        MongoDatabase mymg = client.getDatabase("mymg");
        MongoCollection<Document> data = mymg.getCollection("data");

        //删除符合条件的第一个文档
//        DeleteResult one = data.deleteOne(Filters.eq("age", "200"));
//        System.out.println(one.getDeletedCount());
        DeleteResult many = data.deleteMany(Filters.in("age", "21", "22"));
        System.out.println(many.getDeletedCount());

        FindIterable<Document> documents = data.find();
        for (Document document : documents) {
            System.out.println(document);
        }
    }


}
