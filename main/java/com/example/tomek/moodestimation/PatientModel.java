package com.example.tomek.moodestimation;

/**
 * Created by Tomek on 10/11/2016.
 */
public class PatientModel {
    private int idPatient;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String repeatPassword;

    public PatientModel(){}
    public PatientModel(int id, String name, String surname, String email, String username, String password,String repeatPassword){
        this.idPatient=id;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.username=username;
        this.password=password;
        this.repeatPassword=repeatPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
