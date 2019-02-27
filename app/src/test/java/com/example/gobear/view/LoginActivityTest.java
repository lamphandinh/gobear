package com.example.gobear.view;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.domain.model.User;
import com.example.gobear.R;
import com.example.gobear.ui.login.LoginActivity;
import com.example.gobear.ui.login.LoginPresenter;
import com.example.gobear.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    @Mock
    LoginPresenter mockLoginPresenter;

    LoginActivity loginActivity;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        loginActivity = Robolectric.setupActivity(LoginActivity.class);
        loginActivity.setPresenter(mockLoginPresenter);
    }

    @Test
    public void testCallLogin() {
        String expectUserName = "abc";
        String expectPass = "123";

        EditText userNameEdt = loginActivity.findViewById(R.id.etUsername);
        userNameEdt.setText("abc");
        EditText passEdt = loginActivity.findViewById(R.id.etPwd);
        passEdt.setText("123");
        CheckBox remember = (CheckBox) loginActivity.findViewById(R.id.cb_remember_me);
        remember.setChecked(true);
        Button loginBtn = loginActivity.findViewById(R.id.btn_sign_in);
        loginBtn.performClick();
        verify(mockLoginPresenter).login(expectUserName, expectPass, true);
    }

    @Test
    public void testLoginSuccess() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        loginActivity.loginSuccess(new User(""));
        Intent expectedIntent = new Intent(loginActivity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
