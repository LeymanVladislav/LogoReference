package app.servlets;

import app.db.JDBCDriverConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class ChangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        response.setContentType("UTF-8");
        request.setCharacterEncoding("UTF-8");

        ArrayList<JDBCDriverConnection.UserType> UserList;
        JDBCDriverConnection.connect();
        UserList = JDBCDriverConnection.getUserList();
        JDBCDriverConnection.close();
        //System.out.println("DBServlet Names.size():" + Names.size());

        request.setAttribute("UserList", UserList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/change.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        response.setContentType("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Получаем id выбраных пользователей на странице change.jsp
        String[] IdStr = request.getParameterValues("ID");

        if (IdStr != null) {
        ArrayList<String> IdList = new ArrayList<>(Arrays.asList(IdStr));

            System.out.println("ChangeServlet IdList:" + IdList.toString());

            ArrayList<JDBCDriverConnection.UserType> UserList;
            // Получаем список c данными пользователей по id
            JDBCDriverConnection.connect();
            UserList = JDBCDriverConnection.getUserList(IdList);
            JDBCDriverConnection.close();
            //System.out.println("DBServlet Names.size():" + Names.size());

            // Передаем набранный список в запрос
            request.setAttribute("UserList", UserList);
    }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(request, response);
    }
}
