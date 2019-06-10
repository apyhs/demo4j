package demo.elasticsearch;

import artoria.io.IOUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EsRestHighLevelClientBasicDemo {
    private static Logger log = LoggerFactory.getLogger(EsRestHighLevelClientBasicDemo.class);
    private static String index = "posts";
    private RestHighLevelClient restHighLevelClient;

    @Before
    public void init() {
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.10.201", 9200, "http"),
                        new HttpHost("192.168.10.202", 9200, "http"),
                        new HttpHost("192.168.10.203", 9200, "http")
                )
        );
    }

    @After
    public void destroy() {

        IOUtils.closeQuietly(restHighLevelClient);
    }

    @Test
    public void addOrUpdateDocument() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "李四");
        jsonMap.put("age", "18");
        jsonMap.put("email", "lisi@email.com");
        IndexRequest indexRequest = new IndexRequest(index)
                .id("2")
                .source(jsonMap);
        IndexResponse indexResponse =
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("{}", JSON.toJSONString(indexResponse));
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        DocWriteResponse.Result result = indexResponse.getResult();
//        if (result == DocWriteResponse.Result.CREATED) {
//            // 文档第一次被创建
//        }
//        else if (result == DocWriteResponse.Result.UPDATED) {
//            // 文档被更新
//        }
        log.info("index={}, id={}, version={}, result={}", index, id, version, result);
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            // 成功的分片数 和 总分片数不同，有失败
            log.info("Total={}, Successful={}", shardInfo.getTotal(), shardInfo.getSuccessful());
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                // 失败原因
                log.info("reason={}", reason);
            }
        }
    }

    @Test
    public void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest(index);
        getRequest.id("1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String index = getResponse.getIndex();
        String id = getResponse.getId();
        boolean exists = getResponse.isExists();
        long version = getResponse.getVersion();
        String sourceAsString = getResponse.getSourceAsString();
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        log.info("index={}, id={}, exists={}, version={}", index, id, exists, version);
        log.info("sourceAsString={}", sourceAsString);
        log.info("sourceAsMap={}", sourceAsMap);
    }

    @Test
    public void existsDocument() throws IOException {
        GetRequest getRequest = new GetRequest(index);
        getRequest.id("1");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        log.info("exists={}", exists);
    }

    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index);
        deleteRequest.id("1");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        String index = deleteResponse.getIndex();
        String id = deleteResponse.getId();
        DocWriteResponse.Result result = deleteResponse.getResult();
        RestStatus status = deleteResponse.status();
        String toString = deleteResponse.toString();
        log.info("index={}, id={}, result={}, status={}, toString={}", index, id, result, status, toString);
    }

    @Test
    public void searchDocument() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("name", "zhangsan"));
        sourceBuilder.from(0).size(10);
        sourceBuilder.timeout(TimeValue.timeValueSeconds(60));

        sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.DESC));
        // https://blog.csdn.net/wild46cat/article/details/62889554
        sourceBuilder.sort(SortBuilders.fieldSort("age.keyword").order(SortOrder.DESC));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        int failedShards = searchResponse.getFailedShards();
        int successfulShards = searchResponse.getSuccessfulShards();
        int totalShards = searchResponse.getTotalShards();
        boolean timedOut = searchResponse.isTimedOut();
        RestStatus status = searchResponse.status();
        log.info("failedShards={}, successfulShards={}, totalShards={}, timedOut={}, status={}"
                , failedShards, successfulShards, totalShards, timedOut, status);
        log.info("toString={}", searchResponse.toString());
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
//        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        String collapseField = hits.getCollapseField();
        log.info("totalHits={}, maxScore={}, collapseField={}", totalHits, maxScore, collapseField);
        for (SearchHit hit : hits.getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info("sourceAsMap={}", sourceAsMap);
        }
    }

}
