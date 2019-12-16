<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:bundle basename="pageContent" prefix="label." >
    <html>
    <head>
        <title>Registration</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <style>
            ul.nav li a {
                font-size: 14px;
            }
        </style>
    </head>
    <body>

    <nav class="navbar navbar-default" style="font-size: 15px; border-width: 1px;">
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
                    </c:choose>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${status == 'autoresized'}">
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/controller?command=account_command"><fmt:message key="account" />    <span class="glyphicon glyphicon-user"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${pageContext.session.servletContext.contextPath}/jsp/login.jsp" id="myBtn"><fmt:message key="login" />    <span class="glyphicon glyphicon-log-in"></span></a>
                            </li>
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/jsp/signup.jsp"><fmt:message key="signup" />    <span class="glyphicon glyphicon-user"></span></a></li>
                        </c:otherwise>
                    </c:choose>
                    <li class="dropdown">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown"><fmt:message key="lang" /> <span class="glyphicon glyphicon-globe"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=ru&jsp=login.jsp">RU</a></li>
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=en&jsp=login.jsp">EN</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container" style="padding-left: 30%;padding-top: 15%">
        <h2><fmt:message key="login" /></h2>
        <form role="form" name="loginForm" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
            <input type="hidden" name="command" value="login">
            <div class="form-group">
                <label><fmt:message key="nikname" /></label> </br>
                <input size="60" type="text" name="login" placeholder="<fmt:message key="enter_login" />">
            </div>
            <div class="form-group">
                <label><fmt:message key="password" /></label> </br>
                <input size="60" type="password" name="password" placeholder="<fmt:message key="enter_password" />"> <font color="red" style="margin-left: 30px">${errorLoginPass}</font> <font color="red" style="margin-left: 15px">${errorBlockedAccount}</font>
            </div>
            <button type="submit" class="btn btn-default"><fmt:message key="login" /></button>
        </form>
    </div>

    </body>
    </html>
</fmt:bundle>