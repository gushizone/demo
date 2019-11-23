package tk.gushizone.mongodb.test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

/**
 * mongodb-driver 演示
 *
 * @author gushizone@gmail.com
 * @date 2019-10-20 16:55
 */
@RunWith(JUnit4.class)
public class MongodbDriverTest {

    private MongoClient client;

    private MongoCollection<Document> testc;

    @Before
    public void before() {
        // 链接
        client = new MongoClient("127.0.0.1");
        // 获得数据库
        MongoDatabase db = client.getDatabase("testdb");
        // 获得集合
        testc = db.getCollection("testc");
    }

    @After
    public void after() {
        client.close();
    }


    /**
     * 测试文档
     */
    @Test
    public void testDocument(){

        // 获取文档
        FindIterable<Document> documents = testc.find();

        // 遍历文档数据
        for (Document document : documents) {
            for (Map.Entry<String, Object> entry : document.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }

        // 遍历文档数据
        for (Document document : documents) {
            System.out.println(document);
            System.out.println(document.getString("name"));
            System.out.println(document.getDouble("version"));
        }
    }

    /**
     * 删除数据、添加数据
     *
     * insert({"_id":"1", "name":"测试数据", "version":1})
     */
    @Test
    public void testInert(){
        // 删除数据
        BasicDBObject bson = new BasicDBObject("_id", "1");
        testc.deleteOne(bson);

        Map<String, Object> map = new HashMap<>();
        map.put("_id", "1");
        map.put("name", "测试数据");
        map.put("version", 1);
        Document document = new Document(map);
        // 添加数据
        testc.insertOne(document);
    }

    /**
     * 简单查询
     *
     * 查询 id 为 1
     * find("_id", "1")
     */
    @Test
    public void testFind1(){
        // 封装条件
        BasicDBObject bson = new BasicDBObject("_id", "1");

        // 获取文档
        FindIterable<Document> documents = testc.find(bson);

        for (Document document : documents) {
            System.out.println(document);
        }

    }

    /**
     * 复杂查询
     *
     * 查询 version 大于 0
     * find("version":{$gt:0})
     */
    @Test
    public void testFind2(){
        // 封装条件
        BasicDBObject bson = new BasicDBObject("version", new BasicDBObject("$gt", 0));

        // 获取文档
        FindIterable<Document> documents = testc.find(bson);

        for (Document document : documents) {
            System.out.println(document);
        }
    }


}
