package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.apache.commons.collections.map.HashedMap;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.mongo.dao.MongoHost;
import pro.mojo.mongo.dao.MongoManager;
import pro.mojo.mongo.dao.Page;
import pro.mojo.mongo.query.QueryUtil;
import pro.mojo.type.DateFromTo;
import pro.mojo.type.DatePro;

import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import javax.servlet.Filter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by steven on 2016/12/14.
 */
public class TestFindMany extends SpringTest{

    @Test
    public void FindMany() throws Exception {
        FastModel query = new FastModel();
        List<FastModel> list = adapter.collection("collection1").findMany("{}").getList();
        for(FastModel tm : list){
            System.err.println(pro.cg.convert.ModelToJson(tm));
        }
    }

    @Test
    public void FindPage() throws Exception {
        AccountModel queryModel = new AccountModel();
        queryModel.setName("name");

        DateFromTo dft = new DateFromTo("addDate");
        dft.setFrom(new DatePro().addDay(-10));

        Page<String> page = adapter.collection("AccountModel").findMany("{name:'name'}").contain().dateFromTo(dft).page(0,10);

        System.err.println(page);

        for(String tm : page.getList()){
            System.err.println(tm);
        }
    }

    @Test
    public void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("_id", new ObjectId());
        map.put("name","name");
        map.put("count",1234);
        FastModel query = new FastModel();
        pro.cg.convert.MapToModel(map,query.getClass());
        System.err.println(pro.cg.convert.ModelToJson(query));
    }

    @Test
    public void TestFindCount() throws Exception {
        DateFromTo dft = new DateFromTo("date");
        dft.setFrom(new DatePro().toMaxDate().addHour(-10));
//        System.err.println(dft.toJson());
        MongoHost host = new MongoHost(adapter, MongoManager.getMongoConfig(adapter.getId()));
        MongoCollection<Document> collection = host.getDatabase().getCollection("collection1");
//        FindIterable<Document> finder = collection.find(new Document()).filter(and(gte(dft.getName(),dft.getFrom().getDate()),lte(dft.getName(),dft.getTo().getDate())));
        FindIterable<Document> finder = collection.find(and(gte(dft.getName(),dft.getFrom().getDate()),lte(dft.getName(),dft.getTo().getDate()),gt("count",1234)));
        Bson bson =  and(gte(dft.getName(),dft.getFrom().getDate()),lte(dft.getName(),dft.getTo().getDate()),eq("count",1234),eq("name","name0"));

        Bson document = Document.parse( bson.toBsonDocument(bson.getClass(), QueryUtil.createCodecRegistry()).toJson());

        System.err.println(bson instanceof Filter);
        System.err.println(document instanceof  Filter);
        MongoCursor<Document> cursor = finder.iterator();
        while (cursor.hasNext())
            System.out.println(cursor.next());
    }
}
