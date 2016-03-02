package com.gordon.discountCrawler.mail;

import com.gordon.discountCrawler.constants.CrawlerParams;
import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.queue.FilteredDiscountProductQueue;
import com.gordon.discountCrawler.util.TimeUtil;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by wwz on 2016/2/25.
 */
public class SendMail implements Runnable {
    private static final Logger log = Logger.getLogger(SendMail.class.getName());
    private static final Properties prop = new Properties();

    public void run() {
        boolean flag = true;
        StringBuffer sb = new StringBuffer();
        while (true) {
            DiscountProduct discountProduct = FilteredDiscountProductQueue.pollElement();
            if (discountProduct != null) {
                flag = true;
                sb.append(discountProduct.getTitle() +
                        "&nbsp现价：<span style=\"color:red\">" + discountProduct.getDiscountedPrice() + "</span>" +
                        "&nbsp原价：<span style=\"color:blue\">" + discountProduct.getPrice() + "</span>" +
                        "&nbsp链接地址：<a href=\"" + discountProduct.getUrl() + "\">" + discountProduct.getUrl() + "</a><br/>");
            } else {
                try {
                    if (flag) {
                        try {
                            //TODO 发送邮件
//                            send(sb.toString());
                            System.out.println(sb.toString());
                        } catch (Exception e) {
                            log.error("邮件发送失败:+\n" + sb.toString() + "\n" + e.getMessage());
                        }
                        sb.setLength(0);
                        System.out.println("wait for detecting...");
                    }
                    flag = false;
                    //等待生产者进程将商品信息加入队列
                    Thread.sleep(CrawlerParams.EMAIL_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void send(String msg) throws MessagingException {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", CrawlerParams.SMTP_ADDRESS);
        prop.put("mail.user", CrawlerParams.OUTBOX);
        prop.put("mail.password", CrawlerParams.PASSWORD);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.user"), prop.getProperty("mail.password"));
            }
        };
        //创建邮件会话
        Session mailSession = Session.getInstance(prop, authenticator);

        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        //设置发件人
        InternetAddress from = new InternetAddress(prop.getProperty("mail.user"));

        message.setFrom(from);

        //设置收件人
        InternetAddress to = new InternetAddress(CrawlerParams.MAIl_ADDRESS);
        message.setRecipient(MimeMessage.RecipientType.TO, to);

        //设置邮件标题
        message.setSubject(TimeUtil.getTimeStamp() + "更新的折扣商品信息");

        //设置邮件内容
        message.setContent(msg, "text/html;charset=UTF-8");

        //发送
        Transport.send(message);
    }
}
