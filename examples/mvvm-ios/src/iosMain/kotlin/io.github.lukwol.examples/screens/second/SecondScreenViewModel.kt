package io.github.lukwol.examples.screens.second

import io.github.lukwol.viewmodel.ViewModel

class SecondScreenViewModel(
    text: String,
) : ViewModel() {
    val text: String = text.ifEmpty { "No text passed" }
}
