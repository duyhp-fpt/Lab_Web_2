<%-- 
    Document   : resultpage
    Created on : Feb 17, 2021, 2:49:39 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result Page</title>
    </head>
    <body>
        <c:set var="questionDetail" value="${sessionScope.UPDATE_QUESTION}"></c:set>
            <!-- Welcome zone -->
        <c:set var="USER" value="${sessionScope.USER}"></c:set>
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
            <h1 style="color: green; text-align: center; font-family: sans-serif">Quiz Online</h1>
            <hr  width="80%" align="center"/>
        </div>
        <div style="margin-left: 30px; font-family: sans-serif">
            <h1 style="color: gray; text-transform: uppercase; font-size: 60px">Xin Chao</h1>
            <label>Student's Name: </label>
            <h3 style="color: green">${USER.name}</h3>
            <label>Student's Email:</label>
            <h5> ${USER.email}</h5>
        </div>
        <div style="margin-left: 500px;font-family: sans-serif">
            <h1 style="color: green">Result's Quiz
                <a href="QuizHomePage" style="font-size: 15px;color: red">Back to Take Quiz Page</a>
            </h1>
            <table border="1" style="text-align: center">
                <thead>
                    <tr>
                        <th>Quiz's Subject</th>
                        <th>Quiz's ID</th>
                        <th>Total Question</th>
                        <th>Score per Question</th>
                        <th>Correct Answer</th>
                        <th>Total Score</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${sessionScope.SUBJECT}</td>
                        <td>${sessionScope.IDQUIZ}</td>
                        <td>${sessionScope.TOTAL_QUESTION}</td>
                        <td>${sessionScope.SCORE_PER_QUESE}</td>
                        <td>${sessionScope.RIGHT_ANSWER}</td>
                        <td>${sessionScope.SCORE}</td>
                    </tr>
                </tbody>
            </table>

        </div>
    </body>
</html>

