package com.example.cice.descargaconservices;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DESCARGA="http://www.hrsanroque.com/cervezas/cice.pdf";
    private static final String RUTA_DESTINO= Environment.getExternalStorageDirectory().getPath()+"/pgmcice.pdf";

    private void pedir_permisos(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Preparo el progressDialog.
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Descargando archivo...");
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setTitle("Accediendo al servidor");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        //Asocio el receiver a la señal de fin de servicio.
        BroadcastReceiver broadcastReceiver=null;
        IntentFilter filter=null;

        filter=new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        broadcastReceiver= new DescargaCompletaReceiver(this,progressDialog);
        //Asocio el fin de la descarga a mi Receiver, la señal que recibe el receiver es ACTION_DOWNLOAD_COMPLETE.
        registerReceiver(broadcastReceiver,filter);
        //Petición de la descarga y preparo el objeto request.
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(URL_DESCARGA));
        //Datos de la barra de descargas en la notificación.
        request.setDescription("en proceso...");
        request.setTitle("DESCARGANDO PDF");
        //Solo disponible a partir de la version HONEYCOMB
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            //Indexa la descarga del fichero pdf y se clasifique.
            request.allowScanningByMediaScanner();
            //Cuando se descarge el pdf completo se visualiza la notificación.
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        }

        Uri uri_destino=Uri.fromFile(new File(RUTA_DESTINO));
        request.setDestinationUri(uri_destino);
        //Asociamos la request al servicio DownLoadManager e iniciamos la petición de descarga
        DownloadManager manager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        long id_descarga=manager.enqueue(request);//Esto me devuelve el id de la descarga (long).
        ((DescargaCompletaReceiver)broadcastReceiver).setId_descarga(id_descarga);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedir_permisos();

    }
}
