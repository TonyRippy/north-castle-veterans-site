package myapp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

abstract class SaveServlet extends HttpServlet {

  /** Normalizes a request parameter to null if missing, empty or whitespace. */
  protected static String s(HttpServletRequest request, String parameterName) {
    String value = request.getParameter(parameterName);
    if (value == null) {
      return null;
    }
    value = value.trim();
    if (value.isEmpty()) {
      return null;
    }
    return value;
  }

  protected static Number n(HttpServletRequest request, String parameterName) {
    String value = request.getParameter(parameterName);
    if (value == null) {
      return null;
    }
    value = value.trim();
    if (value.isEmpty()) {
      return null;
    }
    try {
      return new Long(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  protected static List<String> l(HttpServletRequest request, String parameterName) {
    String[] value = request.getParameterValues(parameterName);
    if (value == null) {
      return null;
    }
    return Arrays.asList(value);
  }

  /**
   * Displays an animation to let the user know the write succeeded,
   * then redirects the browser to the view page.
   */
  protected void sendSuccessResponse(HttpServletResponse resp, String redirectUrl) throws IOException {
    resp.setContentType("text/html");
    resp.getWriter()
      .append("<html>")
      .append("<head>")
      // Delayed redirect
      .append("<meta http-equiv=\"refresh\" content=\"2;") // wait 2 seconds
        .append("url=").append(redirectUrl).append("\"/>")
      .append("<style>")
      .append(" body { background-color: #1c1c1f; }")
      .append(" img { display: block; margin: auto; }")
      .append("</style>")
      .append("</head>")
      .append("<body>")
      .append("<img src=\"/images/saved.gif\" width=400 alt=\"Saved!\"></img>")
      .append("</body>")
      .append("</html>");
  }
}
