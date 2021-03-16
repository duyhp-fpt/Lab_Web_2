<%-- 
    Document   : register
    Created on : Feb 17, 2021, 2:50:18 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <h1 style="color: chartreuse; text-align: center; font-family: sans-serif">Register Page</h1>
        <h5 style="padding-left: 10.8em;color: slategrey">Already have an Account..&ensp;<a href="index" style="color: orangered; font-family: serif">Login Now!</a></h5>
        <hr  width="80%" align="center"/>
        <h2 style="color: green; text-align: center; font-family: inherit; text-transform: uppercase">Create Account</h2>
        <c:set var="errors" value="${requestScope.ERRORS_CREATE}"></c:set>
            <form action="register" method="POST" style="text-align: center">
                <label style="margin-left: 175px;">Email*</label>
                <input style="padding: 2px 10px" type="text" name="txtEmail" value="${param.txtEmail}" />&nbsp;(6 - 60 characters) [Ex: duyhpse140893@fpt.edu.vn]
            <h6 style="color: red">${errors.emailLengthError}</h6>
            <h6 style="color: red">${errors.emailFormatError}</h6>
            <h6 style="color: red">${errors.emailAlreadyExist}</h6>
            <label style="margin-left: -80px;">Full Name*</label>
            <input style="padding: 2px 10px" type="text" name="txtName" value="${param.txtName}" />&nbsp;(6 - 30characters)<br>
            <h6 style="color: red">${errors.nameLengthError}</h6>
            <label style="margin-left: -73px;">Password*</label>
            <input style="padding: 2px 10px" type="password" name="txtPassword" value="${param.txtPassword}" />&nbsp;(6 - 30characters)<br>
            <h6 style="color: red">${errors.passwordLengthError}</h6>
            <label style="margin-left: -65px;">Confirm*</label>
            <input style="padding: 2px 10px" type="password" name="txtConfirm" value="" />&nbsp;(6 - 30characters)<br>
            <h6 style="color: red">${errors.confirmNoMatchError}</h6>
            <input type="hidden" name="txtStatus" value="New" />
            <input type="hidden" name="txtRole" value="0" />
            <input style="color: blue;padding: 10px 20px;" type="submit" value="register" name="btnAction" />
        </form>
    </body>
</html>

