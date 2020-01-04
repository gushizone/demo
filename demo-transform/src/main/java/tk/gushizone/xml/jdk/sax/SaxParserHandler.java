package tk.gushizone.xml.jdk.sax;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * TODO 使用 泛型 + 反射 将数据放入 entity
 *
 * @author gushizone@gmail.com
 * @date 2019-06-22 09:38
 */
@Slf4j
public class SaxParserHandler extends DefaultHandler {

    /**
     * 记录当前节点元素名
     */
    private String currentNode;

    private StringBuilder sb = new StringBuilder();

    /**
     * 解析Dom开始
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /**
     * 解析Dom结束
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     * 解析标签开始
     * @param qName      便签名
     * @param attributes 属性集合
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentNode = qName;

        if ("object".equals(qName)) {
            log.info("过属性名获取属性：{}", attributes.getValue("id"));

            log.info("属性遍历");
            for (int i = 0; i < attributes.getLength(); i++) {
                log.info("- {} : {}", attributes.getQName(i), attributes.getValue(i));
            }
        }
    }

    /**
     * 解析标签结束
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (StringUtils.isNotEmpty(currentNode)) {
            log.info("- {} : {}", currentNode, sb.toString());
        }

        currentNode = "";
        sb.delete(0, sb.length());
    }


    /**
     * 获取节点值 （和 DOM 一样 空白也被识别为节点）
     *
     * @param ch     dom块内容 , 默认2k
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        // FIXME 由于 SAX 分块读取，直接获取的可能不完整
        String value = new String(ch, start, length);
        if (StringUtils.isNotEmpty(currentNode)
                && StringUtils.isNotEmpty(value.trim())) {
            sb.append(value);
            log.info("理想状态的节点值：{}", value);
        }
    }
}
