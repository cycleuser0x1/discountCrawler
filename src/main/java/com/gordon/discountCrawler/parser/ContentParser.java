package com.gordon.discountCrawler.parser;

import com.gordon.discountCrawler.model.DiscountProduct;
import com.gordon.discountCrawler.model.FetchedPage;
import com.gordon.discountCrawler.util.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wwz on 2016/2/18.
 */
public class ContentParser {
    /**
     *初始化运行爬虫时抓取全部折扣商品信息
     * @param fetchedPage
     * @return
     */
    public List<DiscountProduct> parseHTML(FetchedPage fetchedPage) {
        Document doc = Jsoup.parse(fetchedPage.getContent());
        //获取折扣商品元素集合
        Elements productElements = doc.getElementsByClass("normal");
        List<DiscountProduct> discountProductList = new ArrayList<DiscountProduct>();
        //将页面元素转化为对象保存到集合中
        for (Element element : productElements) {
            String id = element.attr("id");
            Date releasedTime = TimeUtil.parseTime(element.select(".t").text());
            String title = element.select(".i").text();
            Double discountedPrice = Double.parseDouble(element.select(".p").text());
            Double price = Double.parseDouble(element.select(".h").text());
            discountProductList.add(new DiscountProduct(id,releasedTime,title,discountedPrice,price));
        }
        return discountProductList;
    }

}
