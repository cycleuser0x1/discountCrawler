package com.gordon.discountCrawler;

import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.storage.impl.ListStorage;
import com.gordon.discountCrawler.worker.CrawlerWorker;

/**
 * Created by wwz on 2016/2/18.
 * 自体测试，微软雅黑，红红火火恍恍惚惚
 */
public class CrawlerStart {
    public static void main(String[] args) {
//        Thread thread = new Thread(new CrawlerWorker(1));
//        thread.start();
//        while (thread.isAlive());
        CrawlerWorker crawlerWorker = new CrawlerWorker(1);
        crawlerWorker.startCrawl();

        new Thread(crawlerWorker).start();

    }

}
