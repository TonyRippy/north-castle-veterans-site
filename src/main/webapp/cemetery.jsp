<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>
<%@ page import="myapp.Veteran" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="content-language" content="en-US">
    <title>North Castle War Veterans</title>
    <link rel="stylesheet" type="text/css" href="/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/css/text.css">
    <link rel="stylesheet" type="text/css" href="/css/960.css">
    <link rel="stylesheet" type="text/css" href="/css/theme.css">
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header">
        <h1>North Castle War Veterans</h1>
      </div>
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
      %>
      <div id="sl-menu">
        <ul>
          <li><a href="/">About</a></li>
          <%
          for (Cemetery c : Cemetery.listAll()) {
          %>
            <li <c:if test="${c.id == selected.id}"> class="selected" </c:if> >
              <a href="/cemetery/<%= c.id %>">
                <%= c.name %>
              </a>
            </li>
          <% } %>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content">
        <h1><%= selected.name %></h1>
        <% if (selected.veterans.size() > 0) { %>
        <h2 id="veterans">Veterans</h2>
        The following is a list of all veterans that were laid to rest at this cemetery.
        Click on a name for more information.
        <ul>
          <% for (Veteran v : selected.veterans) { %>
          <li>
            <a href="veteran/<%= v.cemeteryId %>/<%= v.id %>">
              <%= v.lastName == null ? "" : v.lastName %>,
              <%= v.firstName ==null ? "" : v.firstName %>
              <%= v.middleName == null ? "" : v.middleName %>
            </a>
          </li>
          <% } %>
        </ul>
        <% } %>
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
