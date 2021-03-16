/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuizDAO;
import java.io.IOException;
import java.util.Map;
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
@WebServlet(name = "FinishedQuizServlet", urlPatterns = {"/FinishedQuizServlet"})
public class FinishedQuizServlet extends HttpServlet {

    private final String RESULT_PAGE = "resultPage";

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
        int rightAnswer = 0;
        float score = 0;
        ServletContext context = (ServletContext) request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(RESULT_PAGE);
        try {
            HttpSession session = request.getSession();
            String idQuiz = (String) session.getAttribute("IDQUIZ");
            QuizDAO dao = new QuizDAO();
            if (idQuiz != null) {
                boolean isInvalid = dao.quizInvalid(idQuiz);
                if (isInvalid) {
                    rightAnswer = dao.countRightAnswer(idQuiz);
                    int totalQues = dao.countQues(idQuiz);
                    float as = (float) 10 / totalQues;
                    score = (float) as * rightAnswer;
                    if (dao.updateScore(idQuiz, score)) {
                        session.setAttribute("TOTAL_QUESTION", totalQues);
                        session.setAttribute("SCORE_PER_QUESE", as);
                        session.setAttribute("SCORE", score);
                        session.setAttribute("RIGHT_ANSWER", rightAnswer);
                    }
                }
            }
        } catch (Exception e){
            Logger.getLogger(FinishedQuizServlet.class.getName()).error(e.toString());
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
