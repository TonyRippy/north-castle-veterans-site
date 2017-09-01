package myapp;

import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CemeteryServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cemetery cemetery = Cemetery.forPath(req.getPathInfo());
    if (cemetery == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    if (!cemetery.readFromDatastore()) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    new Gson().toJson(cemetery, resp.getWriter());
  }
}
