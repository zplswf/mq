package org.activemqstudy.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * activemq工具类
 * @author bruce2018 [微信公众号:程序江湖]
 * csdn:https://blog.csdn.net/zpl123456
 * jianshu:https://www.jianshu.com/u/effeedbfe8d7
 * juejin:https://juejin.im/post/5c8b0cfce51d4553de1fa545
 */
public final class ConnectionUtil {

	public ConnectionUtil() {
	}

	private final static String URL = "tcp://127.0.0.1:61616";

	public Connection createConnection() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		try {
			return connectionFactory.createConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close(Session session, Connection connection) {
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

	private static class Singletion {
		private static ConnectionUtil instance;
		static {
			instance = new ConnectionUtil();
		}

		public static ConnectionUtil getInstance() {
			return instance;
		}
	}

	public static ConnectionUtil getInstance() {
		return Singletion.getInstance();
	}
}
