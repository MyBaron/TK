package com.tk.monitor.tkclient.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * 创建消息
 * @author baron
 */
public class MessageBuild {
    public static volatile BlockingQueue<MessageBody> messageBodies = new ArrayBlockingQueue<MessageBody>(3000);

    public static MessageVO.Message build(MessageBody messageBody) {
        if (Objects.isNull(messageBody)) {
            return null;
        }
        //构建头部
        MessageVO.Head head = buildHead("1.0");
        //类型
        MessageVO.Type monitor_command = MessageVO.Type.monitor_command;

        //实体信息
        MessageVO.Body body = buildBody(messageBody);

        //拼接message
        MessageVO.Message.Builder messageBui = MessageVO.Message.newBuilder();
        messageBui.setHead(head);
        messageBui.setBody(body);
        messageBui.setType(monitor_command);
        return messageBui.build();
    }

    /**
     * 心跳信息
     * @return
     */
    public static MessageVO.Message buildHeart() {
        //构建头部
        MessageVO.Head head = buildHead("1.0");
        //类型
        MessageVO.Type heart = MessageVO.Type.heart;
        MessageVO.Message.Builder messageBui = MessageVO.Message.newBuilder();
        messageBui.setHead(head);
        messageBui.setType(heart);
        return messageBui.build();
    }

    /**
     * client 登录信息
     * @return
     */
    public static MessageVO.Message buildLogion(MessageBody messageBody ) {
        //构建头部
        MessageVO.Head head = buildHead("1.0");
        //类型
        MessageVO.Type client = MessageVO.Type.client;
        //消息载体
        MessageVO.Message.Builder messageBui = MessageVO.Message.newBuilder();
        messageBui.setHead(head);
        messageBui.setType(client);
        if (!Objects.isNull(messageBody)) {
            MessageVO.Body body = buildBody(messageBody);
            messageBui.setBody(body);
        }
        return messageBui.build();
    }


    private static MessageVO.Head  buildHead(String version) {
        //头部
        MessageVO.Head.Builder headBui = MessageVO.Head.newBuilder();
        headBui.setId("");//未启用
        headBui.setVersion(version);
        return headBui.build();
    }


    /**
     * todo 多条信息
     * conBui.setType("URL.Method");
     * conBui.setContent("www.demo.com");
     * conBui.setProperty("info");
     *
     * @param messageBody
     */
    private static MessageVO.Body buildBody(MessageBody messageBody) {
        //实体
        MessageVO.Body.Builder bodyBui = MessageVO.Body.newBuilder();
        MessageVO.Body.Content.Builder conBui = MessageVO.Body.Content.newBuilder();
        List list = new ArrayList();
        conBui.setType(messageBody.getType());
        conBui.setContent(messageBody.getContent());
        conBui.setProperty(messageBody.getProperty());
        MessageVO.Body.Content content = conBui.build();
        list.add(content);
        bodyBui.addAllContent(list);
        return bodyBui.build();
    }

}

