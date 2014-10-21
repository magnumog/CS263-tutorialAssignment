package com.google.appengine.demos.guestbook;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadData extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String var1 = req.getParameter("key");
		String var2 = req.getParameter("value");
		
		System.out.println("Value: " + var2 + "Key: " +var1);
		
	}
}
