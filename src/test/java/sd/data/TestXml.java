package sd.data;

import org.junit.Test;

/**
 * @author SeanDragon
 *         Create By 2017-04-10 10:08
 */
public class TestXml {

    @Test
    public void test1() {
        //String xml = "<xml>";
        //xml += "<URL><![CDATA[http://littleant.duapp.com/msg]]></URL>";
        //xml += "<ToUserName><![CDATA[jiu_guang]]></ToUserName>";
        //xml += "<FromUserName><![CDATA[dongcb678]]></FromUserName>";
        //xml += "<CreateTime>11</CreateTime>";
        //xml += "<MsgType><![CDATA[text\\//]]></MsgType>";
        //xml += "<Content><![CDATA[wentest]]></Content>";
        //xml += "<MsgId>11</MsgId>";
        //xml += "</xml>";
        //
        //Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        //map.put("xml", RecevieMsgText.class);
        //
        //RecevieMsgText recevie = (RecevieMsgText) xmlToBean(xml, map);
        //
        //System.out.println(beanToXml(recevie, RecevieMsgText.class));
    }

    @Test
    public void test2() {

        String xml = "<xml>";
        xml += "<URL><![CDATA[http://littleant.duapp.com/msg]]></URL>";
        xml += "<ToUserName><![CDATA[jiu_guang]]></ToUserName>";
        xml += "<FromUserName><![CDATA[dongcb678]]></FromUserName>";
        xml += "<CreateTime>11</CreateTime>";
        xml += "<MsgType><![CDATA[text\\//]]></MsgType>";
        xml += "<Content><![CDATA[wentest]]></Content>";
        xml += "<MsgId>11</MsgId>";
        xml += "</xml>";
        System.out.println(ToolFormatXml.formatXML(xml));
    }

}
