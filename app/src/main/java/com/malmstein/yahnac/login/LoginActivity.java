package com.malmstein.yahnac.login;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

import com.malmstein.yahnac.BuildConfig;
import com.malmstein.yahnac.HNewsActivity;
import com.malmstein.yahnac.R;
import com.malmstein.yahnac.views.AnimationFactory;
import com.novoda.notils.caster.Views;

public class LoginActivity extends HNewsActivity {

    public static final String EXTRA_CX = BuildConfig.APPLICATION_ID + "EXTRA_CX";
    public static final String EXTRA_CY = BuildConfig.APPLICATION_ID + "EXTRA_CY";

    private EditText usernameView;
    private EditText passwordView;

    private InputFieldValidator inputFieldValidator = new InputFieldValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupSubActivity();

        if (savedInstanceState == null) {

            if (getIntent().hasExtra(EXTRA_CX) && getIntent().hasExtra(EXTRA_CY)) {

                final View root = Views.findById(this, R.id.login_root);
                root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        root.getViewTreeObserver().removeOnPreDrawListener(this);

                        int radius = Math.max(root.getWidth(), root.getHeight());
                        int cx = getIntent().getIntExtra(EXTRA_CX, 0);
                        int cy = getIntent().getIntExtra(EXTRA_CY, 0);
                        Animator reveal = AnimationFactory.createRevealAnimation(root, cx, cy, radius);
                        reveal.start();
                        return true;
                    }
                });
            }
        }

        usernameView = Views.findById(this, R.id.login_username);
        passwordView = Views.findById(this, R.id.login_password);

        Button login = Views.findById(this, R.id.login_action);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                }

            }
        });
    }

    protected String getPassword() {
        return passwordView.getText().toString();
    }

    protected String getUsername() {
        return usernameView.getText().toString();
    }

    private boolean validate() {
        boolean isUsernameValid = inputFieldValidator.isValid(getUsername());
        boolean isPasswordValid = inputFieldValidator.isValid(getPassword());
        if (!isPasswordValid) {
            passwordView.setError(getString(R.string.login_password_not_valid));
            passwordView.requestFocus();
        }
        if (!isUsernameValid) {
            usernameView.setError(getString(R.string.login_username_not_valid));
            usernameView.requestFocus();
        }
        return isUsernameValid && isPasswordValid;
    }

//    private void hideLoginBox() {
//        // previously visible view
//        final View myView = findViewById(R.id.login_container);
//
//        // get the center for the clipping circle
//        int cx = (myView.getLeft() + myView.getRight()) / 2;
//        int cy = (myView.getTop() + myView.getBottom()) / 2;
//
//        // get the initial radius for the clipping circle
//        int initialRadius = myView.getWidth();
//
//        // create the animation (the final radius is zero)
//        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
//
//        // make the view invisible when the animation is done
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                myView.setVisibility(View.INVISIBLE);
//            }
//        });
//    }
}