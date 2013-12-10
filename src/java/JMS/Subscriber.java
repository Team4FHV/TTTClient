/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import DTO.objecte.DTOMessage;
import client.Client;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author media
 */
public class Subscriber implements MessageListener {

    String topicname;
    String topicConnectionFactoryName = "jms/topicConnectionFactory";
    Context jndiContext = null;
    TopicConnectionFactory topicConnectionFactory = null;
    TopicConnection topicConnection = null;
    TopicSession topicSession = null;
    Topic topic = null;
    String host = "localhost";
    Client client;

    public Subscriber( Client client) {
         this.client = client;
    }

    public Subscriber(String topicname, Client client) {
        this.topicname = topicname;
        this.client = client;
        System.out.println("Subscriber created " + topicname + "client=" + client.toString());
        start();
    }

    public void start() {

        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("org.omg.CORBA.ORBInitialHost", host);//ur server ip  
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); //default is 3700  

        try {
            jndiContext = new InitialContext(props);
            topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup(topicConnectionFactoryName);
            System.out.println("TopicConnectionFactory created");

        } catch (NamingException e) {
            System.out.println("JNDI lookup failed: "
                    + e.toString());

        }
    }

    public void subscribe() throws NamingException {

        try {
            Topic topic = (Topic) jndiContext.lookup("jms/" + topicname);
            jndiContext.close();

            topicConnection = topicConnectionFactory.createTopicConnection();

            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);


            // create a topic subscriber
            TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);

            // start the connection
            topicConnection.start();


//            ObjectMessage msg = (ObjectMessage) topicSubscriber.receive();
           
            // wait for messages
            System.out.print("waiting for messages\n");

            // set an asynchronous message listener
            topicSubscriber.setMessageListener(new Subscriber(client));

        } catch (JMSException e) {
            System.out.println("Exception occurred: " + e.toString());

        }
    }

    @Override
    public void onMessage(Message message) {
        DTOMessage m = null;
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage msg = (ObjectMessage) message;
                m = (DTOMessage) msg.getObject();
                System.out.println("received: "+ m.getTitle()+ "   "+ m.getText());
                this.client.addMessageToClient(m);

            } catch (JMSException ex) {
                Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void stop() throws JMSException {
        topicConnection.close();

    }

    public void setHost(String host) {
        this.host = host;
    }
}
