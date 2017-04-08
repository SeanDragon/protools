package pro.tools.format;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml 格式化
 */
public abstract class ToolFormatXml {

    public static void main(String[] args) {
        String xml = "<xml>";
        xml += "<URL><![CDATA[http://littleant.duapp.com/msg]]></URL>";
        xml += "<ToUserName><![CDATA[jiu_guang]]></ToUserName>";
        xml += "<FromUserName><![CDATA[dongcb678]]></FromUserName>";
        xml += "<CreateTime>11</CreateTime>";
        xml += "<MsgType><![CDATA[text\\//]]></MsgType>";
        xml += "<Content><![CDATA[wentest]]></Content>";
        xml += "<MsgId>11</MsgId>";
        xml += "</xml>";
        System.out.println(formatXML(xml));
    }

    public static String formatXML(String inputXML) {
        String requestXML = null;
        Document document = null;

        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new StringReader(inputXML));
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }

        if (document != null) {
            XMLWriter writer = null;
            try {
                StringWriter stringWriter = new StringWriter();
                OutputFormat format = new OutputFormat("    ", true);
                writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                requestXML = stringWriter.getBuffer().toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return requestXML;
    }

}
