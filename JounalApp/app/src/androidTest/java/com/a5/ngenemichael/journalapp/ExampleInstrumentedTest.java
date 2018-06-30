package com.a5.ngenemichael.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.a5.ngenemichael.journalapp.Activities.AddJournalActivity;
import com.a5.ngenemichael.journalapp.Activities.MainActivity;
import com.a5.ngenemichael.journalapp.Activities.SingleDisplayActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.a5.ngenemichael.journalapp", appContext.getPackageName());
    }


    @RunWith(AndroidJUnit4.class)
    public class TestMainActivity {
        @Rule
        public ActivityTestRule<AddJournalActivity> activityActivityTestRule = new ActivityTestRule<AddJournalActivity>(AddJournalActivity.class);

    }

    @Test
    public void TestFab(){
         ActivityTestRule<AddJournalActivity> activityActivityTestRule = new ActivityTestRule<AddJournalActivity>(AddJournalActivity.class);

        onView(withId(R.id.gridview)).perform(click());
        onView(withId(R.id.text_mood)).perform(clearText(),typeText("Jou"));
    }



}
