package cn.zhanghl.servlet;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cn.zhanghl.service.MessageService;
import cn.zhanghl.util.Const;
import cn.zhanghl.util.EncryptUtil;
import cn.zhanghl.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class test
 */
@WebServlet(name="indexServlet",value="/")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String msg;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = Const.Token;

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //�ж��Ƿ��ĸ������п�
        if (!StringUtil.hasBlank(signature, timestamp, nonce, echostr)) {
            String[] list = {token, timestamp, nonce};
            //�����ֵ�����
            Arrays.sort(list);

            StringBuilder builder = new StringBuilder();
            //��ƴ�ӳ�һ���ַ���
            for (String str : list) {
                builder.append(str);
            }
            //����sha1����
            String hashcode = EncryptUtil.sha1(builder.toString());
            //��signature�ȶ�
            if (hashcode.equalsIgnoreCase(signature)) {

                response.getWriter().println(echostr);

            }
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //��request�����л�ȡ������
            InputStream inputStream = request.getInputStream();
            MessageService msgService = new MessageService();
            String msg = msgService.replyMessage(inputStream);
            response.getWriter().println(msg);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//            InputStream inputStream = req.getInputStream();
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = null;
//            builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inputStream);
//            //������Ϣ����ҵ��
//
////           String res = msgService.replyMessage(builder.toString());
//            //����XML ����Context���ظ�����
//
//            //��ȡ���ڵ�Ԫ��
//            Element rootNode = document.getDocumentElement();
//            //��ȡ���ڵ��µ��ӽڵ�
//            String rootName = rootNode.getNodeName();
//            NodeList childNodes = rootNode.getChildNodes();
//            Map<String, String> map = new HashMap<String, String>();
//            for (int i = 0; i < childNodes.getLength(); i++) {
//                Node node = childNodes.item(i);
//                String nodeName = node.getNodeName();
//                String nodeValue = node.getTextContent();
//                if (node.equals("#text")) {
//                    continue;
//                }
//                map.put(nodeName, nodeValue);
//            }
//            String Content = map.get("Content");
//            if (Content.equals("1")) {
//                String toUserName = map.get("FromUserName");
//                String fromUserName = map.get("ToUserName");
//                long time = Long.parseLong(map.get("CreateTime"));
//                time = time + 2;
//                String createTime = time + "";
//                //                createTime = System.currentTimeMillis() / 1000 + "";
//                String msgType = "text";
//                String content = "hello";
//                String result = "<xml><ToUserName><![CDATA[%s]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content></xml>";
//                String res = String.format(result, toUserName, fromUserName, createTime, msgType, content);
//                resp.getWriter().println(res);
//                System.out.println(document.toString());
//                System.out.println(res);
//            } else {
//            }
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            System.out.println("SAXe");
//            e.printStackTrace();
//        }
//    }
}