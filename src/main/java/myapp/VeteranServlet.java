package myapp;

import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VeteranServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Veteran veteran = Veteran.forPath(req.getPathInfo());
    if (veteran == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    if (!veteran.readFromDatastore()) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    new Gson().toJson(veteran, resp.getWriter());
  }
}
