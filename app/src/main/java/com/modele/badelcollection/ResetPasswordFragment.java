package com.modele.badelcollection;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;

    private FrameLayout parentFramelayout;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        registeredEmail=view.findViewById(R.id.forgot_password_email);
        resetPasswordBtn = view.findViewById(R.id.reset_password_btn);
        goBack = view.findViewById(R.id.forgot_password_go_back);

        emailIconContainer = view.findViewById(R.id.forgot_password_email_icon_container);
        emailIcon = view.findViewById(R.id.forgot_password_email_icon);
        emailIconText =view.findViewById(R.id.forgot_password_icon_text);
        progressBar = view.findViewById(R.id.forgot_password_progressbar);

        parentFramelayout=getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registeredEmail.addTextChangedListener(new TextWatcher() {
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

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setFragment(new SignInFragment());
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);
                //TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    // a realiser Avec video #10
                                    //ScaleAnimation scaleAnimation =new ScaleAnimation();
                                    // animation
                                    Toast.makeText(getActivity(), "email sent succesfully", Toast.LENGTH_SHORT).show();

                                }else{

                                    String error=task.getException().getMessage();
                                    resetPasswordBtn.setEnabled(true);
                                    resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
                                    //Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                    //progressBar.setVisibility(View.GONE);

                                    //TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.colorRouge));
                                    emailIconText.setVisibility(View.VISIBLE);
                                }
                                progressBar.setVisibility(View.GONE);

                     }
                });
            }
        });
    }

    public void checkInputs()
    {
        if(TextUtils.isEmpty(registeredEmail.getText()))
        {
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }else{
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
        }

    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
