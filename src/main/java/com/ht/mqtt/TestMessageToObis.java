package com.ht.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class TestMessageToObis implements MqttCallback {
    MqttClient client;
    public void doDemo() {
        try {
        	
            
        	MqttConnectOptions connectionOptions = new MqttConnectOptions();
		 	 
             
			MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			
			connectionOptions.setUserName("guest");
			connectionOptions.setPassword("guest".toCharArray());
			
			
            client.connect( connectionOptions );
             
            MqttMessage message = new MqttMessage();
            message.setPayload("$GPRMC,114221.041,A,5740.7766,N,01200.2149,E,0.52,358.12,090718,,,A*68".getBytes());
            client.publish("mmi/gateway/sensor/gps/nmea/GPRMC", message);
            client.disconnect();
            
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void connectionLost(Throwable arg0) {
        // TODO Auto-generated method stub
        
    } 
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // TODO Auto-generated method stub
        
    } 
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        System.out.println(arg1);
    }

    public static void main(String[] args) {
        new TestMessageToObis().doDemo();
    }
}
