package com.example.furniture;


public class retrieve {

    public retrieve() {
    }

    String id,question,email;

    public retrieve(String id, String question, String email) {
        this.id = id;
        this.question = question;
        this.email = email;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
