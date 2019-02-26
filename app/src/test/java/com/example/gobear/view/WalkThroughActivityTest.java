package com.example.gobear.view;

import android.content.Intent;
import android.widget.TextView;

import com.example.gobear.R;
import com.example.gobear.ui.login.LoginActivity;
import com.example.gobear.ui.walkthough.WalkThroughActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class WalkThroughActivityTest {

    @Test
    public void testMoveToWalkThough() {
        WalkThroughActivity walkThroughActivity = Robolectric.setupActivity(WalkThroughActivity.class);
        TextView skip = (TextView) walkThroughActivity.findViewById(R.id.btn_skip);
        skip.performClick();
        Intent expectedIntent = new Intent(walkThroughActivity, LoginActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }


}
