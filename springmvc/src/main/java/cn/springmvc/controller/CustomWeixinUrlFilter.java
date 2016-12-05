package cn.springmvc.controller;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.weixin4j.Configuration;
import org.weixin4j.spi.HandlerFactory;
import org.weixin4j.spi.IMessageHandler;
import org.weixin4j.util.TokenUtil;

/**
 * ���ֽ���
 *
 * @author qsyang
 * @version 1.0
 */
@Controller
@RequestMapping("/api/weixin4j")
public class CustomWeixinUrlFilter {

    @RequestMapping(method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //΢�ŷ�����������GET������д��URL��,������Ҫ�ж��Ƿ�ΪGET����
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (Configuration.isDebug()) {
            System.out.println("���΢������:" + request.getMethod() + " ��ʽ");
            System.out.println("΢������URL:" + request.getServletPath());
        }
        //��Ϣ��Դ�ɿ�����֤
        String signature = request.getParameter("signature");// ΢�ż���ǩ��
        String timestamp = request.getParameter("timestamp");// ʱ���
        String nonce = request.getParameter("nonce");       // �����
        //TokenΪweixin4j.properties�����õ�Token
        String token = TokenUtil.get();
        //1.��֤��Ϣ��ʵ��
        //http://mp.weixin.qq.com/wiki/index.php?title=��֤��Ϣ��ʵ��
        //URLΪhttp://www.weixin4j.org/api/���ں�
        //��Ϊ��������֤
        String echostr = request.getParameter("echostr");   //
        //ȷ�ϴ˴�GET��������΢�ŷ�������ԭ������echostr�������ݣ��������Ч����Ϊ�����߳ɹ����������ʧ��
        if (TokenUtil.checkSignature(token, signature, timestamp, nonce)) {
            response.getWriter().write(echostr);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            //��ȡPOST��
            ServletInputStream in = request.getInputStream();
            if (Configuration.isDebug()) {
                System.out.println("���յ�΢��������,׼������...");
            }

            //����������Ϣ�����ؽ��
            IMessageHandler messageHandler = HandlerFactory.getMessageHandler();
            //����������Ϣ�����ؽ��
            String xml = messageHandler.invoke(in);
            //���ؽ��
            response.getWriter().write(xml);
            //���ؽ��
            response.getWriter().write(xml);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("");
        }
    }
}