package app.servlets;

import app.db.SQLiteJDBCDriverConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        resp.setContentType("UTF-8");
        req.setCharacterEncoding("UTF-8");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        resp.setContentType("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String Name = req.getParameter("name");
        String Password = req.getParameter("pass");
        SQLiteJDBCDriverConnection.connect();
        SQLiteJDBCDriverConnection.AddUser(Name,Password);
        SQLiteJDBCDriverConnection.close();

        req.setAttribute("userName", Name);
        doGet(req, resp);
    }
}