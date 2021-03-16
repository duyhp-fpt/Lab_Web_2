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
public class QuestionDTO implements Serializable{
    private String idQues;
    private String subject;
    private String contentQus;
    private Timestamp createDate;
    private boolean isActive;

    public QuestionDTO() {
    }

    public QuestionDTO(String idQues, String subject, String contentQus, Timestamp createDate, boolean isActive) {
        this.idQues = idQues;
        this.subject = subject;
        this.contentQus = contentQus;
        this.createDate = createDate;
        this.isActive = isActive;
    }

    public String getIdQues() {
        return idQues;
    }

    public void setIdQues(String idQues) {
        this.idQues = idQues;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String Subject) {
        this.subject = Subject;
    }

    public String getContentQus() {
        return contentQus;
    }

    public void setContentQus(String contentQus) {
        this.contentQus = contentQus;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}

