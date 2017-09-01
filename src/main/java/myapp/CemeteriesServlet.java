package myapp;

import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class CemeteriesServlet extends HttpServlet {
  private static class Output {
    List<Cemetery> cemeteries;
  }
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Output out = new Output();
    out.cemeteries = Cemetery.listAll();
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    new Gson().toJson(out, resp.getWriter());
  }
}
