/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.SubjectDTO;
import duyhp.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class SubjectDAO implements Serializable{

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

    public ArrayList<SubjectDTO> loadSubject() throws NamingException, SQLException{
        ArrayList<SubjectDTO> listSubject = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select s.idSub, s.nameSub\n"
                        + "from tblSubject s ";
                pst = con.prepareCall(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listSubject.add(new SubjectDTO(rs.getString("idSub"), rs.getString("nameSub")));
                }
            }
        } finally {
            closeConnection();
        }
        return listSubject;
    }
}
