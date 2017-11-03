package br.com.sardinha.iohan.enem_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    Question currentQuestion;
    Rules rules;
    TextView tempo;

    TextView pergunta;
    Button a;
    Button b;
    Button c;
    Button d;
    Button e;

    int score = 0;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        questions = (ArrayList<Question>) intent.getSerializableExtra("Questions");
        rules = (Rules) intent.getSerializableExtra("Rules");

        tempo = (TextView)findViewById(R.id.tempo_count_down);
        pergunta = (TextView)findViewById(R.id.pergunta);
        a = (Button)findViewById(R.id.opcaoA);
        b = (Button)findViewById(R.id.opcaoB);
        c = (Button)findViewById(R.id.opcaoC);
        d = (Button)findViewById(R.id.opcaoD);
        e = (Button)findViewById(R.id.opcaoE);

        if(!rules.isTempoBool())
        {
            tempo.setVisibility(View.GONE);
            findViewById(R.id.clock).setVisibility(View.GONE);
        }
        else
        {
            new CountDownTimer(rules.getTempoInMillis(),1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                    int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                    int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                    tempo.setText(hours+":"+minutes+":"+seconds);
                    if(hours == 1)
                    {
                        tempo.setTextColor(Color.rgb(150,150,0));
                    }
                    else if(minutes <= 30 && hours <= 0)
                    {
                        tempo.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    tempo.setText("0:0:0");
                    Toast.makeText(QuestionActivity.this, "Acabou o tempo!", Toast.LENGTH_LONG).show();
                    position = questions.size();
                    Draw();
                }
            }.start();
        }
        Draw();
    }

    private void Draw()
    {
        position ++;
        if(position >= questions.size())
        {
            if(score >= questions.size()*0.7)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Parabéns")
                        .setMessage(String.valueOf(score)+" de "+String.valueOf(questions.size()))
                        .setCancelable(false)
                        .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }
            else if(score >= questions.size()*0.5)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Muito bem!")
                        .setMessage(String.valueOf(score)+" de "+String.valueOf(questions.size()))
                        .setCancelable(false)
                        .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setTitle("Precisa treinar mais...")
                        .setMessage(String.valueOf(score)+" de "+String.valueOf(questions.size()))
                        .setCancelable(false)
                        .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }

            return;
        }
        currentQuestion = questions.get(position);
        pergunta.setText(currentQuestion.getText());
        a.setText("A) "+currentQuestion.getA());
        b.setText("B) "+currentQuestion.getB());
        c.setText("C) "+currentQuestion.getC());
        d.setText("D) "+currentQuestion.getD());
        e.setText("E) "+currentQuestion.getE());
    }

    public void getAnswer(View view) {
        if(view.getId() == R.id.opcaoA)
        {
            if(currentQuestion.getAnswer().equals("A"))
            {
                score++;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Draw();
                }
            },500);
        }
        else if(view.getId() == R.id.opcaoB)
        {
            if(currentQuestion.getAnswer().equals("B"))
            {
                score++;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Draw();
                }
            },500);
        }
        else if(view.getId() == R.id.opcaoC)
        {
            if(currentQuestion.getAnswer().equals("C"))
            {
                score++;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Draw();
                }
            },500);
        }
        else if(view.getId() == R.id.opcaoD)
        {
            if(currentQuestion.getAnswer().equals("D"))
            {
                score++;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Draw();
                }
            },500);
        }
        else if(view.getId() == R.id.opcaoE)
        {
            if(currentQuestion.getAnswer().equals("E"))
            {
                score++;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Draw();
                }
            },500);
        }
    }
}
