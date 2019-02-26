package com.mobileprograming.g4.dien.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Startup activity for user registers persional information.
 * After registering, user will go to MainActivity
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullname;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtRePassword;

    private TextInputLayout tilFullName;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputLayout tilRePassword;

    private Button btnRegister;
    private Button btnFacebook;
    private Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mapControls();
        addEvents();
    }

    /** Map all used controls from layout */
    private void mapControls() {
        edtFullname = findViewById(R.id.edtFullname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);

        tilFullName = findViewById(R.id.tilFullname);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilRePassword = findViewById(R.id.tilRePassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);
    }

    /** Add events for controls: Buttons, EditTexts mapped from layout. */
    private void addEvents() {
        btnRegister.setOnClickListener(this::btnRegisterOnClick);
        btnFacebook.setOnClickListener(this::btnFacebookOnClick);
        btnGoogle.setOnClickListener(this::btnGoogleOnClick);

        edtFullname.addTextChangedListener(new RegisterTextWatcher(edtFullname));
        edtEmail.addTextChangedListener(new RegisterTextWatcher(edtEmail));
        edtPassword.addTextChangedListener(new RegisterTextWatcher(edtPassword));
        edtRePassword.addTextChangedListener(new RegisterTextWatcher(edtRePassword));
    }

    /**
     * Process for btnGoogle on click
     * @param view btnGoogle
     */
    private void btnGoogleOnClick(View view) {
        Snackbar.make(view, "Bạn đăng nhập bằng Google", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Process for btnFacebook on click
     * @param view btnFacebook
     */
    private void btnFacebookOnClick(View view) {
        Snackbar.make(view, "Bạn đăng nhập bằng Facebook", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Process for btnRegister on click
     * @param view btnRegister
     */
    private void btnRegisterOnClick(View view) {
        if (validateRePassword() || validatePassword() || validateEmail() || validateFullname()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Validate for password
     * @return result of validating
     */
    private boolean validatePassword() {
        String pass = edtPassword.getText().toString();
        tilPassword.setCounterEnabled(true);

        if (pass.isEmpty()) {
            tilPassword.setError(getString(R.string.password_empty));
            requestFocus(edtPassword);
            return false;
        }

        if (pass.length() < 8) {
            tilPassword.setError(getString(R.string.password_len_invalid));
            requestFocus(edtPassword);
            return false;
        }

        tilPassword.setErrorEnabled(false);
        return true;
    }

    /**
     * Validate for re-enter password
     * @return result of validating
     */
    private boolean validateRePassword() {
        String repass = edtRePassword.getText().toString();
        if (repass.isEmpty()) {
            tilRePassword.setError(getString(R.string.repasswod_empty));
            requestFocus(edtRePassword);
            return false;
        }

        if (!repass.equals(edtPassword.getText().toString())) {
            tilRePassword.setError(getString(R.string.repasswod_not_match));
            requestFocus(edtRePassword);
            return false;
        }

        tilRePassword.setErrorEnabled(false);
        return true;
    }

    /**
     * Validate for email
     * @return result of validating
     */
    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.email_empty));
            requestFocus(edtEmail);
            return false;
        }

        if (!isValidEmail(email)) {
            tilEmail.setError(getString(R.string.email_invalid));
            requestFocus(edtEmail);
            return false;
        }

        tilEmail.setErrorEnabled(false);
        return true;
    }

    /**
     * Validate for fullname
     * @return result of validating
     */
    private boolean validateFullname() {
        if (edtFullname.getText().toString().trim().isEmpty()) {
            tilFullName.setError(getString(R.string.fullname_empty));
            requestFocus(edtFullname);
            return false;
        }

        tilFullName.setErrorEnabled(false);
        return true;
    }

    /**
     * Check email is valid or not
     * @param email email text string
     * @return result valid or invalid
     */
    private static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Focus on specified ediText
     * @param editText editText focused
     */
    private void requestFocus(EditText editText) {
        if (editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Class implimenting TextWatcher interface to validate text after change
     */
    class RegisterTextWatcher implements TextWatcher {

        private EditText editText;

        private RegisterTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (editText.getId()) {
                case R.id.edtFullname:
                    validateFullname();
                    break;
                case R.id.edtEmail:
                    validateEmail();
                    break;
                case R.id.edtPassword:
                    validatePassword();
                    break;
                case R.id.edtRePassword:
                    validateRePassword();
                    break;
            }
        }
    }
}