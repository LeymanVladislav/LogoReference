package app.servlets;

import app.db.JDBCDriverConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class ChangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        response.setContentType("UTF-8");
        request.setCharacterEncoding("UTF-8");

        ArrayList<JDBCDriverConnection.DefectType> DefectList;
        JDBCDriverConnection.connect();
        DefectList = JDBCDriverConnection.getDefectList();
        JDBCDriverConnection.close();

        request.setAttribute("DefectList", DefectList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/change.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Задаем кодировку для обработчика запросов
        response.setContentType("UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        ArrayList<String> DefectIDList;
        ArrayList<JDBCDriverConnection.ExercisesType> ExercisesList;

        String FormName;
    FormName = request.getParameter("FormName");
    System.out.println("Тест ChangeServlet FormName:" + FormName);

    // Если запрос из формы change, сохраняем список ошибок
    if (FormName != null && !FormName.isEmpty()) {
        if (FormName.equals(new String("change"))) {
            System.out.println("Тест еее ChangeServlet FormName = change");

            // Получаем id выбраных ошибок на странице change.jsp
            String[] IdStr = request.getParameterValues("ID");

            if (IdStr != null) {
                // Передаем набранный список в запрос
                DefectIDList = new ArrayList<>(Arrays.asList(IdStr));
                session.setAttribute("DefectIDList", DefectIDList);
                System.out.println("ChangeServlet IdList:" + DefectIDList);
            }

            System.out.println("Тест открываем views/age.jsp");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/period.jsp");
            requestDispatcher.forward(request, response);
        }
        // Если запрос из формы age, сохраняем период и запрашиваем упражнения
        else if (FormName.equals(new String("period"))) {

            // Получаем период
            String Period = request.getParameter("period");
            System.out.println("Получен период Period:" + Period);

            // Получаем список c id ошибок
            DefectIDList = (ArrayList<String>) session.getAttribute("DefectIDList");

            // Получаем в БД список упражнений
            JDBCDriverConnection.connect();
            ExercisesList = JDBCDriverConnection.getExercisesList(DefectIDList,Period);
            JDBCDriverConnection.close();

            request.setAttribute("ExercisesList", ExercisesList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/exercises.jsp");
            requestDispatcher.forward(request, response);
        }
    }
    }
}
