package mongo;

import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.redis.time.TimeClient;

import java.util.Date;

/**
 * Created by steven on 16/12/10.
 */
public class so {
    public static MongoAdapter tuhaoAdapter() throws Exception {
        return new MongoAdapter("tuhao-p2p").database("tuhao");
    }

    public static Date nowDate(){
        return TimeClient.timeClient.getDate();
    }
}
