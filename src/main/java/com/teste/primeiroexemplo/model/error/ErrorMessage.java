package com.teste.primeiroexemplo.model.error;

public class ErrorMessage {
    private String title;

    private Integer status;
    
    private String message;

    public ErrorMessage(String title, Integer status, String message) {
        this.title = title;
        this.status = status;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTile(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
}
