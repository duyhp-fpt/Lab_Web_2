/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.OptionDetailDTO;
import duyhp.dtos.QuizDTO;
import duyhp.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class QuizDAO implements Serializable {

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

    public ArrayList<String> getRandomKeyPRJ321(String idSubject, String numberQuestion) throws NamingException, SQLException {
        ArrayList<String> listkey = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select top " + numberQuestion + "idQues from tblQuestion\n"
                        + "where idSubject = ? and statusQues = 1\n"
                        + "order by newId()";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listkey.add(rs.getString("idQues"));
                }
            }
        } finally {
            closeConnection();
        }
        return listkey;
    }

    public ArrayList<String> getRandomKeyPRJ311(String idSubject, String numberQuestion) throws NamingException, SQLException {
        ArrayList<String> listkey = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select top " + numberQuestion + " idQues from tblQuestion\n"
                        + "where idSubject = ? and statusQues = 1 \n"
                        + "order by newId()";
                pst = con.prepareCall(sql);
                pst.setString(1, idSubject);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listkey.add(rs.getString("idQues"));
                }
            }
        } finally {
            closeConnection();
        }
        return listkey;
    }

    public ArrayList<String> getidQuiz() throws NamingException, SQLException {
        ArrayList<String> listkey = new ArrayList<>();
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select idQuiz\n"
                        + "from tblQuiz";
                pst = con.prepareCall(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    listkey.add(rs.getString("idQuiz"));
                }
            }
        } finally {
            closeConnection();
        }
        return listkey;
    }

    public boolean takeQuiz(String idQuiz, String titleQuiz, String idSubject, String emailUser, Timestamp currentTime, Timestamp timeStopQuiz) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblQuiz(idQuiz, idSubject, titleQuiz, timeStartQuiz, timeStopQuiz, emailUser, isActive) values (?,?,?,?,?,?,?)";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                pst.setString(2, idSubject);
                pst.setString(3, titleQuiz);
                pst.setTimestamp(4, currentTime);
                pst.setTimestamp(5, timeStopQuiz);
                pst.setString(6, emailUser);
                pst.setBoolean(7, true);
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

    public void inserstQuestion(String idQuiz, String idQUes) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblResultDetail(idQuiz, idQues) values (?,?)";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                pst.setString(2, idQUes);
                pst.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public void updateIdChoice(String idQues, String idChoic) throws NamingException, SQLException {
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "update tblResultDetail\n"
                        + "set idOptionChoice = ? \n"
                        + "where idQues = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idChoic);
                pst.setString(2, idQues);
                pst.executeUpdate();
            }
        } finally {
            closeConnection();
        }
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

    public boolean quizInvalid(String idQuiz) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "update tblQuiz\n"
                        + "set isActive = 0\n"
                        + "where idQuiz = ?";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
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

    public boolean updateScore(String idQuiz, float Score) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "update tblQuiz\n"
                        + "set Scrore = ? \n"
                        + "where idQuiz = ? ";
                pst = con.prepareCall(sql);
                pst.setFloat(1, Score);
                pst.setString(2, idQuiz);
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

    public int countRightAnswer(String idQuiz) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select  count(r.id) as id \n"
                        + "from tblResultDetail r\n"
                        + "inner join tblOption o\n"
                        + "on o.id = r.idOptionChoice\n"
                        + "where o.isRight = 1 and  r.idQuiz = ? ";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int countQues(String idQuiz) throws NamingException, SQLException {
        int result = 0;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "select count(r.id) as id \n"
                        + "from tblResultDetail r\n"
                        + "where r.idQuiz = ?";
                pst = con.prepareCall(sql);
                pst.setString(1, idQuiz);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
