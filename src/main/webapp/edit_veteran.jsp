<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>
<%@ page import="myapp.Veteran" %>

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
    <script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>
    <script>
      tinymce.init({
        selector: '#biography',
        plugins: 'lists, link'
      });
    </script>
    <style type="text/css">
      .sl-content {
        padding: 30px;
      }
    </style>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div class="sl-content">
        <h1>Edit Veteran</h1>
        <%
        Veteran v = Veteran.forPath(request.getPathInfo());
        if (v != null) {
           v.readFromDatastore();
        }
        %>
        <form action="/__save__/veteran" method="post">
          <h2>Name:</h2>
          <p>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName"
              value="<%= v == null || v.firstName == null ? "" : v.firstName %>">
          </p><p>
            <label for="middleName">Middle Name:</label>
            <input type="text" id="middleName" name="middleName"
              value="<%= v == null || v.middleName == null ? "" : v.middleName %>">
          </p><p>
            <label for="lastName">Last Name:</lablel>
            <input type="text" id="lastName" name="lastName"
              value = "<%= v == null || v.lastName == null ? "" : v.lastName %>">
          </p>
          <h2>Biography:</h2>
          <textarea id="biography">
            <%= v.biography == null ? "" : v.biography %>
          </textarea>
          <input type="submit" value="Save" />
        </form>
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
