<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>

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
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header">
        <h1>North Castle War Veterans</h1>
      </div>
      <div id="sl-menu">
        <ul>
          <li><a href="/">About</a></li>
          <%
          for (Cemetery c : Cemetery.listAll()) {
          %>
            <li>
              <a href="/cemetery/<%= c.id %>">
                <%= c.name %>
              </a>
            </li>
          <% } %>
          <li class="selected disabled"><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content">
        <p>
        This website was made possible by many hours of volunteer work and the kindness of others.
        In particular, we would like to recognize several individuals and organizations for their support.
        </p>
        <h1>Contributors</h1>
        <p>TODO</p>
        <h1>Services</h1>
        <p>TODO</p>
        <h1>Images</h1>
        The site's <a href="http://www.usafa.af.mil/News/Photos/igphoto/2001562927/">background photo</a>
        was made publicly available by the <a href="http://www.usafa.af.mil/">United States Air Force Academy</a>.
        The flag icon used in the URL bar was originally provided by
        <a href="https://openclipart.org/detail/193886/United%20States%20Flag">Open Clip Art</a>, and converted to an icon by
        <a href="https://www.freefavicon.com/freefavicons/flags/iconinfo/united-states-flag-152-193886.html">Free Favicon</a>.
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
