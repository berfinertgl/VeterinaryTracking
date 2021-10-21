package com.berfinertoglu.veterinarytrackingapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class ChangeFragment {

    private Context context;

    public ChangeFragment(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment){
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment,"fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
