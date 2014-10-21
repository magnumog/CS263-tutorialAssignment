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
	UserService userService = UserServiceFactory.getUserService();
	String key = request.getParameter("key");
%>
<%-- //[START datastore] --%>
<%
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key guestbookKey = KeyFactory.createKey("key", key);
	Query query = new Query("Key", guestbookKey).addSort("date",Query.SortDirection.DESCENDING);
	List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
%>
<form action="/done.html" method="post">
	<p>You entered the key:  and the value:</p>
</form>
<%-- //[END datastore] --%>
<!-- <form action="/tqueue.jsp" method="get"> -->
<!-- 	<div><textarea name="value" rows="3" cols="50"></textarea></div> -->
<!-- 	<div><textarea name="content" rows="3" cols="50"></textarea></div> -->
<!-- 	<div><input type="submit" value="post"/> </div> -->
<!-- </form> -->
</body>
</html>
<%-- //[End All] --%>