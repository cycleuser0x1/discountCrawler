# discountCrawler

抓取http://www.joyj.com/real_all/网站折扣商品的多线程爬虫，将过滤后的商品信息发送邮箱

概述：

首次运行会初始化数据，将网站近一天的折扣商品信息存储到集合中，并会检索过滤符合条件的商品

每运行24小时只保存网站前两页的商品信息，清空剩余的商品信息

由于网站商品更新频率高，为了避免爬虫频繁发送邮件，邮件发送模块才用了生产者-消费者模式，
抓取商品信息的线程将符合过滤条件的商品信息添加到队列中，邮件发送线程会间隔一段时间从队列中获取商品信息

爬虫结构如下：

PageFetcher 用于获取页面内容

ProductFilter 定义折扣商品过滤条件的Filter

ContentParser 通过Jsoup实现的解析器，将页面中的商品信息转换为对象

DataStorage 数据存储接口，用于存储商品信息，ListStorage是通过list存储的实现

TimeUtil 转换时间的工具类

CrawlerWork