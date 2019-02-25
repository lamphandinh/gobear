package com.example.domain.interactor;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;

public class BaseUnitTest {

    @Mock
    protected BatchExecutor mockBatchExecutor;
    @Mock
    protected PostExecutionThread mockPostExecutionThread;

    protected TestEventBus mTestEventBus;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mTestEventBus = new TestEventBus();
    }

    @After
    public void teardown() throws Exception {
        if (mTestEventBus != null) {
            mTestEventBus.reset();
        }
    }

    protected <T> TestSubscriber<T> subscribeOnTask(BaseTask<T> task) {
        Observable<T> testObservable = task.buildUseCaseObservable();
        TestSubscriber<T> testSubscriber = new TestSubscriber<>();
        testObservable.subscribe(testSubscriber);
        return testSubscriber;
    }

}
