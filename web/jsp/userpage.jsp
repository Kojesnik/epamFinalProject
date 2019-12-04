<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" />
<fmt:bundle basename="pageContent" prefix="label.">
    <html>
    <head>
        <title></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
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
            tbody.scrollable {
                display: block;
                height: 262px;
                overflow: auto;
                width: 100%;
            }
            tr.norCell {
                display: block;
            }

            tr.norCell th,tr.norCell td {
                width: 350px;

            }
        </style>
    </head>
    <body>
    <div class="header" style="color: #16191a; height: 300px;">
        <center>
            <p style="font-size: 80px; padding-top: 50px">CourierPicker</p>
            <p idUser="Ticker" style="font-size: 30px"></p>
        </center>
    </div>

    <nav class="navbar navbar-default" style="font-size: 15px; border-width: 1px;border-color: #16191a">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" idUser="navbar-main">
                <ul class="nav navbar-nav" style="text-align: center">
                    <li><div class="navbar-header">
                        <a class="navbar-brand" style="background: #F1A9A0; color: #fff; font-size: 14px;" href="${pageContext.session.servletContext.contextPath}/controller?command=home_command"><fmt:message key="home" />   <span class="glyphicon glyphicon-home"></span></a>
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
                    </c:choose>
                    <li class="dropdown">
                        <a style="background:#F1A9A0;" class="dropdown-toggle" href="#" data-toggle="dropdown"><fmt:message key="lang" /> <span class="glyphicon glyphicon-globe"></span></a>
                        <ul class="dropdown-menu" style="background:#F1A9A0;">
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=ru&jsp=userpage.jsp">RU</a></li>
                            <li><a href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&lang=en&jsp=userpage.jsp">EN</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div>
            ${login} <br/>
            ${firstname} ${lastname} <br/>
            ${email} <br/>
        <br/>
        <image style="height: 100px;" src="https://st2.depositphotos.com/5934840/11830/v/950/depositphotos_118307520-stock-illustration-user-pictogram-icon.jpg" />
    </div>

    <c:choose>
        <c:when test="${active_offers}">
            <table class="table">
                <thead>
                    <tr class="norCell">
                        <th>Offer Info</th>
                        <th>Comment</th>
                        <th>Courier info</th>
                        <th>Start date</th>
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
                    <br/>
                    <script>
                        $(document).ready(function(){
                            $("#myBtn${currentOffer.key.idOffer}").click(function(){
                                $("#myModal${currentOffer.key.idOffer}").modal();
                            });
                        });
                    </script>
                </c:forEach>
                <c:forEach var="currentOffer" items="${offerMap}">
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
                    <br/>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${past_offers}">
            <table class="table">
                <thead>
                <tr>
                    <th>Offer Info</th>
                    <th>Comment</th>
                    <th>Courier info</th>
                    <th>Start date</th>
                    <th>Finish date</th>
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
                    <br/>
                    <script>
                        $(document).ready(function(){
                            $("#myBtn${currentOffer.key.idOffer}").click(function(){
                                $("#myModal${currentOffer.key.idOffer}").modal();
                            });
                        });
                    </script>
                </c:forEach>
                <c:forEach var="currentOffer" items="${offerMap}">
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
                    <br/>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${user_offers}">
            <table class="table">
                <thead>
                <tr>
                    <th>Goods</th>
                    <th>Couriers</th>
                    <th>Comment</th>
                    <th>Status</th>
                    <th>Responses</th>
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
                    <br/>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${send_user_offer}">
            <form name="userOffer" action="${pageContext.session.servletContext.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="user_offer">
                <div class="form-group">
                    <label>Select type of packages:</label>
                    <select multiple class="form-control" name="package">
                        <option value="food">Food</option>
                        <option value="clothes">Clothes</option>
                        <option value="medicine">Medicine</option>
                        <option value="technics">Technics</option>
                        <option value="home">Home goods</option>
                        <option value="cosmetics">Cosmetics</option>
                    </select>
                </div>
                <label>Courier number</label>
                <input type="text" name="needed_courier_number" value=""/>
                <div class="form-group">
                    <label>Comment:</label></br>
                    <textarea name="comment" cols="80"></textarea>
                </div>
                <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span>Offer</button>
            </form>
        </c:when>
    </c:choose>

    <a href="${pageContext.session.servletContext.contextPath}/controller?command=user_button&button=user_offers">Your offers</a> </br>
    <a href="${pageContext.session.servletContext.contextPath}/controller?command=user_button&button=active_offers">Active offers</a> </br>
    <a href="${pageContext.session.servletContext.contextPath}/controller?command=user_button&button=past_offers">Past offers</a> </br>
    <a href="${pageContext.session.servletContext.contextPath}/controller?command=user_button&button=send_user_offer">Add user offer</a> </br>
    <a href="${pageContext.session.servletContext.contextPath}/controller?command=logout"><fmt:message key="logout"/></a>


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
