package com.eldarwallet.ui.addnewcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eldarwallet.ui.theme.EldarWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCardFragment : Fragment() {
    private val addNewCardViewModel: AddNewCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                EldarWalletTheme {
                    AddNewCardScreen(
                        viewModel = addNewCardViewModel
                    )
                }
            }
        }

    }

}