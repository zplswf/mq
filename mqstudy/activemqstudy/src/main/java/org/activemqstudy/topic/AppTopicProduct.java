package org.activemqstudy.topic;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.activemqstudy.util.ConnectionUtil;
import org.activemqstudy.util.Constanst;

/**
 * 生产者
 * @author bruce2018 [微信公众号:程序江湖]
 * csdn:https://blog.csdn.net/zpl123456
 * jianshu:https://www.jianshu.com/u/effeedbfe8d7
 * juejin:https://juejin.im/post/5c8b0cfce51d4553de1fa545
 */
public class AppTopicProduct 
{
    public static void main( String[] args )
    {
    	product();
    }
    
    public static void product(){
    	Connection connection = null;//连接
        Session session = null;//发送或者接受发送消息的线程
        MessageProducer createProducer = null;//消息生产者
        try {
        	//1.创建工厂链接对象,指定Ip和端口号
			connection = ConnectionUtil.getInstance().createConnection();
			//开启连接
			connection.start();
			//使用连接创建回话session对象
//		    static final int AUTO_ACKNOWLEDGE = 1;  自动确认模式,不需要客户端进行确认
//		    static final int CLIENT_ACKNOWLEDGE = 2; 客户端进行确认
//		    static final int DUPS_OK_ACKNOWLEDGE = 3; 允许重复消息
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);//开始事务  也可以不用开启事务
			//使用回话对象创建目标对象,包含queue或者topic
			Destination de = session.createTopic(Constanst.TOPIC);
			
			//使用回话对象创建生产者对象
			createProducer = session.createProducer(de);
			for(int i=0;i<10;i++){
				TextMessage textMessage = session.createTextMessage("hello bruce2018,程序江湖!"+i);
				createProducer.send(textMessage);
			}
		} catch (JMSException e) {
			e.printStackTrace();
			try {
				session.rollback();//事务回滚
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(createProducer != null){
				try {
					createProducer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			ConnectionUtil.getInstance().close(session, connection);
		}
    }
}
