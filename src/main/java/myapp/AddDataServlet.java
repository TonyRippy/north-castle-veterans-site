package myapp;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDataServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Person").setProjectId("skiera-test");
    Entity entity = Entity.newBuilder(keyFactory.newKey("rippy/tony"))
        .set("familyName", "Rippy")
        .set("givenName", "Tony")
        .build();
    datastore.put(entity);
    entity = Entity.newBuilder(keyFactory.newKey("skiera/jack"))
        .set("familyName", "Skiera")
        .set("givenName", "Jack")
        .build();
    datastore.put(entity);

    resp.setContentType("text/plain");
    resp.getWriter().print("ok");
  }
}
