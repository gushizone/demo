package tk.gushizone.xml.jdk.dom;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gushizone@gmail.com
 * @date 2019-06-22 08:40
 */
@Slf4j
public class XmlDomTest {

    /**
     * dom 读入
     *
     * 节点类型
     * 1. Element  元素（节点）
     * 2. Attr 属性
     * 3. Text 节点内容 （标签和标签之间）
     *
     * <pre>
     * 属性遍历
     * - id : 1
     * 通过属性名获取（有且仅有一个）: 1
     * 子节点遍历
     * - name
     * 非期望节点获取：null
     * 期望节点获取：Foo
     * 包含子节点文本内容: Foo
     * - price
     * 非期望节点获取：null
     * 期望节点获取：1999
     * 包含子节点文本内容: 1999
     * </pre>
     */
    @Test
    public void domReader() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 解析文件
        InputStream inputStream = XmlDomTest.class.getClassLoader().getResourceAsStream("xmlTemplate.xml");
        assert inputStream != null;
        Document document = db.parse(inputStream);

        // 元素节点：通过节点名获取
        NodeList nodeList = document.getElementsByTagName("object");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            // 属性：获取所有属性
            log.info("属性遍历");
            NamedNodeMap attrs = node.getAttributes();
            for (int j = 0; j < attrs.getLength(); j++) {
                // 属性本身也是节点
                log.info("- {} : {}", attrs.item(j).getNodeName(), attrs.item(j).getNodeValue());
            }

            Element element = (Element) nodeList.item(i);
            log.info("通过属性名获取（有且仅有一个）: {}", element.getAttribute("id"));

            log.info("子节点遍历");
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    log.info("- {}", childNode.getNodeName());
                    log.info("非期望节点获取：{}", childNode.getNodeValue());
                    log.info("期望节点获取：{}", childNode.getFirstChild().getNodeValue());
                    log.info("包含子节点文本内容: {}", childNode.getTextContent());
                }
            }
        }

    }

    /**
     * dom 写出
     */
    @Test
    public void domWriter() throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.newDocument();
        // FIXME 去除 standalone ，但是会造成不换行
        document.setXmlStandalone(true);
        Element rootNode = document.createElement("transaction");

        // 创建节点
        Element objectNode = document.createElement("object");
        // 设置属性
        objectNode.setAttribute("id", "1");
        Element nameNode = document.createElement("name");
        // 设置节点值
        nameNode.setTextContent("Foo");
        Element yearNode = document.createElement("price");
        yearNode.setTextContent("1999");
        // 追加节点
        objectNode.appendChild(nameNode);
        objectNode.appendChild(yearNode);

        rootNode.appendChild(objectNode);
        document.appendChild(rootNode);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // 设置输出属性: 换行
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // 输出文件
        transformer.transform(new DOMSource(document), new StreamResult(new File("domTest.xml")));

        log.info("文件创建成功：{}", new File("domTest.xml").getAbsolutePath());
    }

}
