package com.anz.common.ioc;

public interface IIoCFactory {
	
	<T> T getBean(String name, Class<T> clazz);

	<T> T getBean(Class<T> clazz);
}
