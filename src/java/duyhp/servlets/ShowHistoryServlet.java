/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.HistoryDAO;
import duyhp.dtos.HistoryDTO;
import duyhp.dtos.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ShowHistoryServlet", urlPatterns = {"/ShowHistoryServlet"})
public class ShowHistoryServlet extends HttpServlet {

    private final String HISTORY_PAGE = "historyPage";

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
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(HISTORY_PAGE);
        ArrayList<HistoryDTO> listHistory = null;
        try {
            String idsubject = request.getParameter("drbSubject");
            int page = 1;
            String pageN = request.getParameter("txtPageN");
            HistoryDAO dao = new HistoryDAO();
            HttpSession session = request.getSession();
            UserDTO currentUser = (UserDTO) session.getAttribute("USER");
            int totalHistory = dao.countAll(currentUser.getEmail());
            if (idsubject != null) {
                totalHistory = dao.countAllBySubject(currentUser.getEmail(), idsubject);
            }
            int totalPage;
            if (pageN != null) {
                page = Integer.parseInt(pageN);
            }
            if (totalHistory % 10 != 0) {
                totalPage = (totalHistory / 10) + 1;
            } else {
                totalPage = (totalHistory / 10);
            }
            listHistory = dao.getHistoryAll(page, currentUser.getEmail());
            if (idsubject != null) {
                listHistory = dao.getHistoryBySubject(page, idsubject, currentUser.getEmail());
            }
            request.setAttribute("LAST_SEARCH", idsubject);
            request.setAttribute("SHOW", "Status: Search by idSubject");
            session.setAttribute("HISTORY", listHistory);
            session.setAttribute("PAGEN", pageN);
            session.setAttribute("TOTAL_PAGE_HISTORY", totalPage);
        } catch (NamingException ne) {
            Logger.getLogger(ShowHistoryServlet.class.getName()).error(ne.toString());
        } catch (SQLException sq) {
            Logger.getLogger(ShowHistoryServlet.class.getName()).error(sq.toString());
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
