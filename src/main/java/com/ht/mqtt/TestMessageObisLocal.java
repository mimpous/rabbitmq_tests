package com.ht.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class TestMessageObisLocal implements MqttCallback {
    MqttClient client;
    public void doDemo() {
        try {
        	
        	 
        	MqttConnectOptions option = new MqttConnectOptions();
        	option.setUserName("guest");
        	option.setPassword("guest".toCharArray()); 
        	
            client = new MqttClient("tcp://localhost:1883", "reports");
		    
            client.connect(option);
             client.setCallback(this);
             client.subscribe("bus2.topic");
             
            MqttMessage message = new MqttMessage();
            message.setPayload("A single message hello"
                    .getBytes());
            client.publish("vsi_reports1", message);
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
        new TestMessageObisLocal().doDemo();
    }
}
