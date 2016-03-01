package com.gordon.discountCrawler;

import com.gordon.discountCrawler.mail.SendMail;
import org.junit.Test;

import javax.mail.MessagingException;

/**
 * Created by wwz on 2016/3/1.
 */
public class MailTest {
    @Test
    public void mailTest(){
        SendMail sendMail = new SendMail();
        try {
            sendMail.send("test msg");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
