<%@ page import="java.util.ArrayList" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.db.JDBCDriverConnection" %>
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
            <h2>Список упражнений</h2>
        </div>
        <%
            //ArrayList<String> names = (ArrayList<String>) request.getAttribute("userNames");
            ArrayList<JDBCDriverConnection.DefectType> DefectList = (ArrayList<JDBCDriverConnection.DefectType>) request.getAttribute("DefectList");

            if (DefectList != null && !DefectList.isEmpty()) {
                out.println(
                        "<form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">\n"
                        + "<table class=\"w3-table-all\">\n"
                            + "<tr class=\"w3-blue\"><th>Change</th><th>ID</th><th>Название</th><th>Описание</th></tr> <!--ряд с ячейками заголовков-->\n");
                for (JDBCDriverConnection.DefectType s : DefectList) {
                    //out.println("<li class=\"w3-hover-sand\">" + s + "</li>");
                    out.println(
                               "<tr><td><input type=\"checkbox\" name=\"ID\" value=\"" + s.id + "\"/></td><td>" + s.id + "</td><td>" + s.name + "</td><td>" + s.description + "</td></tr> <!--ряд с ячейками тела таблицы-->\n"

                    );
                }
                out.println("</table>"
                        + "<p><button type=\"submit\" class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\">Submit</button></p>"
                        + "</form>");

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