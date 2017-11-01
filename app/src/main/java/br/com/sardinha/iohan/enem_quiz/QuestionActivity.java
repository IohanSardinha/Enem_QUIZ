package br.com.sardinha.iohan.enem_quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionActivity extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        reference = FirebaseDatabase.getInstance().getReference("Questões").child("-Kxswgw7p-PgzDlycvH-");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question q = dataSnapshot.getValue(Question.class);
                ((TextView)findViewById(R.id.pergunta)).setText(q.getText());
                ((Button)findViewById(R.id.opcaoA)).setText(q.getA());
                ((Button)findViewById(R.id.opcaoB)).setText(q.getB());
                ((Button)findViewById(R.id.opcaoC)).setText(q.getC());
                ((Button)findViewById(R.id.opcaoD)).setText(q.getD());
                ((Button)findViewById(R.id.opcaoE)).setText(q.getE());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
