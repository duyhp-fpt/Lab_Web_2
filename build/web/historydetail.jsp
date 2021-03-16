<%-- 
    Document   : historydetail
    Created on : Feb 17, 2021, 2:49:16 PM
    Author     : Huynh Duy
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Detail</title>
    </head>
    <body>
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
            <h1 style="color: green; text-align: center; font-family: sans-serif ;font-size: 50px">Quiz Online</h1>
            <hr  width="80%" align="center"/>
        </div>
        <div style="margin-left: 30px; font-family: sans-serif">
            <h1 style="color: gray; text-transform: uppercase; font-size: 50px">Quiz history Detail</h1>
            <h3 style="color: green">Student's Name: ${USER.name}</h3>
            <h3 style="color: green">Student's Email: ${USER.email}</h3>
            <h4>Total Right Answer: ${sessionScope.TOTAL_RIGHT} </h4>
            <h3 style="font-size: 30px; text-transform: uppercase; color: red">Score: ${sessionScope.DETAIL_SCORE}</h3>
             <a href="QuizHomePage" style="font-size: 15px;color: red">Back to Take Quiz Page</a>
        </div>
        <div style="display: flex">
            <div style="margin-left: 10px">
                <c:set var="detailHistory" value="${sessionScope.DETAIL}"></c:set>
                    <table border="1" style="text-align: center">
                        <thead>
                            <tr><th>Num*</th>
                                <th>Quiz's Content</th>
                                <th>Answer's 1</th>
                                <th>Answer's 2</th>
                                <th>Answer's 3</th>
                                <th>Answer's 4</th>
                                <th>UserChoice</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="historyDetail" items="${detailHistory}" varStatus="number">
                            <tr>
                                <td>${number.count}</td>
                                <td>${historyDetail.quizContent}</td>
                                <td>${historyDetail.answer1.content}</td>
                                <td>${historyDetail.answer2.content}</td>
                                <td>${historyDetail.answer3.content}</td>
                                <td>${historyDetail.answer4.content}</td>
                                <td>${historyDetail.userChoiceId}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>

