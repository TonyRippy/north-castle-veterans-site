<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Veteran" %>

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
          <li><a href="/pouder">George Pouder</a></li>
          <li><a href="/cemeteries">Cemeteries</a></li>
          <li class="selected disabled"><a href="/veterans">Veterans</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content">
        <h1>Veterans</h1>
        The following is a list of all veterans available on the site, listed alphabetically.
        Click on a name for more information.
        <ul>
          <% for (Veteran v : Veteran.listAll()) { %>
          <li>
            <a href="/veteran/<%= v.cemeteryId %>/<%= v.id %>">
              <%= v.lastName == null ? "" : v.lastName %>,
              <%= v.firstName == null ? "" : v.firstName %>
              <%= v.middleName == null ? "" : v.middleName %>
            </a>
          </li>
          <% } %>
        </ul>
      </div>
      <div id="sl-bkg-attribution">
        <a href="http://www.usafa.af.mil/News/Photos/igphoto/2001562927/">Background photo</a>
        provided by the
        <a href="http://www.usafa.af.mil/">United States Air Force Academy</a>.
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
