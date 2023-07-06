package io.github.lukwol.examples.screens.second

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.lukwol.cmnav.screens.vm.ViewModel

class SecondScreenViewModel(text: String?) : ViewModel() {

    var state by mutableStateOf(
        SecondScreenViewState(text = text.orEmpty().ifEmpty { "No text passed" }),
    )
        private set

    @Suppress("UNUSED_PARAMETER")
    fun onCommand(command: SecondScreenCommand) {}
}
