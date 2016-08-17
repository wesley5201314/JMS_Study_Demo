package com.active.mq.demo;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class TopicConsumer {
	
	public static void main(String[] args) {
		TopicConnection connection = null;
	    TopicSession session = null;
	    try {
	        // 通过工厂创建一个连接
	        connection = MQConnectionFactory.getTopicConnection();
	        // 启动连接
	        connection.start();
	        // 创建一个session会话
	        session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	        // 创建一个消息队列
	        Topic topic = session.createTopic("TopicDemo");
	        // 创建消息消费者
	        TopicSubscriber subscriber = session.createSubscriber(topic);
	        
	        subscriber.setMessageListener(new MessageListener() { 
	            public void onMessage(Message msg) { 
	                if (msg != null) {
	                    MapMessage map = (MapMessage) msg;
	                    try {
	                        System.out.println(map.getLong("time") + "Topic接收消息#" + map.getString("text"));
	                    } catch (JMSException e) {
	                        e.printStackTrace();
	                    }
	                }
	            } 
	        }); 
	        // 休眠100ms再关闭
	        Thread.sleep(1000 * 100); 
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
	
}
