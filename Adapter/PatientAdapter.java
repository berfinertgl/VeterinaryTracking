package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import Models.Info;
import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PatientAdapter extends BaseAdapter {
    ArrayList<Info> infos;
    LayoutInflater layoutInflater;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    LinearLayout anaLayout;

    public PatientAdapter(Activity activity, ArrayList<Info> infos){

        this.infos = infos;
        this.context = activity;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("Pet's information");
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {

        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info info = infos.get(position);
        View view= (View) layoutInflater.inflate(R.layout.patient,null);
        anaLayout.findViewById(R.id.anaLayout);

        TextView textname = (TextView)view.findViewById(R.id.patientname);
        TextView texttype = (TextView)view.findViewById(R.id.patienttype);
        TextView textemail = (TextView)view.findViewById(R.id.useremail);
        TextView textusername = (TextView)view.findViewById(R.id.username);



        textname.setText(info.getName());
        texttype.setText(info.getType());
        textemail.setText(info.getUseremail());
        textusername.setText(info.getUsername());


        return view;
    }

}
