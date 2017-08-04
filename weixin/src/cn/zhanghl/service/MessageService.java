package cn.zhanghl.service;

import cn.zhanghl.util.XMLUtil;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 消息业务处理实现类
 */
public class MessageService {

    /**
     * 回复消息总函数
     * @param inputStream
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public String replyMessage(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        //将XML转化成Map
        Map<String,String> result = XMLUtil.getXMLToMap(inputStream);
        String Content = result.get("Content");
        if (Content.equals("1")) {
            String res = this.replyTextMsg(result);
            return res;
        } else {
            return "success";
        }
    }

    /**
     * 回复文本消息
     * @param result
     * @return
     */
    public String replyTextMsg(Map<String,String> result) {
        //xml格式化
        String xml = "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[%s]]></MsgType>" +
                "<Content><![CDATA[%s]]></Content>" +
                "</xml>";
        String toUser = result.get("FromUserName");
        String fromUser = result.get("ToUserName");
        String createTime = System.currentTimeMillis() / 1000 + "";
        String msgType = "text";
        String content = "hello";
        //格式化输出
        String res = String.format(xml, toUser, fromUser, createTime, msgType, content);
        return res;
    }
}

