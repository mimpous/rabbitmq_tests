package com.ht.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTClientTest implements MqttCallback {

    MqttClient client;
    public static void main(String[] args) throws MqttException {
        new MQTTClientTest().demoClient();
    }
    
    public void demoClient( ) throws MqttException {
        
        MqttConnectOptions option = new MqttConnectOptions();
        option.setCleanSession(true);
        option.setKeepAliveInterval(30);
        option.setUserName("guest");
        option.setPassword("guest".toCharArray()); 
        
        client = new MqttClient("tcp://localhost:1883", "testvm");
        client.setCallback(this);
        client.connect(option);
         
          
        MqttMessage message = new MqttMessage( );
        message.setRetained(false);
        
        message.setQos(2);
        message.setPayload("A single asas hello"
                .getBytes());
        client.publish("vsi_reports1", message);
        
        client.disconnect();
         
    }       
         
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
        
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        
    }
}
