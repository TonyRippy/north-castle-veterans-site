package myapp;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveCemeteryServlet extends SaveServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // Create/Load the Cemetery object being modified.
    Cemetery c;
    String oldId = s(req, "oldId");
    String newId = s(req, "newId");
    if (oldId == null) {
      // The record didn't exist before, we should create a new one.
      if (newId == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }
      c = new Cemetery(newId);
    } else {
      // Load the existing record from the database.
      c = new Cemetery(oldId);
      if (!c.readFromDatastore()) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      if (!oldId.equals(newId)) {
        // The ID has changed.
        // We need to remove the old record and create a new one.
        // TODO(trippy): Implement a delete method.
        if (newId == null) {
          // No new record, nothing more to do.
          sendSuccessResponse(resp, "/");
          return;
        }
        // Set the new identifier, which will result in a new record once saved.
        c.id = newId;
      }
    }

    // Read the rest of the fields and save the record.
    c.name = s(req, "name");
    c.description = s(req, "description");
    c.writeToDatastore();

    // Let the user know the request succeeded, redirect to the view page.
    sendSuccessResponse(resp, "/cemetery/" + newId);
  }
}
