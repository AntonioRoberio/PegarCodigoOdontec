package com.inovare.equipe.odontec.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.inovare.equipe.odontec.Model.GenericDao;
import com.inovare.equipe.odontec.Model.Usuario;
import com.inovare.equipe.odontec.R;

public class TelaPrincipal extends AppCompatActivity {
    private Button sair;
    private Button excluirConta;
    private FirebaseAuth aut;
    private DatabaseReference referencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sair = (Button) findViewById(R.id.btSair);
        excluirConta = (Button) findViewById(R.id.apagarConta);


        aut = GenericDao.autenticarDados();
        final Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle != null){
            atualizarSenha(bundle);
        }



        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aut.signOut();
                Intent intent = new Intent(TelaPrincipal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        excluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario=new Usuario();
                usuario.deletarConta();
                Intent intent=new Intent(TelaPrincipal.this,Login.class);
                startActivity(intent);
            }
        });



    }

    public void atualizarSenha(Bundle bundle) {
        referencia = GenericDao.refernciaBancoFirebase();

            String sennha=bundle.getString("senhaAtualizada");
            FirebaseUser atual = aut.getCurrentUser();
            String id = atual.getUid();
            referencia.child("user").child(id).child("senha").setValue(sennha);
        }


    }



