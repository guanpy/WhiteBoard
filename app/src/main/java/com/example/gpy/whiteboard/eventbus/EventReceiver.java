package com.example.gpy.whiteboard.eventbus;

public interface EventReceiver<T> {

	void onEvent(String name, T data);

}
