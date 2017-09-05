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
    <style type="text/css">
      legend {
        font-weight: bold;
      }
      #sl-container {
        width: 980px;
      }
      #book-image img {
        width: 60%;
      }
      #book-text {
        margin-top: 20px;
        margin-bottom: 20px;
      }
    </style>
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

      var toUrl = function (pageNumber, suffix) {
        var bucket = 'https://storage.googleapis.com/north-castle-veterans-site-scans/';
        var pad = '000';
        var file = 'page-' + pad.substring(0, pad.length - pageNumber.length) + pageNumber + suffix;
        return bucket + file;
      };
      var toImage = function (url) {
        return '<a href="' + url + '" target="_blank" rel="alternate">'
             + '<img src="' + url + '"></img></a>';
      };
      var onPageChange = function(event) {
        var pageNum = event.target.value;
        if (!pageNum) return;
        $('#book-image').html(toImage(toUrl(pageNum, '.jpg')));
        $.get(toUrl(pageNum, '.txt'), function (txt) {
            $('#book-text').html(txt);
        }).fail(function () {
            $('#book-text').html('<em>Scanned text unavailable.</em>');
        });
      };
      $( document ).ready(function () {
        var n = $('#book-page');
        n.on('input change', onPageChange);
        onPageChange({'target': n[0]});
      });
    </script>
  </head>
  <body>
    <div id="sl-container" class="container_12">
      <div id="sl-content" class="grid_12">
        <h1 class="grid_12 alpha omega">
          Edit Veteran
        </h1>
        <div class="clear"></div>
        <%
        Veteran v = Veteran.forPath(request.getPathInfo());
        if (v == null) {
          v = new Veteran(null, null);   
        } else {
          v.readFromDatastore();
        }
        %>
        <form action="/__save__/veteran" method="post" class="grid_12 alpha omega">
          <div class="grid_6 alpha">
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
          </div>
          <div id="pages" class="grid_6 omega">
          <fieldset>
            <legend>Pouder Book</legend>
            <p>
              To aid you in entering in records about veterans, we have scanned 
              and processed George Pouder's book "Soldier, Rest". To use, select
              a page number below. This will bring up a preview of that page of 
              the book, and an attempt to convert the page contents to copy &amp;
              pasteable text.
            </p>
            <label for="book-page">Page number:</label>
            <input id="book-page" type="number" min="1" max="133" value="1"></input>                         <div id="book-image"></div>
            <p id="book-text"></p>
            </p>
          </div>
          <div class="clear"></div>
          <fieldset>
          <legend>Biography</legend>
          <textarea id="biography" name="biography" class="grid_12 alpha omega">
            <%= v.biography == null ? "" : v.biography %>
          </textarea>
          </fieldset>
          <fieldset>
          <legend>References</legend>
          <textarea id="references" name="references" class="grid_12 alpha omega">
            <%= v.references == null ? "" : v.references %>
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
