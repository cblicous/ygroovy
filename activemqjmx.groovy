package de;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import javax.management.remote.*;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;


public void runme(){
    MBeanServerConnection conn;
    try{
        def queueViewMBeanList = ['incoming.dealeraccount.productdata.search.queue', 'outgoing.dealeraccount.productdata.search.queue'];
        conn =  connectToServer("service:jmx:rmi:///jndi/rmi://localhost:9003/jmxrmi",null,null);

        println("connected");

        ObjectName activeMQ = new ObjectName("org.apache.activemq:BrokerName=amqBroker,Type=Broker");
        BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, activeMQ,BrokerViewMBean.class, true);

        println("mbean get");
        queueViewMBeanList.each{
            QueueViewMBean bean = findQueueMBean(conn, mbean, it);
            println("Queue" + bean + " Size " + bean.getQueueSize());
        }

		}
		catch ( IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( MalformedObjectNameException  e){
			 e.printStackTrace();
		} finally {
      //if (conn != null) {
      //  conn.close();
      //}
    }
	}

  private MBeanServerConnection connectToServer(String urlString, String username, String password) {
    if (username && password) {
        HashMap environment = new HashMap();
        String[] credentials =  [username, password].toArray();
        environment.put (JMXConnector.CREDENTIALS, credentials);
        JMXServiceURL url = new JMXServiceURL(urlString);
        JMXConnector jmxc = JMXConnectorFactory.connect(url,environment);
        return jmxc.getMBeanServerConnection();
      } else {
        HashMap   environment = new HashMap();
        JMXServiceURL url = new JMXServiceURL(urlString);
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        return jmxc.getMBeanServerConnection();
      }
  }

  private QueueViewMBean findQueueMBean( MBeanServerConnection conn,  BrokerViewMBean mbean, String queueName) {
    def queueBeans = mbean.getQueues().collect {
          (QueueViewMBean)  MBeanServerInvocationHandler.newProxyInstance(conn, it, QueueViewMBean.class, true);
    }

    return  queueBeans.find{
          it.getName().equals(queueName)
      }
  }


runme();

