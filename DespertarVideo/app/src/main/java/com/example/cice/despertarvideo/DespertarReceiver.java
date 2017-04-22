package com.example.cice.despertarvideo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DespertarReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getCanonicalName(),"El telefóno se ha encendido");
        //Aquí tendre que lanzar la actividad que yo quiero.
        Intent intent1=new Intent(context,MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Solo así puedo lanzar la actividad fuera del contexto.
        context.startActivity(intent1);
    }
}
