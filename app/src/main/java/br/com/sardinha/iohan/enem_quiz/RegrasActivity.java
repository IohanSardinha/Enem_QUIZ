package br.com.sardinha.iohan.enem_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class RegrasActivity extends AppCompatActivity {

    Switch tempoOnOff;
    View tempoHolder;
    Switch humanas;
    Switch portugues;
    Switch matematica;
    Switch ciencias;
    EditText numeroDeQuestoes;
    EditText horas;
    EditText minutos;
    EditText segundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regras);
        tempoOnOff = (Switch)findViewById(R.id.tempo_on_off);

        tempoHolder = findViewById(R.id.tempo_holder);
        humanas = (Switch)findViewById(R.id.humanas);
        portugues = (Switch)findViewById(R.id.portugues);
        matematica = (Switch)findViewById(R.id.matematica);
        ciencias = (Switch)findViewById(R.id.ciencias);

        numeroDeQuestoes = (EditText)findViewById(R.id.numero_de_questoes);
        horas = (EditText)findViewById(R.id.horas);
        minutos = (EditText)findViewById(R.id.minutos);
        segundos = (EditText)findViewById(R.id.segundos);

        tempoOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    tempoHolder.setVisibility(View.VISIBLE);
                }
                else
                {
                    tempoHolder.setVisibility(View.GONE);
                }
            }
        });
    }

    public void comecar(View view) {
        if(numeroDeQuestoes.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Número de quesões não pode estar em branco!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!ciencias.isChecked() && !humanas.isChecked() && !portugues.isChecked() && !matematica.isChecked())
        {
            Toast.makeText(this, "Pelo menos uma área de conhecimento deve ser selecionada!", Toast.LENGTH_LONG).show();
            return;
        }

        int intHoras;
        int intMinutos;
        int intSegundos;
        if(horas.getText().toString().isEmpty())
        {
            intHoras = 0;
        }
        else
        {
            intHoras = Integer.parseInt(horas.getText().toString())*60*60;
        }

        if(minutos.getText().toString().isEmpty())
        {
            intMinutos = 0;
        }
        else
        {
            intMinutos = Integer.parseInt(minutos.getText().toString())*60;
        }

        if(segundos.getText().toString().isEmpty())
        {
            intSegundos = 0;
        }
        else
        {
            intSegundos = Integer.parseInt(segundos.getText().toString());
        }

        long tempoInMillis = (1000*(intHoras+intMinutos+intSegundos))+5000;

        Rules rules = new Rules(humanas.isChecked(),ciencias.isChecked(),portugues.isChecked(),matematica.isChecked(),tempoOnOff.isChecked(),tempoInMillis,Integer.parseInt(numeroDeQuestoes.getText().toString()));

        if(tempoInMillis <= 5000)
        {
            rules.setTempoBool(false);
        }

        Intent intent = new Intent(this,LoadingActivity.class);
        intent.putExtra("Rules",rules);
        startActivity(intent);
        finish();
    }
}
