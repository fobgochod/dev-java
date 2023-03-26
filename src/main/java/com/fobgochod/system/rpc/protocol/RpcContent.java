package com.fobgochod.system.rpc.protocol;

import java.io.Serializable;

/**
 * 请求服务信息、和响应结果
 *
 * @author fobgochod
 * @date 2021/8/9 15:27
 */
public class RpcContent implements Serializable {

    private String name;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
    /**
     * 返回的数据
     */
    private Object resData;

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
