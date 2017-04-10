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
 *
 * @author SeanDragon
 */
public final class ToolFormatXml {


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
