/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.AnswerDTO;
import duyhp.dtos.QuestionDTO;
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
public class QuestionDAO implements Serializable {

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

    public QuestionDTO getAnswer(String idQues) throws NamingException, SQLException {
        QuestionDTO obj = null;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select  t.idQues, t.contentQues, t.idSubject, t.createDateQues, t.statusQues\n"
                        + "from tblQuestion t\n"
                        + "where t.idQues = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                rs = pst.executeQuery();
                if (rs.next()) {
                    obj = new QuestionDTO(rs.getString("idQues"), rs.getString("idSubject"), rs.getString("contentQues"), rs.getTimestamp("createDateQues"), rs.getBoolean("statusQues"));
                    return obj;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public int countNumberOfQuesByName(String searchName) throws NamingException, SQLException {
        int count = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select COUNT(q.idQues)as Num\n"
                        + "from tblQuestion q\n"
                        + "where contentQues LIKE ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, "%" + searchName + "%");

                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("Num");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int countNumberOfQuesByidSubject(String idSubject) throws NamingException, SQLException {
        int count = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select COUNT(q.idQues)as Num\n"
                        + "from tblQuestion q\n"
                        + "where idSubject = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("Num");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int countNumberOfQuesByNameByIsActive(String isActive) throws NamingException, SQLException {
        int count = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select COUNT(q.idQues)as Num\n"
                        + "from tblQuestion q\n"
                        + "where statusQues like ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, "%" + isActive + "%");
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("Num");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public ArrayList<QuestionDTO> pagingSearchByIdQuest(String idSubject, int page) throws NamingException, SQLException {
        ArrayList<QuestionDTO> listQues = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select idQues,idSubject,contentQues,createDateQues,statusQues\n"
                        + "from tblQuestion q\n"
                        + "where idSubject = ? \n"
                        + "order by q.createDateQues desc\n"
                        + "offset(? -1)*20 ROWS\n"
                        + "FETCH NEXT 20 ROWS ONLY";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                pst.setInt(2, page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    QuestionDTO obj = new QuestionDTO(rs.getString("idQues"), rs.getString("idSubject"), rs.getString("contentQues"), rs.getTimestamp("createDateQues"), rs.getBoolean("statusQues"));
                    listQues.add(obj);
                }
            }
        } finally {
            closeConnection();
        }
        return listQues;
    }

    public ArrayList<QuestionDTO> pagingSearchByName(String searchName, int page) throws NamingException, SQLException {
        ArrayList<QuestionDTO> listQues = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select idQues,idSubject,contentQues,createDateQues,statusQues\n"
                        + "from tblQuestion q\n"
                        + "where contentQues LIKE ? \n"
                        + "order by q.createDateQues desc\n"
                        + "offset(? -1)*20 ROWS\n"
                        + "FETCH NEXT 20 ROWS ONLY";
                pst = con.prepareCall(sql);
                pst.setString(1, "%" + searchName + "%");
                pst.setInt(2, page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    QuestionDTO obj = new QuestionDTO(rs.getString("idQues"), rs.getString("idSubject"), rs.getString("contentQues"), rs.getTimestamp("createDateQues"), rs.getBoolean("statusQues"));
                    listQues.add(obj);
                }
            }
        } finally {
            closeConnection();
        }
        return listQues;
    }

    public ArrayList<QuestionDTO> pagingSearchbyIsActive(String isActive, int page) throws NamingException, SQLException {
        ArrayList<QuestionDTO> listQues = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select idQues,idSubject,contentQues,createDateQues,statusQues\n"
                        + "from tblQuestion q\n"
                        + "where statusQues like ?\n"
                        + "order by q.createDateQues desc\n"
                        + "offset(? -1)*20 ROWS\n"
                        + "FETCH NEXT 20 ROWS ONLY";
                pst = con.prepareCall(sql);
                pst.setString(1, "%" + isActive + "%");
                pst.setInt(2, page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    QuestionDTO obj = new QuestionDTO(rs.getString("idQues"), rs.getString("idSubject"), rs.getString("contentQues"), rs.getTimestamp("createDateQues"), rs.getBoolean("statusQues"));
                    listQues.add(obj);
                }
            }
        } finally {
            closeConnection();
        }
        return listQues;
    }

    public ArrayList<AnswerDTO> loadAnswerbyIdQues(String idQues) throws NamingException, SQLException {
        ArrayList<AnswerDTO> listAnswer = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select o.optionContext, o.isRight\n"
                        + "from tblOption o\n"
                        + "where o.idQues = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                rs = pst.executeQuery();
                while (rs.next()) {
                    AnswerDTO dto = new AnswerDTO(rs.getString("optionContext"), rs.getBoolean("isRight"));
                    listAnswer.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswer;
    }

    public ArrayList<String> loadAllIdQuest() throws NamingException, SQLException {
        ArrayList<String> listIdQues = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select idQues\n"
                        + "from tblQuestion";
                pst = con.prepareCall(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listIdQues.add(rs.getString("idQues"));
                }
            }
        } finally {
            closeConnection();
        }
        return listIdQues;
    }

    public boolean createQuestion(String idQues, String idSubject, String contentQuest, boolean statusQues) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblQuestion(idQues,idSubject,contentQues,statusQues) values(?,?,?,?)";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                pst.setString(2, idSubject);
                pst.setString(3, contentQuest);
                pst.setBoolean(4, statusQues);
                result = pst.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateQuestion(String idQues, String idSubject, String contentQuest, boolean statusQues) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "Update tblQuestion \n"
                        + "set idSubject = ? , statusQues = ? , contentQues = ? \n"
                        + "where idQues =  ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                pst.setBoolean(2, statusQues);
                pst.setString(3, contentQuest);
                pst.setString(4, idQues);
                result = pst.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public void insertOption(String idQues, String optionContex, boolean isRight) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblOption(idQues,optionContext,isRight) values(?,?,?)";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                pst.setString(2, optionContex);
                pst.setBoolean(3, isRight);
                pst.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Integer> loadIdOption(String idQues) throws NamingException, SQLException {
        ArrayList<Integer> listId = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select id\n"
                        + "from tblOption\n"
                        + "where idQues = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listId.add(rs.getInt("id"));
                }
            }
            return listId;
        } finally {
            closeConnection();
        }
    }

    public void updateOption(int id, String optionContex, boolean isRight) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "update tblOption\n"
                        + "set isRight = ?, optionContext = ?\n"
                        + "where id = ?";
                pst = con.prepareCall(sql);
                pst.setBoolean(1, isRight);
                pst.setString(2, optionContex);
                pst.setInt(3, id);
                pst.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public boolean deleteQuestion(String id) throws NamingException, SQLException {
        int result;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "update tblQuestion\n"
                        + "set	statusQues = 0\n"
                        + "where idQues = ?";
                pst = con.prepareCall(sql);
                pst.setString(1, id);
                result = pst.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

}