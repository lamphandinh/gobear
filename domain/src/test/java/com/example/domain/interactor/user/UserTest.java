package com.example.domain.interactor.user;

import com.example.domain.communication.error.UserNonRegisterException;
import com.example.domain.communication.error.WrongPassException;
import com.example.domain.dsextension.Pair;
import com.example.domain.interactor.BaseUnitTest;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.interactor.user.task.GetLastUserTokenTask;
import com.example.domain.interactor.user.task.LastUserUsecase;
import com.example.domain.interactor.user.task.LoadUserTask;
import com.example.domain.interactor.user.task.UserLoginUsecase;
import com.example.domain.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

public class UserTest extends BaseUnitTest {
    private UserLoginUsecase userLoginUsecase;
    private LastUserUsecase lastUserUsecase;
    private GetLastUserTokenTask getLastUserTokenTask;
    private LoadUserTask loadUserTask;

    @Mock
    protected UserRepository mockUserRepo;

    @Before
    public void setup() throws Exception {
        super.setup();
        getLastUserTokenTask = new GetLastUserTokenTask(mockUserRepo, mockBatchExecutor, mockPostExecutionThread);
        loadUserTask = new LoadUserTask(mockUserRepo, mockBatchExecutor, mockPostExecutionThread);
        lastUserUsecase = new LastUserUsecase(getLastUserTokenTask, mockUserRepo, mockBatchExecutor, mockPostExecutionThread);
        userLoginUsecase = new UserLoginUsecase(loadUserTask, mockUserRepo, mockBatchExecutor, mockPostExecutionThread);
    }

    @Test
    public void shouldReturnNullIfThereIsNoLastUser() {
        final String token = "";
        when(mockUserRepo.getLastUserToken()).thenReturn(Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return token;
            }
        }));
        TestSubscriber<User> subscriber = subscribeOnTask(lastUserUsecase);
        subscriber.assertNoErrors();
        User result = subscriber.getOnNextEvents().get(0);
        Assert.assertNull(result);
    }

    @Test
    public void shouldReturnUserIfThereIsValidToken() {
        final String token = "123456";
        when(mockUserRepo.getLastUserToken()).thenReturn(Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return token;
            }
        }));
        when(mockUserRepo.getUserByToken(token)).thenReturn(Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return new User("");
            }
        }));
        TestSubscriber<User> subscriber = subscribeOnTask(lastUserUsecase);
        subscriber.assertNoErrors();
        User result = subscriber.getOnNextEvents().get(0);
        Assert.assertNotNull(result);
    }

    @Test
    public void shouldReturnNullIfThereIsInValidToken() {
        final String token = "123456";
        when(mockUserRepo.getLastUserToken()).thenReturn(Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return token;
            }
        }));
        when(mockUserRepo.getUserByToken(token)).thenReturn(Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return null;
            }
        }));
        TestSubscriber<User> subscriber = subscribeOnTask(lastUserUsecase);
        subscriber.assertNoErrors();
        User result = subscriber.getOnNextEvents().get(0);
        Assert.assertNull(result);
    }

    @Test
    public void shouldValidateUserName() {
        userLoginUsecase.setUserData("", "123456");
        TestSubscriber<User> subscriber = subscribeOnTask(userLoginUsecase);
        subscriber.assertError(IllegalArgumentException.class);
    }

    @Test
    public void shouldValidatePassword() {
        userLoginUsecase.setUserData("abc", "");
        TestSubscriber<User> subscriber = subscribeOnTask(userLoginUsecase);
        subscriber.assertError(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowErrorIfNotMatchPassHash() {
        final String name = "abc";
        final String pass = "123456";
        final String token = "fake_hash";
        when(mockUserRepo.generateHashFromPassword(pass)).thenReturn(Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return token;
            }
        }));
        when(mockUserRepo.getUserByName(name)).thenReturn(Observable.fromCallable(new Callable<Pair<User, String>>() {
            @Override
            public Pair<User, String> call() throws Exception {
                return new Pair<>(new User(""), "xxxxxx");
            }
        }));
        userLoginUsecase.setUserData(name, pass);
        TestSubscriber<User> subscriber = subscribeOnTask(userLoginUsecase);
        subscriber.assertError(WrongPassException.class);
    }

    @Test
    public void shouldThrowErrorIfNotFoundUser() {
        String name = "abc";
        when(mockUserRepo.getUserByName(name)).thenReturn(Observable.fromCallable(new Callable<Pair<User, String>>() {
            @Override
            public Pair<User, String> call() throws Exception {
                return null;
            }
        }));
        userLoginUsecase.setUserData(name, "123456");
        TestSubscriber<User> subscriber = subscribeOnTask(userLoginUsecase);
        subscriber.assertError(UserNonRegisterException.class);
    }

    @Test
    public void shouldReturnCorrectUser() {
        final String name = "abc";
        final String pass = "123456";
        final String token = "fake_hash";
        when(mockUserRepo.generateHashFromPassword(pass)).thenReturn(Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return token;
            }
        }));
        when(mockUserRepo.getUserByName(name)).thenReturn(Observable.fromCallable(new Callable<Pair<User, String>>() {
            @Override
            public Pair<User, String> call() throws Exception {
                return new Pair<>(new User(name), token);
            }
        }));
        userLoginUsecase.setUserData(name, pass);
        TestSubscriber<User> subscriber = subscribeOnTask(userLoginUsecase);
        subscriber.assertNoErrors();
        User result = subscriber.getOnNextEvents().get(0);
        Assert.assertEquals(name, result.getName());
    }
}
