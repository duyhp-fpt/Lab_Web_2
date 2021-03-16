<%-- 
    Document   : login
    Created on : Feb 16, 2021, 4:07:36 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1 style="text-align: center; color: chartreuse">Welcome to Login Page</h1>
        <hr width="80%" align="center"/>
        <h3 style="text-align: center">Quiz Online</h3>
        <form action="login" method="POST" style="text-align: center">
            Email <input type="text" name="txtEmail" value="" />
            Password <input type="password" name="txtPassword" value="" />
            <c:set var="BUG" value="${requestScope.LOGIN_FAIL}"></c:set>
            <input type="submit" value="Login" name="btnAction" />
            <input type="reset" value="Reset Login" />
            <c:if test="${not empty BUG}">
                <h5>Login Fail: ${BUG}</h5>
            </c:if>
                <h3>Don't have account. <a href="createAccoutPage" style="color: darkgreen">Register</a>
        </form>
    </body>
</html>
