package com.example.gobear.view;

import android.content.Intent;

import com.example.gobear.ui.login.LoginActivity;
import com.example.gobear.ui.main.MainActivity;
import com.example.gobear.ui.splash.SplashActivity;
import com.example.gobear.ui.walkthough.WalkThroughActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SplashActivityTest {

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testMoveToWalkThough() {
        SplashActivity splashActivity = Robolectric.setupActivity(SplashActivity.class);
        splashActivity.moveToWalkThrough();
        Intent expectedIntent = new Intent(splashActivity, WalkThroughActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void testMoveToLogin() {
        SplashActivity splashActivity = Robolectric.setupActivity(SplashActivity.class);
        splashActivity.moveToLogin();
        Intent expectedIntent = new Intent(splashActivity, LoginActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void testMoveToMain() {
        SplashActivity splashActivity = Robolectric.setupActivity(SplashActivity.class);
        splashActivity.moveToMain();
        Intent expectedIntent = new Intent(splashActivity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
