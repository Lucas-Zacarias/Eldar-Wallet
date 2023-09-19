package com.eldarwallet.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eldarwallet.domain.usecases.HomeUseCase
import com.eldarwallet.ui.login.LogInActivity
import com.eldarwallet.ui.paywithcard.PayWithCardActivity
import com.eldarwallet.ui.screens.HomeScreen
import com.eldarwallet.ui.theme.EldarWalletTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var homeUseCase: HomeUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                EldarWalletTheme {
                    HomeScreen(
                        userInitials = homeUseCase.getUserInitials(),
                        userName = homeUseCase.getUserName(),
                        viewModel = homeViewModel,
                        signOffEvent = {
                            logOut()
                        },
                        goToPayEvent = {
                            goToPay(it)
                        }
                    )
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getCardList()
    }

    private fun goToPay(cardNumber: ByteArray) {
        val intent = Intent(requireContext(), PayWithCardActivity()::class.java)
        intent.putExtra("card", cardNumber)

        startActivity(intent)
    }

    private fun logOut() {
        homeUseCase.logOut()
        startActivity(Intent(activity, LogInActivity::class.java))
        requireActivity().finish()
    }

}