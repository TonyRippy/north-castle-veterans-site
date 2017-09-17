package myapp;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveVeteranServlet extends SaveServlet {
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
            sendSuccessResponse(resp, "/cemetery/" + oldCemeteryId);
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
    v.birthDate = s(req, "birthDate");
    v.birthLocation = s(req, "birthLocation");
    v.deathDate = s(req, "deathDate");
    v.deathLocation = s(req, "deathLocation");
    v.eyes = s(req, "eyes");
    v.height = s(req, "height");
    v.sex = s(req, "sex");
    v.biography = s(req, "biography");
    v.references = s(req, "references");
    v.pageNumber = n(req, "pageNumber");
    v.images = l(req, "images");
    v.writeToDatastore();

    // Let the user know the request succeeded, redirect to the view page.
    sendSuccessResponse(resp, "/veteran/" + newCemeteryId + "/" + newId);
  }
}
