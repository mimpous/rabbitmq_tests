package com.ht.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TestPublisher {

	   public void doDemo() {
	        try {
	        	
	            
	        	MqttConnectOptions connectionOptions = new MqttConnectOptions();
			 	 
	             
			//	MqttClient client = new MqttClient("tcp://127.0.0.1:1883", MqttClient.generateClientId());
//				 

	        	 MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
              connectionOptions.setUserName("guest:guest");
              connectionOptions.setPassword("guest".toCharArray());
//           
	             
	            
				
	            client.connect( connectionOptions );
	             
	            MqttMessage message = new MqttMessage();
	            message.setPayload("22222222$GPRMC,114221.041,A,5740.7766,N,01200.2149,E,0.52,358.12,090718,,,A*68".getBytes());
	            client.publish("/bus.topic", message);
	            client.disconnect();
	            client.close();
	            
	        } catch (MqttException e) {
	            e.printStackTrace();
	        }
	    }

	   
	   public static void main(String[] args) {
		new TestPublisher().doDemo();
	}
}
