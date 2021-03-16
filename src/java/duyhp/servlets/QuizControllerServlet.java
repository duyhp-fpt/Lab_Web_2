/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuizDAO;
import duyhp.dtos.QuizDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Huynh Duy
 */
@WebServlet(name = "QuizControllerServlet", urlPatterns = {"/QuizControllerServlet"})
public class QuizControllerServlet extends HttpServlet {

    private final String QUIZ_PAGE = "quizPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        ArrayList<QuizDTO> listQuestion = null;
        ServletContext context = request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(QUIZ_PAGE);
        int currentQuestion = 1;
        try {
            String action = request.getParameter("btnAction");
            String curentQuestionString = request.getParameter("txtCurrent");
            String questionSelect = request.getParameter("questionNumber");
            String userChoice = request.getParameter("rbOption");
            if (questionSelect != null) {
                currentQuestion = Integer.parseInt(questionSelect);
            }
            if (session != null) {
                listQuestion = (ArrayList<QuizDTO>) session.getAttribute("QUIZ");

            }
            if (curentQuestionString != null) {
                currentQuestion = Integer.parseInt(curentQuestionString);
            }
            String idSubject = (String) session.getAttribute("SUBJECT");
            int numberQuestion = Integer.parseInt(request.getParameter("txtNumberQuestion"));
            if (action != null) {
                if (idSubject.trim().equals("PRJ311")) {
                    if (action.equals("Next") && currentQuestion <= numberQuestion) {
                        currentQuestion = currentQuestion + 1;
                    } else if (action.equals("Previous") && currentQuestion > 0) {
                        currentQuestion = currentQuestion - 1;
                    }
                } else if (idSubject.trim().equals("PRJ321")) {
                    if (action.equals("Next") && currentQuestion <= numberQuestion) {
                        currentQuestion = currentQuestion + 1;
                    } else if (action.equals("Previous") && currentQuestion > 0) {
                        currentQuestion = currentQuestion - 1;
                    }
                }
            }
            QuizDAO dao = new QuizDAO();
            if (userChoice != null && currentQuestion > 1 && currentQuestion < listQuestion.size() + 1) {
                if (userChoice.equals(listQuestion.get(currentQuestion - 2).getAnswer1().getId())) {
                    listQuestion.get(currentQuestion - 2).setUserChoiceId(userChoice);
                    dao.updateIdChoice(listQuestion.get(currentQuestion - 2).getIdQuest(), userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion - 2).getAnswer2().getId())) {
                    listQuestion.get(currentQuestion - 2).setUserChoiceId(userChoice);
                    dao.updateIdChoice(listQuestion.get(currentQuestion - 2).getIdQuest(), userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion - 2).getAnswer3().getId())) {
                    listQuestion.get(currentQuestion - 2).setUserChoiceId(userChoice);
                    dao.updateIdChoice(listQuestion.get(currentQuestion - 2).getIdQuest(), userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion - 2).getAnswer4().getId())) {
                    listQuestion.get(currentQuestion - 2).setUserChoiceId(userChoice);
                    dao.updateIdChoice(listQuestion.get(currentQuestion - 2).getIdQuest(), userChoice);
                }
            }
            if (userChoice != null && currentQuestion > 1 && currentQuestion < listQuestion.size() + 1) {
                if (userChoice.equals(listQuestion.get(currentQuestion).getAnswer1().getId())) {
                    listQuestion.get(currentQuestion).setUserChoiceId(userChoice);
                    dao.updateIdChoice(listQuestion.get(currentQuestion).getIdQuest(), userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion).getAnswer2().getId())) {
                    listQuestion.get(currentQuestion).setUserChoiceId(userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion).getAnswer3().getId())) {
                    listQuestion.get(currentQuestion).setUserChoiceId(userChoice);
                }
                if (userChoice.equals(listQuestion.get(currentQuestion).getAnswer4().getId())) {
                    listQuestion.get(currentQuestion).setUserChoiceId(userChoice);
                }
            }
            if (currentQuestion > 0 && currentQuestion <= listQuestion.size()) {
                QuizDTO currentQuiz = listQuestion.get(currentQuestion - 1);
                session.setAttribute("QUIZ", listQuestion);
                session.setAttribute("QUIZ_CURENT", currentQuiz);
                session.setAttribute("QUESTION_NMBER", currentQuestion);
            }
            session.setAttribute("MAX", listQuestion.size());
            int totalQuestion = listQuestion.size();
            session.setAttribute("TOTAL_QUESTION", totalQuestion);
        } catch (NamingException na) {
            Logger.getLogger(QuizControllerServlet.class.getName()).error(na.toString());
        } catch (SQLException sq) {
            Logger.getLogger(QuizControllerServlet.class.getName()).error(sq.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
