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
}
