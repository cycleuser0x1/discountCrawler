package com.gordon.discountCrawler.email;

import com.gordon.discountCrawler.constants.CrawlerParams;
import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.queue.FilteredDiscountProductQueue;
import org.apache.log4j.Logger;

/**
 * Created by wwz on 2016/2/25.
 */
public class SendEmail implements Runnable {
    private static final Logger log = Logger.getLogger(SendEmail.class.getName());
    public void run() {
        boolean flag = true;
        StringBuffer stingBuffer = new StringBuffer();
        while (true) {
            DiscountProduct discountProduct = FilteredDiscountProductQueue.pollElement();
            if (discountProduct != null) {
                flag = true;
                //TODO 发送邮件
                System.out.println("title:" + discountProduct.getTitle() + " url:" + discountProduct.getUrl());
            } else {
                try {
                    if (flag) {
                        System.out.println("waiting for producing...");
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
}
