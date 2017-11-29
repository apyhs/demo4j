package demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * 点对点DEMO
 */
public class ActivemqPTPDemo {
    private static final String url = "tcp://localhost:61616";
    private static final String user = "root";
    private static final String password = "root";
    private static final String queue = "queue";

    @Test
    public void PTPSender() {
        try {
            // 根据用户名，密码，url创建一个连接工厂
            ConnectionFactory factory = new ActiveMQConnectionFactory(user, password, url);
            // 从工厂中获取一个连接
            Connection connection = factory.createConnection();
            connection.start();
            // 创建一个session
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这里就是连接了一个 queue变量 所指的队列，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
            // 目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic
            Destination destination = session.createQueue(queue);
            // 从session中，获取一个消息生产者
            // 生产者，就是产生数据的对象
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            // DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            // DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // 创建一条消息，当然，消息的类型有很多，如文字，字节，对象等,可以通过session.create..方法来创建出来
            TextMessage textMsg = session.createTextMessage("Hello, World! ");
            for(int i = 0 ; i < 100 ; i ++){
                //发送一条消息
                producer.send(textMsg);
            }

            System.out.println("发送消息成功");
            // 即便生产者的对象关闭了，程序还在运行哦
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PTPReceiver() {
        try {
            // 根据用户名，密码，url创建一个连接工厂
            ConnectionFactory factory = new ActiveMQConnectionFactory(user, password, url);
            // 从工厂中获取一个连接
            Connection connection = factory.createConnection();
            connection.start();
            // 创建一个session
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这里就是连接了一个 queue变量 所指的队列，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
            Destination destination = session.createQueue(queue);
            // 根据session，创建一个接收者对象
            MessageConsumer consumer = session.createConsumer(destination);


            // 实现一个消息的监听器
            // 实现这个监听器后，以后只要有消息，就会通过这个监听器接收到
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        // 获取到接收的数据
                        String text = ((TextMessage)message).getText();
                        System.out.println(text);
                        // CLIENT_ACKNOWLEDGE 下消息
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 关闭接收端，也不会终止程序哦
             consumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
