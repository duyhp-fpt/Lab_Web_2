<%-- 
    Document   : takequizhome
    Created on : Feb 17, 2021, 2:49:54 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take Quiz Page</title>
    </head>
    <body>
        <c:set var="questionDetail" value="${sessionScope.UPDATE_QUESTION}"></c:set>
            <!-- Welcome zone -->
        <c:set var="USER" value="${sessionScope.USER}"/>
        <c:if test="${not empty USER}">
            <br>
            <div style="float:right;margin-right: 170px; font-family: sans-serif">
                <h5 style="color: coral">
                    <c:if test="${USER.role eq 'Lecture'}">
                        <small style="color: brown">${USER.role}</small>
                    </c:if>
                    <c:if test="${USER.role eq 'Student'}">
                        <small style="color: royalblue">${USER.role}</small> 
                    </c:if>
                    <br>
                    <form action="logout">
                        ${USER.name}  &nbsp;<input type="submit" value="Logout" style="font-family:inherit "/> 
                    </form>
                </h5>  
            </div>
        </c:if>
        <div>        
            <h1 style="color: green; text-align: center; font-family: sans-serif ;font-size: 50px">Quiz Online</h1>
            <hr  width="80%" align="center"/>
        </div>
        <div style="margin-left: 30px; font-family: sans-serif">
            <h3 style="color: gray; text-transform: uppercase; font-size: 50px">Welcome to Quiz Online</h3>
            <h3 style="color: green">Student's Name: ${USER.name}</h3>
            <h3 style="color: green">Student's Email: ${USER.email}</h3>
            <a href="showHistory">Show Quiz's History</a>
        </div>
        <c:set var="Subject" value="${sessionScope.LIST_SUBJECT}"></c:set>
        <c:set var="quizExist" value="${sessionScope.quizExist}"></c:set>
            <form action="startQuiz" method="POST">
                <div style="margin-left: 700px;text-align: center">
                    <table border="1" style=" width: 500px">
                        <thead>
                            <tr>
                                <th>Take Quiz*</th>
                                <th>Time</th>
                                <th>Number Question</th>
                                <th>Action*</th>
                                <c:if test="${not empty quizExist}">
                                <th>Quiz Exist*</th>
                                <th>Action*</th>
                                </c:if>
                        </tr>
                    </thead>

                    <tr>
                        <td>
                            <select name="drbSubject">
                                <c:forEach var="subject" items="${Subject}">
                                    <option value="${subject.id}">${subject.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                        <input type="text" name="txtTimeForSub" value="" />
                        </td>
                        <td>
                        <input type="text" name="txtNumberQuestion" value="" />
                        </td>
                        <td>
                            <input type="submit" value="Start Quiz" name="btnStart" />
                        </td>
                        <c:if test="${not empty quizExist}">
                            <td></td>
                            <td></td>
                        </c:if>
                    </tr>
                </table>
            </div>
        </form>
    </tbody>
</html>

