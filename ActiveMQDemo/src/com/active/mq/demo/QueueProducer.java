package com.active.mq.demo;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

public class QueueProducer {
	 private static final int SEND_NUM = 10;


	public static void main(String[] args) {
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		try {
			// 通过工厂创建一个连接
			queueConnection = MQConnectionFactory.getQueueConnection();
            // 启动连接
			queueConnection.start();
            // 创建一个session会话
			queueSession = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Queue queue = queueSession.createQueue("QueueMsgDemo");
            // 创建消息发送者
            QueueSender sender = queueSession.createSender(queue);
            // 设置持久化模式
            sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(queueSession, sender);
            // 提交会话
            queueSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            // 关闭释放资源
            if (queueSession != null) {
            	try {
					queueSession.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }
            if (queueConnection != null) {
            	try {
					queueConnection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }
        }
	}
	 
	 
	 public static void sendMessage(QueueSession session, QueueSender sender) throws Exception {
	        for (int i = 0; i < SEND_NUM; i++) {
	            String message = "发送queue消息第" + (i + 1) + "条";
	            //创建一个Map集合信息
	            MapMessage map = session.createMapMessage();
	            map.setString("text", message);
	            map.setLong("time", System.currentTimeMillis());
	            System.out.println("ActiveMQ 发送queue消息："+(i + 1));
	            sender.send(map);
	        }
	    }
}
