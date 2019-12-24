package com.example.capstoneprototype.fragments;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.LoginAct;
import com.example.capstoneprototype.MainActivity;
import com.example.capstoneprototype.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrag extends Fragment {
Button signup_login;
Button signin_login;
EditText email,password;


    RegisterFrag registerFrag;

    private FirebaseAuth mauth;


    public LoginFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        signup_login=(Button)v.findViewById(R.id.Sign_up);
        signin_login=(Button)v.findViewById(R.id.SignIn);
        email=(EditText)v.findViewById(R.id.email);
        password=(EditText)v.findViewById(R.id.password);

//      firebaseAuth
        mauth=FirebaseAuth.getInstance();

        registerFrag=new RegisterFrag();
        signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing data to activity

                ((LoginAct)getActivity()).replaceFragment(registerFrag);
            }
        });

        signin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate_cred(email.getText().toString(),password.getText().toString())){
                    mauth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent i = new Intent(getContext(), MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getContext(), "Error while logging...try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Vibrate_EditField();
                            Toast.makeText(getContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{


                }
            }
        });



        return v;
    }

    public Boolean Validate_cred(String email,String pass){
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(pass.length()>=8){
                    return true;
                }else{
                    changeStroke(this.password, ContextCompat.getColor(getContext(),R.color.red));
                    changeStroke(this.email, ContextCompat.getColor(getContext(),R.color.black));

                    password.startAnimation(shakeEdit());
                    Vibrate_EditField();
                    Toast.makeText(getContext(), "Password length should be grater than 7", Toast.LENGTH_SHORT).show();

                    return false;

                }
            }else{
                changeStroke(this.email, ContextCompat.getColor(getContext(),R.color.red));
                changeStroke(this.password, ContextCompat.getColor(getContext(),R.color.black));

                this.email.startAnimation(shakeEdit());
                Vibrate_EditField();


                Toast.makeText(getContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{
            changeStroke(this.email, ContextCompat.getColor(getContext(),R.color.red));
            changeStroke(this.password, ContextCompat.getColor(getContext(),R.color.red));

            this.email.startAnimation(shakeEdit());
            this.password.startAnimation(shakeEdit());
            Vibrate_EditField();
            Vibrate_EditField();
            Toast.makeText(getContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
            return false;
        }



    }

    //for shaking edittext animation

    public TranslateAnimation shakeEdit(){
        TranslateAnimation ta=new TranslateAnimation(0,10,0,0);
        ta.setInterpolator(new CycleInterpolator(5));
        ta.setDuration(500);
        return ta;
    }

    //for vibrating edittext

    public void Vibrate_EditField(){
        Vibrator vibe=(Vibrator)getContext().getSystemService(getContext().VIBRATOR_SERVICE);
        vibe.vibrate(50);

    }

    //changing edittext stroke color

    public void changeStroke(View view,int color){
        ((GradientDrawable)view.getBackground()).setStroke(2,color);
    }

}
