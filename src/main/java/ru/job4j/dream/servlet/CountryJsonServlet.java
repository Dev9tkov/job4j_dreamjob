package ru.job4j.dream.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CountryJsonServlet extends HttpServlet {
    /**
     * Получаем список стран
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        List<String> country = new ArrayList<>(PsqlStore.instOf().getCountry());
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(country);
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.append(value);
        pw.flush();
    }

    /**
     * Получаем список городов по выбранной стране
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        String country = req.getParameter("country");
        List<String> cities = new ArrayList<>(PsqlStore.instOf().getCity(country));
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(cities);
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.append(value);
        pw.flush();
    }
}
