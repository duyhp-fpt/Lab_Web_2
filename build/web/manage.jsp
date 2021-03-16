<%-- 
    Document   : manage
    Created on : Feb 17, 2021, 2:47:28 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage page</title>
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
        <h1 style="color: green; text-align: center; font-family: sans-serif">Quiz Online</h1>
        <hr  width="80%" align="center"/>
    </div>
    <!-- Welcome zone -->
    <div style="margin-left: 50px; font-family: sans-serif">
        <h4 style="color: gray;text-transform: uppercase">Search Questions  &nbsp;&nbsp;&nbsp;   
            <a href="createPage" style="border: 2px solid green;color: darksalmon">Create Question</a></h4>
        <form action="search" method="POST" >
            <div style="margin-left: 60px">
                <label for="name">Name's Question:</label>
                <input type="text" name="txtSearch" value="${param.txtSearch}" size="50">
                <c:set var="searchValue" value="${param.txtSearch}"></c:set>
                <input type="radio" name="radChoice" id="name"value="name" checked="checked" />
                <br>
                <label for="isActive">Is Active :</label>
                <input type="checkbox" name="chkActive" value="ON" checked="checked" />
                <input type="radio" name="radChoice" id="isActive" value="active" />
                <c:set var="active" value="${param.isActive}"></c:set>
                <br>
                <label for="idQues">Subject:</label>
                <c:set var="listSubject" value="${sessionScope.LIST_SUBJECT}"></c:set>
                <select name="cbSubject">
                    <c:forEach var="subject" items="${sessionScope.LIST_SUBJECT}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </select>
                <input type="radio" id="idQues" name="radChoice" value="idQuest" />
                <br>
                <input type="submit" value="Search" name="btnSearch" />
                <br>
                <input type="hidden" name="lastChoice" value="${param.radChoice}" />
                <c:set var="Seaching" value="${sessionScope.SEARCHING}"></c:set>
                <small style="color: green">${Seaching}</small>
                <c:set var="totalpage" value="${sessionScope.TOTAL_PAGE}"></c:set>
                <div>
                    <c:if test="${totalpage >1}">
                        <br>
                        <label>Page: </label>
                        <c:set var="userChoicePage" value="${sessionScope.USER_CHOICE}"></c:set>
                        <c:if test="${userChoicePage == 'searchByName'}">
                            <c:forEach var="count" begin="1" end="${totalpage}">
                                <c:url var="page" value="searchByName">
                                    <c:param name="pageNumber" value="${count}"></c:param>
                                </c:url>
                                <a href="${page}" style="color: green">${count}</a>
                            </c:forEach>
                        </c:if>
                        <c:if test="${userChoicePage == 'searchByIsActive'}">
                            <c:forEach var="count" begin="1" end="${totalpage}">
                                <c:url var="page" value="searchByIsActive">
                                    <c:param name="pageNumber" value="${count}"></c:param>
                                    <c:param name="chkActive" value="${requestScope.SAVE_CHOICE}"></c:param>
                                </c:url>
                                <a href="${page}" style="color: green">${count}</a>
                            </c:forEach>
                        </c:if>
                        <c:if test="${userChoicePage == 'searchBySubject'}">
                            <c:forEach var="count" begin="1" end="${totalpage}">
                                <c:url var="page" value="searchBySubject">
                                    <c:param name="pageNumber" value="${count}"></c:param>
                                    <c:param name="cbSubject" value="${requestScope.SAVE_SUBJECT}"></c:param>
                                </c:url>
                                <a href="${page}" style="color: green">${count}</a>
                            </c:forEach>
                        </c:if>
                    </c:if>
                    <br>
                    <div style="text-align: center">
                        <c:set var="listSearch" value="${sessionScope.SEARCH_QUES}" ></c:set>
                        <c:if test="${empty listSearch}">
                            <h1 style="text-align: center; color: gray;text-transform: uppercase">No record data!</h1>
                        </c:if>
                        <c:if test="${not empty listSearch}">
                            <table border="1" style="width: 1300px">
                                <thead>
                                    <tr>
                                        <td>No*</td>
                                        <th>ID's Question*</th>
                                        <th>Subject*</th>
                                        <th>Detail*</th>
                                        <th>Create Date*</th>
                                        <th>Status*</th>
                                        <th>Action*</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="ques"  items="${listSearch}" varStatus="number">
                                    <c:set var="questionDetail" value="${ques.question}"></c:set>
                                    <tr>
                                        <td>${number.count}</td>
                                        <td>${questionDetail.idQues}</td>
                                        <td>${questionDetail.subject}</td>
                                        <td style="text-align: left">${questionDetail.contentQus}
                                            <br>
                                    <c:forEach var="answer" items="${ques.listAnswer}">
                                        <c:if test="${answer.isRight == true}">
                                            <label>Answer:</label>
                                            <small style="color: red; text-transform: uppercase">${answer.ansDetail}</small>
                                            <br>
                                        </c:if>
                                        <c:if test="${answer.isRight == false}">
                                            <label>Answer:</label>
                                            <small>${answer.ansDetail}</small>
                                            <br>
                                        </c:if>
                                    </c:forEach> 
                                    </td>
                                    <td>${questionDetail.createDate}</td>
                                    <td>${questionDetail.isActive}</td>
                                    <td>
                                    <c:if test="${questionDetail.isActive == true}">
                                        <c:url var="deleteQuestion" value="delete">
                                            <c:param name="idQuestion" value="${questionDetail.idQues}"></c:param>
                                            <c:param name="lastSearchValue" value="${searchValue}"></c:param>
                                        </c:url>
                                        <a href="${deleteQuestion}" style="border: 2px solid green;color: green;text-transform: uppercase;font-family: sans-serif">Delete</a>
                                    </c:if>
                                    <c:if test="${questionDetail.isActive == false}">
                                        <small style="color: red">Deleted</small>
                                    </c:if>
                                    <br>
                                    <br>
                                    <c:url var="updateQuestion" value="updateShowPage">
                                        <c:param name="idQuestion" value="${questionDetail.idQues}"></c:param>
                                    </c:url>
                                    <a href="${updateQuestion}" style="border: 2px solid green;color: darkcyan;text-transform: uppercase;font-family: sans-serif">Update</a>
                                    </td>
                                    </tr>
                                </c:forEach>
                        </c:if>
                        </tbody>
                        </table>
                    </div>
                </div>
        </form>
    </div>
</body>
</html>
