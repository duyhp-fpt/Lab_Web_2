/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuestionDAO;
import duyhp.dtos.CreateQuestionError;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Huynh Duy
 */
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

    private final String CREATE = "createPage";

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

        String nameQues = request.getParameter("txtQuestion");
        String answer1 = request.getParameter("txtAnswer1");
        String answer2 = request.getParameter("txtAnswer2");
        String answer3 = request.getParameter("txtAnswer3");
        String answer4 = request.getParameter("txtAnswer4");
        String subject = request.getParameter("cbSubject");
        String isActiveText = request.getParameter("chkIsActive");
        String isRightText1 = request.getParameter("chkIsRIght1");
        String isRightText2 = request.getParameter("chkIsRIght2");
        String isRightText3 = request.getParameter("chkIsRIght3");
        String isRightText4 = request.getParameter("chkIsRIght4");
        ArrayList<String> listID;
        boolean canCreate = true;
        CreateQuestionError error = new CreateQuestionError();
        boolean isRight1 = false;
        boolean isRight2 = false;
        boolean isRight3 = false;
        boolean isRight4 = false;
        boolean isActive = false;
        ServletContext context = (ServletContext) request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(CREATE);
        try {
            if (isRightText1 != null) {
                isRight1 = true;
            }
            if (isRightText2 != null) {
                isRight2 = true;
            }
            if (isRightText3 != null) {
                isRight3 = true;
            }
            if (isRightText4 != null) {
                isRight4 = true;
            }
            if (isActiveText != null) {
                isActive = true;
            }
            QuestionDAO dao = new QuestionDAO();
            listID = dao.loadAllIdQuest();
            String idQuestion = null;
            if (subject.isEmpty()) {
                canCreate = false;
                error.setSubjectNameNotNull("Fail: Subject's ID must be not null!");
            } else {
                do {
                    idQuestion = duyhp.utils.GenerateID.getID(subject.trim());
                } while (listID.contains(idQuestion));
            }
            if (subject.length() > 7 && subject.length() < 3) {
                canCreate = false;
                error.setNameNotNull("Fail: (4 - 6 characters)  [Ex: PRJ321]");
            }
            if (nameQues.isEmpty()) {
                canCreate = false;
                error.setNameNotNull("Fail: Question's Content must be not null!");
            }
            if (isRight1 == false && isRight2 == false && isRight3 == false && isRight4 == false) {
                canCreate = false;
                error.setIsRightInclude("Fail: At least one correct Answer!");
            }
            if (canCreate) {
                boolean isCreated = dao.createQuestion(idQuestion, subject, nameQues, isActive);
                if (isCreated) {
                    if (!answer1.isEmpty()) {
                        dao.insertOption(idQuestion, answer1, isRight1);
                    }
                    if (!answer2.isEmpty()) {
                        dao.insertOption(idQuestion, answer2, isRight2);
                    }
                    if (!answer3.isEmpty()) {
                        dao.insertOption(idQuestion, answer3, isRight3);
                    }
                    if (!answer4.isEmpty()) {
                        dao.insertOption(idQuestion, answer4, isRight4);
                    }
                    String Success = "Status: Created!";
                    request.setAttribute("SUCCESS", Success);
                }
            } else {
                request.setAttribute("ERROR", error);
            }
        } catch (NamingException ne) {
            Logger.getLogger(CreateQuestionServlet.class.getName()).error(ne.toString());
        } catch (SQLException sq) {
            Logger.getLogger(CreateQuestionServlet.class.getName()).error(sq.toString());
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
