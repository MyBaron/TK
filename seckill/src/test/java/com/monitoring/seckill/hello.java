package com.monitoring.seckill;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class hello {

	public static void main(String[] args) throws MQClientException {
		consumer("消费者1",1);
		consumer("消费者2",2);
		producer();
//		producer_asy();
		producer_oneway();
//		consumer("消费者2",2);

	}

    class kk implements Delayed {

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }



	public static  void consumer(String name,int number) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("this_is_group_" +number);
		consumer.setNamesrvAddr("127.0.0.1:9876");

		// Subscribe one more more topics to consume.
		consumer.subscribe("TopicTest", "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
															ConsumeConcurrentlyContext context) {
				System.out.println("-----------------Consumer开始执行 "+name+"----------------------");
				System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
				MessageExt ext=msgs.get(0);
				System.out.println(new String(ext.getBody(), StandardCharsets.UTF_8));
				System.out.println("-----------------Consumer执行结束 "+name);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		//Launch the consumer instance.
		consumer.start();

		System.out.printf("Consumer Started.%n");
	}


	public static void producer() {
		try {
			//Instantiate with a producer group name.
			DefaultMQProducer producer = new
                    DefaultMQProducer("this_is_group");
			// Specify name server addresses.
			producer.setNamesrvAddr("localhost:9876");
			//Launch the instance.
			producer.start();
			for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }
			//Shut down once the producer instance is not longer in use.
			producer.shutdown();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (RemotingException e) {
			e.printStackTrace();
		}
		catch (MQBrokerException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void producer_asy() {
			//Instantiate with a producer group name.
		try {
			DefaultMQProducer producer = new DefaultMQProducer("this_is_group");
			// Specify name server addresses.
			producer.setNamesrvAddr("localhost:9876");
			//Launch the instance.
			producer.start();
			producer.setRetryTimesWhenSendAsyncFailed(0);
			for (int i = 0; i < 100; i++) {
                final int index = i;
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
						System.out.println("发送成功");
                        System.out.printf("%-10d OK %s %n", index,
                                sendResult.getMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }
			//Shut down once the producer instance is not longer in use.
			producer.wait();
     	} catch (MQClientException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void producer_oneway(){
		try {
			//Instantiate with a producer group name.
			DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
			// Specify name server addresses.
			producer.setNamesrvAddr("localhost:9876");
			//Launch the instance.
			producer.start();
			for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                producer.sendOneway(msg);
            }
			//Shut down once the producer instance is not longer in use.
			producer.shutdown();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


}
