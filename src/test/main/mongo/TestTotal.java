package mongo;

import org.junit.Test;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by steven on 2016/12/15.
 */
public class TestTotal extends SpringTest{

    @Test
    public void TotalCount(){
//        System.err.println(adapter.collection("collection1").count("{}"));

//        String fieldName = "field";
//        Total total = new Total(adapter.collection("AccountModel"),"{}",new QueryFactory());
//        Document document = new Document();
//        document.append("_id",null);
//        document.append("total",new Document("$sum","$"+fieldName));
////        System.err.println(group(document).toBsonDocument(null, QueryUtil.createCodecRegistry()).toJson());
//        System.err.println(new Document("$group",document).toJson());

        adapter.total(new AccountModel(),"fastModel.count");

        long d1 = System.currentTimeMillis();
        System.err.println(adapter.total(new AccountModel(),"asdf"));
        System.err.println(System.currentTimeMillis() - d1);
    }

    @Test
    public void test(){
//        String str = "[{ \"id\" : 1, \"checkType\" : \"初审\", \"checkResult\" : \"初审已通过\", \"date\" : { \"$date\" : 1478569638394 }, \"userName\" : \"SeanDragon\", \"checkNote\" : \"2016年11月8日, AM 09:47:14\" }]";
//        System.err.println(str);
//        System.err.println(pro.tools.convert.BsonToModelList(str,TestModel.class));
        System.err.println(pro.tools.format.isPhoneNumber("1234123412"));
    }
}
