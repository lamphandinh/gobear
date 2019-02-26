package com.example.gobear.presenter;

import com.example.domain.model.User;
import com.example.gobear.ui.login.ILoginView;
import com.example.gobear.ui.login.LoginPresenter;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    ILoginView mockLoginView;

    @InjectMocks LoginPresenter cut;

    @Test
    public void testHandleLoginError() {
        String expected = "xxx";
        cut.handleLoginError(new IllegalArgumentException("xxx"));
        verify(mockLoginView).loginFailed(expected);
    }

    @Test
    public void testHandleLoginSuccess() {
        User user = new User("");
        cut.handleLoginSuccess(user);
        verify(mockLoginView).loginSuccess(user);
    }

}
