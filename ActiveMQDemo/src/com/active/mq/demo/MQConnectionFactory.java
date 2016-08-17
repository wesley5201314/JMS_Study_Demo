package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MQConnectionFactory {
	
	private  static final  String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private  static final  String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private  static final  String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
    
    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);//连接工厂
    
    private static QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
    
    private static TopicConnectionFactory topicConnectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
    /**
     * 通过连接工厂获取连接(JMS方式)
     * @return
     */
    public static Connection getConnection(){
    	Connection connection = null;
    	try {
			connection = connectionFactory.createConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	return connection;
    }
    
    /**
     * 通过连接工厂获取连接(Queue方式)
     * @return
     */
    public static QueueConnection getQueueConnection(){
    	QueueConnection queueConnection = null;
    	try {
    		queueConnection = queueConnectionFactory.createQueueConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	return queueConnection;
    }
    
    /**
     * 通过连接工厂获取连接(Topic方式)
     * @return
     */
    public static TopicConnection getTopicConnection(){
    	TopicConnection topicConnection = null;
    	try {
    		topicConnection = topicConnectionFactory.createTopicConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	return topicConnection;
    }
}
