package br.com.sardinha.iohan.enem_quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SugerirQuestaoActivity extends AppCompatActivity {

    User currentUser;
    Spinner spinner;
    Spinner spinner2;
    EditText textoQuestão;
    EditText a;
    EditText b;
    EditText c;
    EditText d;
    EditText e;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerir_questao);
        currentUser = (User) getIntent().getSerializableExtra("user");
        textoQuestão = (EditText)findViewById(R.id.texto_questao_sugerir_questao);
        a = (EditText)findViewById(R.id.a_sugerir_questao);
        b = (EditText)findViewById(R.id.b_sugerir_questao);
        c = (EditText)findViewById(R.id.c_sugerir_questao);
        d = (EditText)findViewById(R.id.d_sugerir_questao);
        e = (EditText)findViewById(R.id.e_sugerir_questao);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.materias,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this,R.array.opcoes,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
    }

    public void enviar(View view) {
        if(textoQuestão.getText().toString().isEmpty() || a.getText().toString().isEmpty() || b.getText().toString().isEmpty() ||
                c.getText().toString().isEmpty() || d.getText().toString().isEmpty() || e.getText().toString().isEmpty() || 
                spinner.getSelectedItem().toString().equals("Matérias") || spinner2.getSelectedItem().toString().equals("Resposta"))
        {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Question question = new Question(textoQuestão.getText().toString(),a.getText().toString(),b.getText().toString(),c.getText().toString()
                    ,d.getText().toString(),e.getText().toString(),spinner2.getSelectedItem().toString(), spinner.getSelectedItem().toString());
            if(currentUser.isAdmin())
            {
                database = FirebaseDatabase.getInstance().getReference("Questões");
                String id = database.push().getKey();
                question.setId(id);
                database.child(id).setValue(question);

            }
            else
            {
                database = FirebaseDatabase.getInstance().getReference("QuestõesSugeridas");
                String id = database.push().getKey();
                question.setId(id);
                database.child(id).setValue(question);
            }
            finish();
        }
    }
}
