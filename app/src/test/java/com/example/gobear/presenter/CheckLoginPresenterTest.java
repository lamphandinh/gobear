package com.example.gobear.presenter;

import com.example.domain.model.User;
import com.example.gobear.ui.splash.CheckLoginPresenter;
import com.example.gobear.ui.splash.IFirstRouterView;
import com.example.gobear.util.GobearAppCache;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CheckLoginPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    IFirstRouterView routerView;

    @Mock
    GobearAppCache gobearAppCache;

    @InjectMocks
    CheckLoginPresenter cut;

    @Test
    public void shouldMoveToWalkThroughInTheFirstTime() {
        when(gobearAppCache.isFirstTime()).thenReturn(true);
        cut.checkLogin();
        verify(routerView).moveToWalkThrough();
        verify(gobearAppCache).userOpenedApp();
    }

    @Test
    public void shouldMoveToLoginIfNotFirstTimeAndUserNotLoginOrNotRemember() {
        cut.moveBaseOnUser(null);
        verify(routerView).moveToLogin();
    }

    @Test
    public void shouldMoveToMainIfUserLoginedAndRemember() {
        cut.moveBaseOnUser(new User(""));
        verify(routerView).moveToMain();
    }
}
