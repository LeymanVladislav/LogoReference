<%@ page import="java.util.ArrayList" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.db.JDBCDriverConnection" %>
<html>
<head>
    <title>W3.CSS Template</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="styles/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif;}
        body, html {
            height: 100%;
            color: #777;
            line-height: 1.8;
        }

        /* Create a Parallax Effect */
        .bgimg-1 {
            background-attachment: fixed;
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
        }

        /* First image (Logo. Full height) */
        .bgimg-1 {
            background-image: url('/resources/images/logopedia.png');
            min-height: 100%;
            /*opacity: 0.3;*/
        }

    </style>
</head>
<body class="w3-deep-ocean">

<!-- Navbar (sit on top) -->
<div class="w3-top">
    <div class="w3-bar w3-text-cast-green-l1" style="background-color:rgba(255, 255, 255, 0.7);" id="myNavbar">
        <div class="w3-opacity-off">
            <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right w3-hover-dark-grey" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
                <i class="fa fa-bars"></i>
            </a>
            <a href="/change" class="w3-bar-item w3-button w3-right w3-hide-small w3-hover-dark-grey"><i class="fa fa-book"></i>СПРАВОЧНИК ОШИБОК НА ПИСЬМЕ</a>
            <a href="#about" class="w3-bar-item w3-button w3-right w3-hide-small w3-hover-dark-grey"><i class="fa fa-user"></i>О НАС</a>
            <a href="/" class="w3-bar-item w3-button w3-right w3-hover-dark-grey">ГЛАВНАЯ</a>
            <a href="#" class="w3-bar-item w3-button w3-hide-small w3-right w3-hover-dark-grey">
                <i class="fa fa-search"></i>
            </a>
        </div>
    </div>

    <!-- Navbar on small screens -->
    <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-right">
        <a href="#about" class="w3-bar-item w3-button" onclick="toggleFunction()">О НАС</a>
        <a href="/change" class="w3-bar-item w3-button" onclick="toggleFunction()">СПРАВОЧНИК ОШИБОК НА ПИСЬМЕ</a>
        <a href="#" class="w3-bar-item w3-button">SEARCH</a>
    </div>
</div>

<!-- Container (About Section) -->
<div class="bgimg-1 w3-display-container" id="home">
    <div class="w3-center w3-padding-top-64">
        <span class="w3-center w3-padding-large w3-font-bold w3-text-green  w3-xlarge w3-wide w3-animate-opacity" style="background-color:rgba(255, 255, 255, 0.7);">СПРАВОЧНИК ОШИБОК НА ПИСЬМЕ</span>
    </div>
    <div class="w3-content w3-container w3-center w3-padding-32">
        <div class="w3-card-4" style="background-color:rgba(0, 102, 0, 0.7);">
            <div class="w3-container">
                <h3 class="w3-animate-opacity w3-wide" style="white-space:nowrap;">СПИСОК УПРАЖНЕНИЙ</h3>
            </div>
            <%
                ArrayList<JDBCDriverConnection.ExercisesType> ExercisesList = (ArrayList<JDBCDriverConnection.ExercisesType>) request.getAttribute("ExercisesList");

                if (ExercisesList != null && !ExercisesList.isEmpty()) {
                    out.println("<table class=\"w3-table w3-striped-cast-green-l1 w3-animate-opacity\">\n"
                            + "<tr class=\"w3-cast-green\"><th>Упражнение</th><th>Описание</th><th>Дисграфия</th><th></th></tr> <!--ряд с ячейками заголовков-->\n");
                    for (JDBCDriverConnection.ExercisesType s : ExercisesList) {
                        //out.println("<li class=\"w3-hover-sand\">" + s + "</li>");
                        out.println(
                                "<tr class=\"w3-hover-cast-green\"><td valign=\"middle\">" + s.name + "</td><td>" + s.description + "</td><td  align=\"center\">" + s.dysgraphia + "</td><td><img src=\"resources\\images\\exercises\\" + s.id + ".jpg\" class=\"w3-round-xlarge\" alt=\"Norway\" style=\"width:50%\"></td></tr> <!--ряд с ячейками тела таблицы-->\n"
                        );
                    }
                    out.println("</table>"
                            + "<p><a href=\"/change\" class=\"w3-button w3-margin w3-light-grey\"><i class=\"fa fa-arrow-left w3-text-cast-green w3-margin-right\"></i>Назад</a>"
                            + "<a><button type=\"submit\" class=\"w3-button w3-margin w3-light-grey\">Печать<i class=\"fa fa-print w3-text-cast-green w3-margin-left\"></i></button></a></p>"
                            + "</form>");

                } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                        +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                        "   <h5>Приносим свои извинения, упражнения для выбранных ошибок еще не загружены</h5>\n" +
                        "</div>");
            %>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="w3-center w3-black w3-padding-64 w3-opacity w3-hover-opacity-off">
    <a href="#home" class="w3-button w3-light-grey"><i class="fa fa-arrow-up w3-margin-right"></i>To the top</a>
    <div class="w3-xlarge w3-section">
        <i class="fa fa-facebook-official w3-hover-opacity"></i>
        <i class="fa fa-instagram w3-hover-opacity"></i>
        <i class="fa fa-snapchat w3-hover-opacity"></i>
        <i class="fa fa-pinterest-p w3-hover-opacity"></i>
        <i class="fa fa-twitter w3-hover-opacity"></i>
        <i class="fa fa-linkedin w3-hover-opacity"></i>
    </div>
    <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" title="W3.CSS" target="_blank" class="w3-hover-text-green">w3.css</a></p>
</footer>

<script>
    // Change style of navbar on scroll
    window.onscroll = function() {myFunction()};
    function myFunction() {
        var navbar = document.getElementById("myNavbar");
        if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
            navbar.className = "w3-bar" + " w3-card" + " w3-animate-top" + " w3-cast-green";
        } else {
            navbar.className = navbar.className.replace("w3-card w3-animate-top w3-cast-green", "w3-text-cast-green-l1");
        }
    }

    // Used to toggle the menu on small screens when clicking on the menu button
    function toggleFunction() {
        var x = document.getElementById("navDemo");
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
        } else {
            x.className = x.className.replace(" w3-show", "");
        }
    }
</script>
</body>
</html>