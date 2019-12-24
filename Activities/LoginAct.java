package com.example.capstoneprototype.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstoneprototype.MainActivity;
import com.example.capstoneprototype.R;
import com.example.capstoneprototype.fragments.LoginFrag;
import com.example.capstoneprototype.fragments.RegisterFrag;

public class LoginAct extends AppCompatActivity {
LinearLayout login_linear;
Button loginScoll;
Button registerScoll;
Button skip;
ImageView main_image;
LoginFrag loginFrag;
RegisterFrag registerFrag;

int marginreplace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_linear=(LinearLayout)findViewById(R.id.login_linear);
        loginScoll=(Button)findViewById(R.id.login_scroll);
        registerScoll=(Button)findViewById(R.id.register_scroll);
        main_image=(ImageView)findViewById(R.id.main_image);
        main_image.animate().scaleX(1.1f).scaleY(1.1f).setDuration(2000).start();

        skip=(Button)findViewById(R.id.skip_button);



        //fragments
        loginFrag=new LoginFrag();
        registerFrag=new RegisterFrag();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.login_linear,loginFrag);

        ft.commit();
    }




//scrolls the login fragment to view
    public void LoginScroll(View view) {
       // login_linear.animate().translationYBy(-1000).setInterpolator(new BounceInterpolator()).start();
   // loginScoll.animate().alpha(0).setDuration(300).start();
    loginScoll.setVisibility(View.GONE);
    registerScoll.setVisibility(View.GONE);
    skip.setVisibility(View.VISIBLE);




        //animating the linear layout
        final int marginEnd=300;
        Animation a=new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) login_linear.getLayoutParams();
                params.topMargin = (int) (marginEnd * interpolatedTime);
                login_linear.setLayoutParams(params);

            }
        };
        login_linear.setVisibility(View.VISIBLE);

        a.setDuration(1000);
        login_linear.startAnimation(a);

    }




    public void RegisterScroll(View view) {
        loginScoll.setVisibility(View.GONE);
        registerScoll.setVisibility(View.GONE);

        skip.setVisibility(View.VISIBLE);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.remove(loginFrag);
        ft.add(R.id.login_linear,registerFrag);

        ft.commit();


        //animating the linear layout
        final int marginEnd=100;
        Animation a=new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) login_linear.getLayoutParams();
                params.topMargin = (int) (marginEnd * interpolatedTime);
                login_linear.setLayoutParams(params);

            }
        };
        login_linear.setVisibility(View.VISIBLE);
        a.setDuration(1000);
        login_linear.startAnimation(a);
    }


    //replacing activity's  fragment from fragment itself

    public void replaceFragment(Fragment rep){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(rep!=null) {



                ft.replace(R.id.login_linear, rep);
if(rep instanceof LoginFrag){
    marginreplace=300;
}else{
    marginreplace=200;
}







            ft.commit();


            //animating the linear layout

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) login_linear.getLayoutParams();
                    params.topMargin = (int) (marginreplace * interpolatedTime);
                    login_linear.setLayoutParams(params);

                }
            };
            login_linear.setVisibility(View.VISIBLE);
            a.setDuration(800);
            login_linear.startAnimation(a);
        }

    }

    //incase of skipping login

    public void skip_login(View view) {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
