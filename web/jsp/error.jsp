<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
    <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}" />
    <h4>Error page</h4>
    <dl>
        <dt>Error info</dt>
            <dd>Exception: ${exception}</dd>
            <dd>Exception type: ${requestScope['javax.servlet.error.exception_type']}</dd>
            <dd>Status code: ${requestScope['javax.servlet.error.status_code']}</dd>
            <dd>Message: ${requestScope['javax.servlet.error.message']}</dd>
            <dd>Request uri: ${requestScope['javax.servlet.error.request_uri']}</dd>
            <dd>Servlet name: ${requestScope['javax.servlet.error.servlet_name']}</dd>
        <dt>StackTrace</dt>
            <c:forEach var="elem" items="${exception.stackTrace}">
                <dd>${elem}</dd>
            </c:forEach>
    </dl>
    <a href="${pageContext.session.servletContext.contextPath}/controller?command=home_command">Main <span class="glyphicon glyphicon-home"></span></a>
</body>
</html>
