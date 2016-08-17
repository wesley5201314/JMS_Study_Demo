package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSConsumer {
	

    public static void main(String[] args) {
        Connection connection = null;//连接
        
        Session session = null;//会话 接受或者发送消息的线程
        
        Destination destination;//消息的目的地

        MessageConsumer messageConsumer;//消息的消费者

        try {
            //通过连接工厂获取连接
            connection = MQConnectionFactory.getConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("收到的消息:" + textMessage.getText());
                }else {
                    break;
                }
            }
            //提交回话
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session !=null){
            	try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }
        }

    }
}
