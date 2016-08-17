package com.active.mq.demo;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

public class TopicProducer {
	private static final int SEND_NUM = 10;

	public static void main(String[] args) {
		  TopicConnection connection = null;
	      TopicSession session = null;
	        try {
	            // 通过工厂创建一个连接
	            connection = MQConnectionFactory.getTopicConnection();
	            // 启动连接
	            connection.start();
	            // 创建一个session会话
	            session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
	            // 创建一个消息队列
	            Topic topic = session.createTopic("TopicDemo");
	            // 创建消息发送者
	            TopicPublisher publisher = session.createPublisher(topic);
	            // 设置持久化模式
	            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	            sendMessage(session, publisher);
	            // 提交会话
	            session.commit();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            // 关闭释放资源
	            if (session != null) {
	                try {
						session.close();
					} catch (JMSException e) {
						e.printStackTrace();
					}
	            }
	            if (connection != null) {
	                try {
						connection.close();
					} catch (JMSException e) {
						e.printStackTrace();
					}
	            }
	        }
	}
	
	public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception {
        for (int i = 0; i < SEND_NUM; i++) {
            String message = "发送Topic消息第" + (i + 1) + "条";
            
            MapMessage map = session.createMapMessage();
            map.setString("text", message);
            map.setLong("time", System.currentTimeMillis());
            System.out.println("ActiveMQ 发送Topic消息："+(i + 1));
            publisher.send(map);
        }
    }
}
