package com.active.mq.demo;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

public class QueueConsumer {

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
			// 创建消息接收者
			QueueReceiver receiver = queueSession.createReceiver(queue);

			receiver.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {
					if (msg != null) {
						MapMessage map = (MapMessage) msg;
						try {
							System.out.println(map.getLong("time") + "queue接收到消息#" + map.getString("text"));
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});
			// 休眠100ms再关闭
			Thread.sleep(1000 * 100);

			// 提交会话
			queueSession.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
}
