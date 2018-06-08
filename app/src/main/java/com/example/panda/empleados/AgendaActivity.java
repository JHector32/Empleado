package com.example.panda.empleados;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AgendaActivity extends AppCompatActivity {
    String correo[] = new String[500];
    String local[] = new String[500];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        List<HashMap<String,String>> aList = null;
        try{
            String Has = "data.txt";
            File path =
                    Environment.getExternalStoragePublicDirectory
                            (
                                    Environment.DIRECTORY_DOWNLOADS + "/DATOS/"
                            );
            File file = new File(path, Has);
            String line;
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            int pos[]=new int[500];
            pos[0]=2;
            int a=1;
            for (int i = -1; (i = text.toString().indexOf(" ** ", i + 1)) != -1; i++) {
                pos[a]=i;
                correo[a-1]=text.toString().substring(i+7,text.toString().length());
                local[a-1]=correo[a-1].substring(correo[a-1].indexOf(" *** Localización: ")+19,correo[a-1].indexOf("\n"));
                correo[a-1]=correo[a-1].substring(0,correo[a-1].indexOf(" *** "));
                a++;
            }
            String[] lista = new String[500];
            if(a!=0){
                for(int i=0;i<a-1;i++){
                    String temporpa=text.toString().substring(pos[i],pos[i+1]);
                    int pas;
                    if(temporpa.contains("N:")){
                    pas=temporpa.indexOf("N: ")+3;}
                    else{
                        pas=0;
                    }
                        lista[i]=temporpa.substring(pas,temporpa.length());
                }
            }
            aList= new ArrayList<>();
            for(int i=0;i<a-1;i++){
                HashMap<String, String> hm = new HashMap<>();
                hm.put("txt", lista[i]);
                aList.add(hm);
            }
            String[] from = { "txt" };
            int[] to = {R.id.formato};
            final ListView list = findViewById(R.id.list);
            SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(), aList, R.layout.layout_list, from, to);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(AgendaActivity.this, VisualizarActivity.class);
                    myIntent.putExtra("nombre", list.getItemAtPosition(position).toString().replace("{txt=","").replace("}",""));
                    myIntent.putExtra("correo", correo[position]);
                    myIntent.putExtra("local", local[position]);
                    startActivity(myIntent);
                }
            });}
        catch (Exception e){
        }
    }
}
