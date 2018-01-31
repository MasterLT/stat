package com.wasu.es.aop;

import com.wasu.es.service.IDataService;
import com.wasu.es.service.impl.DataService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by MASTER_L on 2018/1/25.
 */
public class TimeHandler implements InvocationHandler {

    private Object object;//真实角色

    /**
     * 所有的流程控制都在invoke方法中
     * proxy：代理类
     * method：正在调用的方法
     * args：方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法前.....");
        object = method.invoke(object, args);//激活调用的方法
        System.out.println("方法后.....");
        return object;
    }

    //通过构造器来初始化真实角色
    public TimeHandler(Object object) {
        super();
        this.object = object;
    }

    /**
     * 动态代理test
     * @param args
     */
    public static void main(String[] args) {
        IDataService iDataService = new DataService();
        InvocationHandler handler = new TimeHandler(iDataService);
        IDataService Proxy= (IDataService)java.lang.reflect.Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDataService.class},handler);
        Proxy.toString();
    }
}
