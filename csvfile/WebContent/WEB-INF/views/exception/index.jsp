<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>
<h2>Spring MVC @ExceptionHandler Example</h2>
 
<c:if test="${not empty msg}">
    <h2>${msg}</h2>
</c:if>

</body>
</html>