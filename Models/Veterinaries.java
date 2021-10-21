package Models;

public class Veterinaries {
    private String email, fullname, password, petname, username, vetImage;
    private int petage;

    public Veterinaries() {
    }

    public Veterinaries(String email, String fullname, String password, String petname, String username, int petage, String vetImage) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.petname = petname;
        this.username = username;
        this.petage = petage;
        this.vetImage = vetImage;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getPetage() {
        return petage;
    }

    public void setPetage(int petage) {
        this.petage = petage;
    }

    public String getVetImage() {
        return vetImage;
    }

    public void setVetImage(String vetImage) {
        this.vetImage = vetImage;
    }

    @Override
    public String toString() {
        return "Veterinaries{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", petname='" + petname + '\'' +
                ", username='" + username + '\'' +
                ", petage=" + petage +
                ",vetImage" + vetImage +
                '}';
    }
}
