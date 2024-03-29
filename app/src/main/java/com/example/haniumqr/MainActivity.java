package com.example.haniumqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;



public class MainActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN=123;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button btn_login,btn_logout;
    Button btn_host,btn_guest;
    TextView text;
    ImageView image;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    String email2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login=findViewById(R.id.login);
        btn_logout=findViewById(R.id.logout);
        btn_host=(Button) findViewById(R.id.host);
        btn_guest=(Button)findViewById(R.id.guest);


        text=findViewById(R.id.text);
        image=findViewById(R.id.image);
        progressBar=findViewById(R.id.progress_circular);



        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        btn_login.setOnClickListener(v -> SignInGoogle());
        btn_logout.setOnClickListener(v->Logout());

        btn_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(MainActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("사용하실 userID를 입력해주세요.")
                        .setView(editText)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userID = editText.getText().toString();
                                senddata senddata = new senddata(email2,1);
                                mDatabase.child("User").child(userID).setValue(senddata);
                                Intent intent = new Intent(MainActivity.this,HostMainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("email",email2);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
                }

        });
        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(MainActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("사용하실 userID를 입력해주세요.")
                        .setView(editText)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userID = editText.getText().toString();
                                senddata senddata = new senddata(email2,2);
                                mDatabase.child("User").child(userID).setValue(senddata);
                                Intent intent = new Intent(MainActivity.this,GuestMainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("email",email2);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();

            }
        });

        if(mAuth.getCurrentUser() !=null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
    }

    void SignInGoogle(){
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,GOOGLE_SIGN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null) firebaseAuthWithGoogle(account);

            }catch(ApiException e){
                e.printStackTrace();
            }
    }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAuthWithGoogle: "+account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,task->{
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("TAG","signin success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.w("TAG","signin failure", task.getException());

                        Toast.makeText(this, "SignIn Failed!", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                });
    }

    private void updateUI(FirebaseUser user) {

        if(user !=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            email2 = email;
            String photo = String.valueOf(user.getPhotoUrl());

            text.append("Info : \n");
            text.append(name+"\n");
            text.append(email);

            Picasso.get().load(photo).into(image);
            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);

            btn_guest.setVisibility(View.VISIBLE);
            btn_host.setVisibility(View.VISIBLE);



        }else {

            text.setText(getString(R.string.firebase_login));
            Picasso.get().load(R.drawable.ic_firebase_logo).into(image);
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);

        }
    }

    void Logout(){

        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        task -> { updateUI(null); });

    }



}
