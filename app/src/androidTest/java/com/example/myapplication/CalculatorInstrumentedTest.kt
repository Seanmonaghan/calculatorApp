package com.example.myapplication

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.ui.viewmodel.CalculatorViewModel.Companion.CALCULATOR_DISPLAY_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CalculatorInstrumentedTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddOperation() {
        // ARRANGE

        // ACT
        // Click on the number 5
        composeTestRule.onNodeWithText("5").performClick()

        // Click on the add operation
        composeTestRule.onNodeWithText("+").performClick()

        // Click on the number 3
        composeTestRule.onNodeWithText("3").performClick()

        // Click on the equals button
        composeTestRule.onNodeWithText("=").performClick()

        // ASSERT

        // Check if the result is 8
        composeTestRule.onNodeWithTag(CALCULATOR_DISPLAY_TAG).assert(hasText("8.0  "))
    }

    @Test
    fun testSubtractOperation() {
        // Click on the number 10
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()

        // Click on the subtract operation
        composeTestRule.onNodeWithText("-").performClick()

        // Click on the number 4
        composeTestRule.onNodeWithText("4").performClick()

        // Click on the equals button
        composeTestRule.onNodeWithText("=").performClick()

        // Check if the result is 6
        composeTestRule.onNodeWithText("6.0  ").assertExists()
    }

    @Test
    fun testMultiplyOperation() {
        // Click on the number 6
        composeTestRule.onNodeWithText("6").performClick()

        // Click on the multiply operation
        composeTestRule.onNodeWithText("*").performClick()

        // Click on the number 7
        composeTestRule.onNodeWithText("7").performClick()

        // Click on the equals button
        composeTestRule.onNodeWithText("=").performClick()

        // Check if the result is 42
        composeTestRule.onNodeWithText("42.0  ").assertExists()
    }

    @Test
    fun testDivideOperation() {
        // Click on the number 15
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        // Click on the divide operation
        composeTestRule.onNodeWithText("/").performClick()

        // Click on the number 3
        composeTestRule.onNodeWithText("3").performClick()

        // Click on the equals button
        composeTestRule.onNodeWithText("=").performClick()

        // Check if the result is 5
        composeTestRule.onNodeWithText("5.0  ").assertExists()
    }

    @Test
    fun testDivideByZero() {
        // Click on the number 10
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()

        // Click on the divide operation
        composeTestRule.onNodeWithText("/").performClick()

        // Click on the number 0
        composeTestRule.onNodeWithText("0").performClick()

        // Click on the equals button
        composeTestRule.onNodeWithText("=").performClick()

        // Check if the result is NaN
        composeTestRule.onNodeWithText("NaN  ").assertExists()
    }
}