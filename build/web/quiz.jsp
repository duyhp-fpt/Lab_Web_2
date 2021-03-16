<%-- 
    Document   : quiz
    Created on : Feb 17, 2021, 2:47:37 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Page</title>
    </head>
    <body>
        <c:set var="USER" value="${sessionScope.USER}"/>
        <c:if test="${not empty USER}">
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
        <c:set var="timeEnd" value="${sessionScope.TIME_END}"></c:set>        
            <form action="quizController" method="POST">

                <div style="display: flex;margin-left: 170px">
                    <div style="  width: 100px;
                         border: 5px solid green;
                         padding: 15px;
                         margin: 10px;
                         font-family: sans-serif;font-size: 20px">
                        Time remaining: 
                        <p id="demo" style="color: green"></p>
                        <script>
                            // Set the date we're counting down to
                            var timeStamp = '${timeEnd}';
                            var countDownDate = new Date(timeStamp + '').getTime();

                            // Update the count down every 1 second
                            var x = setInterval(function () {

                                // Get today's date and time
                                var now = new Date().getTime();

                                // Find the distance between now and the count down date
                                var distance = countDownDate - now;

                                // Time calculations for days, hours, minutes and seconds
                                var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                                // Output the result in an element with id="demo"
                                document.getElementById("demo").innerHTML = days + "d " + hours + "h "
                                        + minutes + "m " + seconds + "s ";

                                // If the count down is over, write some text 
                                if (distance < 0) {
                                    clearInterval(x);
                                    document.getElementById("demo").innerHTML = "EXPIRED";
                                    window.location.href='finishQuiz';
                                }
                            }, 1000);
                        </script>
                    </div>
                <c:set var="number" value="${sessionScope.QUESTION_NMBER}"></c:set>
                    <div style=" width: 1000px;
                         height: 500px;
                         border: 5px solid green;
                         padding: 15px;
                         margin: 10px;
                         font-family: sans-serif">
                        <h4>
                            <div>
                                Question's Number: <small style="color: tomato">${number}</small>
                        </div>
                    </h4>
                    <c:set var="question" value="${sessionScope.QUIZ_CURENT}"></c:set>
                        <div style="margin-left: 50px">
                            <h3 style="font-size: 40px">Question: ${question.quizContent}</h3>
                        <div style="font-size: 20px">
                            <c:if test="${question.userChoiceId eq question.answer1.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer1.id}" checked="checked"/>
                            </c:if>
                            <c:if test="${question.userChoiceId != question.answer1.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer1.id}" />
                            </c:if>
                            <label for="option1"><small> Answer 1: </small> ${question.answer1.content}</label>
                            <br>
                            <c:if test="${question.userChoiceId eq question.answer2.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer2.id}" checked="checked"/>
                            </c:if>
                            <c:if test="${question.userChoiceId != question.answer2.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer2.id}" />
                            </c:if>
                            <label for="option2"><small> Answer 2: </small> ${question.answer2.content}</label>
                            <br>
                            <c:if test="${question.userChoiceId eq question.answer3.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer3.id}" checked="checked"/>
                            </c:if>
                            <c:if test="${question.userChoiceId != question.answer3.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer3.id}" />
                            </c:if>
                            <label for="option3"><small> Answer 3: </small> ${question.answer3.content}</label>
                            <br>
                            <c:if test="${question.userChoiceId eq question.answer4.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer4.id}" checked="checked"/>
                            </c:if>
                            <c:if test="${question.userChoiceId != question.answer4.id}">
                                <input id="option1" type="radio" name="rbOption" value="${question.answer4.id}" />
                            </c:if>
                            <label for="option4"><small> Answer 4: </small> ${question.answer4.content}</label>
                        </div>
                    </div>
                    <br>
                    <div style="margin-left: 800px; margin-top: 20px">
                        <c:set var="lim" value="${sessionScope.MAX}"></c:set>
                        <c:if test="${1 == sessionScope.QUESTION_NMBER}">
                            <input type="submit" value="Previous" name="btnAction" disabled="disabled" />
                        </c:if>
                        <c:if test="${1 != sessionScope.QUESTION_NMBER}">
                            <input type="submit" value="Previous" name="btnAction" />
                        </c:if>
                        <c:if test="${lim == sessionScope.QUESTION_NMBER}">
                            <input type="submit" value="Next" name="btnAction" disabled="disabled" />
                        </c:if>
                        <c:if test="${lim != sessionScope.QUESTION_NMBER}">
                            <input type="submit" value="Next" name="btnAction" />
                        </c:if>
                        <input type="hidden" name="txtCurrent" value="${number}" />
                    </div>
                </div>
                <div style=" width: 100px;
                     height: 500px;
                     border: 5px solid green;
                     padding: 15px;
                     margin: 10px;
                     font-family: sans-serif">
                    <h4>Question's Number</h4>
                    <c:set var="totalQues" value="${sessionScope.TOTAL_QUESTION}"></c:set>
                    <c:forEach var="count" begin="1" end="${totalQues}">
                        <c:url var="page" value="quizController">
                            <c:param name="questionNumber" value="${count}"></c:param>
                        </c:url>
                        <a href="${page}" style="color: green; margin: 1px">${count}</a>
                    </c:forEach>
                    <br>
                    <br>
                    <a href="finishQuiz" style="border: 2px solid red;color: red;text-transform: uppercase;font-family: sans-serif" onclick="return confirm('Do you want to submit quiz!')">Finished</a>
                </div> 
            </div>
        </form>
    </body>
</html>
