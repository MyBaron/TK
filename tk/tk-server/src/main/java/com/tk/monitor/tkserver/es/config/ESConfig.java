package com.tk.monitor.tkserver.es.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Configuration

public class ESConfig {

    public TransportClient client() throws UnknownHostException {
//        Settings settings = Settings.builder()
////                .put("cluster.name", "wxqyh-my-application").build();
//                .put("cluster.name", "baron").build();
//        System.setProperty("es.set.netty.runtime.available.processors","false");
//        TransportClient client = new PreBuiltTransportClient(settings)
////                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.10.3.252"), 9200));
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.10.0.111"), 9300));
//        return client;

        Settings settings = Settings
                .builder()
                .put("cluster.name","baron")
                .put("client.transport.sniff", true)
                .build();
        System.setProperty("es.set.netty.runtime.available.processors","false");
        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }



}
