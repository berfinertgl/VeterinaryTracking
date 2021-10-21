package Models;

import android.content.Intent;

import Activity.AppointmentActivity;

public class Info {
    private String name, username, useremail, type;
    private int age;

    public Info(String name, String username, String useremail, String type, int age) {
        this.name = name;
        this.username = username;
        this.useremail = useremail;
        this.type = type;
        this.age = age;
    }

    public Info(){}


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public String getUseremail(){
        return useremail;
    }

    public void setUseremail(String useremail){
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
