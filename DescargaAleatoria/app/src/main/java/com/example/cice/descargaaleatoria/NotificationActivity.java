package com.example.cice.descargaaleatoria;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String mensaje = getIntent().getStringExtra("MENSAJE");
        if (mensaje == null) {
            //He recibido la foto.
            Bitmap bitmap=getIntent().getParcelableExtra("FOTO");
            ImageView imagen=(ImageView)findViewById(R.id.foto);
            imagen.setImageBitmap(bitmap);
        } else {
            TextView resultado = (TextView) findViewById(R.id.resultado);
            resultado.setText(mensaje);
        }
    }

}