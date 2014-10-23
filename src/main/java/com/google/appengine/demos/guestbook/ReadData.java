package com.google.appengine.demos.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public class ReadData extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Added tasks into datastore from worker</title>");
		out.println("</head>");
		out.println("<body>");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Work").addSort("date", Query.SortDirection.DESCENDING);
		List<Entity> entityDatastore = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1000));
		
		if(entityDatastore.isEmpty()) {
			out.println("<p>There is no task that has been processed yet.<p>");
		} else {
			out.println("<table>");
			for(Entity entity : entityDatastore) {
				String key = (String)entity.getProperty("key");
				String date = (String)entity.getProperty("date").toString();
				String value = (String)entity.getProperty("value");
				
				out.println("<tr>");
				out.println("<td>" + key + "</td>");
				out.println("<td>" + value + "</td>");
				out.println("<td>" + date + "</td>");
				out.println("</tr>");
				
			}
			out.println("</table>");
		}
		
		
		
//		resp.sendRedirect("/test");
		
	}
}
