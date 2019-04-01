package org.activemqstudy.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.activemqstudy.util.ConnectionUtil;
import org.activemqstudy.util.Constanst;

/**
 * 消费者
 * 
 * @author bruce2018 [微信公众号:程序江湖] 
 * csdn:https://blog.csdn.net/zpl123456
 * jianshu:https://www.jianshu.com/u/effeedbfe8d7
 * juejin:https://juejin.im/post/5c8b0cfce51d4553de1fa545
 */
public class AppConsumer {

	public static void main(String[] args) {
		consumer();
	}

	/**
	 * 生产者
	 */
	public static void consumer() {
		Connection connection = null;
		Session session = null;
		MessageConsumer createConsumer = null;
		try {
			connection = ConnectionUtil.getInstance().createConnection();
			connection.start();
			session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(Constanst.QUEUE);
			createConsumer = session.createConsumer(queue);
			createConsumer.setMessageListener(new ConsumerListener());
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (createConsumer != null) {
				try {
					createConsumer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			ConnectionUtil.getInstance().close(session, connection);
		}
	}

	/**
	 * 
	 * @author bruce2018
	 *
	 */
	private static class ConsumerListener implements MessageListener {
		@Override
		public void onMessage(Message message) {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println(textMessage.getText());
					textMessage.acknowledge();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
