package com.gordon.discountCrawler;

import com.gordon.discountCrawler.constants.CrawlerParams;
import com.gordon.discountCrawler.fetcher.PageFetcher;
import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.parser.ContentParser;
import com.gordon.discountCrawler.storage.impl.ListStorage;
import com.gordon.discountCrawler.worker.CrawlerWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wwz on 2016/2/18.
 * 自体测试，微软雅黑，红红火火恍恍惚惚
 */
public class CrawlerStart {
    public static void main(String[] args) {
//        Thread thread = new Thread(new CrawlerWorker(1));
//        thread.start();
//        while (thread.isAlive());
        final PageFetcher pageFetcher = new PageFetcher();
        final List<DiscountProduct> list = new ArrayList<DiscountProduct>();
        Timer timer = new Timer();
        CrawlerWorker crawlerWorker = new CrawlerWorker(1);
        Thread thread = new Thread(crawlerWorker);
        crawlerWorker.startCrawl();
        thread.start();
        timer.schedule(new TimerTask() {
            //定时清空保存商品信息的集合,只保留前两页的商品信息
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i < 3; i++) {
                    list.addAll(ContentParser.parseHTML(pageFetcher.getContentFromUrl(CrawlerParams.PAGE_URL + Integer.toString(i))));
                }
                ListStorage.getDiscountProductList().retainAll(list);
            }
        }, 6000, 6000);
    }

}
