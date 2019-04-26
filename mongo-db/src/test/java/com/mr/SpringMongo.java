package com.mr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2019/4/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mongo.xml")
public class SpringMongo {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增一条数据
     */
    @Test
    public void test1(){
        Car c =new Car();
        c.setName("reha");
        c.setAge(14);
        c.setBir(new Date());
        mongoTemplate.save(c);
    }

    /**
     * 查询所有数据
     */
    @Test
    public void test2(){
        List<Car> cars = mongoTemplate.find(null, Car.class);
        System.out.println(cars);
    }

    /**
     * 查询单条数据
     */
    @Test
    public void test3(){
        Query query = new Query();
        Criteria where = new Criteria();
        where.and("age").gt(14);
        query.addCriteria(where);
        List<Car> cars = mongoTemplate.find(query, Car.class);
        System.out.println(cars);
    }

    /**
     * 多条件查询
     */
    @Test
    public void test4(){
        Query query = new Query();
        Criteria where = new Criteria();
        //where.andOperator(new Criteria().and("age").gt(14),new Criteria().and("age").lt(17));
        where.orOperator(new Criteria().and("age").gt(17),new Criteria().and("age").lt(14));
        query.addCriteria(where);
        List<Car> cars = mongoTemplate.find(query, Car.class);
        System.out.println(cars);
    }

    /**
     * 分页查询
     */
    @Test
    public void test5(){
        int page =1;//查询页数
        int rows =2;//没页显示条数
        Query query = new Query();
        Criteria where = new Criteria();
        where.and("age").gt(1);
        query.addCriteria(where).skip((page-1)*rows).limit(rows);
        List<Car> cars = mongoTemplate.find(query, Car.class);
        System.out.println(cars);
    }

    /**
     * 修改数据
     */
    @Test
    public void test6(){
        //修改条件
        Query query = new Query();
        Criteria where = new Criteria();
        where.and("name").is("reha");
        query.addCriteria(where);

        Update update = new Update();
        update.set("age",12);//将age的值改为12，其他字段不变
        //在原有的值加1 update.inc("age",1);
        //改多个字段 update.set("age",7).set("createDate",new Date())
        mongoTemplate.updateMulti(query,update,Car.class);
    }

    /**
     * 删除数据
     */
    @Test
    public void test7(){
        //修改条件
        Query query = new Query();
        Criteria where = new Criteria();
        where.and("name").is("reha");
        query.addCriteria(where);

        mongoTemplate.remove(query,Car.class);//删除名字为reha
    }
}
