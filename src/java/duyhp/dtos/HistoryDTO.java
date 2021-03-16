/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Huynh Duy
 */
public class HistoryDTO implements Serializable {

    private String idQUiz;
    private String titleQuiz;
    private String idSubject;
    private float score;
    private Timestamp timeStart;

    public HistoryDTO() {
    }

    public HistoryDTO(String idQUiz, String titleQuiz, String idSubject, float score, Timestamp timeStart) {
        this.idQUiz = idQUiz;
        this.titleQuiz = titleQuiz;
        this.idSubject = idSubject;
        this.score = score;
        this.timeStart = timeStart;
    }

    public String getIdQUiz() {
        return idQUiz;
    }

    public void setIdQUiz(String idQUiz) {
        this.idQUiz = idQUiz;
    }

    public String getTitleQuiz() {
        return titleQuiz;
    }

    public void setTitleQuiz(String titleQuiz) {
        this.titleQuiz = titleQuiz;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

}
