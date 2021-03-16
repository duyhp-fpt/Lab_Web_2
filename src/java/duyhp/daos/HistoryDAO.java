/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.HistoryDTO;
import duyhp.dtos.OptionDetailDTO;
import duyhp.dtos.QuizDTO;
import duyhp.utils.MyConnection;
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
public class HistoryDAO {
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

    public ArrayList<HistoryDTO> getHistoryAll(int Page, String emailUser) throws NamingException, SQLException {
        ArrayList<HistoryDTO> listHistory = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select q.idQuiz,q.titleQuiz, q.idSubject, q.timeStartQuiz, q.Scrore\n"
                        + "from tblQuiz q\n"
                        + "where q.emailUser = ?\n"
                        + "order by timeStartQuiz desc\n"
                        + "offset(? -1)*10 ROWS\n"
                        + "FETCH NEXT 10 ROWS ONLY";
                pst = con.prepareCall(sql);
                pst.setString(1, emailUser);
                pst.setInt(2, Page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listHistory.add(new HistoryDTO(rs.getString("idQuiz"), rs.getString("titleQuiz"), rs.getString("idSubject"), rs.getFloat("Scrore"), rs.getTimestamp("timeStartQuiz")));
                }
            }
        } finally {
            closeConnection();
        }
        return listHistory;
    }

    public ArrayList<HistoryDTO> getHistoryBySubject(int Page, String idSubject, String emailUser) throws NamingException, SQLException {
        ArrayList<HistoryDTO> listHistory = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select q.idQuiz,q.titleQuiz, q.idSubject, q.timeStartQuiz, q.Scrore\n"
                        + "from tblQuiz q\n"
                        + "where q.emailUser = ? and q.idSubject = ? \n"
                        + "order by timeStartQuiz desc\n"
                        + "offset(? -1)*10 ROWS\n"
                        + "FETCH NEXT 10 ROWS ONLY";
                pst = con.prepareCall(sql);
                pst.setString(1, emailUser);
                pst.setString(2, idSubject);
                pst.setInt(3, Page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listHistory.add(new HistoryDTO(rs.getString("idQuiz"), rs.getString("titleQuiz"), rs.getString("idSubject"), rs.getFloat("Scrore"), rs.getTimestamp("timeStartQuiz")));
                }
            }
        } finally {
            closeConnection();
        }
        return listHistory;
    }

    public int countAll(String emailUser) throws NamingException, SQLException {
        int count = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select count(q.idQuiz) as id\n"
                        + "from tblQuiz q\n"
                        + "where q.emailUser = ?\n";
                pst = con.prepareCall(sql);
                pst.setString(1, emailUser);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int countAllBySubject(String emailUser, String idSubject) throws NamingException, SQLException {
        int count = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select count(q.idQuiz) as id\n"
                        + "from tblQuiz q\n"
                        + "where q.idSubject= ? and "
                        + "q.emailUser = ?\n";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                pst.setString(2, emailUser);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public String getUserChoice(String idQues, String idQuiz) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select o.optionContext\n"
                        + "from tblOption o\n"
                        + "where o.id = (\n"
                        + "select r.idOptionChoice\n"
                        + "from tblResultDetail r\n"
                        + "where r.idQues = ? \n"
                        + "and r.idQuiz = ? )";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                pst.setString(2, idQuiz);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString("optionContext");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public ArrayList<String> getAllIdQues(String idQuiz) throws NamingException, SQLException {
        ArrayList<String> listid = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select r.idQues\n"
                        + "from tblResultDetail r\n"
                        + "where r.idQuiz = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listid.add(rs.getString("idQues"));
                }
            }
        } finally {
            closeConnection();
        }
        return listid;
    }

    public float getScore(String idQuiz) throws NamingException, SQLException {
        float score = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select q.Scrore\n"
                        + "from tblQuiz q\n"
                        + "where q.idQuiz = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                rs = pst.executeQuery();
                if (rs.next()) {
                    score = rs.getFloat("Scrore");
                }
            }
        } finally {
            closeConnection();
        }
        return score;
    }

    public QuizDTO createatOption(String idQues) throws NamingException, SQLException {
        ArrayList<OptionDetailDTO> listOption = new ArrayList<>();
        String QuesContent = null;
        QuizDTO obj = null;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select q.contentQues, o.optionContext, o.id\n"
                        + "from tblQuestion q\n"
                        + "inner join tblOption o\n"
                        + "on q.idQues = o.idQues\n"
                        + "where q.idQues = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQues);
                rs = pst.executeQuery();
                while (rs.next()) {
                    QuesContent = rs.getString("contentQues");
                    listOption.add(new OptionDetailDTO(rs.getString("id"), rs.getString("optionContext")));
                }
            }
        } finally {
            closeConnection();
        }
        if (listOption.size() == 4) {
            obj = new QuizDTO(idQues, QuesContent, listOption.get(0), listOption.get(1), listOption.get(2), listOption.get(3), "");
        }
        return obj;
    }
}

