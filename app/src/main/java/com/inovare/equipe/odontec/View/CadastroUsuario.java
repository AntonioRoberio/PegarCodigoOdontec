package com.inovare.equipe.odontec.View;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.inovare.equipe.odontec.Model.GenericDao;
import com.inovare.equipe.odontec.Model.Usuario;
import com.inovare.equipe.odontec.R;

public class CadastroUsuario extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confimarSenha;
    private EditText idade;
    private EditText estado;
    private EditText cidade;
    private RadioGroup sexo;
    private RadioButton mf;
    private Button salvar;
    private Usuario usuario;
    private FirebaseAuth aut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadatro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome=(EditText) findViewById(R.id.nomeUsuario);
        email=(EditText) findViewById(R.id.emailUsuario);
        senha=(EditText) findViewById(R.id.senhaUsuario);
        idade=(EditText) findViewById(R.id.idadeUsuario);
        estado=(EditText) findViewById(R.id.estadoUsuario);
        cidade=(EditText) findViewById(R.id.cidadeUsuario);
        sexo=(RadioGroup) findViewById(R.id.selecionarSexo);
        confimarSenha=(EditText) findViewById(R.id.confirSenhaUsuario);
        salvar=(Button) findViewById(R.id.btSalvar);
        int escolha=sexo.getCheckedRadioButtonId();
        mf=(RadioButton) findViewById(escolha);



       salvar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(senha.getText().toString().equals(confimarSenha.getText().toString())){
                   usuario=new Usuario();
                   aut= GenericDao.autenticarDados();
                   usuario.setNome(nome.getText().toString());
                   usuario.setEmail(email.getText().toString());
                   usuario.setSenha(senha.getText().toString());
                   usuario.setIdade(idade.getText().toString());
                   usuario.setEstado(estado.getText().toString());
                   usuario.setCidade(cidade.getText().toString());
                   usuario.setSexo(mf.getText().toString());





                   cadastraUsuario();
               }else{
                   Toast.makeText(CadastroUsuario.this,"As senhas são divergentes",Toast.LENGTH_SHORT).show();
               }

           }
       });

    }

    public void cadastraUsuario(){
        aut= GenericDao.autenticarDados();

        aut.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroUsuario.this,"Usuario cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                    FirebaseUser pegarId=aut.getCurrentUser();
                    String id=pegarId.getUid();
                    usuario.setId(id);
                    usuario.salvarBD();
                    finish();
                    Intent intent=new Intent(CadastroUsuario.this,TelaPrincipal.class);
                    startActivity(intent);

                }
            }
        });
    }

}
