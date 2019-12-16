<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" />
<fmt:bundle basename="pageContent" prefix="label.">
    <html>
    <head>
        <title><fmt:message key="account" /></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.session.servletContext.contextPath}/js/jquery.tablesorter.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#yourOffers").tablesorter({sortList: [[0,1]]});
            });
        </script>
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
                            <li style="padding-right: 1px;"><a href="${pageContext.session.servletContext.contextPath}/controller?command=logout"><fmt:message key="logout" />    <span class="glyphicon glyphicon-log-out"></span></a></li>
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
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=ru&jsp=courierpage.jsp&redirectTo=account_command">RU</a></li>
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=en&jsp=courierpage.jsp&redirectTo=account_command">EN</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container" style="padding-left: 8%">
        <h1 style="padding-left: 30%"><fmt:message key="account" /></h1>
        <div style="width: 33%;float: left;padding-top: 7%">
            <img class="img-circle img-responsive"  src="${pageContext.session.servletContext.contextPath}/img/user.jpg">
        </div>
        <div style="width: 66%;float: right">
            <div class="panel panel-default">
                <div class="panel-heading"><fmt:message key="nikname" /></div>
                <div class="panel-body">${login}</div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading"><fmt:message key="email" /></div>
                <div class="panel-body">${email}</div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading"><fmt:message key="firstname" /></div>
                <div class="panel-body">${firstname}</div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading"><fmt:message key="lastname" /></div>
                <div class="panel-body">${lastname}</div>
            </div>
            <form role="form" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="edit_command">
                <button type="submit" class="btn btn-default"><fmt:message key="edit" /></button>
            </form>
        </div>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home">Approve courier offers</a></li>
        <li><a data-toggle="tab" href="#menu1">Approve user offers</a></li>
        <li><a data-toggle="tab" href="#menu2">Users</a></li>
        <li><a data-toggle="tab" href="#menu3">Add admin</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <center>
                <h3>Approve courier offers</h3>
                <p>Here u can see not approved courier offers and control them</p>
            </center>
            <c:choose>
                <c:when test="${empty courierOffers}">
                    <center>
                        <h1>There are no not approved offers</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Courier Info</th>
                            <th>Transport</th>
                            <th>Goods</th>
                            <th>Comment</th>
                            <th>Approve</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="currentOffer" items="${courierOffers}">
                            <tr>
                                <td>
                                        ${currentOffer.value.login}
                                        ${currentOffer.value.email}
                                        ${currentOffer.value.firstName}
                                        ${currentOffer.value.lastName}
                                </td>
                                <td>
                                    <c:forEach var="currentTransport" items="${currentOffer.key.transport}">
                                        ${currentTransport.type}
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.courierComment}
                                </td>
                                <td>
                                    <a href="${pageContext.session.servletContext.contextPath}/controller?command=approve_courier_offer&idOffer=${currentOffer.key.idOffer}">Approve offer</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="menu1" class="tab-pane fade in">
            <center>
                <h3>Approve user offers</h3>
                <p>Here u can see not approved user offers and control them</p>
            </center>
            <c:choose>
                <c:when test="${empty userOffers}">
                    <center>
                        <h1>There are no not approved offers</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>User Info</th>
                            <th>Goods</th>
                            <th>Courier number</th>
                            <th>Comment</th>
                            <th>Approve</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="currentOffer" items="${userOffers}">
                            <tr>
                                <td>
                                        ${currentOffer.value.login}
                                        ${currentOffer.value.email}
                                        ${currentOffer.value.firstName}
                                        ${currentOffer.value.lastName}
                                </td>
                                <td>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.neededCourierNumber}
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                    <a href="${pageContext.session.servletContext.contextPath}/controller?command=approve_user_offer&idOffer=${currentOffer.key.idOffer}">Approve offer</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="menu2" class="tab-pane fade">
            <center>
                <h3>Users</h3>
                <p>Here u can see all user information and control them</p>
            </center>
            <c:choose>
                <c:when test="${empty userMap}">
                    <center>
                        <h1>There are no users</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Login</th>
                            <th>Email</th>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Block</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="currentList" items="${userMap}">
                            <c:forEach var="currentUser" items="${currentList.value}">
                                <tr>
                                    <td>
                                        ${currentUser.id}
                                    </td>
                                    <td>
                                            ${currentUser.login}
                                    </td>
                                    <td>
                                            ${currentUser.email}
                                    </td>
                                    <td>
                                            ${currentUser.firstName}
                                    </td>
                                    <td>
                                            ${currentUser.lastName}
                                    </td>
                                    <td>
                                            ${currentUser.role}
                                    </td>
                                    <td>
                                        ${currentUser.state}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${currentUser.state == 'active'}">
                                                <a href="${pageContext.session.servletContext.contextPath}/controller?command=block_user&idUser=${currentUser.id}">Block</a>
                                            </c:when>
                                            <c:when test="${currentUser.state == 'blocked'}">
                                                <a href="${pageContext.session.servletContext.contextPath}/controller?command=unblock_user&idUser=${currentUser.id}">Unblock</a>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="menu3" class="tab-pane fade">
            <center>
                <h3>Create new admin account</h3>
                <p>Here u can add a new admin</p>
            </center>
            <div class="container" style="align-content: center;padding-left: 26%">
                <form name="registerForm" method="POST" action="${pageContext.session.servletContext.contextPath}/controller" >
                    <input type="hidden" name="command" value="add_admin" />
                    <div class="form-group">
                        <label><fmt:message key="nikname" />:</label> </br>
                        <input size="60" type="text" name="login" placeholder="<fmt:message key="enter_login" />"/> <font color="red" style="margin-left: 30px">${errorLoginRegister}</font>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="email" />:</label> </br>
                        <input size="60" type="email" name="email" placeholder="<fmt:message key="enter_email" />"/> <font color="red" style="margin-left: 30px">${errorEmailRegister}</font>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="firstname" />:</label> </br>
                        <input size="60" type="text" name="firstname" placeholder="<fmt:message key="enter_firstname" />"/> <font color="red" style="margin-left: 30px">${errorFirstnameRegister}</font>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="lastname" />:</label> </br>
                        <input size="60" type="text" name="lastname" placeholder="<fmt:message key="enter_lastname" />"/> <font color="red" style="margin-left: 30px">${errorLastnameRegister}</font>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="password" />:</label> </br>
                        <input size="60" type="password" name="password" placeholder="<fmt:message key="enter_password" />"/> <font color="red" style="margin-left: 30px">${errorPasswordRegister}</font>
                    </div>
                    <button type="submit" class="btn btn-default"><fmt:message key="add_admin" /></button>
                </form>
            </div>
        </div>
    </div>
    </div>

    </body>
    </html>
</fmt:bundle>