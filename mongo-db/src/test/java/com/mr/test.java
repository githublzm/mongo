package com.mr;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import jdk.nashorn.internal.objects.annotations.Where;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Filter;

/**
 * Created by DELL on 2019/4/10.
 */
public class test {
    /**
     * 添加一条数据
     */
    @Test
    public void test(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //创建一个文档
        Document document = new Document();
        //文档中添加内容
        document.append("name","reha");
        document.append("age",18);
        //添加一个文档到集合中
        car.insertOne(document);
        //关闭连接 释放资源
        client.close();
    }

    /**
     * 添加多条数据
     */
    @Test
    public void test1(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //创建文档
        Document d1 = new Document();
        d1.append("name","reha1");
        d1.append("age",19);
        Document d2 = new Document();
        d2.append("name","宝马X8");
        d2.append("age",20);
        List<Document> list = Arrays.asList(d1, d2);
        car.insertMany(list);
        //关闭连接释放资源
        client.close();
    }

    /**
     * 查询集合文档所有文档
     */
    @Test
    public void test2(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //查询所有文档
        FindIterable<Document> cars = car.find();
        for (Document c:cars) {
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("age"));
        }
        client.close();
    }

    /**
     * 分页查询
     */
    @Test
    public void test3(){
        int page =1;
        int rows =2;
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //分页查询文档
        FindIterable<Document> cars = car.find().skip((page - 1) * rows).limit(rows);
        for (Document c:cars) {
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("age"));
        }
        client.close();
    }

    /**
     * 单一条件查询符合的文档
     */
    @Test
    public void test4(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //查询所有符合条件的文档
        FindIterable<Document> cars = car.find(Filters.eq("age", 18));
        for (Document c:cars) {
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("age"));
        }
        client.close();
    }

    /**
     * 多件查询符合的文档
     */
    @Test
    public void test5(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //创建符合条件 where name="reha" and age<16
        Bson where = Filters.and(Filters.eq("name", "reha"), Filters.lt("age", 19));
        //查询所有符合条件的文档
        FindIterable<Document> cars = car.find(where);
        //遍历所有文档
        for (Document c:cars) {
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("age"));
        }
        client.close();
    }


    /**
     * 修改符合条件的文档
     */
    @Test
    public void test6(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //修改符合条件的第一条数据
        car.updateOne(Filters.eq("name", "reha1"), new Document("$set", new Document("name", "reha")));
        //修改符合条件的所有数据
        car.updateMany(Filters.eq("name","reha"),new Document("$set",new Document("age","222")));
        client.close();
    }

    /**
     * 删除符合条件的文档
     */
    @Test
    public void test7(){
        //创建连接，如果用的本地路径和默认端口可以直接new mongoClient()
        MongoClient  client=new MongoClient("localhost",27017);
        //获得一个库
        MongoDatabase mr = client.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mr.getCollection("car");
        //删除符合条件的第一条数据
        car.deleteOne(Filters.eq("name","宝马X8"));
        //删除符合条件的所有数据
        car.deleteMany(Filters.eq("name","reha"));
        client.close();
    }
}
