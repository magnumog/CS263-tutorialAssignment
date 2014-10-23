<%-- //[Start All] --%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.datastore.Query"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@page import="java.util.Date"%>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/main.css">
</head>
<body>
<%
	String key = request.getParameter("key");
	if(key == null) {
	%>
	<p>There is nothing added into the key value </p>
	<%
	} else {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key queryKey = KeyFactory.createKey("Work",key);
		Query query = new Query("Work",queryKey);
		List<Entity> taskData = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
		if(taskData.isEmpty()) {
		%>
			<p>Try refreshing the page in a couple of minutes, it maybe take some time until the datastore is updated </p>
		<%	
		} else {
			Entity task = taskData.get(taskData.size()-1);
			String value = task.getProperty("value").toString();
			%>
			<p>The key-value is: <%= key %> and the value is: <%= value %></p>
			<%  
		}
	}
	%>
	
</body>
</html>
<%-- //[End All] --%>