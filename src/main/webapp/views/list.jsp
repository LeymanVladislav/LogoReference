<%@ page import="java.util.ArrayList" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.db.SQLiteJDBCDriverConnection" %>
<html>
<head>
    <title>Users list</title>
    <link rel="stylesheet" href="styles/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Super app!</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Users</h2>
        </div>
        <%
            //ArrayList<String> names = (ArrayList<String>) request.getAttribute("userNames");
            ArrayList<SQLiteJDBCDriverConnection.UserType> UserList = (ArrayList<SQLiteJDBCDriverConnection.UserType>) request.getAttribute("UserList");

            if (UserList != null && !UserList.isEmpty()) {
                out.println("<ul class=\"w3-ul\">"
                            + "<table>\n"
                            + "<tr><th>Имя пользователя</th><th>текст заголовка</th></tr> <!--ряд с ячейками заголовков-->\n");
                for (SQLiteJDBCDriverConnection.UserType s : UserList) {
                    //out.println("<li class=\"w3-hover-sand\">" + s + "</li>");
                    out.println(
                               "<tr><td>" + s.Names + "</td><td>" + s.Pass + "</td></tr> <!--ряд с ячейками тела таблицы-->\n"

                    );
                }
                out.println("</table>\n"
                            + "</ul>");

            } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                    +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                    "   <h5>There are no users yet!</h5>\n" +
                    "</div>");
        %>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>