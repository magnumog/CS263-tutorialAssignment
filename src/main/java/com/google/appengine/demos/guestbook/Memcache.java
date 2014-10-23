package com.google.appengine.demos.guestbook;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class Memcache extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		String key = req.getParameter("key");
		String writtenValue = req.getParameter("value");
		String value;
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
//		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		value = (String) syncCache.get(key); //read from cache
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Memcache</title>");
		out.println("</head");
		out.println("<body>");
		
		if(value!=null) {
			out.println("<p>The key " + key + " is allready stored in memcache with the value:" + value + " assigned to it </p>");
		} else {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key memcachKey = KeyFactory.createKey("memcachKey", key);
			Query query = new Query("memcachKey",memcachKey);
			List<Entity> entity = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
			if(entity.isEmpty()) {
				out.println("<p>The key you entered was not in mencache or in the datastor</p>");
				Entity ent = new Entity(memcachKey);
				ent.setProperty("key",key);
				ent.setProperty("value", writtenValue);
				datastore.put(ent);
				out.println("<p>The key: </p>" + key + "<p> and the value: </p>" + writtenValue + "<p> is now stored in datastore</p>");
			} else {
				syncCache.put(key,writtenValue);
				out.println("<p>Found the key in datastore: </p>");
				out.println("<p>it was not in the memcache but is now</p>");
				Entity retEntity = entity.get(0);
				String datastoreEntry = retEntity.getProperty("value").toString();
				out.println("<p>The value retrived from datastore was: " + datastoreEntry);
				
			}
		}
		out.println("</body>");
		out.println("</html>");
		
		
	}

}
