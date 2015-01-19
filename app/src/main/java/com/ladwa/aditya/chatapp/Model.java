package com.ladwa.aditya.chatapp;

/**
 * Created by AdityaLadwa on 19-01-2015.
 */
public class Model {
    private String name, message;
    private boolean right;

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Model(String name, String message, boolean right) {

        this.name = name;
        this.message = message;
        this.right = right;
    }
}
