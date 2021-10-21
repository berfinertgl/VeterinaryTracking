package Models;

public class Users {

    private String email, fullname,petname,username,password, image;
    private int petage;


    public Users(String email, String fullname, String petname, String username, String password, int petage, String image){
        this.email = email;
        this.fullname = fullname;
        this.petage = petage;
        this.petname = petname;
        this.username = username;
        this.password = password;
        this.image = image;
    }
    public Users(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
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

    public int getPetage() {
        return petage;
    }

    public void setPetage(int petage) {
        this.petage = petage;
    }

    public String getImage(){ return image; }

    public void setImage(String image){ this.image = image; }



 /*   @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ",image=" +
                ", petname='" + petname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", petage=" + petage +
                '}';
    }*/
}


