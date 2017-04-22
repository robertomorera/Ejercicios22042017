package com.example.cice.descargaaleatoria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cice on 22/4/17.
 */

public class DescargarNotificacion extends AsyncTask<Void,Void,Object> {

    private final static String URL_SERVLET="http://femxa-ebtm.rhcloud.com/HayMensajes";

    private Bitmap obtenerImagen(HttpURLConnection httpURLConnection) throws IOException{
        Bitmap bitmap=null;
        InputStream is=httpURLConnection.getInputStream();
        bitmap=BitmapFactory.decodeStream(is);
        return bitmap;
    }

    private String obtenerMensaje(HttpURLConnection httpURLConnection) throws IOException{
        String mensaje=null;
        BufferedReader br=null;
        InputStream is=httpURLConnection.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        mensaje=br.readLine();
        br.close();//Esto se pondría en un finally.
        return mensaje;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object object=null;
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        try{
            url=new URL(URL_SERVLET);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            switch(httpURLConnection.getResponseCode()){
                case HttpURLConnection.HTTP_NO_CONTENT:
                    Log.d(getClass().getCanonicalName(),"Sin mensaje recibido");
                    break;
                case HttpURLConnection.HTTP_OK:
                    if(httpURLConnection.getContentType().startsWith("text/plain")){
                        Log.d(getClass().getCanonicalName(),"He recibido texto");
                        String mensaje=obtenerMensaje(httpURLConnection);
                        object=mensaje;
                    }else{
                        Log.d(getClass().getCanonicalName(),"He recibido una imagen");
                        Bitmap bitmap=obtenerImagen(httpURLConnection);
                        object=bitmap;
                    }
                    break;
                default:
                    Log.e(getClass().getCanonicalName(),"Ha habido un error accediendo al servidor");

            }
        }catch(Exception e){
            Log.e(getClass().getCanonicalName(),"ERROR EXCEPTION",e);

        }finally{
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }
        return object;

    }




}
