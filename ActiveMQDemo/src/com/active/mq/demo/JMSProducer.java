package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSProducer {

    //发送的消息数量
    private static final int SENDNUM = 10;
    
	public static void main(String[] args) {

        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
       

        try {
            //通过连接工厂获取连接
            connection = MQConnectionFactory.getConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            sendMessage(session, messageProducer);
            //提交回话
            session.commit();

        } catch (Exception e) {
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
	
	/**
     * 发送消息
     * @param session
     * @param messageProducer  消息生产者
     * @throws Exception
     */
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            //创建一条文本消息 
            TextMessage message = session.createTextMessage("发送JMS消息第" + (i + 1) + "条");
            System.out.println("发送消息：Activemq 发送JMS消息" + (i + 1));
            //通过消息生产者发出消息 
            messageProducer.send(message);
        }

    }
}
