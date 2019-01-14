package com.tk.monitor.tkserver.es.config;

import com.tk.monitor.tkserver.es.util.BulkProcessorUtil;
import com.tk.monitor.tkserver.tknetty.client.ClientManager;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;


@Configuration
public class ESConfig {
    private static final Logger log = LoggerFactory.getLogger(ESConfig.class);

    @Value("${es.address}")
    private String address;
    @Value("${es.port}")
    private int port;
    @Value("${es.cluster.name}")
    private String cluster_name;
    private static String urlLogIndex;
    private static String urlLogType;
    private static String connectlogIndex;
    private static String connectlogType;

    @Value("${es.url.log.index}")
    public  void setUrlLogIndex(String urlLogIndex) {
        ESConfig.urlLogIndex = urlLogIndex;
    }

    @Value("${es.url.log.type}")
    public  void setUrlLogType(String urlLogType) {
        ESConfig.urlLogType = urlLogType;
    }

    @Value("${es.connect.log.index}")
    public  void setConnectlogIndex(String connectlogIndex) {
        ESConfig.connectlogIndex = connectlogIndex;
    }

    @Value("${es.connect.log.type}")
    public  void setConnectlogType(String connectlogType) {
        ESConfig.connectlogType = connectlogType;
    }

    @Bean
    public TransportClient client() throws UnknownHostException {
        Settings settings = Settings
                .builder()
                .put("cluster.name",cluster_name)
                .put("client.transport.sniff", true)
                .build();
        System.setProperty("es.set.netty.runtime.available.processors","false");
        TransportClient transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
        log.info("es 建立连接：address={}，port={}，cluster_name={}",address,port,cluster_name);
        return transportClient;
    }


    /**
     * 创建一个URL请求
     * @param map
     * @return
     */
    public static IndexRequest newURLRequest(Map map) {
        return new IndexRequest(urlLogIndex, urlLogType).source(map);
    }

    /**
     * 创建一个连接请求
     * @param map
     * @return
     */
    public static IndexRequest newConnectRequest(Map map) {
        return new IndexRequest(connectlogIndex, connectlogType).source(map);
    }


}
