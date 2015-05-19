package com.travelee.rest.helpers;

import com.travelee.rest.resource.photo.PhotoList;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author nguyenphucuit
 */
@XmlRegistry
public class ResponseFactory {

    public PhotoList createPhotoResponse() {
        return createInstance(PhotoList.class);
    }

    private <T> T createInstance(Class<T> anInterface) {
        return (T) Proxy.newProxyInstance(anInterface.getClassLoader(), new Class[]{anInterface}, new InterfaceInvocationHandler());
    }

    private static class InterfaceInvocationHandler implements InvocationHandler {

        private Map<String, Object> values = new HashMap<String, Object>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                return values.get(methodName.substring(3));
            } else {
                values.put(methodName.substring(3), args[0]);
                return null;
            }
        }

    }
}
