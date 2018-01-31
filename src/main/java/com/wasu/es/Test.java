package com.wasu.es;

import com.wasu.es.aop.TimeHandler;
import com.wasu.es.service.IDataService;
import com.wasu.es.service.impl.DataService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.Proxy;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.lang.reflect.InvocationHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MASTER_L on 2018/1/12.
 */
public class Test {
    public static void main(String[] args) {
        IDataService iDataService = new DataService();
        InvocationHandler handler = new TimeHandler(iDataService);
        IDataService Proxy= (IDataService)java.lang.reflect.Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDataService.class},handler);
        Proxy.toString();
        SpecialProvider a;
    }
}
