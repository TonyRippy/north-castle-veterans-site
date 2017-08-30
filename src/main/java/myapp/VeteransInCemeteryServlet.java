package myapp;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VeteransInCemeteryServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    String cemeteryName = req.getPathInfo().substring(1); // Remove the leading slash from the path.

    StringBuilder json = new StringBuilder("{\"veterans\":[");
    Query<Entity> query = Query.newEntityQueryBuilder()
        .setKind("Veteran")
        .setFilter(PropertyFilter.eq("cemeteryName", cemeteryName))
        .build();
    QueryResults<Entity> veterans = datastore.run(query);
    boolean first = true;
    while (veterans.hasNext()) {
        Entity veteran = veterans.next();
        if (first) {
            first = false;
        } else {
            json.append(',');
        }
        json.append("{\"id\": \"")
            .append(veteran.getKey().getName())
            .append("\", \"name\": \"")
            .append(veteran.getString("givenName"))
            .append("\", \"cemeteryName\": \"")
            .append(veteran.getString("cemeteryName"))
            .append("\"}");
    }
    json.append("]}");
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().print(json);
  }
}
