package com.truscorp.catsapp.ui.tag_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TagResultsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tag = savedStateHandle.get<String>("tag")
}