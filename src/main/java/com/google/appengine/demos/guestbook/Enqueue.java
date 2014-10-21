package com.google.appengine.demos.guestbook;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

public class Enqueue extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String writtenKey = request.getParameter("key");
        Key key = KeyFactory.createKey("key", writtenKey);
        String param = request.getParameter("value");
        Date date = new Date();
        Entity greeting = new Entity("tqueue", key);
        greeting.setProperty("key", writtenKey);
        greeting.setProperty("value", param);
        greeting.setProperty("date", date);
        Queue queue = QueueFactory.getDefaultQueue();
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(greeting);
        queue.add(withUrl("/worker").param("key", writtenKey).param("value", param));

//        response.sendRedirect("/done.html");
//        response.sendRedirect("/test.html");
        response.sendRedirect("/tqueue.jsp?key=" + key);
    }
}
