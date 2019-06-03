package demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.util.Date;

public class ActivemqSimpleDemo {
    private static final String url = "tcp://localhost:61616";
    private static final String user = "root";
    private static final String password = "root";
    private static final String queue = "queue";

    @Test
    public void receiver() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);

            Connection connection = connectionFactory.createConnection();
            connection.start();
            // connection.setExceptionListener(this);

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination receiveQueue = session.createQueue(queue);
            MessageConsumer consumer = session.createConsumer(receiveQueue);
            System.out.println(Thread.currentThread().getName()+" start");

            while (true) {
                // Wait for a message
                Message message = consumer.receive(1000);
                if (message instanceof TextMessage) {
                    TextMessage receiveMessage = (TextMessage) message;
                    System.out.println("我是Receiver,收到消息如下: " + receiveMessage.getText());
                } else {
                    System.out.println("我是Receiver,收到消息如下: " + message);
                    session.commit(); break;
                }
            }

            consumer.close();
            session.close();
            connection.close();
            System.out.println(Thread.currentThread().getName()+" close");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sender() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);

            Connection connection = connectionFactory.createConnection();
            connection.start();
            System.out.println(Thread.currentThread().getName()+" start");

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination sendQueue = session.createQueue(queue);
            MessageProducer sender = session.createProducer(sendQueue);
            // sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // 递送模式

            for (int count = 0; count < 10; count++) {
                TextMessage message = session.createTextMessage();
                message.setText(new Date().getTime() + "现在发送是第" + count + "条消息");
                sender.send(message);
                session.commit();
                Thread.sleep(1000);
            }

            session.close();
            connection.close();
            System.out.println(Thread.currentThread().getName()+" close");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
