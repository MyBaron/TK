package com.tk.monitor.tkserver.es.config;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESConfigTest {


    @Autowired
    private ESConfig esConfig;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    private ElasticsearchProperties elasticsearchProperties;


    @Test
    public void client() {
        try {
            TransportClient client = esConfig.client();
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("test");
            client.admin().indices().create(createIndexRequest);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}