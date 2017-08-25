package myapp;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    StringBuilder json = new StringBuilder("{\"soldiers\":[");
    Query<Entity> query = Query.newEntityQueryBuilder()
        .setKind("Person")
        .setOrderBy(OrderBy.asc("familyName"), OrderBy.asc("givenName"))
        .build();
    QueryResults<Entity> people = datastore.run(query);
    boolean first = true;
    while (people.hasNext()) {
        Entity person = people.next();
        if (first) {
            first = false;
        } else {
            json.append(',');
        }
        json.append("{\"id\": \"")
            .append(person.getKey().getName())
            .append("\", \"givenName\": \"")
            .append(person.getString("givenName"))
            .append("\", \"familyName\": \"")
            .append(person.getString("familyName"))
            .append("\"}");
    }
    json.append("]}");
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().print(json);
  }
}
