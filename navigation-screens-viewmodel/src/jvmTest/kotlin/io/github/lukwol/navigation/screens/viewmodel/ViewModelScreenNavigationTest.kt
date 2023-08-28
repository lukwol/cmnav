package io.github.lukwol.navigation.screens.viewmodel

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import io.github.lukwol.navigation.screens.viewmodel.components.TestScreenNavigation
import org.junit.Rule
import org.junit.Test

class ViewModelScreenNavigationTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `first screen`(): Unit = with(compose) {
        setContent { TestScreenNavigation() }

        onNode(hasSetTextAction()).run {
            assert(hasText(""))
            performTextInput("Foo")
            assert(hasText("Foo"))
        }
    }

    @Test
    fun `navigate to second screen and update it's state`() {
        with(compose) {
            setContent { TestScreenNavigation() }

            onNodeWithText("Push Second Screen").performClick()
            waitForIdle()

            onNode(hasSetTextAction()).run {
                assert(hasText(""))
                performTextInput("Bar")
                assert(hasText("Bar"))
            }
        }
    }

    @Test
    fun `navigate back and forth`() {
        with(compose) {
            setContent { TestScreenNavigation() }

            onNode(hasSetTextAction()).run {
                assert(hasText(""))
                performTextInput("Foo")
                assert(hasText("Foo"))
            }

            onNodeWithText("Push Second Screen").performClick()
            waitForIdle()

            onNode(hasSetTextAction()).run {
                assert(hasText("Foo"))
                performTextClearance()
                performTextInput("The quick brown fox")
                assert(hasText("The quick brown fox"))
            }

            onNodeWithText("Pop Screen").performClick()
            waitForIdle()

            onNode(hasSetTextAction()).run {
                assert(hasText("Foo"))
                performTextInput("Bar")
                assert(hasText("BarFoo"))
            }

            onNodeWithText("Push Second Screen").performClick()
            waitForIdle()

            onNode(hasSetTextAction()).assert(hasText("BarFoo"))
        }
    }
}
