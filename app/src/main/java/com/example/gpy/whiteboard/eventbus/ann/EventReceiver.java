package com.example.gpy.whiteboard.eventbus.ann;

public interface EventReceiver<T> {

	void onEvent(String name, T data);

}
