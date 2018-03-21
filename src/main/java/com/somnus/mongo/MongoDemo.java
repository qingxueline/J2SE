package com.somnus.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;

public class MongoDemo {
    private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Rule
    public TestName name = new TestName();
    
    /** 
     * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可 
     * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo 
     */  
    private MongoClient mongoClient;  
    
    @Before
    public void setUp() throws Exception {
        log.info("调用测试方法: {}", new Object[]{name.getMethodName()});
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();          
        build.connectionsPerHost(50);   //与目标数据库能够建立的最大connection数量为50  
        build.threadsAllowedToBlockForConnectionMultiplier(50); //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待  
        /* 
         * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟 
         * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception 
         * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败 
         */  
        build.maxWaitTime(1000*60*2);  
        build.connectTimeout(1000*60*1);    //与数据库建立连接的timeout设置为1分钟  
          
        MongoClientOptions myOptions = build.build();         
        try {  
            //数据库连接实例  
            mongoClient = new MongoClient("127.0.0.1", myOptions);            
        } catch (UnknownHostException e) {  
            log.error(e.getMessage(), e);
            throw e;
        } catch (MongoException e){  
            log.error(e.getMessage(), e);
            throw e;  
        }  
    }
    
    @Test
    public void createCollection() {
        mongoClient.getDB("somnus").createCollection("xx", new BasicDBObject());
    }

    @Test
    public void insertOne() {
        // 1.得到集合
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        DBObject dbo = new BasicDBObject();
        dbo.put("name", "somnus");
        dbo.put("age", 2);
        List<String> books = new ArrayList<String>();
        books.add("EXTJS");
        books.add("MONGODB");
        dbo.put("books", books);

        // 2.插入操作
        System.out.println(coll.insert(dbo));
    }

    @Test
    public void insertBatch() {
        // 1.得到集合
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        List<DBObject> dbObjects = new ArrayList<DBObject>();
        DBObject dbo1 = new BasicDBObject("name", "jim");
        DBObject dbo2 = new BasicDBObject("name", "lisi");
        dbObjects.add(dbo1);
        dbObjects.add(dbo2);

        // 2.插入操作
        System.out.println(coll.insert(dbObjects).getN());
    }

    @Test
    public void remove() {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        DBObject dbo = new BasicDBObject("_id", new ObjectId("53200aeedb42a37bd666c75e"));

        System.out.println(coll.remove(dbo).getN());
    }

    @Test
    public void remove2() {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        DBObject dbo = new BasicDBObject();
        dbo.put("name", "lisi");

        System.out.println(coll.remove(dbo).getN());
    }

    @Test
    public void update() {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        DBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("email", "uspcat@126.com"));
        /*
         * db.users.update({age: 25}, {$set: {email: 'uspcat@126.com'}}, false,
         * true); 相当于：update person set email = 'uspcat@126.com’ where age = 25;
         */
        System.out.println(coll.update(new BasicDBObject("age", 25), update, false, true).getN());
        // update的第三个参数设置为true，存在则更新，不存在则插入。
        // update的第四个参数设置为true，则批量更新搜索到的所有记录
    }

    @Test
    public void find()// 查询出persons集合中的name和age
    {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        DBObject keys = new BasicDBObject();
        keys.put("_id", false);
        keys.put("name", true);
        keys.put("age", true);
        keys.put("email", true);

        DBCursor cursor = coll.find(null, keys).sort(new BasicDBObject("age", -1));
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object.get("name"));
            System.out.println(object.get("age"));
            System.out.println(object.get("email"));
            System.out.println("-------------------------");
        }
    }

    @Test
    public void find2() {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");

        /*
         * 查询age = 25的记录 db.persons.find({"age": 25});
         */
        DBObject query1 = new BasicDBObject("age", 25);
        DBCursor cursor1 = coll.find(query1, null);
        while (cursor1.hasNext()) {
            DBObject object = cursor1.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("*******************************查询age = 25的记录*****************************************");
        /*
         * 查询age > 25的记录 db.persons.find({age: {$gt: 22}});
         */
        DBObject query2 = new BasicDBObject();
        query2.put("age", new BasicDBObject("$gt", 25));// $lt[<] $gte[>=]
                                                        // $lte[<=]
        DBCursor cursor2 = coll.find(query2, null);
        while (cursor2.hasNext()) {
            DBObject object = cursor2.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("****************************查询age > 25的记录********************************************");
        /*
         * 查询age >= 23 并且 age <= 26 并且英语成绩及格 db.persons.find({age: {$gte: 23,
         * $lte: 26},e:{$gte:60}});
         */
        BasicDBObject result = new BasicDBObject();
        result.put("$gte", 23);
        result.put("$lte", 26);
        BasicDBObject query22 = new BasicDBObject();
        query22.put("age", result);
        query22.put("e", new BasicDBObject("$gte", 60));
        DBCursor cursor22 = coll.find(query22, null);
        while (cursor22.hasNext()) {
            DBObject object = cursor22.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("*******************************查询age >= 23 并且 age <= 26 并且英语成绩及格*****************************************");
        /*
         * 查询age < 25 或者 age >= 27 db.persons.find({$or: [{age: 27}, {age:
         * 25}]});
         */
        BasicDBObject result1 = new BasicDBObject("age", new BasicDBObject("$gte", 27));
        BasicDBObject result2 = new BasicDBObject("age", new BasicDBObject("$lt", 25));
        BasicDBList values = new BasicDBList();
        values.add(result1);
        values.add(result2);
        BasicDBObject query33 = new BasicDBObject();
        query33.put("$or", values);
        DBCursor cursor33 = coll.find(query33, null);
        while (cursor33.hasNext()) {
            DBObject object = cursor33.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("****************************** 查询age < 25 或者 age >= 27******************************************");
        /*
         * 查询name中以zh开头的数据 db.persons.find({name: /li/});
         */
        DBObject query3 = new BasicDBObject();
        Pattern pattern = Pattern.compile("^zh", Pattern.CASE_INSENSITIVE);
        query3.put("name", pattern);
        DBCursor cursor3 = coll.find(query3, null);
        while (cursor3.hasNext()) {
            DBObject object = cursor3.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("*****************************查询name中以zh开头的数据*******************************************");
        /*
         * 查询name = zhangsan, age > 22的数据 db.persons.find({name: 'zhangsan',
         * age: {$gt: 22}});
         */
        DBObject query4 = new BasicDBObject();
        query4.put("age", new BasicDBObject("$gt", 22));
        query4.put("name", "jim");
        DBCursor cursor4 = coll.find(query4, null);
        while (cursor4.hasNext()) {
            DBObject object = cursor4.next();
            System.out.print(object.get("name") + "-->");
            System.out.print(object.get("age") + "-->");
            System.out.println(object.get("e"));
            System.out.println("-------------------------");
        }
        System.out.println("***************************查询name = zhangsan, age > 22的数据*********************************************");

    }

    @Test
    public void findByPage() {
        DBCollection coll = mongoClient.getDB("somnus").getCollection("persons");
        /*
         * 查询前5条数据 db.persons.find().limit(5); 查询10条以后的数据
         * db.persons.find().skip(10); 查询在5-10之间的数据
         * db.persons.find().limit(10).skip(5);
         * limit是pageSize，skip是(N页)*pageSize
         */
        DBCursor cursor = coll.find(null, null).limit(5).skip(0);
        System.out.println(coll.find(null, null).length());
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object.get("name"));
            System.out.println(object.get("age"));
            System.out.println(object.get("email"));
            System.out.println("-------------------------");
        }
    }

}
