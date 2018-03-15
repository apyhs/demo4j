package com.github.kahlkn.demo.spider;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpiderComponent {

    /**
     * 爬取
     */
    public Map crawling(String who) {
        // 调用 注入 的 service，根据 who 查询 相应 的 配置

        // 根据 配置 创建 PageProcessor 对象， 其中 一条 配置 代表 一个 PageProcessor

        // 根据 配置 创建 downloader 等 一系列 对象

        // 爬取
        return null;
    }

}
