<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>

<!doctype html>
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="content-language" content="en-US">
    <meta name="keywords" content="veterans biography geneology">
    <meta name="description" content="Dedicated to honoring North Castle's deceased veterans.">
    <title>North Castle War Veterans</title>
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/text.css">
    <link rel="stylesheet" type="text/css" href="css/960.css">
    <link rel="stylesheet" type="text/css" href="css/theme.css">
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-header">
        <h1>North Castle War Veterans</h1>
      </div>
      <div id="sl-menu">
        <ul>
          <li class="selected"><a href="/">About</a></li>
          <%
          for (Cemetery c : Cemetery.listAll()) {
          %>
            <li>
              <a href="/cemetery/<%= c.id %>">
                <%= c.name %>
              </a>
            </li>
          <% } %>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div id="sl-content">
        <p>
          Welcome to the North Castle Veterans Website Eagle Scout Project!
        </p><p>
          The site is currently a work in progress and should be completed in Fall 2017.
        </p><p>
          Why this site is in production: Eagle Scout Candidate Jack Skiera wants to contribute to the community by creating a database for veterans that have passed.
        </p><p>
          Great Thanks to Tony Rippy for all the time spent to make this project possible!
        </p>
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
