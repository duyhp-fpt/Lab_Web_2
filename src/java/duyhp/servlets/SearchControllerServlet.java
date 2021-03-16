/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SearchControllerServlet", urlPatterns = {"/SearchControllerServlet"})
public class SearchControllerServlet extends HttpServlet {

    private final String SEARCH_PAGE = "search";
    private final String SEARCH_BY_NAME = "searchByName";
    private final String SEARCH_BY_IsActive = "searchByIsActive";
    private final String SEARCH_By_Subject = "searchBySubject";

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
        String url = mapping.get(SEARCH_PAGE);
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                String userChoice;
                String choice = request.getParameter("radChoice");
                if (choice != null) {
                    if (choice.equals("name")) {
                        url = mapping.get(SEARCH_BY_NAME);
                        userChoice = "searchByName";
                        session.setAttribute("USER_CHOICE", userChoice);
                    }
                    if (choice.trim().equals("active")) {
                        url = mapping.get(SEARCH_BY_IsActive);
                        userChoice = "searchByIsActive";
                        session.setAttribute("USER_CHOICE", userChoice);
                    }
                    if (choice.trim().equals("idQuest")) {
                        url = mapping.get(SEARCH_By_Subject);
                        userChoice = "searchBySubject";
                        session.setAttribute("USER_CHOICE", userChoice);
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SearchControllerServlet.class.getName()).error(e.toString());
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
