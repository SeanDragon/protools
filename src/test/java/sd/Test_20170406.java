package sd;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.uri.Uri;
import org.junit.Test;

import java.util.concurrent.Future;


/**
 * 测试
 *
 * @author SeanDragon
 *         Create By 2017-04-06 16:38
 */
public class Test_20170406 {
    @Test
    public void test1() {
        System.out.println("Test_20170406.test1");
    }

    @Test
    public void test2() {
        ImmutableSet set = ImmutableSet.of(1, "2", 3L, 4.0F, 5.00D);
        set.forEach(one -> {
            System.out.println(one);
        });
    }

    @Test
    public void test3() {
        Multiset<Integer> multiset = HashMultiset.create();
        multiset.add(1);
        multiset.add(2);
        multiset.add(3);
        multiset.add(4);
        multiset.add(2);

        int count = multiset.count(2);
        System.out.println(count);
    }

    public static void main(String[] a) throws Exception {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> f = asyncHttpClient.preparePost("http://m.tuhaolicai.cc/public/index").execute();
        Response r = f.get();
        int statusCode = r.getStatusCode();
        String statusText = r.getStatusText();
        Uri uri = r.getUri();
        String responseBody = r.getResponseBody();
        System.out.println("Test_20170406.main");
        //System.out.println(responseBody);
        System.out.println(statusCode);
        System.out.println(statusText);
        System.out.println(uri.toRelativeUrl());
        System.out.println(uri.toUrl());
        System.out.println(r.isRedirected());
    }
}
