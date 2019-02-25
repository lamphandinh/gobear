package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;

public class UserUsecase extends BaseTask<User> {
    private GetLastUserTask getLastUserTask;
    private LoadUserTask loadUserTask;

    @Inject
    protected UserUsecase(GetLastUserTask getLastUserTask, LoadUserTask loadUserTask,
                          BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.getLastUserTask = getLastUserTask;
        this.loadUserTask = loadUserTask;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return null;
    }
}
