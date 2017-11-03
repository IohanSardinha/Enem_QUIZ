package br.com.sardinha.iohan.enem_quiz;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private TextView emailTV;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        emailTV = (TextView)findViewById(R.id.email_redefinir);
        auth = FirebaseAuth.getInstance();
    }

    public void enviar(View view) {
        String email = emailTV.getText().toString().trim();

        if(email.isEmpty())
        {
            Toast.makeText(this, "Preencha o campo!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final ProgressDialog progress = ProgressDialog.show(this,"Um momento por favor","Enviando...",true);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progress.dismiss();
                        new AlertDialog.Builder(RedefinirSenhaActivity.this)
                                .setTitle("Enviado")
                                .setMessage("Email para redefinição de senha enviado")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                    }
                    else
                    {
                        Toast.makeText(RedefinirSenhaActivity.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
            });
        }
    }
}
