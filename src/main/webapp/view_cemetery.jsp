<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>
<%@ page import="myapp.Veteran" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
Cemetery selected = Cemetery.forPath(request.getPathInfo());
if (selected == null) {
  response.sendError(HttpServletResponse.SC_BAD_REQUEST);
  return;
}
if (!selected.readFromDatastore()) {
  response.sendError(HttpServletResponse.SC_NOT_FOUND);
  return;
}
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
      img {
        width: 90%;
      }
    </style>
    <!--
        Structured data for use by search engines.
        See https://developers.google.com/search/docs/guides/intro-structured-data
    -->
    <script type="application/ld+json">
      {
        "@context": "http://schema.org",
        "@type": "BreadcrumbList",
        "itemListElement": [{
          "@type": "ListItem",
          "position": 1,
          "item": {
            "@id": "https://northcastleveterans.org/cemeteries",
            "name": "Cemeteries"
          }
        },{
          "@type": "ListItem",
          "position": 2,
          "item": {
            "@id": "https://northcastleveterans.org/cemetery/<%= selected.id %>",
            "name": "<%= selected.name %>"
          }
        }]
      }
    </script>
    <script type="application/ld+json">
      {
        "@context": "http://schema.org/",
        "@type": "Place",
        "name": "<%= selected.name %>"
      }
    </script>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header">
        <h1>North Castle War Veterans</h1>
      </div>
      <div id="sl-menu">
        <ul>
          <li><a href="/">About</a></li>
          <li class="selected"><a href="/cemeteries">Cemeteries</a></li>
          <li><a href="/veterans">Veterans</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content" class="grid_12">
        <% if (userService.isUserLoggedIn() && userService.isUserAdmin()) { %>
        <div id="admin">
          <a href="<%= "/__edit__/cemetery" + request.getPathInfo() %>">
            Edit this cemetery.
          </a>
          <br>
          <a href="/__edit__/veteran/<%= selected.id %>/">
            Add new veteran to this cemetery.
          </a>
        </div>
        <% } %>
        <div id="sl-navlink">
          <a href="/cemeteries"><b>&larr;</b> Back to Cemeteries</a>
        </div>
        <div class="grid_7 alpha">
          <h1><%= selected.name %></h1>
          <%= selected.description == null ? "" : selected.description %>
          <% if (selected.veterans.size() > 0) { %>
          <h2 id="veterans">Veterans</h2>
          The following is a list of all veterans that were laid to rest at this cemetery.
          Click on a name for more information.
          <ul>
            <% for (Veteran v : selected.veterans) { %>
            <li>
              <a href="/veteran/<%= v.cemeteryId %>/<%= v.id %>">
                <%= v.lastName == null ? "" : v.lastName %>,
                <%= v.firstName == null ? "" : v.firstName %>
                <%= v.middleName == null ? "" : v.middleName %>
              </a>
            </li>
            <% } %>
          </ul>
          <% } %>
        </div>
        <div class="grid_5 omega">
          <% if (selected.image != null) { %>
          <img src="<%= selected.image %>"></img>
          <% } %>
        </div>
      </div>
      <div class="clear"></div>
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
