/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuizDAO;
import duyhp.dtos.QuizDTO;
import duyhp.dtos.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
@WebServlet(name = "StartQuizServlet", urlPatterns = {"/StartQuizServlet"})
public class StartQuizServlet extends HttpServlet {

    private final String QUIZ_START = "quizController";

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
        ArrayList<QuizDTO> listQuestion = new ArrayList<>();
        ServletContext context = request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(QUIZ_START);
        try {
            float totalTime = Float.parseFloat(request.getParameter("txtTimeForSub"));
            String idSubject = request.getParameter("drbSubject");
            String numberQuestion = request.getParameter("txtNumberQuestion");
            ArrayList<String> listKey = new ArrayList<>();
            if (idSubject != null) {
                QuizDAO dao = new QuizDAO();
                if (idSubject.trim().equals("PRJ311")) {
                    listKey = dao.getRandomKeyPRJ311(idSubject.trim(), numberQuestion);
                } else if (idSubject.trim().equals("PRJ321")) {
                    listKey = dao.getRandomKeyPRJ321(idSubject.trim(), numberQuestion);
                }
                boolean loop = false;
                String idQuiz = null;
                do {
                    loop = false;
                    idQuiz = duyhp.utils.GenerateID.getID("quiz");
                    if (dao.getidQuiz().contains(idQuiz)) {
                        loop = true;
                    }
                } while (loop);
                HttpSession session = request.getSession();
                UserDTO currentUser = (UserDTO) session.getAttribute("USER");
                String emailUser = null;
                if (currentUser != null) {
                    emailUser = currentUser.getEmail();
                }
                Date date = new Date();
                Timestamp currentTime = new Timestamp(date.getTime());
                String titleQuiz = idSubject.trim() + "[" + currentTime + "]";
                //Time dung quiz
                Timestamp timeStopQuiz = new Timestamp(currentTime.getTime() + TimeUnit.MINUTES.toMillis((long) totalTime));
                session.setAttribute("TIME_END", timeStopQuiz);
                //tao moi quiz xong
                boolean isCreated = dao.takeQuiz(idQuiz, titleQuiz, idSubject, emailUser, currentTime, timeStopQuiz);
                //nap de vao quiz
                if (isCreated) {
                    for (String idQuest : listKey) {
                        dao.inserstQuestion(idQuiz, idQuest);
                    }
                    for (String idQuest : listKey) {
                        QuizDTO Question = dao.createatOption(idQuest);
                        listQuestion.add(Question);
                    }
                    session.setAttribute("SUBJECT", idSubject);
                    session.setAttribute("QUIZ", listQuestion);
                    session.setAttribute("IDQUIZ", idQuiz);
                }
            }
        } catch (NamingException na) {
            Logger.getLogger(StartQuizServlet.class.getName()).error(na.toString());
        } catch (SQLException sq) {
            Logger.getLogger(StartQuizServlet.class.getName()).error(sq.toString());
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
