/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.SubjectDAO;
import duyhp.dtos.QuestionDetailDTO;
import duyhp.dtos.SubjectDTO;
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
@WebServlet(name = "UpdatePageServlet", urlPatterns = {"/UpdatePageServlet"})
public class UpdatePageServlet extends HttpServlet {

    private final String UPDATEE_PAGE = "updateQuestionPage";
    private final String FAIL_ID = "search";

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
        ServletContext context = request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(FAIL_ID);
        try {
            HttpSession session = request.getSession();
            String id = request.getParameter("idQuestion");
            session.setAttribute("ID_UPDATE", id);
            if (!id.isEmpty()) {
                ArrayList<QuestionDetailDTO> listQuestionDetai = (ArrayList<QuestionDetailDTO>) session.getAttribute("SEARCH_QUES");
                if (listQuestionDetai != null) {
                    for (QuestionDetailDTO questionDetailDTO : listQuestionDetai) {
                        if (questionDetailDTO.getQuestion().getIdQues().trim().equals(id.trim())) {
                            session.setAttribute("UPDATE_QUESTION", questionDetailDTO);
                            url = mapping.get(UPDATEE_PAGE);
                            SubjectDAO dao = new SubjectDAO();
                            ArrayList<SubjectDTO> listSubject = dao.loadSubject();
                            for (int i = 0; i < listSubject.size(); i++) {
                                if (listSubject.get(i).getId().trim().equals(questionDetailDTO.getQuestion().getSubject().trim())) {
                                    SubjectDTO obj = listSubject.get(0);
                                    listSubject.set(0, listSubject.get(i));
                                    listSubject.set(i, obj);
                                }
                            }
                            session.setAttribute("UPDATE_SUBJECT", listSubject);
                        }
                    }
                }
            }
        } catch (NamingException ne) {
            Logger.getLogger(UpdatePageServlet.class.getName()).error(ne.toString());
        } catch (SQLException sq) {
            Logger.getLogger(UpdatePageServlet.class.getName()).error(sq.toString());
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
