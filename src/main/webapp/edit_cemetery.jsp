<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="myapp.Cemetery" %>

<%
Cemetery c = Cemetery.forPath(request.getPathInfo());
if (c == null) {
  c = new Cemetery(null);
} else {
  c.readFromDatastore();
}
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
      legend {
        font-weight: bold;
      }
      #sl-container {
        width: 980px;
      }
    </style>
    <script src="https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=ixuyq1vlmya4hji8r3wn575whswzx0039e8incvhia67yto2"></script>
    <script>
      tinymce.init({
        selector: '#description',
        plugins: 'lists, link'
      });
    </script>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-content" class="grid_12">
        <h1>Edit Cemetery</h1>
        <h2>Instructions</h2>
        <p>
          This is a form that will allow you to add or make changes to cemeteries on the site.
          The identification fields are required in order for the site to work properly,
          but all other fields are optional and can be left blank.
        </p>
        <form action="/__save__/cemetery" method="post">
          <h2>Data Entry</h2>
          <fieldset>
          <legend>Identification</legend>
          <p>
            <label for="newId">Id:</label>
            <input type="text" id="newId" name="newId"
              value="<%= c == null || c.id == null ? "" : c.id %>">
            <input type="hidden" id="oldId" name="oldId"
              value="<%= c == null || c.id == null ? "" : c.id %>">
          </p>
          <p>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name"
              value="<%= c == null || c.name == null ? "" : c.name %>">
          </p>
          </fieldset>
          <fieldset>
          <legend>Image</legend>
          <input type="text" id="image" name="image" size="100"
              value = "<%= c == null || c.image == null ? "" : c.image %>">
          </fieldset>
          <fieldset>
          <legend>Description</legend>
          <textarea id="description" name="description">
            <%= c.description == null ? "" : c.description %>
          </textarea>
          </fieldset>
          <input type="submit" value="Save" />
        </form>
      </div>
      <div class="clear"></div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
