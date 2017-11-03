package br.com.sardinha.iohan.enem_quiz;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AprovarQuestoesActivity extends AppCompatActivity {

    DatabaseReference reference;
    ArrayList<Question> questions = new ArrayList<>();
    int position = -1;

    TextView pergunta;
    TextView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;

    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovar_questoes);

        pergunta = (TextView)findViewById(R.id.texto_questao_aprovar);
        a = (TextView)findViewById(R.id.a_aprovar);
        b = (TextView)findViewById(R.id.b_aprovar);
        c = (TextView)findViewById(R.id.c_aprovar);
        d = (TextView)findViewById(R.id.d_aprovar);
        e = (TextView)findViewById(R.id.e_aprovar);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Carregando dados...");
        progressDialog.show();
        reference = FirebaseDatabase.getInstance().getReference("QuestõesSugeridas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    questions.add(ds.getValue(Question.class));
                }
                progressDialog.dismiss();
                if(questions.size() > 0)
                {
                    findViewById(R.id.holder_aprovar).setVisibility(View.VISIBLE);
                }
                Draw();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Draw()
    {
        position++;
        if(position >= questions.size())
        {
            new AlertDialog.Builder(this)
                    .setTitle("Nenhuma questão para aprovar")
                    .setCancelable(false)
                    .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
        else {
            currentQuestion = questions.get(position);
            pergunta.setText(currentQuestion.getText());
            a.setText("A) " + currentQuestion.getA());
            b.setText("B) " + currentQuestion.getB());
            c.setText("C) " + currentQuestion.getC());
            d.setText("D) " + currentQuestion.getD());
            e.setText("E) " + currentQuestion.getE());

            a.setTextColor(Color.BLACK);
            b.setTextColor(Color.BLACK);
            c.setTextColor(Color.BLACK);
            d.setTextColor(Color.BLACK);
            e.setTextColor(Color.BLACK);

            switch (currentQuestion.getAnswer()) {
                case "A":
                    a.setTextColor(Color.rgb(0, 155, 0));
                    return;
                case "B":
                    b.setTextColor(Color.rgb(0, 155, 0));
                    return;
                case "C":
                    c.setTextColor(Color.rgb(0, 155, 0));
                    return;
                case "D":
                    d.setTextColor(Color.rgb(0, 155, 0));
                    return;
                case "E":
                    e.setTextColor(Color.rgb(0, 155, 0));
                    return;
            }
        }
    }

    public void rejeitar(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Um momento por favor...");
        progressDialog.show();
        reference.child(currentQuestion.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Draw();
            }
        });
    }

    public void aprovar(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Um momento por favor...");
        progressDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Questões").child(currentQuestion.getId());
        ref.setValue(currentQuestion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child(currentQuestion.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Draw();
                    }
                });
            }
        });
    }
}
