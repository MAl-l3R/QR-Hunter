package com.example.snailscurlup;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Testing we can actually creating an account
     * @throws Exception
     * */
    @Test
    public void canCreatAccount() throws Exception {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.create_account_button)); //Click create_account_button
        //Get view for textfields and enter a user name,email,phonenumber
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1234");
        solo.enterText((EditText) solo.getView(R.id.Email_field), "1234@ua.ca");
        solo.enterText((EditText) solo.getView(R.id.phonenumber_field), "1234");
        solo.clickOnView(solo.getView(R.id.create_account_button)); //Click create_account_button

        /* TODO: Idk why this failed I googled waitForText() should be able to detect toast message
        /* True if there is a text: Account created successfully on the screen, wait at least 2 seconds and
       find minimum one match. */
        assertTrue(solo.waitForText("Account created successfully", 1, 2000));

    }


    /**
     * Testing we can actually login with an exist account
     * @throws Exception
     * */
    @Test
    public void canLogin() throws Exception {

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        /*TODO: The login process goes weird might because user can only see part of the button? Since if I manually "helping" the click it will pass*/
        //login process
        solo.clickOnView(solo.getView(R.id.login_account_button));
        solo.enterText((EditText) solo.getView(R.id.username_field), "user1234");
        solo.clickOnView(solo.getView(R.id.login_account_button));

        //should goto the profile page
        assertTrue(solo.waitForText("My Profile", 1, 2000));

    }










    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
