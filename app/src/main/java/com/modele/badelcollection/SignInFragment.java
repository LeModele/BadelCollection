package com.modele.badelcollection;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.modele.badelcollection.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView donthaveAnAccount;
    private FrameLayout parentFramelayout;
    private EditText email;
    private EditText password;

    private TextView forgotpassword;

    private ImageButton closeBtn;
    private Button signInBtn;

    private FirebaseAuth firebaseAuth;

    private String emailpattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private ProgressBar progressBar;

    public static boolean disableCloseBtn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        donthaveAnAccount = view.findViewById(R.id.tv_dont_have_an_account);

        forgotpassword=view.findViewById(R.id.sing_in_forgot_password);
        parentFramelayout=getActivity().findViewById(R.id.register_framelayout);

        email=view.findViewById(R.id.sing_in_email);
        password=view.findViewById(R.id.sing_in_password);

        closeBtn=view.findViewById(R.id.sing_in_close_btn);
        signInBtn=view.findViewById(R.id.sing_in_btn);

        firebaseAuth=FirebaseAuth.getInstance();

        progressBar = view.findViewById(R.id.sign_in_progressbar);
        if(disableCloseBtn){
            closeBtn.setVisibility(View.GONE);
        }else {
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donthaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());

            }


        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment=true;
                setFragment(new ResetPasswordFragment());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }



    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_form_right,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();

    }
    private void checkInputs() {
        if(TextUtils.isEmpty(email.getText()))
        {
            if (TextUtils.isEmpty(password.getText()))
            {
            //signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.rgb(255,255,255));
            }else{
                //signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.argb(50,255,255,255));
            }
            }else {
            //signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50,255,255,255));
        }

    }

    private void checkEmailAndPassword()
    {
        if(email.getText().toString().matches(emailpattern))
        {
            if(password.length()>=8)
            {
                progressBar.setVisibility(View.VISIBLE);
                //signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                               mainIntent();
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                signInBtn.setEnabled(true);
                                signInBtn.setTextColor(Color.rgb(255,255,255));
                                String error=task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                            }
                        });
            }else {
                Toast.makeText(getActivity(), "incorrect email and Password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "incorrect email and Password", Toast.LENGTH_SHORT).show();
    }
    }

    private void mainIntent(){
        if(disableCloseBtn){
            disableCloseBtn = false;
        }else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }

}
