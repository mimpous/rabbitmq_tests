package com.ht.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
 
 

public class TestRabitMQ {
 
	public static void main(String[] args) throws Exception {
		new TestRabitMQ(new ApplicationProperties()).testMessage();
	}
	private final ApplicationProperties props;
	
	public TestRabitMQ( ApplicationProperties props)  {
		this.props = props;
	}
 
	public void testMessage() throws Exception {
	 
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setNetworkRecoveryInterval(10000);
		factory.setRequestedHeartbeat(10);
		factory.setConnectionTimeout(5000);
		factory.setAutomaticRecoveryEnabled(true);
		factory.setTopologyRecoveryEnabled(false);
		//factory.useSslProtocol();	
		factory.setUri(props.getRabbitmqConnectionString());

		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();

		channel.queueDeclare(props.getRabbitmqTestQueue(), true, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", props.getRabbitmqTestQueue(), null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();

	}
	
	
	private static Object receiveChannelSync = new Object();
	private static Channel receiveChannel;
	 
	private static Object receiveMapSync = new Object();
	
	public void testChannel( String vehicleId ) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setNetworkRecoveryInterval(10000);
		factory.setRequestedHeartbeat(10);
		factory.setConnectionTimeout(10000);
		factory.setAutomaticRecoveryEnabled(true);
		factory.setTopologyRecoveryEnabled(false);

		factory.useSslProtocol();	
//		factory.setUsername("obisbus");
//		factory.setPassword("x67J90sX");
//		factory.setVirtualHost("obis");
//		factory.setHost("st-amqp.swarco.cloud");
//		factory.setPort(5671); 

		
		factory.setUri(props.getRabbitmqConnectionString());
		
		//amqp\://obisbus\:x67J90sX@st-amqp.swarco.cloud\:5671/obis
		
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setUsername("obisbus");
//		factory.setPassword("x67J90sX");
//		factory.setVirtualHost("obis");
//		factory.setHost("st-amqp.swarco.cloud");
//		factory.setPort(5671); 
//
		
		

		Connection connection = factory.newConnection();
		
		try {
			String routingkey = String.format("%s.#", vehicleId);
			String commQueue = String.format("__cmd_%s__", vehicleId);
			
			synchronized (receiveChannelSync) {
				if(null == receiveChannel || !receiveChannel.isOpen())
				{
					receiveChannel = connection.createChannel();
					receiveChannel.queueDeclare(commQueue, false, true, true, null);
					Consumer consumer = new DefaultConsumer(receiveChannel){
						@Override
						public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
							String message = new String(body);
							
							String rk = envelope.getRoutingKey();

							String[] rKeyArr = rk.split("\\.");
							if(rKeyArr.length > 1) {
								synchronized (receiveMapSync) {
									if(_recieveQueue.containsKey(rKeyArr[1]))
										_recieveQueue.get(rKeyArr[1]).offer(message);	
								}
							}	
						}
					};
					receiveChannel.basicConsume(commQueue, true, consumer);
					receiveChannel.queueBind(commQueue, "center2bus", routingkey);
					Thread.sleep(500);
				}
			}
//			while(null != _conn && _conn.isOpen() && receiveChannel != null){
//				Thread.sleep(5000);
//				if(lastConnectedMessage + 10000 < System.currentTimeMillis()){
//					ToolChain.getToolChain().EventServer().raiseEvent("int.vehicle.connectedtocentral", "true");
//					lastConnectedMessage = System.currentTimeMillis();
//				}
//			}
			
		} catch(Exception ex) {
			Thread.sleep(1000);
			System.out.println( "Error in receive connect" +  ex);
							
		}
	}
	
private static Map<String, ArrayBlockingQueue<String>> _recieveQueue = new HashMap<String, ArrayBlockingQueue<String>>();
	
	private static ArrayBlockingQueue<QueuedMessage> _sendqueue = new ArrayBlockingQueue<QueuedMessage>(500);
	
	private static Semaphore _sendSem = new Semaphore(0, true);
	
	/**
	 * Send data to a specific tag
	 * @param data
	 * @param tag
	 */
	public static synchronized void send(String data, String tag) {
		_sendqueue.offer(new QueuedMessage(data, tag));
		_sendSem.release();
	}
	
	/**
	 * Read data from a tag
	 * @param tag
	 * @return the available data
	 */
	public static synchronized String read(String tag) {
		synchronized (receiveMapSync) {
			if(!_recieveQueue.containsKey(tag))
				_recieveQueue.put(tag, new ArrayBlockingQueue<String>(500));
			
			return _recieveQueue.get(tag).poll();	
		}
	}	
}
