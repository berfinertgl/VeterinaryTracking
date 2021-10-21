package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Models.Appointment;

public class AppointmentAdapter extends BaseAdapter {

    ArrayList<Appointment> appointments;
    LayoutInflater layoutInflater;
    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;

    public AppointmentAdapter(Activity activity, ArrayList<Appointment> appointments){
        this.appointments = appointments;
        this.context = activity;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Appointments");
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Appointment appointment = appointments.get(position);
        View view = (View) layoutInflater.inflate(R.layout.appointment,null);

        TextView userName = (TextView) view.findViewById(R.id.username);
        TextView appointmenttext = (TextView) view.findViewById(R.id.appointmenttext);

        userName.setText(appointment.getUsername());
        appointmenttext.setText(appointment.getAppointmenttext());

        return view;
    }
}
