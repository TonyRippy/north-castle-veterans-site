package myapp;

import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class AddCemeteryServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cemetery c = new Cemetery("Test");
    c.name = "St. Test";
    c.writeToDatastore();
    resp.setContentType("text/plain");
    resp.getWriter().print("OK");
  }
}
