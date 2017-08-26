package com.inovare.equipe.odontec.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.inovare.equipe.odontec.Model.GenericDao;
import com.inovare.equipe.odontec.Model.Usuario;
import com.inovare.equipe.odontec.R;

public class Login extends AppCompatActivity {
    private Button botaoTeste;
    private EditText senha;
    private EditText email;
    private Usuario usuario;
    private TextView cadastrar;
    private String TAG;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseAuth autenticar;
    private TextView resetSenha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText) findViewById(R.id.Email);
        senha=(EditText) findViewById(R.id.Senha);
        botaoTeste =(Button) findViewById(R.id.btTeste);
        cadastrar=(TextView) findViewById(R.id.cadastro);
        resetSenha=(TextView) findViewById(R.id.recuperarSenha);
        autenticar= GenericDao.autenticarDados();

       cadastrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(Login.this,CadastroUsuario.class);
               startActivity(intent);
           }
       });

       resetSenha.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Intent intent= new Intent(Login.this,RecuperarSenha.class);
               //startActivity(intent);
           }
       });


        botaoTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario= new Usuario();
                if(!email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()){
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    logar();
                }else{
                    Toast.makeText(Login.this,"Campos em branco!",Toast.LENGTH_SHORT).show();
                }


            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = autenticar.getCurrentUser();
        autenticar.addAuthStateListener(mAuthListener);
        validarLogin(currentUser);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            autenticar.removeAuthStateListener(mAuthListener);
        }
    }





    public  void logar(){


         autenticar.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     FirebaseUser usuario=autenticar.getCurrentUser();
                     validarLogin(usuario);
                     Toast.makeText(Login.this,"Bem vindo",Toast.LENGTH_SHORT).show();
                     finish();

                 }else{
                     Toast.makeText(Login.this,"Usuarios ou senha invalidas",Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    public void validarLogin(FirebaseUser usua){
        if(usuario!=null){
            Bundle dados=new Bundle();
            dados.putString("senhaAtualizada",usuario.getSenha().toString());
            Intent intent= new Intent(Login.this,TelaPrincipal.class);
            intent.putExtras(dados);
            startActivity(intent);
        }

    }
}
