package ru.job4j.dream.ajax;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class JsonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/json.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader()
                .lines()
                .collect(Collectors.joining());
        try (PrintWriter out = resp.getWriter()) {
            JSONObject parameters = (JSONObject) new JSONParser().parse(body);
                out.write(parameters.toString());
            out.flush();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
