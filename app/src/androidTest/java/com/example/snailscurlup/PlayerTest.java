package com.example.snailscurlup;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Random;

public class PlayerTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Sets up the Solo object for the intent tests at beginning of execution.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Fetches the current activity.
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    //TODO: wait for the actual implementation
    /**
     * checks if the player can Add and Remove QR code
     * @throws AssertionError - For when test fails
     */
    public void checkAddRemoveQR(){
        // Assertion for current activity being the main one
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    //TODO: wait for the actual implementation
    /**
     *  checks if the player can check other player's profile
     *  @throws AssertionError - For when test fails
     */
    public void checkGoToOtherProfile(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * Cleans up all intent testing objects after execution completes.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}