package com.rdd.finy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rdd.finy.app.activities.InfoActivity
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
    fun tryToOpenAddMoneyDialog() {
        onView(withId(R.id.btn_show_insert_money_dialog))
            .perform(click())

        onView(withId(R.id.txt_control_for_history))
            .check(matches(withText("From")))
        onView(withId(R.id.txt_control_wallets))
            .check(matches(withText("To wallets")))
    }

    @Test
    fun tryToOpenRemoveMoneyDialog() {
        onView(withId(R.id.btn_show_remove_money_dialog))
            .perform(click())

        onView(withId(R.id.txt_control_for_history))
            .check(matches(withText("To")))
        onView(withId(R.id.txt_control_wallets))
            .check(matches(withText("From")))
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