package com.deficientleaf.smstest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userid = (EditText)findViewById(R.id.email);
        EditText userpw = (EditText)findViewById(R.id.password);
        SharedPreferences setting = getSharedPreferences("saveduid",MODE_PRIVATE);
        userid.setText(setting.getString("PREF_USERID",""));
        userpw.setText(setting.getString("PREF_PASSWD",""));


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "登入:"+
                            user.getUid());
                    userUID =  user.getUid();
                }else{
                    Log.d("onAuthStateChanged", "已登出");
                }

            }
        };


    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }


    public void login(View v) {
        final String email = ((EditText) findViewById(R.id.email))
                .getText().toString();
        final String password = ((EditText) findViewById(R.id.password))
                .getText().toString();


        mAuth.signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                register(email, password);
            }
        })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        SharedPreferences setting = getSharedPreferences("saveduid",MODE_PRIVATE);
                        setting.edit()
                                .putString("PREF_USERID",email)
                                .putString("PREF_PASSWD",password)
                                .commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                   /* @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      task = FirebaseAuth.getInstance().signInAnonymously();

                        if (!task.isSuccessful()){

                            register(email, password);





                        }else {
                            SharedPreferences setting = getSharedPreferences("saveduid",MODE_PRIVATE);
                            setting.edit()
                                   .putString("PREF_USERID",email)
                                   .putString("PREF_PASSWD",password)
                                    .commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }*/
                });


    }

    private void register(final String email, final String password) {
                new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("登入問題")
                            .setMessage("無此帳號，是否要以此帳號與密碼註冊?")
                            .setPositiveButton("註冊", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createUser(email, password);

                                }
             })
                            .setNeutralButton("取消", null)
                            .show();
    }


    private void createUser(String email, String password) {
                mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                            String message = task.isComplete() ? "註冊成功" : "註冊失敗";
                                                        new AlertDialog.Builder(LoginActivity.this)
                                                        .setMessage(message)
                                                        .setPositiveButton("註冊", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .show();

                                        }
             });
            }








}
