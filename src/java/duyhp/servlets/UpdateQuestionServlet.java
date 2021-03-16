/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuestionDAO;
import duyhp.dtos.AnswerDTO;
import duyhp.dtos.CreateQuestionError;
import duyhp.dtos.QuestionDetailDTO;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "UpdateQuestionServlet", urlPatterns = {"/UpdateQuestionServlet"})
public class UpdateQuestionServlet extends HttpServlet {

    private final String UPDATE_PAGE = "updateQuestionPage";
    private final String SUCCESS = "managerPage";

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
        boolean canCreate = true;
        CreateQuestionError error = new CreateQuestionError();
        boolean isRight1 = false;
        boolean isRight2 = false;
        boolean isRight3 = false;
        boolean isRight4 = false;
        boolean isActive = false;
        ServletContext context = (ServletContext) request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(UPDATE_PAGE);
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
            HttpSession session = request.getSession();
            String id = (String) session.getAttribute("ID_UPDATE");
            QuestionDAO dao = new QuestionDAO();
            if (subject.isEmpty()) {
                canCreate = false;
                error.setSubjectNameNotNull("Fail: Subject's ID must be not null!");
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
                boolean isUpdated = dao.updateQuestion(id, subject, nameQues, isActive);
                ArrayList<Integer> listId = dao.loadIdOption(id);
                if (isUpdated) {
                    if (!answer1.isEmpty()) {
                        dao.updateOption(listId.get(0), answer1, isRight1);

                    }
                    if (!answer2.isEmpty() || listId.size() < 3) {
                        dao.updateOption(listId.get(1), answer2, isRight2);
                    }
                    if (!answer3.isEmpty() || listId.size() < 4) {
                        dao.updateOption(listId.get(2), answer3, isRight3);
                    }
                    if (!answer4.isEmpty() || listId.size() < 5) {
                        dao.updateOption(listId.get(3), answer4, isRight4);
                    }
                    ArrayList<QuestionDetailDTO> listQues = (ArrayList<QuestionDetailDTO>) session.getAttribute("SEARCH_QUES");
                    for (QuestionDetailDTO listQue : listQues) {
                        if (listQue.getQuestion().getIdQues().trim().equals(id.trim())) {
                            listQue.getQuestion().setContentQus(nameQues);
                            listQue.getQuestion().setIsActive(isActive);
                            ArrayList<AnswerDTO> listOption = listQue.getListAnswer();
                            if (!answer1.isEmpty()) {
                                listOption.get(0).setAnsDetail(answer1);
                                listOption.get(0).setIsRight(isRight1);
                            }
                            if (!answer2.isEmpty() || listOption.size() < 3) {
                                listOption.get(1).setAnsDetail(answer2);
                                listOption.get(1).setIsRight(isRight2);
                            }
                            if (!answer3.isEmpty() || listOption.size() < 4) {
                                listOption.get(2).setAnsDetail(answer3);
                                listOption.get(2).setIsRight(isRight3);
                            }
                            if (!answer4.isEmpty() || listOption.size() < 5) {
                                listOption.get(3).setAnsDetail(answer4);
                                listOption.get(3).setIsRight(isRight4);
                            }
                        }
                    }
                    session.setAttribute("SEARCH_QUES", listQues);
                    url = mapping.get(SUCCESS);

                }
            } else {
                request.setAttribute("ERROR", error);
            }
        } catch (Exception e) {
            Logger.getLogger(UpdateQuestionServlet.class.getName()).error(e.toString());
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
