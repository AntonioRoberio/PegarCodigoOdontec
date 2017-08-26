package com.inovare.equipe.odontec.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Antonio on 20/08/2017.
 */

public class GenericDao {
    private static DatabaseReference refenciaBanco;
    private static FirebaseAuth autenticar;

    public  static DatabaseReference refernciaBancoFirebase(){
        if(refenciaBanco==null){
            return refenciaBanco= FirebaseDatabase.getInstance().getReference();
        }
        return refenciaBanco;
    }

    public static FirebaseAuth autenticarDados(){
        if(autenticar==null){
            return  autenticar=FirebaseAuth.getInstance();
        }
        return autenticar;
    }
}
