package myapp;

import static myapp.Config.isTest;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddVeteranServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (!isTest()) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }
    
    Veteran v = new Veteran("Johnny_Appleseed", "Test");
    v.firstName = "Johnny";
    v.lastName = "Appleseed";
    v.biography = "He planted <em>lots</em> of apple trees.";
    v.writeToDatastore();
    
    resp.setContentType("text/plain");
    resp.getWriter().print("OK");
  }
}
