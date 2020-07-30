package com.thoughtworks.loginexample;

import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        onView(withId(R.id.auto_create_user_btn)).perform(click());
    }

    @Test
    public void should_success_when_name_and_password_are_correct() {
//        MyApplication myApplication = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext();


        onView(withId(R.id.username)).perform(typeText("android"));
        onView(withId(R.id.password)).perform(typeText("123456"));

        onView(withId(R.id.login_btn)).perform(click());

        SystemClock.sleep(2000);
        onView(withText("Login successfully"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_fail_when_name_is_not_correct() {
        onView(withId(R.id.username)).perform(typeText("android11"));
        onView(withId(R.id.password)).perform(typeText("123456"));

        onView(withId(R.id.login_btn)).perform(click());

        onView(withText("Username does not exist"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_fail_when_password_is_not_correct() {
        onView(withId(R.id.username)).perform(typeText("android"));
        onView(withId(R.id.password)).perform(typeText("12345666"));

        onView(withId(R.id.login_btn)).perform(click());

        onView(withText("Password is invalid"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}