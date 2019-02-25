package com.example.gobear.presenter;

import com.example.domain.interactor.user.task.LastUserUsecase;
import com.example.domain.model.User;
import com.example.gobear.ui.splash.CheckLoginPresenter;
import com.example.gobear.ui.splash.IFirstRouterView;
import com.example.gobear.util.GobearAppCache;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Inject;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CheckLoginPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LastUserUsecase lastUserUsecase;

    @Mock
    IFirstRouterView routerView;

    @Mock
    GobearAppCache gobearAppCache;

    @InjectMocks CheckLoginPresenter cut;

    @Test
    public void shouldMoveToWalkThroughInTheFirstTime() {
        when(gobearAppCache.isFirstTime()).thenReturn(true);
        cut.checkLogin();
        verify(routerView).moveToWalkThrough();
    }

    @Test
    public void shouldMoveToLoginIfNotFirstTimeAndUserNotLoginOrNotRemember() {
        when(gobearAppCache.isFirstTime()).thenReturn(false);
        when(lastUserUsecase.buildUseCaseObservable()).thenReturn(
                Observable.create(new Action1<Emitter<User>>() {
                    @Override
                    public void call(Emitter<User> userEmitter) {
                        userEmitter.onNext(null);
                    }
                }, Emitter.BackpressureMode.LATEST)
        );
        cut.checkLogin();
        verify(routerView).moveToLogin();
    }

    @Test
    public void shouldMoveToMainIfUserLoginedAndRemember() {
        when(gobearAppCache.isFirstTime()).thenReturn(false);
        when(lastUserUsecase.buildUseCaseObservable()).thenReturn(
                Observable.create(new Action1<Emitter<User>>() {
                    @Override
                    public void call(Emitter<User> userEmitter) {
                        userEmitter.onNext(new User("123456"));
                    }
                }, Emitter.BackpressureMode.LATEST)
        );
        cut.checkLogin();
        verify(routerView).moveToMain();
    }
}
