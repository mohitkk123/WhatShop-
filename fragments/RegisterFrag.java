package com.example.capstoneprototype.fragments;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.LoginAct;
import com.example.capstoneprototype.DataModels.user_info;
import com.example.capstoneprototype.MainActivity;
import com.example.capstoneprototype.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFrag extends Fragment {
Button signin_register;

Button signup;
LoginFrag loginFrag;
EditText email,password,password_confirm,name_register;

//data model
  user_info user_data;

//firebase references

private FirebaseAuth mAuth;
private DatabaseReference mdatabase;


    public RegisterFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_register, container, false);
        signup=(Button)v.findViewById(R.id.SignUp_register);
        signin_register=(Button)v.findViewById(R.id.Sign_in_register);
        email=(EditText)v.findViewById(R.id.email_register);
        password=(EditText)v.findViewById(R.id.password_register);
        password_confirm=(EditText)v.findViewById(R.id.password_confirm);
        name_register=(EditText)v.findViewById(R.id.name_register);

        //firebase auth and database init
        mAuth=FirebaseAuth.getInstance();
        mdatabase=FirebaseDatabase.getInstance().getReference();


        loginFrag=new LoginFrag();
        signin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginAct)getActivity()).replaceFragment(loginFrag);

            }
        });

        //registering the user

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_credential(name_register.getText().toString(),email.getText().toString(),password.getText().toString(),password_confirm.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                //direbase database value add
                                String userId=mAuth.getCurrentUser().getUid();
                                user_data=new user_info(name_register.getText().toString(),email.getText().toString(),password.getText().toString(),mAuth.getCurrentUser().getUid());

                                mdatabase.child("Users_Profile").child(userId).setValue(user_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "data added successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), ""+ e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                                //sending to main Activity
                                Intent i=new Intent(getContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Vibrate_EditField();
                            Toast.makeText(getContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return v;
    }

    public Boolean validate_credential(String name,String email,String pass,String pass_con){
        if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(pass_con)) {
        if(name.length()>=7) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (pass.equals(pass_con)) {
                    if (pass.length() >= 8) {
                        return true;
                    } else {
                        changeStroke(this.email, ContextCompat.getColor(getContext(), R.color.black));
                        changeStroke(this.password_confirm, ContextCompat.getColor(getContext(), R.color.red));
                        changeStroke(this.password_confirm, ContextCompat.getColor(getContext(), R.color.red));

                        password.startAnimation(shakeEdit());
                        password_confirm.startAnimation(shakeEdit());
                        Vibrate_EditField();
                        Toast.makeText(getContext(), "Password length should be greater than 7", Toast.LENGTH_SHORT).show();
                        return false;
                    }


                } else {
                    changeStroke(this.email, ContextCompat.getColor(getContext(), R.color.black));
                    changeStroke(this.password, ContextCompat.getColor(getContext(), R.color.red));
                    changeStroke(this.password_confirm, ContextCompat.getColor(getContext(), R.color.red));

                    password.startAnimation(shakeEdit());
                    password_confirm.startAnimation(shakeEdit());
                    Vibrate_EditField();
                    Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                changeStroke(this.email, ContextCompat.getColor(getContext(), R.color.red));
                changeStroke(this.password_confirm, ContextCompat.getColor(getContext(), R.color.black));
                changeStroke(this.password, ContextCompat.getColor(getContext(), R.color.black));

                this.email.startAnimation(shakeEdit());
                Vibrate_EditField();
                Toast.makeText(getContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                return false;
            }


        }else{
            changeStroke(this.email, ContextCompat.getColor(getContext(), R.color.black));
            changeStroke(this.name_register, ContextCompat.getColor(getContext(), R.color.red));
            changeStroke(this.password_confirm, ContextCompat.getColor(getContext(), R.color.black));
            changeStroke(this.password, ContextCompat.getColor(getContext(), R.color.black));

            this.name_register.startAnimation(shakeEdit());

            Vibrate_EditField();
            Toast.makeText(getContext(), "Name is too short", Toast.LENGTH_SHORT).show();
            return false;


            }


        }else{
            changeStroke(this.email, ContextCompat.getColor(getContext(),R.color.red));
            changeStroke(this.password, ContextCompat.getColor(getContext(),R.color.red));
            changeStroke(this.password_confirm, ContextCompat.getColor(getContext(),R.color.red));


            password.startAnimation(shakeEdit());
            password_confirm.startAnimation(shakeEdit());
            this.email.startAnimation(shakeEdit());
            Vibrate_EditField();
            Toast.makeText(getContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
            return false;
        }



    }

    //for shaking editext animation

    public TranslateAnimation shakeEdit(){
        TranslateAnimation ta=new TranslateAnimation(0,10,0,0);
        ta.setInterpolator(new CycleInterpolator(5));
        ta.setDuration(500);
        return ta;
    }

    //for vibrating editext

    public void Vibrate_EditField(){
        Vibrator vibe=(Vibrator)getContext().getSystemService(getContext().VIBRATOR_SERVICE);
        vibe.vibrate(50);

    }

    //changing edittext stroke color

    public void changeStroke(View view,int color){
        ((GradientDrawable)view.getBackground()).setStroke(2,color);
    }

}
