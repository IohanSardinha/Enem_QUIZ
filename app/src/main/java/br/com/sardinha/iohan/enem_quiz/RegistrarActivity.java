package br.com.sardinha.iohan.enem_quiz;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity {

    private TextView nome;
    private TextView email;
    private TextView senha;
    private TextView confSenha;
    private FirebaseAuth auth;
    private DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nome = (TextView)findViewById(R.id.nome_registrar);
        email = (TextView)findViewById(R.id.email_registrar);
        senha = (TextView)findViewById(R.id.senha_registrar);
        confSenha = (TextView)findViewById(R.id.confirmar_senha_registrar);

        auth = FirebaseAuth.getInstance();

        usersReference = FirebaseDatabase.getInstance().getReference("Usuários");
    }

    public void enviar(View view) {
        if(nome.getText().toString().isEmpty() || email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || confSenha.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
        else if(!senha.getText().toString().equals(confSenha.getText().toString()))
        {
            Toast.makeText(this, "Senhas não batem!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final ProgressDialog progress = ProgressDialog.show(this,"","Registrando",true);
            auth.createUserWithEmailAndPassword(email.getText().toString(),senha.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        String id = task.getResult().getUser().getUid();
                        User user = new User(id,nome.getText().toString(),email.getText().toString());
                        usersReference.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progress.dismiss();
                                finish();
                            }
                        });
                    }
                    else if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        progress.dismiss();
                        new AlertDialog.Builder(RegistrarActivity.this)
                                .setTitle("Email já está registrado")
                                .setMessage("Esqueceu sua senha?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(RegistrarActivity.this,RedefinirSenhaActivity.class));
                                        finish();
                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();

                    }
                }
            });
        }
    }
}
