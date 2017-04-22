package com.example.cice.descargaaleatoria;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private void lanzarNotificacion(String mensaje){
        //builder y notification manager y preparo el aspecto de la notificación.
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Nuevo mensaje");
        builder.setContentText("Texto de ejemplo");
        //Cuando pulso sobre la notificación se elimina de forma automática.
        builder.setAutoCancel(true);
        //Aquí iré cuando el usuario toque la notificación.
        Intent resultIntent=new Intent(this,NotificationActivity.class);
        resultIntent.putExtra("MENSAJE",mensaje);
        //Se usa para acceder desde el exterior a la app y envuelve al intent normal, le añade una capa de seguridad.
        PendingIntent pendingIntent=PendingIntent.getActivity(this,(int)System.currentTimeMillis(),resultIntent,PendingIntent.FLAG_ONE_SHOT);
        //Asociamos el pendingIntent a la notificación.
        builder.setContentIntent(pendingIntent);
        //Lanzamos la notificacion.
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //Construimos la notificación.
        Notification notification=builder.build();
        //Notificamos la notificación.
        notificationManager.notify(350,notification);
    }

    private void lanzarNotificacionImagen(Bitmap bitmap){
        //builder y notification manager y preparo el aspecto de la notificación.
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Nuevo mensaje");
        builder.setContentText("Texto de ejemplo");
        //Cuando pulso sobre la notificación se elimina de forma automática.
        builder.setAutoCancel(true);
        //Aquí iré cuando el usuario toque la notificación.
        Intent resultIntent=new Intent(this,NotificationActivity.class);
        resultIntent.putExtra("FOTO",bitmap);
        //Se usa para acceder desde el exterior a la app y envuelve al intent normal, le añade una capa de seguridad.
        PendingIntent pendingIntent=PendingIntent.getActivity(this,(int)System.currentTimeMillis(),resultIntent,PendingIntent.FLAG_ONE_SHOT);
        //Asociamos el pendingIntent a la notificación.
        builder.setContentIntent(pendingIntent);
        //La imagen adjunta se ve en el cuerpo de la notificación
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        //Lanzamos la notificacion.
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //Construimos la notificación.
        Notification notification=builder.build();
        //Notificamos la notificación.
        notificationManager.notify(350,notification);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lanzarNotificacion("AUPA ATLETI");

        try{
            Object object=new DescargarNotificacion().execute().get();
            if(object==null){
                Log.d(getClass().getCanonicalName(),"NADA QUE NOTIFICAR");
            }else if(object instanceof Bitmap){
                Log.d(getClass().getCanonicalName(),"RECIBÍ UNA IMAGEN");
                //Lanzamos la notificación de imagen.
                lanzarNotificacionImagen((Bitmap)object);
            }else{
                Log.d(getClass().getCanonicalName(),"RECIBÍ UN STRING");
                //Lanzamos la notificación de texto.
                lanzarNotificacion((String)object);
            }
        }catch(Exception e){
               Log.e(getClass().getCanonicalName(),"ERROR",e);
        }
    }
}
