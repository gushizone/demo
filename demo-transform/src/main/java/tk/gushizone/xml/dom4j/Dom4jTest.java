package tk.gushizone.xml.dom4j;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import tk.gushizone.xml.jdom.JDomTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-06-22 11:00
 */
@Slf4j
public class Dom4jTest {

    /**
     * dom4j 读入
     *
     * <pre>
     * 通过元素名获取 object 对象
     * 属性遍历
     * - id : 1
     * 通过属性名获取属性: 1
     * 元素遍历
     * - name : Foo
     * - price : 1999
     * </pre>
     */
    @Test
    public void dom4jReader() throws DocumentException {

        InputStream inputStream = JDomTest.class.getClassLoader().getResourceAsStream("xmlTemplate.xml");
        assert inputStream != null;

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 根节点
        Element rootElement = document.getRootElement();
        // 子节点（此处效果等同以下）
        // Iterator<Element> iterator = rootElement.elementIterator();
        // 元素：通过元素名获取
        log.info("通过元素名获取 object 对象");
        Iterator<Element> object = rootElement.elementIterator("object");
        while (object.hasNext()) {
            Element element = object.next();

            // 属性：遍历
            log.info("属性遍历");
            List<Attribute> attrList = element.attributes();
            for (Attribute attribute : attrList) {
                log.info("- {} : {}", attribute.getName(), attribute.getValue());
            }
            // 属性：通过属性名获取
            log.info("通过属性名获取属性: {}", element.attributeValue("id"));

            // 元素：遍历
            log.info("元素遍历");
            Iterator<Element> childIter = element.elementIterator();
            while (childIter.hasNext()) {
                Element childElement = childIter.next();
                log.info("- {} : {}", childElement.getName(), childElement.getStringValue());
            }
        }
    }

    /**
     * dom4j 写出
     */
    @Test
    public void dom4jWriter() {
        Document document = DocumentHelper.createDocument();
        Element rootNode = document.addElement("transaction");

        Element objectNode = rootNode.addElement("object");
        objectNode.addAttribute("id", "1");
        Element nameNode = objectNode.addElement("name");
        nameNode.setText("Foo");
        Element yearNode = objectNode.addElement("price");
        yearNode.setText("1999");

        // 设置输出格式 （换行缩进，字符）
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("Dom4jTest.xml"), format);
            // 设置为不转义 （默认转义）
            // writer.setEscapeText(false);
            writer.write(document);
            log.info("文件创建成功：{}", new File("Dom4jTest.xml").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
