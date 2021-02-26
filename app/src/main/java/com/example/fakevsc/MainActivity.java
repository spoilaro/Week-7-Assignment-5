package com.example.fakevsc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText textInEditor;
    Dialog saveDialog;
    Context context = null;
    Dialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveDialog = new Dialog(this);
        loadDialog = new Dialog(this);

        textInEditor = findViewById(R.id.editTextTextMultiLine2);
        context = MainActivity.this;
    }

    public void ShowLoadDialog(View v){
        loadDialog.setContentView(R.layout.loadpopup);
        loadDialog.show();
    }

    public void ShowSaveDialog(View v){

        saveDialog.setContentView(R.layout.savepopup);
        saveDialog.show();
    }

    public void saveFile(View v){
        EditText getFileName;
        getFileName = (EditText) saveDialog.findViewById(R.id.savefilename);
        String filename = getFileName.getText().toString();

        try{
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

            String txt_to_write = textInEditor.getText().toString();
            ows.write(txt_to_write);
            ows.close();

        } catch (IOException e){
            System.out.println("failed");
        }
        saveDialog.dismiss();

    }

    public void readFile(View v){
        EditText filename;
        filename = (EditText) loadDialog.findViewById(R.id.loadfilename);
        String load_filename = filename.getText().toString();

        try {
            InputStream ins = context.openFileInput(load_filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            String whole_txt = "";
            String txt_to_load = "";
            while ((txt_to_load=br.readLine()) != null){
                whole_txt = whole_txt + txt_to_load + "\n";
            }
            textInEditor.setText(whole_txt);
            ins.close();
            loadDialog.dismiss();

        } catch (IOException e){
            Log.e("IOException", "Failed coz " + e.toString());
        }
    }


}