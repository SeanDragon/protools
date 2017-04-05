package mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.FindMany;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.mongo.dao.Page;
import pro.mojo.type.DateFromTo;

import java.util.List;

/**
 * Created by steven on 2016/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class Demo {
    private MongoAdapter adapter;

    @Before
    public void init() throws Exception {
        adapter = so.tuhaoAdapter();
    }

    @After
    public void destory(){

    }

    @Test
    public void Test_FindManyEveryEvent() {

        //定义出来一个查询实体
        AccountModel searchModel = new AccountModel();
        searchModel.setName("account");

        DateFromTo dft = null;

        String sortStr = "{}";

        //调用下面的每一个方法

        List<AccountModel> list11 = returnList(searchModel, true, true);
        List<AccountModel> list12 = returnList(searchModel, false, true);
        List<AccountModel> list13 = returnList(searchModel, true, false);
        List<AccountModel> list14 = returnList(searchModel, false, false);


        List<AccountModel> list21 = returnListByCondition(searchModel, dft, sortStr, true, true);
        List<AccountModel> list22 = returnListByCondition(searchModel, dft, sortStr, false, true);
        List<AccountModel> list23 = returnListByCondition(searchModel, dft, sortStr, true, false);
        List<AccountModel> list24 = returnListByCondition(searchModel, dft, sortStr, false, false);


        int pageIndex = 0;
        int pageCount = 1;

        Page<AccountModel> list31 = returnPage(searchModel, dft, sortStr, pageIndex, pageCount, true, true);
        Page<AccountModel> list32 = returnPage(searchModel, dft, sortStr, pageIndex, pageCount, false, true);
        Page<AccountModel> list33 = returnPage(searchModel, dft, sortStr, pageIndex, pageCount, true, false);
        Page<AccountModel> list34 = returnPage(searchModel, dft, sortStr, pageIndex, pageCount, false, false);


        System.err.println("returnList contain=true or=true\t" + list11);
        System.err.println("returnList contain=false or=true\t" + list12);
        System.err.println("returnList contain=true or=false\t" + list13);
        System.err.println("returnList contain=false or=false\t" + list14);

        System.err.println("returnListByCondition contain=true or=true\t" + list21);
        System.err.println("returnListByCondition contain=false or=true\t" + list22);
        System.err.println("returnListByCondition contain=true or=false\t" + list23);
        System.err.println("returnListByCondition contain=false or=false\t" + list24);

        System.err.println("returnPage contain=true or=true\t" + list31);
        System.err.println("returnPage contain=false or=true\t" + list32);
        System.err.println("returnPage contain=true or=false\t" + list33);
        System.err.println("returnPage contain=false or=false\t" + list34);
    }

    private List<AccountModel> returnList(AccountModel searchModel, boolean contain, boolean or) {
        try {
            FindMany findMany = adapter.findMany(searchModel);
            if (contain) findMany = findMany.contain();
            if (or) findMany = findMany.OR();
            List<AccountModel> accountModelList = findMany.getList();
            return accountModelList;
        } catch (Exception e) {
            return null;
        }
    }

    private List<AccountModel> returnListByCondition(AccountModel searchModel, DateFromTo dft, String sortStr, boolean contain, boolean or) {
        try {
            FindMany findMany = adapter.findMany(searchModel);
            if (contain) findMany = findMany.contain();
            if (or) findMany = findMany.OR();
            List<AccountModel> accountModelList = findMany.dateFromTo(dft).sort(sortStr).getList();
            return accountModelList;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    private Page<AccountModel> returnPage(AccountModel searchModel, DateFromTo dft, String sortStr, int pageIndex, int pageCount, boolean contain, boolean or) {
        try {
            FindMany findMany = adapter.findMany(searchModel);
            if (contain) findMany = findMany.contain();
            if (or) findMany = findMany.OR();
            Page<AccountModel> accountModelPage = findMany.dateFromTo(dft).sort(sortStr).page(pageIndex, pageCount);
            return accountModelPage;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

}
