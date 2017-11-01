package br.com.sardinha.iohan.enem_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference database;
    User currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        FirebaseUser u = auth.getCurrentUser();
        if(u == null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        else
        {
            database = FirebaseDatabase.getInstance().getReference("Usuários").child(u.getUid());
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentUser = dataSnapshot.getValue(User.class);
                    Draw();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void Draw()
    {
        if(currentUser.isAdmin())
        {
            ((Button)findViewById(R.id.sugerir)).setText("Criar questão");
            findViewById(R.id.aprovar).setVisibility(View.VISIBLE);
        }
    }

    public void comecar(View view) {
        startActivity(new Intent(this,RegrasActivity.class));
    }

    public void sair(View view) {
        auth.signOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void sugerir(View view) {
        if(currentUser != null) {
            Intent intent = new Intent(this, SugerirQuestaoActivity.class);
            intent.putExtra("user", currentUser);
            startActivity(intent);
        }
    }
}
