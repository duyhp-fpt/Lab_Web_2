<%-- 
    Document   : history
    Created on : Feb 17, 2021, 2:48:12 PM
    Author     : Huynh Duy
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
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
            <h1 style="color: green; text-align: center; font-family: sans-serif ;font-size: 50px">Quiz Online</h1>
            <hr  width="80%" align="center"/>
        </div>
        <div style="margin-left: 30px; font-family: sans-serif">
            <h3 style="color: gray; text-transform: uppercase; font-size: 50px">Quiz History</h3>
            <h3 style="color: green">Student's Name: ${USER.name}</h3>
            <h3 style="color: green">Student's Email: ${USER.email}</h3>
            <a href="QuizHomePage" style="font-size: 15px;color: red">Back to Take Quiz Page</a>
        </div>
        <div style="display: flex">
            <div style="margin-left: 10px">
                <c:set var="history" value="${sessionScope.HISTORY}"></c:set>
                <c:if test="${not empty history}">
                    <table border="1" style="text-align: center">
                        <thead>
                            <tr><th>Number*</th>
                                <th>Quiz's ID</th>
                                <th>Quiz's Title</th>
                                <th>Quiz's Subject</th>
                                <th>Quiz's Time Start</th>
                                <th>Quiz's Score</th>
                                <th>Quiz's Detail*</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="historyDetail" items="${history}" varStatus="number">
                                <tr>
                                    <td>${number.count}</td>
                                    <td>${historyDetail.idQUiz}</td>
                                    <td>${historyDetail.titleQuiz}</td>
                                    <td>${historyDetail.idSubject}</td>
                                    <td>${historyDetail.timeStart}</td>
                                    <td>${historyDetail.score}</td>
                                    <td>
                                        <c:url var="detail" value="showDetail">
                                            <c:param name="idQuiz" value="${historyDetail.idQUiz}"></c:param> 
                                        </c:url>
                                        <a href="${detail}">More Detail</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
            <div style="margin-left: 30px">
                <h1>Search by Subject</h1>
                <form action="showHistory" method="POST">
                    <c:set var="subject" value="${sessionScope.LIST_SUBJECT}"></c:set>
                        <select name="drbSubject">
                        <c:forEach var="subjectHis" items="${subject}">
                            <option value="${subjectHis.id}">${subjectHis.name}</option>    
                        </c:forEach>
                    </select>
                    <input type="submit" value="Search" />
                    <br>
                    <c:set var="totalPage" value="${sessionScope.TOTAL_PAGE_HISTORY}"></c:set>
                    <c:if test="${totalPage > 1 }">
                        <small>History Page: </small>
                        <c:forEach var="count" begin="1" end="${totalPage}">
                            <c:url var="page" value="showHistory">
                                <c:param name="txtPageN" value="${count}"></c:param>
                                <c:if test="${not empty requestScope.LAST_SEARCH}">
                                    <c:param name="drbSubject" value="${requestScope.LAST_SEARCH}"></c:param>
                                </c:if>
                            </c:url>
                            <a href="${page}" style="color: green">${count}</a>
                        </c:forEach>
                    </form>
                    <h5>
                        List's Number: ${sessionScope.PAGEN}
                    </h5>
                </c:if>
                <p style="color: green">${requestScope.SHOW}</p>
            </div>
        </div>

    </body>
</html>

