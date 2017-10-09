//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class.getCanonicalName());

    public void executeMethod(String methodName, HttpServletRequest req, HttpServletResponse resp) {
        // This piece of code does not handle method with the same name,
        // you can improve it or you MUST declare unique method name
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                try {
                    method.invoke(this, req, resp);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }
}
