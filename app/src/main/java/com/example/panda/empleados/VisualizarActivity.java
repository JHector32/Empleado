package com.example.panda.empleados;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VisualizarActivity extends AppCompatActivity {
    String nombre,correo,local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        final Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        correo = intent.getStringExtra("correo");
        local = intent.getStringExtra("local");
        TextView textView=findViewById(R.id.textViewViz);
        textView.setText(textView.getText()+nombre);
        TextView textView2=findViewById(R.id.textView2Viz);
        textView2.setText(textView2.getText()+correo);
        TextView textView3=findViewById(R.id.textView3Viz);
        textView3.setText(textView3.getText()+local);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(local.replace("Localización: ",""), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        final double longitude = address.getLongitude();
        final double latitude = address.getLatitude();
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(android.content.Intent.ACTION_VIEW);
                //inte.setData(Uri.parse("geo:"+latitude+","+longitude));
                inte.setData(Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude));
                Intent elegir=Intent.createChooser(inte,"Launch Maps");
                startActivity(elegir);
            }
        });
    }
}
