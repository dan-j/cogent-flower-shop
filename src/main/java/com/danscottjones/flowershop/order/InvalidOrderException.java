package com.danscottjones.flowershop.order;

public class InvalidOrderException extends Exception {
	public InvalidOrderException(String message) {
		super(message);
	}
}
