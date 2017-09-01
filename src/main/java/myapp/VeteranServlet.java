package myapp;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VeteranServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {    
    // Extract the cemeteryId and veteranId from the URL path.
    // Format is "/CEMETERY_ID/VETERAN_ID"
    String path = req.getPathInfo();
    if (path == null || path.length() == 0 || path.charAt(0) != '/') {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    int index = path.indexOf('/', 1); // Ignore the leading slash
    if (index < 0) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    String cemeteryId = path.substring(1, index);
    String veteranId = path.substring(index + 1);

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    Key key = datastore.newKeyFactory()
      .addAncestor(PathElement.of("Cemetery", cemeteryId))
      .setKind("Veteran")
      .newKey(veteranId);
    Query<Entity> query = Query.newEntityQueryBuilder()
      .setKind("Veteran")
      .setFilter(PropertyFilter.eq("__key__", key))
      .build();
    QueryResults<Entity> veterans = datastore.run(query);
    if (!veterans.hasNext()) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    }
    Entity veteran = veterans.next();
    StringBuilder json = new StringBuilder("{")        
      .append("\"id\": \"")
      .append(veteranId)
      .append("\"cemeteryId\": \"")
      .append(cemeteryId)
      .append("\", \"givenName\": \"")
      .append(veteran.getString("givenName"))
      .append("\"}");
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().print(json);
  }
}
