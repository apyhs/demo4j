package demo.common.elasticsearch;

import artoria.net.HttpUtils;
import org.junit.Test;

public class CommonDemo {

    @Test
    public void testBaseInfo() throws Exception {
        // 查看服务器信息
        System.out.println(HttpUtils.get("http://localhost:9200/?pretty"));
    }

    @Test
    public void testHealth() throws Exception {
        // 查看健康状况
        System.out.println(HttpUtils.get("http://localhost:9200/_cat/health?v&pretty"));
    }

    @Test
    public void testClusterHealth() throws Exception {
        // 查看集群健康状况
        System.out.println(HttpUtils.get("http://localhost:9200/_cluster/health?pretty"));
    }

    @Test
    public void testInfo() throws Exception {
        // 查看my_index的mapping和setting的相关信息
        System.out.println(HttpUtils.get("http://localhost:9200/my_index?pretty"));
    }

    @Test
    public void testCatAllIndex() throws Exception {
        // 查看所有的index
        System.out.println(HttpUtils.get("http://localhost:9200/_cat/indices?v&pretty"));
    }

    @Test
    public void testDeleteIndex() throws Exception {
        // 删除 my_index_new
        System.out.println(HttpUtils.delete("http://localhost:9200/my_index_new?pretty"));
    }

    @Test
    public void testCount() throws Exception {
        // 删除 my_index_new
        System.out.println(HttpUtils.get("http://localhost:9200/_count?pretty"));
    }

}
