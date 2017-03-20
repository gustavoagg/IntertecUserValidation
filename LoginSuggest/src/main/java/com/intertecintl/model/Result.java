package com.intertecintl.model;

import java.util.Collection;


public class Result<K,E extends Collection<?>> {
	
	private K key;
	private E values;
	
	public Result(K key, E values) {
		super();
		this.key = key;
		this.values = values;
	}

	public Result(K key) {
		super();
		this.key = key;
	}

	public Result(E values) {
		super();
		this.values = values;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public E getValues() {
		return values;
	}

	public void setValues(E values) {
		this.values = values;
	}

	
	


	

	
}
