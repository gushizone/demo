package tk.gushizone.xml.jdk.sax;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gushizone@gmail.com
 * @date 2019-06-22 08:40
 */
@Slf4j
public class XmlSaxTest {

    /**
     * sax 读入
     *
     * <pre>
     * 过属性名获取属性：1
     * 属性遍历
     * - id : 1
     * 理想状态的节点值：Foo
     * name:Foo
     * 理想状态的节点值：1999
     * price:1999
     * </pre>
     */
    @Test
    public void saxReader() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        InputStream inputStream = XmlSaxTest.class.getClassLoader().getResourceAsStream("xmlTemplate.xml");
        assert inputStream != null;
        saxParser.parse(inputStream, new SaxParserHandler());
    }

    /**
     * sax 写出
     */
    @Test
    public void saxWriter() throws TransformerConfigurationException, SAXException {
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        // 输出设置 （编码，换行）
        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Result result = new StreamResult(new File("saxTest.xml"));
        // FIXME 配置需要在此之前
        handler.setResult(result);

        // start 必须在 setResult 之后
        handler.startDocument();
        AttributesImpl attr = new AttributesImpl();
        attr.addAttribute("", "", "id", "", "1");
        handler.startElement("", "", "transaction", attr);
        attr.clear();

        handler.startElement("", "", "name", attr);
        handler.characters("Foo".toCharArray(), 0, "Foo".length());
        handler.endElement("", "", "name");

        handler.startElement("", "", "price", null);
        handler.characters("1999".toCharArray(), 0, "1999".length());
        handler.endElement("", "", "price");

        handler.endElement("", "", "transaction");
        handler.endDocument();
    }


}
