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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=ixuyq1vlmya4hji8r3wn575whswzx0039e8incvhia67yto2"></script>
    <script>
      tinymce.init({
        selector: '#biography',
        plugins: 'lists, link'
      });
      tinymce.init({
        selector: '#references',
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
        if (v == null) {
          v = new Veteran(null, null);   
        } else {
          v.readFromDatastore();
        }
        %>
        <form action="/__save__/veteran" method="post">
          <fieldset>
          <legend>Identification</legend>
          <p>
            <label for="newId">Id:</label>
            <input type="text" id="newId" name="newId"
              value="<%= v == null || v.id == null ? "" : v.id %>">
            <input type="hidden" id="oldId" name="oldId"
              value="<%= v == null || v.id == null ? "" : v.id %>">
          </p><p>
            <label for="newCemeteryId">Burial Site:</label>
            <select id="newCemeteryId" name="newCemeteryId">
              <% for (Cemetery c : Cemetery.listAll()) { %>
                <option value="<%= c.id %>"
                    <% if (c.id.equals(v.cemeteryId)) { %> selected <% } %> >
                  <%= c.name %>
                </option>
              <% } %>
            </select>
            <input type="hidden" id="oldCemeteryId" name="oldCemeteryId"
                   value="<%= v == null || v.cemeteryId == null ? "" : v.cemeteryId %>">
          </p>
          </fieldset>
          <fieldset>
          <legend>Name:</legend>
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
          </fieldset>
          <fieldset>
          <legend>Statistics</legend>
          <p>
            <label for="born">Born:</lablel>
            <input type="text" id="born" name="born"
              value = "<%= v == null || v.born == null ? "" : v.born %>">
          </p><p>
            <label for="died">Died:</lablel>
            <input type="text" id="died" name="died"
              value = "<%= v == null || v.died == null ? "" : v.died %>">
          </p><p>
            <label for="height">Height:</lablel>
            <input type="text" id="height" name="height"
              value = "<%= v == null || v.height == null ? "" : v.height.replace("\"", "&quot;")  %>">
          </p><p>
            <label for="sex">Sex:</lablel>
            <select id="sex" name="sex">
                <option value="Male"
                    <% if (v == null || v.sex == null || v.sex.equals("Male")) { %>
                      selected
                    <% } %> >
                  Male
                </option>
                <option value="Female"
                    <% if (v.sex != null && v.sex.equals("Female")) { %>
                      selected
                    <% } %> >
                  Female
                </option>
                <option value="Other"
                    <% if (v.sex != null && v.sex.equals("Other")) { %>
                      selected
                    <% } %> >
                  Other
                </option>
            </select>
          </p><p>
            <label for="eyes">Eye Color:</lablel>
            <input type="text" id="eyes" name="eyes"
              value = "<%= v == null || v.eyes == null ? "" : v.eyes %>">
          </p>
          </fieldset>
          <fieldset>
          <legend>Biography</legend>
          <textarea id="biography" name="biography">
            <%= v.biography == null ? "" : v.biography %>
          </textarea>
          </fieldset>
          <fieldset>
          <legend>References</legend>
          <textarea id="references" name="references">
            <%= v.references == null ? "" : v.references %>
          </textarea>
          </fieldset>
          <input type="submit" value="Save" />
        </form>
      </div>
    </div>
  </body>
</html>

<%-- //[END all]--%>
