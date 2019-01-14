import com.tk.monitor.tkclient.message.MessageVO;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class messageVOTest {

    public static void main(String[] args) throws IOException {

        MessageVO.Message.Builder messageBui = MessageVO.Message.newBuilder();
        //头部
        MessageVO.Head.Builder headBui = MessageVO.Head.newBuilder();
        headBui.setId(UUID.randomUUID().toString());
        headBui.setVersion("1.0");
        MessageVO.Head head = headBui.build();
        //类型
        MessageVO.Type monitor_command = MessageVO.Type.monitor_command;
        //实体
        MessageVO.Body.Builder bodyBui = MessageVO.Body.newBuilder();
        MessageVO.Body.Content.Builder conBui = MessageVO.Body.Content.newBuilder();
        List list = new ArrayList();
        conBui.setType("URL.Method");
        conBui.setContent("www.demo.com");
        conBui.setProperty("info");
        MessageVO.Body.Content content = conBui.build();
        list.add(content);
        bodyBui.addAllContent(list);
        MessageVO.Body body = bodyBui.build();

        //拼接message
        messageBui.setHead(head);
        messageBui.setBody(body);
        messageBui.setType(monitor_command);
        MessageVO.Message message = messageBui.build();

        //测试发送信息
        //接收数据
        //将数据写到输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        //将数据序列化后发送
        byte[] byteArray = outputStream.toByteArray();

        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        //反序列化
        MessageVO.Message me = MessageVO.Message.parseFrom(input);
        System.out.println("head:" + me.getHead());
        System.out.println("type:" + me.getType());
        System.out.println("body:" + me.getBody());
    }


    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i=0;i<30;i++) {
            int k = random.nextInt(7);
            System.out.println(k);
        }

    }
}
