<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:bundle basename="pageContent" prefix="label." >
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <style>
        body {
            background: #FFECDB;
        }
        .modal-header, h4, .close {
            background-color: #5cb85c;
            color:white !important;
            text-align: center;
            font-size: 30px;
        }
        .modal-footer {
            background-color: #f9f9f9;
        }
        .header {
            text-align: center;
        }
        .container-fluid {
            background: #F1A9A0;
        }
        ul.nav li a {
            color: #fff !important;
            font-size: 14px;
        }
        ul.nav a:hover {
            background: #37bc9b !important;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" style="font-size: 15px; border-width: 1px;border-color: #16191a">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbar-main">
            <ul class="nav navbar-nav" style="text-align: center">
                <li><div class="navbar-header">
                    <a class="navbar-brand" style="background: #F1A9A0; color: #fff; font-size: 14px;" href="${pageContext.session.servletContext.contextPath}/controller?command=home_command"><fmt:message key="home" />   <span class="glyphicon glyphicon-home"></span></a>
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
                </c:choose>
                <li class="dropdown">
                    <a style="background:#F1A9A0;" class="dropdown-toggle" href="#" data-toggle="dropdown"><fmt:message key="lang" /> <span class="glyphicon glyphicon-globe"></span></a>
                    <ul class="dropdown-menu" style="background:#F1A9A0;">
                        <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=ru&jsp=main.jsp">RU</a></li>
                        <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=en&jsp=main.jsp">EN</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<table class="table">
    <thead>
    <tr>
        <th>Goods</th>
        <th>Needed courier number</th>
        <th>Active courier number</th>
        <th>User info</th>
        <th>Comment</th>
        <th>Send offer</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="currentOffer" items="${offerMap}">
        <tr>
            <td>
                <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                    ${currentGoods.type}
                </c:forEach>
            </td>
            <td>
                    ${currentOffer.key.neededCourierNumber}
            </td>
            <td>
                    ${currentOffer.key.activeCourierNumber}
            </td>
            <td>
                    ${currentOffer.value.login}
                    ${currentOffer.value.email}
                    ${currentOffer.value.firstName}
                    ${currentOffer.value.lastName}
            </td>
            <td>
                ${currentOffer.key.userComment}
            </td>
            <td>
                <a href="#" id="myBtn${currentOffer.key.idOffer}">Send offer<span class="glyphicon glyphicon-log-in"></span></a>
                <div class="modal fade" id="myModal${currentOffer.key.idOffer}" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header" style="padding:35px 50px;">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4>Send offer</h4>
                            </div>
                            <div class="modal-body" style="padding:40px 50px;">
                                <form role="form" name="sendOfferForm2" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
                                    <input type="hidden" name="command" value="send_courier_offer">
                                    <input type="hidden" name="idOffer" value="${currentOffer.key.idOffer}">
                                    <div class="form-group">
                                        <label>Comment:</label></br>
                                        <textarea name="comment" cols="50"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span>    Send offer</button>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <script>
            $(document).ready(function(){
                $("#myBtn${currentOffer.key.idOffer}").click(function(){
                    $("#myModal${currentOffer.key.idOffer}").modal();
                });
            });
        </script>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
</fmt:bundle>