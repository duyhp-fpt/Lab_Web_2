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
public class QuizDTO implements Serializable {

    private String idQuest;
    private String quizContent;
    private OptionDetailDTO answer1;
    private OptionDetailDTO answer2;
    private OptionDetailDTO answer3;
    private OptionDetailDTO answer4;
    private String userChoiceId;

    public QuizDTO() {
    }

    public QuizDTO(String idQuest, String quizContent, OptionDetailDTO answer1, OptionDetailDTO answer2, OptionDetailDTO answer3, OptionDetailDTO answer4, String userChoiceId) {
        this.idQuest = idQuest;
        this.quizContent = quizContent;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.userChoiceId = userChoiceId;
    }

    public String getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(String idQuest) {
        this.idQuest = idQuest;
    }

    public String getQuizContent() {
        return quizContent;
    }

    public void setQuizContent(String quizContent) {
        this.quizContent = quizContent;
    }

    public OptionDetailDTO getAnswer1() {
        return answer1;
    }

    public void setAnswer1(OptionDetailDTO answer1) {
        this.answer1 = answer1;
    }

    public OptionDetailDTO getAnswer2() {
        return answer2;
    }

    public void setAnswer2(OptionDetailDTO answer2) {
        this.answer2 = answer2;
    }

    public OptionDetailDTO getAnswer3() {
        return answer3;
    }

    public void setAnswer3(OptionDetailDTO answer3) {
        this.answer3 = answer3;
    }

    public OptionDetailDTO getAnswer4() {
        return answer4;
    }

    public void setAnswer4(OptionDetailDTO answer4) {
        this.answer4 = answer4;
    }

    public String getUserChoiceId() {
        return userChoiceId;
    }

    public void setUserChoiceId(String userChoiceId) {
        this.userChoiceId = userChoiceId;
    }

}

