package com.example.panda.empleados;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegistroActivity extends AppCompatActivity {

    private  static final int LOC=500;
    boolean cambi=false;
    TextView textView;
    int PLP=1;
    public static final String datos = "data.txt";
    static File path =
            Environment.getExternalStoragePublicDirectory
                    (
                            Environment.DIRECTORY_DOWNLOADS + "/DATOS/"
                    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button button = findViewById(R.id.button);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOC);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},LOC);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},LOC);
            return;
        }
        textView=findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(getApplicationContext());
                    startActivityForResult(intent,PLP);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=findViewById(R.id.editText);
                EditText editText2=findViewById(R.id.editText2);
                if(editText.getText().toString().equals("")||editText2.getText().toString().equals("")){
                    Toast.makeText(RegistroActivity.this, "Por favor, llene los campos",Toast.LENGTH_LONG).show();
                }
                else if(cambi==false){
                    Toast.makeText(RegistroActivity.this, "Seleccione la localización",Toast.LENGTH_LONG).show();
                }
                else if(!editText.getText().toString().equals(editText.getText().toString().replaceAll("[*0-9]", ""))){
                    Toast.makeText(RegistroActivity.this, "El nombre no puede contener números",Toast.LENGTH_LONG).show();
                }
                else if(!editText2.getText().toString().contains("@")&&!editText2.getText().toString().contains(".")){
                    Toast.makeText(RegistroActivity.this, "El correo no es válido",Toast.LENGTH_LONG).show();
                }
                else{
                    File han = new File(path,datos);
                    if (!han.exists()) {
                        try {
                            if (!han.getParentFile().exists())
                                han.getParentFile().mkdirs();
                            han.createNewFile();
                            FileWriter writer = new FileWriter(han);
                            writer.append("N: "+editText.getText().toString()+" ** C: "+editText2.getText().toString()+" *** "+textView.getText()+"\n");
                            writer.flush();
                            writer.close();
                            Toast.makeText(RegistroActivity.this, "El empledado fue registrado",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(RegistroActivity.this, AgendaActivity.class);
                            RegistroActivity.this.startActivity(myIntent);}
                            catch (IOException e) {
                            Toast.makeText(RegistroActivity.this, "Por favor intente mas tarde.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }}else{
                        try {
                            String line;
                            StringBuilder text = new StringBuilder();
                            BufferedReader br = new BufferedReader(new FileReader(han));
                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                            FileWriter writer = new FileWriter(han);
                            text.append("N: "+editText.getText().toString()+" ** C: "+editText2.getText().toString()+" *** "+textView.getText()+"\n");
                            text.append('\n');
                            writer.append(text.toString());
                            writer.flush();
                            writer.close();
                            Toast.makeText(RegistroActivity.this, "El empledado fue registrado",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(RegistroActivity.this, AgendaActivity.class);
                            RegistroActivity.this.startActivity(myIntent);}
                        catch (IOException e) {
                            Toast.makeText(RegistroActivity.this, "Por favor intente mas tarde.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File han = new File(path,datos);
                if (han.exists()) {
                Intent myIntent = new Intent(RegistroActivity.this, AgendaActivity.class);
                RegistroActivity.this.startActivity(myIntent);}else{
                    Toast.makeText(RegistroActivity.this, "Debe dar de alta un usuario.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onActivityResult(int request,int cod,Intent datos){
        if(request==PLP){
            if(cod==RESULT_OK){
                Place place=PlacePicker.getPlace(datos,this);
                String dire=String.format("Localización: %s",place.getAddress());
                textView.setText(dire);
                cambi=true;
                textView.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int cod,@NonNull String[] permiso,@NonNull int[] gran){
        switch (cod){
            case LOC:
                if(gran.length>0&&gran[0]==PackageManager.PERMISSION_GRANTED){
                    Intent myIntent = new Intent(RegistroActivity.this, RegistroActivity.class);
                    RegistroActivity.this.startActivity(myIntent);
                }
                break;

        }
    }
}
