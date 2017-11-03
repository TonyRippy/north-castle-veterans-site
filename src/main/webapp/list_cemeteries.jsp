<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
UserService userService = UserServiceFactory.getUserService();
%>

<!doctype html>
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="content-language" content="en-US">
    <title>North Castle War Veterans</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"> 
    <link rel="stylesheet" type="text/css" href="/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/css/text.css">
    <link rel="stylesheet" type="text/css" href="/css/960.css">
    <link rel="stylesheet" type="text/css" href="/css/theme.css">
    <style type="text/css">
      #admin {
        float: right;
        padding-right: 20px;
      }
    </style>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header">
        <h1>North Castle War Veterans</h1>
      </div>
      <div id="sl-menu">
        <ul>
          <li><a href="/">About</a></li>
          <li class="selected disabled"><a href="/cemeteries">Cemeteries</a></li>
          <li><a href="/veterans">Veterans</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content">
        <% if (userService.isUserLoggedIn() && userService.isUserAdmin()) { %>
        <div id="admin">
          <a href="/__edit__/cemetery/">
            Add new cemetery.
          </a>
        </div>
        <% } %>
        <h1>Cemeteries</h1>
        The following is a list of local cemeteries where veterans have been
        laid to rest in the town of North Castle, listed alphabetically.
        Click on a name for more information, including the names of veterans
        that are buried there.
        <ul>
          <% for (Cemetery c : Cemetery.listAll()) { %>
          <li>
            <a href="/cemetery/<%= c.id %>">
              <%= c.name %>
            </a>
          </li>
          <% } %>
        </ul>
      </div>
      <div id="sl-bkg-attribution">
        <a href="http://www.usafa.af.mil/News/Photos/igphoto/2001562927/">Background photo</a>
        provided by the
        <a href="http://www.usafa.af.mil/">United States Air Force Academy</a>.
        <!-- TODO: Is there an explicit license for image? -->
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
