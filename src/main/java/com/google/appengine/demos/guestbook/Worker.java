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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Worker extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String key = req.getParameter("key");
		String value = req.getParameter("value");
		Key workKey = KeyFactory.createKey("Work", key);
		Date date = new Date();
		Entity work = new Entity(workKey);
		work.setProperty("key", key);
		work.setProperty("date", date);
		work.setProperty("value", value);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(work);
		
		resp.sendRedirect("/work.jsp");
		
	}
}
