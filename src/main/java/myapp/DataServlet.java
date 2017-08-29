package myapp;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    String name = req.getPathInfo().substring(1); // Remove the leading slash from the path.
    Key key = datastore.newKeyFactory()
        .setKind("Cemetery")
        .newKey(name);
    Query<Entity> query = Query.newEntityQueryBuilder()
        .setKind("Cemetery")
        .setFilter(PropertyFilter.eq("__key__", key))
        .build();
    QueryResults<Entity> results = datastore.run(query);
    if (!results.hasNext()) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    }
    Entity person = results.next();
    StringBuilder json = new StringBuilder("{")        
        .append("\"cemeteryName\": \"")
        .append(person.getString("cemeteryName"))
        .append("\", \"familyName\": \"")
        .append(person.getString("familyName"))
        .append("\"}");
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().print(json);
  }
}
