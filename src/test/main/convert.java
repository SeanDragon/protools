import mongo.AccountModel;
import mongo.FastModel;
import mongo.SmallModel;
import mongo.so;
import org.bson.types.ObjectId;
import org.junit.Test;
import pro.mojo.mongo.query.QueryUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by steven on 2016/12/20.
 */
public class convert {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    public static Map ModelToMap(Object model){
        Map map = new HashMap();
        try {
            Class clazz = model.getClass();
            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                Object o = f.get(model);
                if (o != null) {
                    if (QueryUtil.isRegistry(o)) {
                        map.put(f.getName(),ModelToMap(o));
                    }
                }
                f.setAccessible(false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Test
    public void test() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        SmallModel sm = new SmallModel();
        sm.setName("a");
        sm.setDate(so.nowDate());

        List<SmallModel> slist = new LinkedList<>();
        slist.add(sm);

        List<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");

        AccountModel accountModel = new AccountModel();
        accountModel.set_id(new ObjectId());
        accountModel.setName("name");
        accountModel.setAddDate(so.nowDate());

        FastModel t = new FastModel();
        t.setName("name");
        t.set_id(new ObjectId());
        t.setCount(1234l);
        t.setState(true);
        t.setDate(so.nowDate());
        t.setList(slist);
        t.setLists(listString);

        accountModel.setFastModel(t);

        System.err.println(convertBean(t));
    }

    @Test
    public void test2(){
        String str = "abc&&123&&dlkfj";
        String[] ss = str.split("\\&&");
        for(String s : ss)
            System.err.println(s);
        System.err.println("symbol: " + "\\&&");
    }
}
