package com.rdd.finy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rdd.finy.activities.InfoActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@LargeTest
class ControlMoneyTest {

    @get:Rule
    var infoRule: ActivityTestRule<InfoActivity> = ActivityTestRule(InfoActivity::class.java)

    @Test
    fun tryToOpenInsertDialog(){
        onView(withId(R.id.btn_show_insert_money_dialog))
            .perform(click())

        onView(withText("To wallets"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tryToOpenRemoveDialog(){
        onView(withId(R.id.btn_show_remove_money_dialog))
            .perform(click())

        onView(withText("From"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tryToOpenWalletsList(){
        onView(withId(R.id.btn_show_insert_money_dialog))
                .perform(click())

        onView(withId(R.id.btn_control_wallets))
                .perform(click())

        onView(withText(R.string.control_wallets_title))
                .check(matches(isDisplayed()))
    }
}