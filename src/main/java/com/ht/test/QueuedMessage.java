package com.ht.test;

public class QueuedMessage {
	public QueuedMessage(String message, String routingKey) {
		this.message = message;
		this.routingKey = routingKey;
	}
	public String message;
	public String routingKey;
}
