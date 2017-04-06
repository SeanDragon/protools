import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.UserTypeModel;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.tools.date;
import pro.mojo.redis.dao.IRedisAdapter;
import pro.mojo.redis.dao.RedisAdapter;
import pro.mojo.redis.dao.RedisHost;
import redis.RedisTest;
import redis.TaskInterface;
import redis.TaskObject;
import redis.clients.jedis.Jedis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class testTools {


    @Test public void test_redis(){
        new RedisTest().show();
    }


    @Test
    public void test_redisconfig() throws Exception {
        System.out.println(RedisAdapter.selectHost("redis-lock").get("a"));
//        System.out.println(RedisAdapter.selectHost("redis-lock").rpop("key".getBytes()));
    }

    @Test
    public void test_insertList(){
        String[] ss = new String[]{"ss"};
        List<String> list = new ArrayList<>();
        list.add("list1");
        test_receive("abc");
    }

    public <T> void test_receive(T... args){
        for (T arg : args) {
            System.out.println(arg.toString());
        }
    }


    String getFields(String name, Object pojo, Class clazz) throws IllegalAccessException {
        Field[] fs = pojo.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Field f : fs){
            f.setAccessible(true);
            Object value = f.get(pojo);
            Annotation anno = f.getType().getAnnotation(clazz);
            if (anno != null) {
                sb.append(f.getName()+":"+(value == null ? "{}" : getFields(f.getName(), value, clazz)) + ",");
            }else{
                if (f.getType().getName().equals(String.class.getName()))
                    sb.append(f.getName() + ":\"" + (value == null ? "" : value.toString()) + "\",");
                else
                    sb.append(f.getName() + ":" + (value == null ? "null" : value.toString()) + ",");
            }
        }
        if (sb.length() > 1)
            sb.delete(sb.length()-1, sb.length());
        sb.append("}");
        return sb.toString();
    }

    @Test
    public void TestDatePro(){
        System.out.println(pro.mojo.type.DatePro.parseDate("2015-1-31", date.format.ymd).addMonth(1));
    }

    @Test
    public void ListenQueue() throws Exception {
        byte[] redisKey = "Queue:loan".getBytes();
        Jedis ju = RedisAdapter.getHost("redis-queue").getRedis();
        ju.del(redisKey);
        boolean alive = true;
        while(alive) {
            try {
                byte[] bytes = ju.rpop(redisKey);
                if (bytes == null)
                    continue;
                Object obj = pro.tools.convert.bytesToObject(bytes);
//                if (!(obj instanceof TaskInterface))
//                    continue;
                TaskInterface t1 = (TaskInterface) obj;
                System.out.println("interface: " + (obj instanceof TaskInterface));
                System.out.println("class: " + (obj instanceof TaskObject));
//                if (t1 != null)
//                    t1.execute();
            }catch (Exception e){

                e.printStackTrace();
            }

        }
    }

    @Test
    public void PushMessage() throws Exception {
        RedisAdapter ju = RedisAdapter.selectHost("redis-queue");
        while(true) {
            Thread.sleep(1000);
            byte[] redisKey = "Queue:loan".getBytes();
            TaskInterface t1 = new TaskObject(System.currentTimeMillis(), "content thread1");
            ju.lpush(redisKey, pro.tools.convert.objectToBytes(t1));
        }

    }

    @Test
    public void AddThread() throws Exception {
        RedisAdapter ju = RedisAdapter.selectHost("redis-queue");
        while(true) {
            Thread.sleep(1000);
            byte[] redisKey = "Queue:loan".getBytes();
            TaskInterface t1 = new TaskObject(System.currentTimeMillis(), "content thread2");
            ju.lpush(redisKey, pro.tools.convert.objectToBytes(t1));
        }
    }

    static volatile boolean stop = false;

    @Test
    public void test_adapterAddr() throws Exception {

//        callthread();
//        while(true);

        System.err.println(RedisAdapter.selectHost("redis-data").hostInfo("clients"));

    }

    public void callthread() throws Exception {
        IRedisAdapter adapter1 = RedisAdapter.selectHost("redis-cache");
        RedisHost rh = adapter1.getHost();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                while(!stop)
                    adapter1.get("a");
                System.err.println(adapter1.get("a"));
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                while(!stop)
                    adapter1.get("a");
                System.err.println(adapter1.get("a"));
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        Thread.sleep(2 * 1000);

        stop = true;

        t1.join();
        t2.join();
    }

    @Test
    public void test1(){
        System.err.println(pro.tools.security.MD5("admin@9819"));
    }

    @Test
    public void test2(){
        Map<String,String> map = new HashMap<>();
        map.put("a","");
        map.put("b",null);
        System.err.println(pro.tools.convert.MapToJson(map));
    }

    @Test
    public void test3(){
        Gson gson = new Gson();
        String data  = "[{\"value\":1,\"text\":\"11\",\"homePage\":1},{\"value\":2,\"text\":\"22\",\"homePage\":2}]";
//        List<UserTypeModel> userTypeModelList = pro.tools.convert.JsonToModelList(data, UserTypeModel.class);
//        System.out.println(userTypeModelList);

        List<UserTypeModel> list = pro.tools.convert.jsonToArrayList(data, UserTypeModel.class);
        for(UserTypeModel utm : list ){
            System.out.println(pro.tools.convert.ModelToBson(utm));
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T[]> clazz){
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    @Test
    public void testTime(){
        Date d = pro.tools.date.maxDate();
        System.err.println(pro.tools.date.toFormat(d));
    }
}
