package demo.spider;

import org.quartz.Scheduler;
import redis.clients.jedis.Pipeline;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selector;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 爬虫群 配置
 * 一个数据对象，数据源 来源 多个 站点 多个 页面
 * 因此，一个数据对象需要 一个 爬虫群 才能 完成
 */
public class SpidersConfig {
    private String id; // 数据库ID
    private String name; // 自定义名称
    private List<SpiderConfig> spiderConfigs;



    public static class SpiderConfig {
        // PageProcessor 的 配置，此处 暂时 用 PageProcessor 表示
        private PageProcessor pageProcessor;
        // Downloader 的 配置，此处 展示 用 Downloader 表示
        private Downloader downloader;
        // Pipeline 的 配置
        private List<Pipeline> pipelines;
        // 代理 配置
        private Proxy proxy;
        // url管理 的 配置
        private Scheduler scheduler;
        // 数据 筛出 配置
        private Selector selector;
        // 线程数 配置

        // 是否 进行 表达式 处理
        // 数据 计算 表达式 配置 （计划 使用 jfinal 的 enjoy）

        // 是否需要登录

        // 登陆 配置

        // 是否 有 验证码

        // 验证码配置

        /**
         * 根据 配置 生成 pageProcessor 来 进行 爬取，
         *
         *   pageProcessor 内 先 判断 是否 要 登陆 - 处理 登陆
         *      登陆参数可以以json配置到数据库 （判断 是否 有 验证码 - 单独 调用 验证码 验证）
         *   设置 状态 为 已经 登陆， 开始 爬取 指定 内容
         *
         * 通过 比如 key - xpath 等 类似 的 配置，，获取 指定 数据，复制 给 key，形成map
         * 然后 用   计算表达式 进行 处理
         * 然后 用 pipelines 序列化
         */
    }

}
