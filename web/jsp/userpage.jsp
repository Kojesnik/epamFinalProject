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
            .modal-header, h4, .close {
                text-align: center;
                font-size: 30px;
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
        <li class="active"><a data-toggle="tab" href="#home"><fmt:message key="send_offer" /></a></li>
        <li><a data-toggle="tab" href="#menu1"><fmt:message key="your_offers" /></a></li>
        <li><a data-toggle="tab" href="#menu2"><fmt:message key="active_offers" /></a></li>
        <li><a data-toggle="tab" href="#menu3"><fmt:message key="past_offers" /></a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <form style="padding-left: 10%;padding-top: 2%;padding-right: 10%" name="userOffer" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="user_offer">
                <div class="form-group">
                    <label><fmt:message key="select_goods" />:</label>
                    <select multiple class="form-control" name="package">
                        <option value="food"><fmt:message key="food" /></option>
                        <option value="clothes"><fmt:message key="clothes" /></option>
                        <option value="medicine"><fmt:message key="medicine" /></option>
                        <option value="technics"><fmt:message key="technics" /></option>
                        <option value="home"><fmt:message key="homegoods" /></option>
                        <option value="cosmetics"><fmt:message key="cosmetics" /></option>
                    </select>
                </div>
                <div class="form-group">
                    <label><fmt:message key="courier_number" /></label> </br>
                    <input type="text" name="needed_courier_number" value=""/>
                </div>
                <div class="form-group">
                    <label><fmt:message key="comment" />:</label></br>
                    <textarea name="comment" class="form-control" rows="5"></textarea>
                </div>
                <button type="submit" class="btn btn-default"><fmt:message key="send" /></button>
            </form>
        </div>
        <div id="menu1" class="tab-pane fade in">
            <c:choose>
                <c:when test="${empty offerMap}">
                    <center>
                        <h1>There are no offers</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th><fmt:message key="goods" /></th>
                            <th><fmt:message key="courier" /></th>
                            <th><fmt:message key="comment" /></th>
                            <th><fmt:message key="status" /></th>
                            <th><fmt:message key="responses" /></th>
                            <th>&times;</th>
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
                                        ${currentOffer.key.activeCourierNumber}/${currentOffer.key.neededCourierNumber}
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                        ${currentOffer.key.status}
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${currentOffer.key.activeCourierNumber == currentOffer.key.neededCourierNumber}">
                                            All couriers dialed
                                        </c:when>
                                        <c:when test="${empty currentOffer.value}">
                                            No responses to this offer
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="currentUser" items="${currentOffer.value}">
                                                ${currentUser.key.login}
                                                ${currentUser.key.email}
                                                ${currentUser.key.firstName}
                                                ${currentUser.key.lastName}
                                                </br>
                                                <a href="#" id="myBtn${currentOffer.key.idOffer}${currentUser.key.login}">Courier comment<span class="glyphicon glyphicon-log-in"></span></a>
                                                <div class="modal fade" id="myModal${currentOffer.key.idOffer}${currentUser.key.login}" role="dialog">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header" style="padding:35px 50px;">
                                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                <h4>Courier comment</h4>
                                                            </div>
                                                            <div class="modal-body" style="padding:40px 50px;">
                                                                    ${currentUser.value}
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <a href="${pageContext.session.servletContext.contextPath}/controller?command=accept_courier_offer&idUser=${currentUser.key.id}&idOffer=${currentOffer.key.idOffer}">Accept</a></br>
                                                <script>
                                                    $(document).ready(function(){
                                                        $("#myBtn${currentOffer.key.idOffer}${currentUser.key.login}").click(function(){
                                                            $("#myModal${currentOffer.key.idOffer}${currentUser.key.login}").modal();
                                                        });
                                                    });
                                                </script>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="${pageContext.session.servletContext.contextPath}/controller?command=delete_user_offer&idOffer=${currentOffer.key.idOffer}">Delete offer</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="menu2" class="tab-pane fade">
            <c:choose>
                <c:when test="${empty activeOffers}">
                    <center>
                        <h1>There are no active offers</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr class="norCell">
                            <th><fmt:message key="offer" /></th>
                            <th><fmt:message key="comment" /></th>
                            <th><fmt:message key="courier" /></th>
                            <th><fmt:message key="start_date" /></th>
                            <th>&times;</th>
                        </tr>
                        </thead>
                        <tbody class="scrollable">
                        <c:forEach var="currentOffer" items="${activeOffers}">
                            <tr class="norCell">
                                <td>
                                    <c:forEach var="currentTransport" items="${currentOffer.key.transport}">
                                        ${currentTransport.type}
                                    </c:forEach>
                                    </br>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                        ${currentOffer.value.login}
                                        ${currentOffer.value.email}
                                        ${currentOffer.value.firstName}
                                        ${currentOffer.value.lastName}
                                    </br>
                                    <a href="#" id="myBtn${currentOffer.key.idOffer}">Courier comment<span class="glyphicon glyphicon-log-in"></span></a>
                                    <div class="modal fade" id="myModal${currentOffer.key.idOffer}" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header" style="padding:35px 50px;">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4>Courier comment</h4>
                                                </div>
                                                <div class="modal-body" style="padding:40px 50px;">
                                                        ${currentOffer.key.courierComment}
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                        ${currentOffer.key.startDate}
                                </td>
                                <td>
                                    <a href="${pageContext.session.servletContext.contextPath}/controller?command=finish_courier_offer&idOffer=${currentOffer.key.idOffer}&status=accepted">Finish work</a>
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
                        <c:forEach var="currentOffer" items="${activeOfferMap}">
                            <tr class="norCell">
                                <td>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                        ${currentOffer.key.activeCourierNumber}/${currentOffer.key.neededCourierNumber}
                                    </br>
                                    <c:forEach var="currentUser" items="${currentOffer.value}">
                                        ${currentUser.key.login}
                                        ${currentUser.key.email}
                                        ${currentUser.key.firstName}
                                        ${currentUser.key.lastName}
                                        </br>
                                        <a href="#" id="myBtn${currentOffer.key.idOffer}${currentUser.key.login}">Courier comment<span class="glyphicon glyphicon-log-in"></span></a>
                                        <div class="modal fade" id="myModal${currentOffer.key.idOffer}${currentUser.key.login}" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header" style="padding:35px 50px;">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h4>Courier comment</h4>
                                                    </div>
                                                    <div class="modal-body" style="padding:40px 50px;">
                                                            ${currentUser.value}
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <a href="${pageContext.session.servletContext.contextPath}/controller?command=kick_courier_from_offer&idOffer=${currentOffer.key.idOffer}&idUser=${currentUser.key.id}">Kick</a>
                                        <script>
                                            $(document).ready(function(){
                                                $("#myBtn${currentOffer.key.idOffer}${currentUser.key.login}").click(function(){
                                                    $("#myModal${currentOffer.key.idOffer}${currentUser.key.login}").modal();
                                                });
                                            });
                                        </script>
                                        </br>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.startDate}
                                </td>
                                <td>
                                    <a href="${pageContext.session.servletContext.contextPath}/controller?command=finish_user_offer&idOffer=${currentOffer.key.idOffer}">Finish work</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="menu3" class="tab-pane fade">
            <c:choose>
                <c:when test="${empty pastOffers}">
                    <center>
                        <h1>There are no past offers</h1>
                    </center>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th><fmt:message key="offer" /></th>
                            <th><fmt:message key="comment" /></th>
                            <th><fmt:message key="courier" /></th>
                            <th><fmt:message key="start_date" /></th>
                            <th><fmt:message key="finish_date" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="currentOffer" items="${pastOffers}">
                            <tr>
                                <td>
                                    <c:forEach var="currentTransport" items="${currentOffer.key.transport}">
                                        ${currentTransport.type}
                                    </c:forEach>
                                    </br>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                        ${currentOffer.value.login}
                                        ${currentOffer.value.email}
                                        ${currentOffer.value.firstName}
                                        ${currentOffer.value.lastName}
                                    </br>
                                    <a href="#" id="myBtn${currentOffer.key.idOffer}">Courier comment<span class="glyphicon glyphicon-log-in"></span></a>
                                    <div class="modal fade" id="myModal${currentOffer.key.idOffer}" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header" style="padding:35px 50px;">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4>Courier comment</h4>
                                                </div>
                                                <div class="modal-body" style="padding:40px 50px;">
                                                        ${currentOffer.key.courierComment}
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                        ${currentOffer.key.startDate}
                                </td>
                                <td>
                                        ${currentOffer.key.endDate}
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
                        <c:forEach var="currentOffer" items="${pastOfferMap}">
                            <tr>
                                <td>
                                    <c:forEach var="currentGoods" items="${currentOffer.key.goods}">
                                        ${currentGoods.type}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${currentOffer.key.userComment}
                                </td>
                                <td>
                                        ${currentOffer.value.login}
                                        ${currentOffer.value.email}
                                        ${currentOffer.value.firstName}
                                        ${currentOffer.value.lastName}
                                    </br>
                                    <a href="#" id="myBtn${currentOffer.key.idOffer}${currentOffer.value.login}">Courier comment<span class="glyphicon glyphicon-log-in"></span></a>
                                    <div class="modal fade" id="myModal${currentOffer.key.idOffer}${currentOffer.value.login}" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header" style="padding:35px 50px;">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4>Courier comment</h4>
                                                </div>
                                                <div class="modal-body" style="padding:40px 50px;">
                                                        ${currentOffer.key.courierComment}
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <script>
                                        $(document).ready(function(){
                                            $("#myBtn${currentOffer.key.idOffer}${currentOffer.value.login}").click(function(){
                                                $("#myModal${currentOffer.key.idOffer}${currentOffer.value.login}").modal();
                                            });
                                        });
                                    </script>
                                </td>
                                <td>
                                        ${currentOffer.key.startDate}
                                </td>
                                <td>
                                        ${currentOffer.key.endDate}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    </div>

    </body>
    </html>
</fmt:bundle>
