package com.gordon.discountCrawler.worker;

import com.gordon.discountCrawler.constants.CrawlerParams;
import com.gordon.discountCrawler.fetcher.PageFetcher;
import com.gordon.discountCrawler.filter.ProductFilter;
import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.parser.ContentParser;
import com.gordon.discountCrawler.storage.DataStorage;
import com.gordon.discountCrawler.storage.impl.ListStorage;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wwz on 2016/2/22.
 */
public class CrawlerWorker implements Runnable {
    private static final Logger Log = Logger.getLogger(CrawlerWorker.class.getName());

    private PageFetcher pageFetcher = new PageFetcher();

    private ContentParser contentParser = new ContentParser();

    private DataStorage dataStorage = ListStorage.getInstance();

    private int threadIndex;

    private Integer page = 1;


    public CrawlerWorker(int i) {
        this.threadIndex = i;
    }

    public void startCrawl() {
        while (true) {
            List<DiscountProduct> discountProductList =
                    contentParser.parseHTML(pageFetcher.getContentFromUrl(CrawlerParams.PAGE_URL + page.toString()));
            //当抓取页面的element不为空时抓取
            if (page == 3) {
                break;
            }
            dataStorage.store(discountProductList);
            try {
                Thread.sleep(CrawlerParams.DEYLAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            page++;
        }
    }

    /**
     * 运行该线程爬取全部商品信息
     */
    public void run() {
        Integer page = 1;
        while (true) {
            List<DiscountProduct> newDetectedList =
                    contentParser.parseHTML(pageFetcher.getContentFromUrl(CrawlerParams.PAGE_URL + Integer.toString(page)));
            if (ListStorage.getDiscountProductList().containsAll(newDetectedList)) {
                //
                try {
                    Thread.sleep(CrawlerParams.DEYLAY_TIME);
                    page = 1;
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (DiscountProduct discountProduct : newDetectedList) {
                if (!ListStorage.getDiscountProductList().contains(discountProduct)) {
                    if (ProductFilter.isMatch(discountProduct)) {
                        //将商品信息发送邮箱
                        System.out.println(discountProduct.getTitle() + "page:" + page);
                        //TODO
                        ListStorage.getDiscountProductList().add(discountProduct);
                    }
                }
            }
            try {
                Thread.sleep(CrawlerParams.DEYLAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            page++;
        }
    }
}
