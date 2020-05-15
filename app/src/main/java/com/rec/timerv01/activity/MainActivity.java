package com.rec.timerv01.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rec.timerv01.R;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irParaEdit();


        btnPlus = findViewById(R.id.btnPlus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    private void irParaEdit(){
        //Intent para una Activityes pasando dados
        Intent intent = new Intent(getApplicationContext(), EditForm.class);
        // PASSAR DADOS
//        intent.putExtra("titulo", "Titulo Txt");
//        intent.putExtra("txt", "Txt xoxoxoxoxoxxooxoxoxo xoxoxoxoxo xoxoxoxoxoxxooxoxoxo");
        startActivity(intent);
    }
}
