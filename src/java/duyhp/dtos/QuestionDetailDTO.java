/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Huynh Duy
 */
public class QuestionDetailDTO implements Serializable {

    private QuestionDTO question;
    private ArrayList<AnswerDTO> listAnswer;

    public QuestionDetailDTO(QuestionDTO question, ArrayList<AnswerDTO> listAnswer) {
        this.question = question;
        this.listAnswer = listAnswer;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public ArrayList<AnswerDTO> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(ArrayList<AnswerDTO> listAnswer) {
        this.listAnswer = listAnswer;
    }

}
