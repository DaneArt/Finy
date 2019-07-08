package com.rdd.finy

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rdd.finy.activities.InfoActivity
import com.rdd.finy.fragments.WalletsContainerFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@LargeTest
class WalletsContainerTest {

    @get:Rule
    var infoRule: ActivityTestRule<InfoActivity> = ActivityTestRule(InfoActivity::class.java)

    @Before
    fun init() {



    }

    private fun createEmptyWallet() {
        onView(withId(R.id.btn_menu_create_wallet))
            .perform(click())

        onView(withText("OK"))
            .inRoot(isDialog()) // <---
            .check(matches(isDisplayed()))
            .perform(click())
    }

    companion object {

        private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }

    @Test
    fun tryToDeleteCreatedWallet() {
        createEmptyWallet()

        launchFragmentInContainer<WalletsContainerFragment>()

        onView(withRecyclerView(R.id.rv_wallets).atPosition(0))
            .perform(longClick())

        onView(withId(R.id.btn_setup_wallet_delete)).perform(click())
    }
}