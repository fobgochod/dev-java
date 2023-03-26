package com.fobgochod.system.rpc.dispatcher;

import com.fobgochod.system.rpc.service.Car;
import com.fobgochod.system.rpc.service.CarService;
import com.fobgochod.system.rpc.service.Fly;
import com.fobgochod.system.rpc.service.FlyService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册和发现
 *
 * @author fobgochod
 * @date 2021/8/9 15:43
 */
public class Dispatcher {

    private static Dispatcher dispatcher = null;

    static {
        dispatcher = new Dispatcher();
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    private Dispatcher() {

    }

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k, Object obj) {
        invokeMap.put(k, obj);
    }

    public Object get(String k) {
        return invokeMap.get(k);
    }

    public static void registerService() {
        CarService car = new CarService();
        FlyService fly = new FlyService();
        Dispatcher dispatcher = Dispatcher.getDispatcher();
        dispatcher.register(Car.class.getName(), car);
        dispatcher.register(Fly.class.getName(), fly);
    }
}
