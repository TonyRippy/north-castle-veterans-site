<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>
<%@ page import="myapp.Veteran" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
Veteran selected = Veteran.forPath(request.getPathInfo());
if (selected == null) {
  response.sendError(HttpServletResponse.SC_BAD_REQUEST);
  return;
}
if (!selected.readFromDatastore()) {
  response.sendError(HttpServletResponse.SC_NOT_FOUND);
  return;
}
Cemetery cemetery = new Cemetery(selected.cemeteryId);
if (!cemetery.readFromDatastore()) {
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
      #dates span b {
        width: 7em;
      }
      #stats span {
        margin-right: 1em;
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
            "@id": "https://northcastleveterans.org/cemetery/<%= cemetery.id %>",
            "name": "<%= cemetery.name %>"
          }
        },{
          "@type": "ListItem",
          "position": 3,
          "item": {
            "@id": "https://northcastleveterans.org/veterans",
            "name": "Veterans"
          }
        },{
          "@type": "ListItem",
          "position": 4,
          "item": {
            "@id": "https://northcastleveterans.org/veteran/<%= cemetery.id %>/<%= selected.id %>",
            "name": "<%= selected.lastName %>, <%= selected.firstName %><%= selected.middleName == null ? "" : " " + selected.middleName %>"
          }
        }]
      }
    </script>
    <script type="application/ld+json">
      {
        "@context": "http://schema.org/"
        , "@type": "Person"
        , "givenName": "<%= selected.firstName %>"
        , "familyName": "<%= selected.lastName %>"
        <% if (selected.middleName != null) { %>
          , "additionalName": "<%= selected.middleName %>"
        <% } %>
        <% if (selected.sex != null) { %>
          , "gender": "http://schema.org/<%= selected.sex %>"
        <% } %>
        <% if (selected.birthDate != null) { %>
          , "birthDate": "<%= selected.birthDate %>"
        <% } %>
        <% if (selected.birthLocation != null) { %>
          , "birthPlace": "<%= selected.birthLocation %>"
        <% } %>
        <% if (selected.deathDate != null) { %>
          , "deathDate": "<%= selected.deathDate %>"
        <% } %>
        <% if (selected.deathLocation != null) { %>
          , "deathPlace": "<%= selected.deathLocation %>"
        <% } %>
      }
    </script>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header" class="grid_12">
        <h1>North Castle War Veterans</h1>
      </div>
      <div class="clear"></div>
      <div id="sl-menu">
        <ul>
          <li><a href="/">About</a></li>
          <li><a href="/cemeteries">Cemeteries</a></li>
          <li class="selected"><a href="/veterans">Veterans</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div class="clear"></div>
      <div id="sl-content" class="grid_12">
        <% if (userService.isUserLoggedIn() && userService.isUserAdmin()) { %>
        <div id="admin">
          <a href="/__edit__/veteran/<%= selected.cemeteryId %>/">
            Add new record.
          </a>
          <br>
          <a href="<%= "/__edit__/veteran" + request.getPathInfo() %>">
            Edit this record.
          </a>
        </div>
        <% } %>
        <div id="sl-navlink">
          <a href="/cemetery/<%= cemetery.id %>"><b>&larr;</b> <%= cemetery.name %></a>
        </div>
        <div class="grid_7 alpha">
          <h1>
            <%= selected.firstName %>
            <%= selected.middleName == null ? "" : selected.middleName %>
            <%= selected.lastName %>
          </h1>
          <div id="dates">
            <% if (selected.birthDate != null || selected.birthLocation != null) { %>
            <span>
              <b>Born:</b>
              <% if (selected.birthDate != null) { %>
                <%= selected.birthDate %>
              <% } %>
              <% if (selected.birthLocation != null) { %>
                In <%= selected.birthLocation %>
              <% } %>
            </span>
            <br/>
            <% } %>
            <% if (selected.deathDate != null || selected.deathLocation != null) { %>
            <span>
              <b>Died:</b>
              <% if (selected.deathDate != null) { %>
                <%= selected.deathDate %>
              <% } %>
              <% if (selected.deathLocation != null) { %>
                In <%= selected.deathLocation %>
              <% } %>
            </span>
            <br/>
            <% } %>
          </div>
          <div id="stats">
            <% if (selected.sex != null) { %>
            <span>
              <b>Sex:</b> <%= selected.sex %>
            </span>
            <% } %>
            <% if (selected.eyes != null) { %>
            <span>
              <b>Eyes:</b> <%= selected.eyes %>
            </span>
            <% } %>
            <% if (selected.height != null) { %>
            <span>
              <b>Height:</b> <%= selected.height %>
            </span>
            <% } %>
          </div>
          <p>
            <%= selected.biography == null ? "" : selected.biography %>
          </p>
        </div>
        <div class="grid_5 omega">
          <% if (selected.images == null) { %>
            <img src="/images/grave.jpg"></img>
          <% } else for (String url : selected.images) { %>
            <img src="<%= url %>"></img>
          <% } %>  
          <p>
            <%= selected.references == null ? "" : selected.references %>
          </p>
        </div>
      </div>
      <div class="clear"></div>
      <div id="sl-bkg-attribution">
        <a href="http://www.usafa.af.mil/News/Photos/igphoto/2001562927/">Background photo</a>
        provided by the
        <a href="http://www.usafa.af.mil/">United States Air Force Academy</a>.
        <%-- TODO: Is there an explicit license for image? --%>
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
