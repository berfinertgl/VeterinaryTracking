package Models;

public class Appointment {

    private String username, appointmenttext;


    public Appointment(String username, String appointmenttext) {
        this.username = username;
        this.appointmenttext = appointmenttext;
    }

    public Appointment() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppointmenttext() {
        return appointmenttext;
    }

    public void setAppointmenttext(String appointmenttext) {
        this.appointmenttext = appointmenttext;
    }
}
