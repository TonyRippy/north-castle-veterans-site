package myapp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveVeteranServlet extends HttpServlet {

  /** Normalizes a request parameter to null if missing, empty or whitespace. */
  private static String s(HttpServletRequest request, String parameterName) {
    String value = request.getParameter(parameterName);
    if (value == null) {
      return null;
    }
    value = value.trim();
    if (value.isEmpty()) {
      return null;
    }
    return value;
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // Create/Load the Veteran object being modified.
    Veteran v;
    String oldId = s(req, "oldId");
    String newId = s(req, "newId");
    String oldCemeteryId = s(req, "oldCemeteryId");
    String newCemeteryId = s(req, "newCemeteryId");
    if (oldId == null) {
      // The record didn't exist before, we should create a new one.
      if (newId == null || newCemeteryId == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }
      v = new Veteran(newId, newCemeteryId);
    } else {
      // Load the existing record from the database.
      if (oldCemeteryId == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }
      v = new Veteran(oldId, oldCemeteryId);
      if (!v.readFromDatastore()) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      if (!oldId.equals(newId) || !oldCemeteryId.equals(newCemeteryId)) {
        // The ID has changed.
        // We need to remove the old record and create a new one.
        if (newId != null && (newCemeteryId == null || newCemeteryId.isEmpty())) {
          resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
          return;
        }
        // We need to remove the old record.
        // TODO(trippy): Implement a delete method.
        if (newId == null) {
          // No new record, nothing more to do.
          return;
        }
        // Set the new identifiers, which will result in a new record once saved.
        v.id = newId;
        v.cemeteryId = newCemeteryId;
      }
    }

    // Read the rest of the fields and save the record.
    v.firstName = s(req, "firstName");
    v.middleName = s(req, "middleName");
    v.lastName = s(req, "lastName");
    v.born = s(req, "born");
    v.died = s(req, "died");
    v.eyes = s(req, "eyes");
    v.height = s(req, "height");
    v.sex = s(req, "sex");
    v.biography = s(req, "biography");
    v.references = s(req, "references");
    v.writeToDatastore();

    // Display an animation to let the user know the write succeeded,
    // then redirect the browser to the veteran's page. 
    resp.setContentType("text/html");
    resp.getWriter()
      .append("<html>")
      .append("<head>")
      // Delayed redirect
      .append("<meta http-equiv=\"refresh\" content=\"2;") // wait 2 seconds
      .append("url=/veteran/").append(newCemeteryId).append('/').append(newId).append("\"/>")
      .append("</head>")
      // Embedded "green check" animation from Giphy
      .append("<body>")
      .append("<iframe ")
      .append(  "src=\"https://giphy.com/embed/8GY3UiUjwKwhO\"")
      .append(  "width=\"480\" ")
      .append(  "height=\"360\" ")
      .append(  "frameBorder=\"0\" ")
      .append(  "class=\"giphy-embed\" ")
      .append(  "style=\"margin: auto\" ")
      .append(  "allowFullScreen>")
      .append("</iframe>")
      .append("<p style=\"font-size: small\">")
      .append("<a href=\"https://giphy.com/gifs/check-8GY3UiUjwKwhO\">via GIPHY</a>")
      .append("</p>")
      .append("</body>")
      .append("</html>");
  }
}
