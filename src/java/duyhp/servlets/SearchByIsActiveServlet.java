/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.QuestionDAO;
import duyhp.dtos.AnswerDTO;
import duyhp.dtos.QuestionDTO;
import duyhp.dtos.QuestionDetailDTO;
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
@WebServlet(name = "SearchByIsActiveServlet", urlPatterns = {"/SearchByIsActiveServlet"})
public class SearchByIsActiveServlet extends HttpServlet {

    private final int NUMBER_OF_PAGE = 20;
    private final String SEARCH_PAGE = "managerPage";

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
        ArrayList<QuestionDTO> listQues = new ArrayList<>();
        ServletContext context = request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(SEARCH_PAGE);
        HttpSession session = request.getSession();
        try {
            QuestionDAO dao = new QuestionDAO();
            int count = 0;
            String isActiveText = request.getParameter("chkActive");
            if (isActiveText == null) {
                isActiveText = "0";
            } else {
                isActiveText = "1";
            }
            request.setAttribute("SAVE_CHOICE", isActiveText);
            count = dao.countNumberOfQuesByNameByIsActive(isActiveText);
            session.setAttribute("SEARCHING", "Status: Search question with isActive");
            String pageN = request.getParameter("pageNumber");
            int page = 1;
            if (pageN != null) {
                page = Integer.parseInt(pageN);
            }
            int totalPage;
            if (count % NUMBER_OF_PAGE != 0) {
                totalPage = (count / NUMBER_OF_PAGE) + 1;
            } else {
                totalPage = (count / NUMBER_OF_PAGE);

            }
            ArrayList<QuestionDetailDTO> listQuestionDetail = new ArrayList<>();
            listQues = dao.pagingSearchbyIsActive(isActiveText, page);
            for (QuestionDTO listQue : listQues) {
                ArrayList<AnswerDTO> listAnswer = dao.loadAnswerbyIdQues(listQue.getIdQues().trim());
                listQuestionDetail.add(new QuestionDetailDTO(listQue, listAnswer));
            }
            session.setAttribute("SEARCH_QUES", listQuestionDetail);
            session.setAttribute("TOTAL_PAGE", totalPage);

        } catch (NamingException na) {
            Logger.getLogger(SearchByIsActiveServlet.class.getName()).error(na.toString());
        } catch (SQLException sq) {
            Logger.getLogger(SearchByIsActiveServlet.class.getName()).error(sq.toString());
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
