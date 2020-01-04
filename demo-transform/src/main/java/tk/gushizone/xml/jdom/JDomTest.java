package tk.gushizone.xml.jdom;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-06-22 10:27
 */
@Slf4j
public class JDomTest {

    /**
     * jDom 读入
     *
     * <pre>
     * 元素索引位: 0
     * 属性遍历
     * - id : 1
     * 通过属性名获取属性值：1
     * 元素遍历
     * - name : Foo
     * - price : 1999
     * </pre>
     */
    @Test
    public void jDomReader() throws IOException, JDOMException {

        InputStream inputStream = JDomTest.class.getClassLoader().getResourceAsStream("xmlTemplate.xml");
        assert inputStream != null;

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        // 根节点
        Element rootElement = document.getRootElement();
        // 子节点
        List<Element> elementList = rootElement.getChildren();
        for (Element element : elementList) {
            log.info("元素索引位: {}", elementList.indexOf(element));

            log.info("属性遍历");
            List<Attribute> attrs = element.getAttributes();
            for (Attribute attr : attrs) {
                log.info("- {} : {}", attr.getName(), attr.getValue());
            }

            log.info("通过属性名获取属性值：{}", element.getAttributeValue("id"));

            log.info("元素遍历");
            List<Element> childElements = element.getChildren();
            for (Element childElement : childElements) {
                log.info("- {} : {}", childElement.getName(), childElement.getValue());
            }
        }
    }

    /**
     * jDom 写出
     *
     * todo 默认转义内容， 设置转义内容
     */
    @Test
    public void jDomWriter() throws IOException {
        Element rootNode = new Element("transaction");

        Element objectNode = new Element("object");
        objectNode.setAttribute("id", "1");

        Element nameNode = new Element("name");
        nameNode.setText("Foo");
        objectNode.addContent(nameNode);

        Element yearNode = new Element("price");
        yearNode.setText("1999");
        objectNode.addContent(yearNode);

        rootNode.addContent(objectNode);
        Document document = new Document(rootNode);

        // 设置输出格式（换行缩进，字符）
        Format format = Format.getCompactFormat();
        format.setIndent("  ");
        format.setEncoding("UTF-8");

        XMLOutputter xmlOutputter = new XMLOutputter(format);
        xmlOutputter.output(document, new FileOutputStream(new File("jDomTest.xml")));

        log.info("文件创建成功：{}", new File("jDomTest.xml").getAbsolutePath());
    }

}
