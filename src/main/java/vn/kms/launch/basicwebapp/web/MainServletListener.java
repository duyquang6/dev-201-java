//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thanhtran
 *
 */
@WebListener
public class MainServletListener implements ServletContextListener, ServletContextAttributeListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private static final Logger LOG = LoggerFactory.getLogger(MainServletListener.class.getCanonicalName());

    @Override
    public void contextDestroyed(ServletContextEvent evnt) {
        // TODO Clean up/release any resource if needed.

    }

    @Override
    public void contextInitialized(ServletContextEvent evnt) {
        // TODO setup application-wide resource: database connection pool
        // TODO Read the initial values of application-wide data that will be
        // used by multiple servlets and JSP pages.
        LOG.info(evnt.getServletContext().getInitParameter("jdbc.host"));
        LOG.info(evnt.getServletContext().getInitParameter("jdbc.port"));
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent evnt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent evnt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent evnt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent evnt) {
        // TODO Auto-generated method stub
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent evnt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent evnt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionCreated(HttpSessionEvent evnt) {
        // TODO Auto-generated method stub
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent evnt) {
        // TODO Auto-generated method stub

    }

}
