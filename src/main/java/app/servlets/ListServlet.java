package app.servlets;

import app.db.SQLiteJDBCDriverConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        resp.setContentType("UTF-8");
        req.setCharacterEncoding("UTF-8");

        ArrayList<SQLiteJDBCDriverConnection.UserType> UserList;
        SQLiteJDBCDriverConnection.connect();
        UserList = SQLiteJDBCDriverConnection.getUserList();
        SQLiteJDBCDriverConnection.close();
        //System.out.println("DBServlet Names.size():" + Names.size());

        req.setAttribute("UserList", UserList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }
}
