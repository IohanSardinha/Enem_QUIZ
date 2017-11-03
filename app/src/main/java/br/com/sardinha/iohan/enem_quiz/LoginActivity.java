package br.com.sardinha.iohan.enem_quiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView email;
    TextView senha;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (TextView)findViewById(R.id.email);
        senha = (TextView)findViewById(R.id.senha);
        auth = FirebaseAuth.getInstance();

        TextView title = (TextView)findViewById(R.id.textView2);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font.otf");
        title.setTypeface(typeface);

    }

    public void registrar(View view) {
        startActivity(new Intent(this,RegistrarActivity.class));
    }

    public void entrar(View view) {
        if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            findViewById(R.id.progressBar3).setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email.getText().toString(),senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    findViewById(R.id.progressBar3).setVisibility(View.GONE);
                    if(task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Email e senha não encontrados", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void esqueciSenha(View view) {
        startActivity(new Intent(this,RedefinirSenhaActivity.class));
    }
}
