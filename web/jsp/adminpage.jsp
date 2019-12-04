<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="ru" />
<fmt:bundle basename="pageContent" prefix="label.">
    <html>
    <head>
        <title>Admin Account</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    </head>
    <body bgcolor="#ffe4c4">
        <h1>Admin <fmt:message key="account" /></h1>
        ${login} <br/>
        ${firstname} ${lastname} <br/>
        ${email} <br/>
        <br/>
        <a href="${pageContext.session.servletContext.contextPath}/controller?command=admin_button&button=courier_offers">Courier offers</a> </br>
        <a href="${pageContext.session.servletContext.contextPath}/controller?command=admin_button&button=user_offers">User offers</a> </br>
        <c:choose>
            <c:when test="${courier_offers}">
                <table class="table">
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
                        <c:forEach var="currentOffer" items="${offerMap}">
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
            </c:when>
            <c:when test="${user_offers}">
                <table class="table">
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
                    <c:forEach var="currentOffer" items="${offerMap}">
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
            </c:when>
        </c:choose>
        <a href="${pageContext.session.servletContext.contextPath}/controller?command=logout">Logout</a>
    </body>
    </html>
</fmt:bundle>