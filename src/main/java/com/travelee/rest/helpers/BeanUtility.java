package com.travelee.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 *
 * @author nguyenphucuit
 */
public class BeanUtility {

    private static BeanUtilsBean _instance = null;

    public static BeanUtilsBean getBeanUtility() {
        if (_instance == null) {
            _instance = new NullAwareBeanUtilsBean();
        }
        return _instance;
    }

    public static void copyProperties(Object dest, Object origin) throws IllegalAccessException, InvocationTargetException {
        getBeanUtility().copyProperties(dest, origin);
    }
}
