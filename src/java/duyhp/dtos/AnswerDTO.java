/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

import java.io.Serializable;

/**
 *
 * @author Huynh Duy
 */
public class AnswerDTO implements Serializable {

    private String ansDetail;
    private boolean isRight;

    public AnswerDTO(String ansDetail, boolean isRight) {
        this.ansDetail = ansDetail;
        this.isRight = isRight;
    }

    public String getAnsDetail() {
        return ansDetail;
    }

    public void setAnsDetail(String ansDetail) {
        this.ansDetail = ansDetail;
    }

    public boolean isIsRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

}
