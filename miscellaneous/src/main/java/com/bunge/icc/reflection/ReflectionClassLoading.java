package com.bunge.icc.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionClassLoading {

	
	public static void main(String args[]){
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		String className = "com.bunge.icc.reflection.DemoClass";
		
			Class<?> cl;
			try {
				cl = Class.forName(className);
				Constructor<?> argCon = cl.getConstructor(String.class);
				Object obj = argCon.newInstance("Hello Reflection");
				
				Class[] parameterTypes = new Class[]{};
				Method printMethod = cl.getMethod("printStrValue", parameterTypes);
				
				printMethod.invoke(obj, new Object[]{});
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}


class DemoClass{
	String strVal;
	
	public DemoClass(String arg){
		this.strVal = arg;
	}
	
	public void demoMethod(String demoParam){
		System.out.println("Parameter Value := "+demoParam);
	}
	
	public void printStrValue(){
		System.out.println("Str Value :="+ this.strVal);
	}
}