/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.servlets;

import duyhp.daos.UserDAO;
import duyhp.dtos.CreateError;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String ERROR_PAGE = "createErrorPage";
    private final String LOGIN_PAGE = "index";

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
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String name = request.getParameter("txtName");
        String status = request.getParameter("txtStatus");
        String role = request.getParameter("txtRole");
        boolean erroFound = false;
        CreateError errors = new CreateError();
        ServletContext context = (ServletContext) request.getServletContext();
        Map<String, String> mapping = (Map<String, String>) context.getAttribute("MAPPING");
        String url = mapping.get(ERROR_PAGE);
        try {
            if (!duyhp.utils.UserVAD.checkEmail(email)) {
                erroFound = true;
                errors.setEmailFormatError("Invalid Email (Ex: duyhpse140893@fpt.edu.vn)");
            }
            if (email.trim().length() < 6 || email.trim().length() > 60) {
                erroFound = true;
                errors.setEmailLengthError("Email must be contained 6-20 charcters");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                erroFound = true;
                errors.setPasswordLengthError("Password must be contained 6-30 charcters");
            }
            if (!confirm.trim().equals(password.trim())) {
                erroFound = true;
                errors.setConfirmNoMatchError("Confirm must be matched with password");
            }
            if (name.trim().length() < 6 || name.trim().length() > 30) {
                erroFound = true;
                errors.setNameLengthError("Name must be contained 6-30 charcters");
            }
            int roles = Integer.parseInt(role);
            if (erroFound) {
                request.setAttribute("ERRORS_CREATE", errors);
            } else {
                UserDAO dao = new UserDAO();
                String encodePassword = duyhp.utils.UserVAD.encodePassword(password);
                boolean result = dao.insertUser(email, encodePassword, name, roles, status);
                if (result) {
                    url = mapping.get(LOGIN_PAGE);
                }
            }
        } catch (NamingException ne) {
            Logger.getLogger(RegisterServlet.class.getName()).error(ne.toString());
        } catch (SQLException sq) {
            String msg = sq.getMessage();
            if (msg.contains("duplicate")) {
                errors.setEmailAlreadyExist("Email is existed");
                request.setAttribute("ERRORS_CREATE", errors);
            }
        } catch (NumberFormatException nf) {
            Logger.getLogger(RegisterServlet.class.getName()).error(nf.toString());
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
