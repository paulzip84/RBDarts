package com.rbdarts.materialyou

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText

fun SemanticsNodeInteractionsProvider.assertVisibleText(text: String) {
    onNodeWithText(text).assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.assertVisibleDescription(description: String) {
    onNodeWithContentDescription(description).assertIsDisplayed()
}
