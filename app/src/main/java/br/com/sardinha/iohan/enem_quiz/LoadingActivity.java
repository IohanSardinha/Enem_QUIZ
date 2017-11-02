package br.com.sardinha.iohan.enem_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    Rules rules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        rules = (Rules)getIntent().getSerializableExtra("Rules");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Questões");
        final int totalTrue[] = {0};
        if(rules.isCiencias())
        {
            totalTrue[0]++;
        }
        if(rules.isHumanas())
        {
            totalTrue[0]++;
        }
        if(rules.isMatematica())
        {
            totalTrue[0]++;
        }
        if(rules.isPortugues())
        {
            totalTrue[0]++;
        }
//----------------------------------------------------------------
        final int done[] = {0};
        final ArrayList<Question> questions = new ArrayList<>();
        int limitToFirst = Math.round(rules.getNumeroDeQuestoes()/totalTrue[0]);
        if(limitToFirst < 1)
        {
            limitToFirst = 1;
        }
        if(rules.isCiencias())
        {
            Query query = reference.orderByChild("materia").equalTo("CIÊNCIAS DA NATUREZA E SUAS TECNOLOGIAS").limitToFirst(limitToFirst);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        questions.add(ds.getValue(Question.class));
                    }
                    done[0]++;
                    Done(done[0],totalTrue[0],questions);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(rules.isHumanas())
        {
            Query query = reference.orderByChild("materia").equalTo("CIÊNCIAS HUMANAS E SUAS TECNOLOGIAS").limitToFirst(limitToFirst);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        questions.add(ds.getValue(Question.class));
                    }
                    done[0]++;
                    Done(done[0],totalTrue[0],questions);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(rules.isMatematica())
        {
            Query query = reference.orderByChild("materia").equalTo("MATEMÁTICA E SUAS TECNOLOGIAS").limitToFirst(limitToFirst);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        questions.add(ds.getValue(Question.class));
                    }
                    done[0]++;
                    Done(done[0],totalTrue[0],questions);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(rules.isPortugues())
        {
            Query query = reference.orderByChild("materia").equalTo("LINGUAGENS, CÓDIGOS E SUAS TECNOLOGIAS").limitToFirst(limitToFirst);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        questions.add(ds.getValue(Question.class));
                    }
                    done[0]++;
                    Done(done[0],totalTrue[0],questions);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void Done(int done, int total, final ArrayList<Question> questions)
    {
        if(done >= total)
        {
            final Intent intent = new Intent(this,QuestionActivity.class);
            intent.putExtra("Rules",rules);
            if(questions.size() < rules.getNumeroDeQuestoes())
            {
                new AlertDialog.Builder(this)
                        .setTitle("Pedimos desculpas")
                        .setMessage("O número de questões encontrado em nosso banco de dados é menor que o selecionado. O que deseja fazer?")
                        .setCancelable(false)
                        .setPositiveButton("Continuar mesmo assim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent.putExtra("Questions",questions);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }
            else
            {
                ArrayList<Question> questions2 =  new ArrayList<>();
                questions2.addAll(questions.subList(0,rules.getNumeroDeQuestoes()));
                intent.putExtra("Questions",questions2);
                startActivity(intent);
                finish();
            }
        }
    }
}
