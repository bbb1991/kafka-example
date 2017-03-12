package me.bbb1991.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by bbb1991 on 3/13/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@XmlRootElement
public class ErrorMessage {

    private String errorMessage;
    private int errorCode;

    public ErrorMessage() {

    }

    public ErrorMessage(String errorMessage, int errorCode) {
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}