package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.BeforeClass;
import org.junit.Test;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolJson;
import pro.tools.http.netty.clientpool.DefaultClientPool;
import pro.tools.http.okhttp.ToolSendHttp;
import pro.tools.http.pojo.HttpException;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 17/9/3 13:12 星期日.
 *
 * @author sd
 */
public class TestURL {

    @BeforeClass
    public static void bc() {
        map = ToolJson.jsonToMap("{\n" +
                "  \"Action\": \"2\",\n" +
                "  \"DelayTransfer\": \"\",\n" +
                "  \"LoanJsonList\": \"[{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200000\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200001\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200002\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200003\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200004\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200005\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200006\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200007\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"200.06\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200008\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"900.25\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"2017112017130299284200009\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000010\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"200.06\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000011\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000012\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000013\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000014\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000015\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000016\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000017\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"100.03\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000018\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"200.06\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"m121901\\\",\\\"OrderNo\\\":\\\"20171120171302992842000019\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"7001.94\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"},{\\\"LoanOutMoneymoremore\\\":\\\"m121906\\\",\\\"LoanInMoneymoremore\\\":\\\"p2015\\\",\\\"OrderNo\\\":\\\"20171120171302992842000020\\\",\\\"BatchNo\\\":\\\"171120_2\\\",\\\"ExchangeBatchNo\\\":\\\"\\\",\\\"AdvanceBatchNo\\\":\\\"\\\",\\\"Amount\\\":\\\"1000.0\\\",\\\"FullAmount\\\":\\\"10000.0\\\",\\\"TransferName\\\":\\\"2\\\",\\\"Remark\\\":\\\"\\\",\\\"SecondaryJsonList\\\":\\\"\\\"}]\",\n" +
                "  \"NeedAudit\": \"1\",\n" +
                "  \"NotifyURL\": \"http://47.94.168.101:83/admin/payPlatform/notify/repayNotify\",\n" +
                "  \"Remark1\": \"2\",\n" +
                "  \"Remark2\": \"84\",\n" +
                "  \"Remark3\": \"\",\n" +
                "  \"TransferAction\": \"2\",\n" +
                "  \"TransferType\": \"2\",\n" +
                "  \"orderNo\": \"20171120171302992842\"\n" +
                "}");
    }

    @Test
    public void test1() {
        String url = "https://baidu.com";
        url = "http://192.168.15.100:92/getService";
        HttpSend httpSend = new HttpSend(url);
        HttpReceive send = ToolSendHttp.send(httpSend);
        System.out.println(send);
    }

    @Test
    public void test2() {
        ByteBuf buffer = Unpooled.wrappedBuffer("123".getBytes());
        System.out.println(buffer.toString(StrConst.DEFAULT_CHARSET));
    }

    private volatile static Map map;

    @Test
    public void test3() throws HttpException {
        DefaultClientPool clientPool = new DefaultClientPool("http://192.168.15.22:8888");
        HttpSend httpSend = new HttpSend("/payPlatform/transferServlet");
        httpSend.setParams(map)
                .setMethod(HttpMethod.POST);

        HttpReceive post = clientPool.request(httpSend);

        System.out.println(post.getHaveError());
        System.out.println(post.getResponseBody());
        System.out.println(post.getErrMsg());
        System.out.println(post.getStatusCode());

        // post = ToolSendHttp.post("http://192.168.15.22:8888/payPlatform/transferServlet", map);
        //
        // System.out.println(post.getHaveError());
        // System.out.println(post.getResponseBody());
        // System.out.println(post.getErrMsg());
        // System.out.println(post.getStatusCode());
    }

    @Test
    public void test4() throws Exception {
        DefaultClientPool clientPool = new DefaultClientPool("http://localhost:9092");
        HttpSend httpSend = new HttpSend("/test");
        httpSend.setMethod(HttpMethod.POST);
        httpSend.setParams(map);

        HttpReceive post = clientPool.request(httpSend);
        //HttpReceive post = ToolSendHttp.post("http://localhost:9092/test", map);

        System.out.println(post.getHaveError());
        System.out.println(post.getResponseBody());
        System.out.println(post.getErrMsg());
        System.out.println(post.getStatusCode());
    }

    @Test
    public void test5() {
        String s = "dHKj71wpcLi8XeLO33T4Y5DoEYE3TjMPcn0tc21hNyJY4J5iOxnu2Fj3iq7xgjrkke3fYq7a6n3R\n" +
                "6w8AEQfNxICdpKGCHp6WH67eHWdvR9ae/4j3flA36yq7qEYfszXX27I+sQmVj7fnYwg8R8CQlMwJ\n" +
                "Iak4KAN03ggdJoYXpTI=\n";

        ByteBuf byteBuf = Unpooled.copiedBuffer(s.getBytes());
        System.out.println(byteBuf.toString(StrConst.DEFAULT_CHARSET));

        StringBuffer stringBuffer = new StringBuffer("123");
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
        System.out.println(substring);
    }

    @Test
    public void test6() throws Exception {

        String info = "R8Jgu2qcmomnUjeFDNnx6QQBTy6yIsQu1wFl9L7eCc4fWx3YxFSpDxZHgHg4i9FcblhWc3/L7EbZ\n" +
                "w5Nwxm9SKSc2QFSSiOY0riEL57WLjzXdgQUQrzAIrypXN4GJGgSiGQ4oDf2lv3qInWqMIdGoFHSh\n" +
                "KfmW8j80yIYzlW68fCQ=\n";

        String request = "dHKj71wpcLi8XeLO33T4Y5DoEYE3TjMPcn0tc21hNyJY4J5iOxnu2Fj3iq7xgjrkke3fYq7a6n3R\n" +
                "6w8AEQfNxICdpKGCHp6WH67eHWdvR9ae/4j3flA36yq7qEYfszXX27I+sQmVj7fnYwg8R8CQlMwJ\n" +
                "Iak4KAN03ggdJoYXpTI=\n";

        System.out.println(request);
        String decode = URLDecoder.decode(request, StrConst.DEFAULT_CHARSET_NAME);
        System.out.println(decode);
//
//        String received = "dHKj71wpcLi8XeLO33T4Y5DoEYE3TjMPcn0tc21hNyJY4J5iOxnu2Fj3iq7xgjrkke3fYq7a6n3R\n" +
//                "6w8AEQfNxICdpKGCHp6WH67eHWdvR9ae/4j3flA36yq7qEYfszXX27I sQmVj7fnYwg8R8CQlMwJ\n" +
//                "Iak4KAN03ggdJoYXpTI=\n";

        DefaultClientPool defaultClientPool = new DefaultClientPool("http://192.168.15.22:8888");

        Map<String, Object> map = new LinkedHashMap<>();
//        String info = "C9C6ALCYDmivbo+eXz8ZRnLMPtXzpFiZTZKGD+K0EoA6gocrCbuofntyzPYlLwMbEUGHeZwRXvpS\n" +
//                "xUuejkIwfCwpkUqAxZnbWwZJaWqZxJifz3kCnr2pR6gGppndU2B8xyOD7+CQAYGIfLK1zcVnWNEZ\n" +
//                "r+gZvCFp3qee38a45BE=";

//        String info = "jWcKBkO4xGnm/MCD7s9txWXgWuxTEI0DYt6HRfQUk14QMUWJTcEgmN0hlqPjdNKBGpO sb8tNgRN\n" +
//                "j5Du2K1zullre4VWd/2MezgQcvcsHJKallmhIalj iBRk868wdDy3vL36LsXNGWKmrqb32LrjhZ2\n" +
//                "Yv2L1XpfGtm0tt  oY8=\n";

//        String info = "pSTIEx+Z9Y5E6CyHwz57XBJwhfKcQWTA5IWmLzTW/xNOjID9YWwTqk3EqaCj3nUEO1E7EPstBPPj\n" +
//                "KOVJIENAEeriGdmHOCBF9miPuiT1gpDN812hzwRhFg3tGM7A7uqxG6KN3XsT95zSAsCoa4g967v6\n" +
//                "N9vbPOU6OHdffhxObuA=\n";
        map.put("data", request);
//        map.put("data", info);

//        HttpReceive post = ToolSendHttp.post("http://127.0.0.1:8888/payPlatform/balanceDecodeServlet", map);
//        System.out.println("post:" + post.getResponseBody());

        HttpSend httpSend = new HttpSend("/payPlatform/balanceDecodeServlet");
        httpSend.setParams(map).setMethod(HttpMethod.POST);
        HttpReceive post1 = defaultClientPool.request(httpSend);
        System.out.println("post1:" + post1.getResponseBody());

//        HttpReceive httpReceive = ToolHttp.sendGet("http://127.0.0.1:8888/payPlatform/balanceDecodeServlet", map);
//        System.err.println("infoResult:" + httpReceive.getResponseBody());

    }
}
