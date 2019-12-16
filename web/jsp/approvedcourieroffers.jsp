<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:bundle basename="pageContent" prefix="label." >
    <html>
    <head>
        <title><fmt:message key="courier_offers" /></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.session.servletContext.contextPath}/js/jquery.tablesorter.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
            $("#courierOffers").tablesorter({sortList: [[0,1]]});
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
    <nav class="navbar navbar-default" style="font-size: 15px; border-width: 1px;border-color: #16191a">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbar-main">
                <ul class="nav navbar-nav" style="text-align: center">
                    <li>
                        <div class="navbar-header">
                            <a class="navbar-brand" style="font-size: 14px;" href="${pageContext.session.servletContext.contextPath}/jsp/main.jsp"><fmt:message key="home" />   <span class="glyphicon glyphicon-home"></span></a>
                        </div>
                    </li>
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

    <center>
        <h3>Courier offers</h3>
        <p>Here u can find all courier offers that are available to you. Look through and send your offer</p>
    </center>
    <c:choose>
        <c:when test="${empty offerMap}">
            <center>
                <h1>There are no courier offers</h1>
            </center>
        </c:when>
        <c:otherwise>
            <table class="table table-striped" id="courierOffers">
                <thead>
                <tr>
                    <th>Transport</th>
                    <th>Goods</th>
                    <th>User info</th>
                    <th>Comment</th>
                    <th>Send offer</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="currentOffer" items="${offerMap}">
                    <tr>
                        <td>
                            <c:forEach var="currentTransport" items="${currentOffer.value.transport}">
                                ${currentTransport.type}
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="currentGoods" items="${currentOffer.value.goods}">
                                ${currentGoods.type}
                            </c:forEach>
                        </td>
                        <td>
                                ${currentOffer.key.login}
                                ${currentOffer.key.email}
                                ${currentOffer.key.firstName}
                                ${currentOffer.key.lastName}
                        </td>
                        <td>
                            ${currentOffer.value.courierComment}
                        </td>
                        <td>
                            <a href="#" id="myBtn${currentOffer.value.idOffer}">Send offer<span class="glyphicon glyphicon-log-in"></span></a>
                            <div class="modal fade" id="myModal${currentOffer.value.idOffer}" role="dialog">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header" style="padding:35px 50px;">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4>Send offer</h4>
                                        </div>
                                        <div class="modal-body" style="padding:40px 50px;">
                                            <form role="form" name="sendOfferForm" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
                                                <input type="hidden" name="command" value="send_user_offer">
                                                <input type="hidden" name="idOffer" value="${currentOffer.value.idOffer}">
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
                            $("#myBtn${currentOffer.value.idOffer}").click(function(){
                                $("#myModal${currentOffer.value.idOffer}").modal();
                            });
                        });
                    </script>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <button style="margin-left: 30px" class="btn btn-default" data-toggle="collapse" data-target="#find">Select parameters</button>

    <div style="padding-top: 30px" id="find" class="collapse">
        <form action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
            <input type="hidden" name="command" value="find_courier_offers_by_parameters">
            <div style="width: 30%; float: left; padding-left: 30px">
                <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#transport">Transport</button>
                <div id="transport" class="collapse">
                    <div style="width: 50%; float: left">
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="bicycle" >Bicycle</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="rollers">Rollers</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="car">Car</label>
                        </div>
                    </div>
                    <div style="width: 50%; float: right; padding-left: 15px">
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="drone">Drone</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="atv">Atv</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="transport" value="motorbike">Motorbike</label>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 30%; float: left; padding-left: 30px">
                <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#goods">Goods</button>
                <div id="goods" class="collapse">
                    <div style="width: 50%; float: left">
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="food">Food</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="clothes">Clothes</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="medicine">Medicine</label>
                        </div>
                    </div>
                    <div style="width: 50%; float: right; padding-left: 15px">
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="technics">Technics</label>
                            </div>
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="home">Home</label>
                        </div>
                        <div class="radio">
                            <label><input type="checkbox" name="package" value="cosmetics">Cosmetics</label>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 30%; float: left; margin-left: 30px">
                <button type="submit" class="btn btn-default">Search</button>
            </div>
        </form>
    </div>

</body>
</html>
</fmt:bundle>