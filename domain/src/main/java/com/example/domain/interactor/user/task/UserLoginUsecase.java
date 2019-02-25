package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;

public class UserLoginUsecase extends BaseTask<User> {
    private GetLastUserTokenTask getLastUserTokenTask;
    private LoadUserTask loadUserTask;
    private String userName, userPassword;

    @Inject
    public UserLoginUsecase(GetLastUserTokenTask getLastUserTokenTask, LoadUserTask loadUserTask,
                            BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.getLastUserTokenTask = getLastUserTokenTask;
        this.loadUserTask = loadUserTask;
    }

    public void setUserData(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return Observable.just(null);
    }
}
