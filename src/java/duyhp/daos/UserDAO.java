/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.UserDTO;
import duyhp.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class UserDAO implements Serializable{

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void closeConnection() throws NamingException, SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public UserDTO getLogin(String email, String password) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "Select c.email, c.name, r.roleName,c.status\n"
                        + "From tblUsers c\n"
                        + "inner join tblRoles r\n"
                        + "on r.roleID = c.roleID\n"
                        + "Where c.email = ? and c.password = ?";
                pst = con.prepareCall(sql);
                pst.setString(1, email);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String emailUser = rs.getString("email");
                    String nameUser = rs.getString("name");
                    String roleName = rs.getString("roleName");
                    String status = rs.getString("status");
                    UserDTO user = new UserDTO(emailUser, nameUser, roleName, status);
                    return user;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean insertUser(String userEmail, String userPassword, String userName, int UserRole, String status) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblUsers(email,password,name,roleID,status) values(?,?,?,?,?)";
                pst = con.prepareCall(sql);
                pst.setString(1, userEmail);
                pst.setString(2, userPassword);
                pst.setString(3, userName);
                pst.setInt(4, UserRole);
                pst.setString(5, status);
                int row = pst.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
