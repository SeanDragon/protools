package pro.tools.data.binary;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * XML工具类
 *
 * @author SeanDragon
 */
public final class ToolXml {

    private ToolXml() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取XStream对象
     *
     * @return
     */
    public static XStream getXStream() {
        //在文本前后加上<![CDATA[和]]>
        DomDriver domDriver = new DomDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith("<![CDATA[") && text.endsWith("]]>")) {
                            writer.write(text);
                        } else {
                            //super.writeText(writer, text);
                            super.writeText(writer, "<![CDATA[" + text + "]]>");
                        }
                    }
                };
            }
        };

        //去除XML属性在JavaBean中映射不到属性值的异常
        XStream xStream = new XStream(domDriver) {
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @SuppressWarnings("rawtypes")
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            try {
                                return this.realClass(fieldName) != null;
                            } catch (Exception e) {
                                return false;
                            }
                        } else {
                            return super.shouldSerializeMember(definedIn, fieldName);
                        }
                    }
                };
            }
        };

        return xStream;
    }

    /**
     * 获取xml一级节点文本值，不区分元素名称大小写
     *
     * @param xml
     * @param elementName
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getStairText(String xml, String elementName) throws DocumentException {
        elementName = elementName.toLowerCase();
        String result = null;
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        for (Iterator elementIterator = root.elementIterator(); elementIterator.hasNext(); ) {
            Element element = (Element) elementIterator.next();
            if (element.getName().toLowerCase().equals(elementName)) {
                result = element.getText();
            }
        }
        return result;
    }

    /**
     * 把xml转bean对象
     *
     * @param xml
     * @param map
     * @return
     */
    public static Object xmlToBean(String xml, Map<String, Class<?>> map) {
        XStream xStream = getXStream();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            xStream.alias(key, map.get(key));
        }
        return xStream.fromXML(xml);
    }

    /**
     * bean对象转xml
     *
     * @param bean
     * @param rootClass 根节点名称转换
     * @return
     */
    public static String beanToXml(Object bean, Class<?> rootClass) {
        XStream xStream = getXStream();
        xStream.alias("xml", rootClass);
        String content = xStream.toXML(bean);
        content = content.replaceAll("&lt;", "<");// <
        content = content.replaceAll("&gt;", ">");// >
        return content;
    }

}