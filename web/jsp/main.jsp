<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="welcometag" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:bundle basename="pageContent" prefix="label." >
        <html>
    <head>
        <title>Main</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <style>
            .header {
                text-align: center;
            }
            ul.nav li a {
                font-size: 14px;
            }
        </style>
    </head>
    <body>

    <div class="header" style="color: #16191a; height: 300px;">
        <center>
            <p style="font-size: 80px; padding-top: 50px">CourierPicker</p>
            <p id="Ticker" style="font-size: 30px"></p>
        </center>
    </div>

    <nav class="navbar navbar-default" style="font-size: 15px; border-width: 1px;border-color: #16191a">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbar-main">
                <ul class="nav navbar-nav" style="text-align: center">
                    <li><div class="navbar-header">
                        <a class="navbar-brand" style="font-size: 14px;" href="${pageContext.session.servletContext.contextPath}/jsp/main.jsp"><fmt:message key="home" />   <span class="glyphicon glyphicon-home"></span></a>
                    </div></li>
                    <li><a href="#"><fmt:message key="contactus" />   <span class="glyphicon glyphicon-envelope"></span></a></li>
                    <c:choose>
                        <c:when test="${role == 'user'}">
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=show_approved_courier_offers"><fmt:message key="courier_offers" /></a></li>
                        </c:when>
                        <c:when test="${role == 'courier'}">
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=show_approved_user_offers"><fmt:message key="user_offers" /></a></li>
                        </c:when>
                    </c:choose>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${status == 'autoresized'}">
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/controller?command=account_command"><fmt:message key="account" />    <span class="glyphicon glyphicon-user"></span></a></li>
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/controller?command=logout"><fmt:message key="logout" />    <span class="glyphicon glyphicon-log-out"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${pageContext.session.servletContext.contextPath}/jsp/login.jsp"><fmt:message key="login" />    <span class="glyphicon glyphicon-log-in"></span></a>
                            </li>
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/jsp/signup.jsp"><fmt:message key="signup" />    <span class="glyphicon glyphicon-user"></span></a></li>
                        </c:otherwise>
                    </c:choose>
                    <li class="dropdown">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown"><fmt:message key="lang" /> <span class="glyphicon glyphicon-globe"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=ru&jsp=main.jsp">RU</a></li>
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=en&jsp=main.jsp">EN</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container" style="padding-top: 60px">
        <center>
            <h2><welcometag:hello role="${role}" lang="${lang}"/></h2>
        </center>
    </div>

    <div class="container" style="padding-top: 60px">
        <div class="jumbotron">
            <h2><fmt:message key="jumbotron1.h2" /></h2>
            <p><fmt:message key="jumbotron1.p" /></p>
        </div>
    </div>

    <div class="container">
        <div class="page-header">
            <h1><fmt:message key="pageheader1" /></h1>
        </div>
        <div style="width: 33%;float: left">
            <p><fmt:message key="pageheader1.div1.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div1.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div1.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div1.p4" /></p>
        </div>
        <div style="width: 33%;float: right">
            <p><fmt:message key="pageheader1.div2.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div2.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div2.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div2.p4" /></p>
        </div>
        <div style="width: 33%;float: right">
            <p><fmt:message key="pageheader1.div3.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div3.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div3.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader1.div3.p4" /></p>
        </div>
    </div>

    <div class="container" style="padding-top: 60px">
        <div class="jumbotron">
            <h2><fmt:message key="jumbotron2.h2" /></h2>
            <p><fmt:message key="jumbotron2.p" /></p>
        </div>
    </div>

    <div class="container">
        <div class="page-header">
            <h1><fmt:message key="pageheader2" /></h1>
        </div>
        <div style="width: 33%;float: left">
            <p><fmt:message key="pageheader2.div1.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div1.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div1.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div1.p4" /></p>
        </div>
        <div style="width: 33%;float: right">
            <p><fmt:message key="pageheader2.div2.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div2.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div2.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div2.p4" /></p>
        </div>
        <div style="width: 33%;float: right">
            <p><fmt:message key="pageheader2.div3.p1" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div3.p2" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div3.p3" /></p>
            <p style="margin-left: 20px"><span class="glyphicon glyphicon-bookmark" />      <fmt:message key="pageheader2.div3.p4" /></p>
        </div>
    </div>

    <div class="container" style="padding-top: 60px;padding-bottom: 60px">
        <div class="jumbotron">
            <h2><fmt:message key="jumbotron3.h2" /></h2>
            <p><fmt:message key="jumbotron3.p" /></p>
        </div>
    </div>

    <script type="text/javascript">
        var CharTimeout = 50; // скорость печатания
        var StoryTimeout = 2000; // время ожидания перед переключением

        var Summaries = new Array();
        var SiteLinks = new Array();

        Summaries[0] = 'helps you to live quicker!';

        function startTicker(){
            massiveItemCount =  Number(Summaries.length); //количество элементов массива
            // Определяем значения запуска
            CurrentStory     = -1;
            CurrentLength    = 0;
            // Расположение объекта
            AnchorObject     = document.getElementById("Ticker");
            runTheTicker();
        }
        // Основной цикл тиккера
        function runTheTicker(){
            var myTimeout;
            // Переход к следующему элементу
            if(CurrentLength == 0){
                CurrentStory++;
                CurrentStory      = CurrentStory % massiveItemCount;
                StorySummary      = Summaries[CurrentStory].replace(/"/g,'-');
                AnchorObject.href = SiteLinks[CurrentStory];
            }
            // Располагаем текущий текст в анкор с печатанием
            AnchorObject.innerHTML = StorySummary.substring(0,CurrentLength) + znak();
            // Преобразуем длину для подстроки и определяем таймер
            if(CurrentLength != StorySummary.length){
                CurrentLength++;
                myTimeout = CharTimeout;
            } else {
                CurrentLength = 0;
                myTimeout = StoryTimeout;
            }
            // Повторяем цикл с учетом задержки
            setTimeout("runTheTicker()", myTimeout);
        }
        // Генератор подстановки знака
        function znak(){
            if(CurrentLength == StorySummary.length) return "";
            else return "|";
        }

        startTicker();
    </script>

    </body>
    </html>
</fmt:bundle>

