package com.poweruniverse.nim.data.entity.base;

public interface EntityI {
	public Integer pkValue();
	public String pkName();
	public void pkNull();
	public EntityI clone();
}
