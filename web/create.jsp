<%-- 
    Document   : create
    Created on : Feb 17, 2021, 2:48:03 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
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
    <h4 style="color: gray;text-transform: uppercase">Create Questions</h4>
    <div style="font-family: sans-serif">
        <form action="createQuestion" method="POST">
            <table border="1" style="width: 1000px;text-align: center">
                <thead>
                    <tr>
                        <th>
                            <p>
                                Question's
                            </p>
                        </th>
                        <th>
                            <p>
                                Question's Detail*
                            </p>
                        </th>
                        <th>
                            <p>
                                Question isRight*
                            </p>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Question's Content*</td>
                        <td>
                            <input type="text" name="txtQuestion" value="${param.txtQuestion}" style="width: 500px"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Answer's 1 Detail</td>
                        <td><input type="text" name="txtAnswer1" value="${param.txtAnswer1}" style="width: 500px"/></td>
                        <td>
                            <input type="checkbox" name="chkIsRIght1" value="ON" />
                        </td>
                    </tr>
                    <tr>
                        <td>Answer's 2 Detail</td>
                        <td><input type="text" name="txtAnswer2" value="${param.txtAnswer2}" style="width: 500px"/></td>
                        <td><input type="checkbox" name="chkIsRIght2" value="ON" /></td>
                    </tr>
                    <tr>
                        <td>Answer's 3 Detail</td>
                        <td><input type="text" name="txtAnswer3" value="${param.txtAnswer3}" style="width: 500px"/></td>
                        <td><input type="checkbox" name="chkIsRIght3" value="ON" /></td>
                    </tr>
                    <tr>
                        <td>Answer's 4 Detail</td>
                        <td><input type="text" name="txtAnswer4" value="${param.txtAnswer4}" style="width: 500px" /></td>
                        <td><input type="checkbox" name="chkIsRIght4" value="ON" /></td>
                    </tr>
                </tbody>
                <label style="margin-left: px;">Subject</label> &nbsp;
                <c:set var="listSubject" value="${sessionScope.LIST_SUBJECT}"></c:set>
                <select name="cbSubject">
                    <c:forEach var="subject" items="${sessionScope.LIST_SUBJECT}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </select>
                <label style="margin-left: 10px;">is Active</label> &nbsp;
                <input type="checkbox" name="chkIsActive" value="ON" checked="checked" />
            </table>
            <div>
                <c:set var="errors" value="${requestScope.ERROR}"></c:set>
                <small style="margin-left: 10px;color: red">${errors.nameNotNull}</small>
                <br>
                <small style="margin-left: 10px;color: red">${errors.isRightInclude}</small>
                <br>
                <small style="margin-left: 10px;color: red">${errors.subjectNameNotNull}</small>
                <br>
                <small style="margin-left: 10px;color: red">${errors.subjectNameLength}</small>
                <small style="margin-left: 10px;color: green">${requestScope.SUCCESS}</small>

            </div>
            <div>
                <br>
                <a href="LoadPage" style="margin-left: 10px;">Back to Search Page</a>
                <input style="margin-left:700px;" type="submit" value="Create Question" name="btncreate" />
            </div>
        </form>
    </div>
</body>
</html>

