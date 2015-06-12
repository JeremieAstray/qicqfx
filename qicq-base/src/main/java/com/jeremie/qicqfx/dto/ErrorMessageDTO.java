package com.jeremie.qicqfx.dto;

/**
 * Created by jeremie on 2015/6/5.
 */
public class ErrorMessageDTO extends MessageDTO {
    private String errorMessage;
    public ErrorMessageDTO(){
        status = Status.ERROR_MESSAGE;
    }

    public ErrorMessageDTO(String errorMessage){
        status = Status.ERROR_MESSAGE;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
