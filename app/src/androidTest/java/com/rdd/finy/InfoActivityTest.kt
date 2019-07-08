package com.rdd.finy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rdd.finy.activities.InfoActivity

import com.rdd.finy.adapters.WalletsAdapter
import com.rdd.finy.data.Wallet
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@LargeTest
class InfoActivityTest {

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
    fun testIfWalletsContainerShown() {
        onView(withId(R.id.container_wallets_fragment))
                .check(matches(isDisplayed()))
    }

    @Test
    fun setWalletsListToAdapter() {
        val walletsAdapter = WalletsAdapter(infoRule.activity)
        val walletsList = arrayListOf<Wallet>(Wallet(), Wallet(), Wallet())

        walletsAdapter.setupWalletsList(walletsList)
    }

    @Test
    fun tryToCreateEmptyWallet() {
        onView(withId(R.id.btn_menu_create_wallet))
                .perform(click())

        onView(withText("OK"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click())
    }

    @Test
    fun tryToCreateIdealWallet(){
        onView(withId(R.id.btn_menu_create_wallet))
                .perform(click())

        onView(withId(R.id.etxt_setup_wallet_title))
                .perform(typeText("Test"))

        onView(withId(R.id.etxt_setup_wallet_balance))
                .perform(typeText("50"))

        onView(withId(R.id.etxt_setup_wallet_upper_divider))
                .perform(typeText("100"))

        onView(withId(R.id.etxt_setup_wallet_bottom_divider))
                .perform(typeText("10"))

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click())
    }

    @Test
    fun tryToCreateBadWallet(){
        onView(withId(R.id.btn_menu_create_wallet))
                .perform(click())

        onView(withId(R.id.etxt_setup_wallet_title))
                .perform(typeText("Test"))

        onView(withId(R.id.etxt_setup_wallet_balance))
                .perform(typeText("50"))

        onView(withId(R.id.etxt_setup_wallet_upper_divider))
                .perform(typeText("100"))

        onView(withId(R.id.etxt_setup_wallet_bottom_divider))
                .perform(typeText("10"))

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click())
    }


}