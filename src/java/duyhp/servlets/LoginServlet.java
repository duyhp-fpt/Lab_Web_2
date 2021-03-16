/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.UserDAO;
import duyhp.dtos.UserDTO;
import duyhp.utils.UserVAD;
import java.io.IOException;
import java.util.Map;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String LOGIN_FAIL = "login.jsp";
    private final String LOGIN_LECTURE = "LoadPage";
    private final String LOGIN_STUDENT = "QuizHomePage";

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
        String url = LOGIN_FAIL;
        try {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            if (email != null && password != null) {
                UserDAO dao = new UserDAO();
                String encodePassword = UserVAD.encodePassword(password);
                UserDTO currentUser = dao.getLogin(email, encodePassword);
                if (currentUser != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", currentUser);
                    if (currentUser.getRole().equals("Lecture")) {
                        url = mapping.get(LOGIN_LECTURE);
                    }
                    if (currentUser.getRole().equals("Student")) {
                        url = mapping.get(LOGIN_STUDENT);
                    }
                } else {
                    request.setAttribute("LOGIN_FAIL", "Email or Password is incorrect!");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(LoginServlet.class.getName()).error(e.toString());

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
