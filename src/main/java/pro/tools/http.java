package pro.tools;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * HTTP 请求封装
 * Created by Administrator on 2016/3/7.
 *
 * @author Steven Duan
 * @version 1.0
 */
public final class http {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendHttpGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {

            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {

        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {

            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendHttpPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {

        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return result;
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param url     请求地址
     * @param parms   参数
     * @param charset 编码
     * @return 返回请求数据
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String sendHttpsPost(String url, String parms, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(parms.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return new String(outStream.toByteArray(), charset);
        }
        return null;
    }

    /**
     * 文件下载
     *
     * @param remotePath 需要下载的url地址
     * @param localPath  本地存放的路径和文件名
     */
    public static String download(String remotePath, String localPath) {
        // 构造URL
        URL url = null;
        InputStream is = null;
        OutputStream os = null;
        String t = "";
        try {
            url = new URL(remotePath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置Java服务器代理连接，要不然报错403
            // 浏览器可以访问此url图片并显示，但用Java程序就不行报错Server returned HTTP response code:403 for URL
            // 具体原因：服务器的安全设置不接受Java程序作为客户端访问(被屏蔽)，解决办法是设置客户端的User Agent
            con.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
            // 输入流
            is = con.getInputStream();
            t = con.getContentType();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(localPath);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (Exception e) {

            }
        }
        return t;
    }

    /**
     * 发送post请求
     *
     * @param strURL
     * @param parms
     * @return
     */
    public static <T> ResultStatus sendPostMap(String strURL, Map<String, T> parms) {
        String returncode = null;
        String result = null;
        try {
            // 构造HttpClient的实例
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
            // 创建GET方法的实例
            PostMethod postMethod = new PostMethod(strURL);
            // 使用系统提供的默认的恢复策略
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
            NameValuePair[] param = new NameValuePair[parms.size()];
            int i = 0;
            for (Map.Entry<String, T> entry : parms.entrySet()) {
                String key = entry.getKey();
                T value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        param[i] = new NameValuePair(key, (String) value);
                    } else if (value instanceof String[]) {
                        String[] ss = (String[]) value;
                        if (ss.length > 0)
                            param[i] = new NameValuePair(key, ss[0]);
                    }
                } else {
                    param[i] = new NameValuePair(key, "");
                }
                i++;
            }
            postMethod.setRequestBody(param);
            try {
                // 执行getMethod
                int statusCode = httpClient.executeMethod(postMethod);
                returncode = Integer.toString(statusCode);

                // 读取内容
                // byte[] responseBody = postMethod.getResponseBody();
                // result = postMethod.getResponseBodyAsString();
                // 处理内容
                // result = new String(responseBody, "UTF-8");
                // System.out.println(result);

                StringBuffer sb = new StringBuffer();
                InputStream in = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
                if (br != null) {
                    br.close();
                }
            } catch (HttpException e) {
                // 发生致命的异常，可能是协议不对或者返回的内容有问题

            } catch (IOException e) {
                // 发生网络异常

            } finally {
                // 释放连接
                postMethod.releaseConnection();
            }
        } catch (Exception ex) {

        }
        if (result == null) {
            result = "";
        }
        if (returncode == null) {
            returncode = "";
        }

        ResultStatus rs = new ResultStatus();
        rs.statusCode = returncode;
        rs.result = result;

        return rs;
    }

    /**
     * TrustAnyTrustManager
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        /**
         * TrustAnyTrustManager
         *
         * @param chain    X509Certificate[]
         * @param authType String
         */
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        /**
         * checkServerTrusted
         *
         * @param chain    X509Certificate[]
         * @param authType String
         */
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        /**
         * getAcceptedIssuers
         *
         * @return X509Certificate[]
         */
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        /**
         * verify
         *
         * @param hostname String
         * @param session  SSLSession
         * @return boolean
         */
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static class ResultStatus {
        public String statusCode;
        public String result;

        public ResultStatus() {
            statusCode = "";
            result = "";
        }
    }

//    /**
//     * 下载图片的主方法
//     * @param remotePath 远程地址
//     * @param localPath 本地地址
//     */
//    private void getPicture(String remotePath, String localPath) {
//        URL url = null;
//        InputStream is = null;
//        FileOutputStream fos = null;
//        try {
//            //构建图片的url地址
//            url = new URL(remotePath);
//            //开启连接
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //设置超时的时间，5000毫秒即5秒
//            conn.setConnectTimeout(5000);
//            //设置获取图片的方式为GET
//            conn.setRequestMethod("GET");
//            //响应码为200，则访问成功
//            if (conn.getResponseCode() == 200) {
//                //获取连接的输入流，这个输入流就是图片的输入流
//                is = conn.getInputStream();
//                //构建一个file对象用于存储图片
//                File file = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
//                fos = new FileOutputStream(file);
//                int len = 0;
//                byte[] buffer = new byte[1024];
//                //将输入流写入到我们定义好的文件中
//                while ((len = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len);
//                }
//                //将缓冲刷入文件
//                fos.flush();
//                //告诉handler，图片已经下载成功
//            }
//        } catch (Exception e) {
//            //告诉handler，图片已经下载失败
//            e.printStackTrace();
//        } finally {
//            //在最后，将各种流关闭
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}