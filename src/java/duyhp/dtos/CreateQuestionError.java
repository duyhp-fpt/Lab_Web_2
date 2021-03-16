/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

/**
 *
 * @author Huynh Duy
 */
public class CreateQuestionError {
    private String nameNotNull;
    private String isRightInclude;
    private String subjectNameNotNull;
    private String subjectNameLength;

    public String getNameNotNull() {
        return nameNotNull;
    }

    public void setNameNotNull(String nameNotNull) {
        this.nameNotNull = nameNotNull;
    }

    public String getIsRightInclude() {
        return isRightInclude;
    }

    public void setIsRightInclude(String isRightInclude) {
        this.isRightInclude = isRightInclude;
    }

    public String getSubjectNameNotNull() {
        return subjectNameNotNull;
    }

    public void setSubjectNameNotNull(String subjectNameNotNull) {
        this.subjectNameNotNull = subjectNameNotNull;
    }

    public String getSubjectNameLength() {
        return subjectNameLength;
    }

    public void setSubjectNameLength(String subjectNameLength) {
        this.subjectNameLength = subjectNameLength;
    }
    
}

