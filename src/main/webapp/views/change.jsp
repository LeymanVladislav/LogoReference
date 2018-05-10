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
        background-image: url('/resources/images/logopedia.jpg');
        min-height: 100%;
    }

</style>
</head>
<body class="w3-deep-ocean">

<!-- Navbar (sit on top) -->
<div class="w3-top">
    <div class="w3-bar w3-text-white" id="myNavbar">
        <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right w3-hover-indigo" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
            <i class="fa fa-bars"></i>
        </a>
        <a href="/change" class="w3-bar-item w3-button w3-right w3-hide-small w3-hover-dark-grey"><i class="fa fa-book"></i>СПРАВОЧНИК</a>
        <a href="/" class="w3-bar-item w3-button w3-right w3-hover-dark-grey">HOME</a>
        <a href="#" class="w3-bar-item w3-button w3-hide-small w3-right w3-hover-dark-grey">
            <i class="fa fa-search"></i>
        </a>

    </div>

    <!-- Navbar on small screens -->
    <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-right">
        <a href="/change" class="w3-bar-item w3-button" onclick="toggleFunction()">СПРАВОЧНИК</a>
        <a href="#" class="w3-bar-item w3-button">SEARCH</a>
    </div>
</div>

<!-- Container (About Section) -->
<div class="bgimg-1 w3-display-container w3-opacity-min">
    <div class="w3-center w3-padding-top-64">
         <span class="w3-center w3-padding-large w3-black w3-xlarge w3-wide w3-animate-opacity">СПРАВОЧНИК</span>
    </div>
    <div class="w3-content w3-container w3-center w3-padding-32">
    <div class="w3-card-4 w3-deep-ocean-l1">
        <div class="w3-container">
            <h3 class="w3-animate-opacity w3-wide" style="white-space:nowrap;">СПИСОК ОШИБОК</h3>
        </div>
        <%
            //ArrayList<String> names = (ArrayList<String>) request.getAttribute("userNames");
            ArrayList<JDBCDriverConnection.DefectType> DefectList = (ArrayList<JDBCDriverConnection.DefectType>) request.getAttribute("DefectList");

            if (DefectList != null && !DefectList.isEmpty()) {
                out.println(
                        "<form method=\"post\" class=\"w3-selection\">\n"
                                + "<table class=\"w3-table w3-striped-deep-ocean-l3 w3-animate-opacity\">\n"
                                + "<tr class=\"w3-deep-ocean\"><th>Change</th><th>ID</th><th>Название</th><th>Описание</th></tr> <!--ряд с ячейками заголовков-->\n");
                for (JDBCDriverConnection.DefectType s : DefectList) {
                    //out.println("<li class=\"w3-hover-sand\">" + s + "</li>");
                    out.println(
                            "<tr class=\"w3-hover-deep-ocean\"><td><input type=\"checkbox\" name=\"ID\" value=\"" + s.id + "\"/></td><td>" + s.id + "</td><td>" + s.name + "</td><td>" + s.description + "</td></tr> <!--ряд с ячейками тела таблицы-->\n"

                    );
                }
                out.println("</table>"
                        + "<p><button type=\"submit\" class=\"w3-btn w3-deep-ocean w3-round-large w3-margin-bottom\">Далее</button></p>\n"
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
</div>

<!-- Footer -->
<footer class="w3-center w3-black w3-padding-64 w3-opacity w3-hover-opacity-off">
    <a href="/" class="w3-button w3-light-grey"><i class="fa fa-arrow-left w3-margin-right"></i>Back</a>
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
            navbar.className = "w3-bar" + " w3-card" + " w3-animate-top" + " w3-deep-ocean";
        } else {
            navbar.className = navbar.className.replace("w3-card w3-animate-top w3-deep-ocean", "w3-text-white");
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
