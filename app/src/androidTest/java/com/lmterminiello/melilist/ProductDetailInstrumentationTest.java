package com.lmterminiello.melilist;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.lmterminiello.melilist.utils.ElapsedTimeIdlingResource;
import com.lmterminiello.melilist.view.ProductActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class ProductDetailInstrumentationTest {

    private static final String SEARCH_ITEM = "chromecast";
    private static final String SEARCH_ITEM_FAIL = "asdasdasdasdasdasdasd";
    private static final long TIME_OUT = 5000;

    @Rule
    public ActivityTestRule<ProductActivity> activityTestRule =
            new ActivityTestRule<>(ProductActivity.class);


    @Before
    public void resetTimeout() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
    }

    @Test
    public void validateOpenDetail() {
        ViewInteraction toolbar = onView(withId(R.id.toolbar));
        toolbar.perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText(SEARCH_ITEM), pressImeActionButton());
        IdlingPolicies.setMasterPolicyTimeout(TIME_OUT * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(TIME_OUT * 2, TimeUnit.MILLISECONDS);
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(TIME_OUT);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.products_list)).check(matches(isDisplayed()));
        Espresso.unregisterIdlingResources(idlingResource);
        onView(withId(R.id.products_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.titleItem)).check(matches(isDisplayed()));
        Espresso.unregisterIdlingResources(idlingResource);
    }

}