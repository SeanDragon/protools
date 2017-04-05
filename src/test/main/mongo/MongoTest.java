package mongo;

import com.mongodb.Block;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by steven on 2016/12/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class MongoTest {
    @Test
    public void Test_ReadOperations(){
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };
        Document query = new Document();
        query.put("userName","user0");
    }

    public class testclass{
        public void start() throws Exception {
            MongoAdapter adapter = new MongoAdapter("");
            MongoDatabase database = null;

            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    while(true){
//                    System.out.println(MongoAdapter.getHost("tuhao-p2p").getMongoDB("tuhao").getCollection("account").count());
                        System.out.println("thread1: " + database.getCollection("users").count());
                    }
                }
            };
            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    while(true){
//                    System.out.println(MongoAdapter.getHost("tuhao-p2p").getMongoDB("tuhao").getCollection("account").count());
                        System.out.println("thread2: " + database.getCollection("users").count());
                    }
                }
            };
            Runnable r3 = new Runnable() {
                @Override
                public void run() {
                    while(true){
//                    System.out.println(MongoAdapter.getHost("tuhao-p2p").getMongoDB("tuhao").getCollection("account").count());
                        System.out.println("thread3: " + database.getCollection("users").count());
                    }
                }
            };
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            Thread t3 = new Thread(r3);

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
        }
    }
}
