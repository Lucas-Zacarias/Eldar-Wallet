package com.eldarwallet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eldarwallet.R
import com.eldarwallet.databinding.FragmentHomeBinding
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.usecases.HomeUseCase
import com.eldarwallet.ui.adapter.CardsAdapter
import com.eldarwallet.ui.login.LogInActivity
import com.eldarwallet.ui.paywithcard.PayWithCardActivity
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInitials: TextView
    private lateinit var userGreeting: TextView
    private lateinit var btnClose: ImageView
    private lateinit var btnLogOut: MaterialButton
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var cardRV: RecyclerView

    @Inject
    lateinit var homeUseCase: HomeUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViews()
        setViews()
        setObservables()
        setListeners()
    }

    private fun getViews() {
        userInitials = binding.tvUserInitials
        userGreeting = binding.tvUserGreeting
        btnClose = binding.ivClose
        btnLogOut = binding.mbLogOut
    }

    private fun setViews() {
        setUserInitials()
        setUserGreeting()
    }

    private fun setObservables() {
        homeViewModel.getCardList()

        homeViewModel.cardList.observe(viewLifecycleOwner){
            if(it.isNotEmpty()) {
                setUpRecyclerCards(it)
                hideNoCards()
                showRVCards()
            }else{
                showNoCards()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerCards(cards: List<Card>) {
        cardRV = binding.rvCards
        cardRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = CardsAdapter(cards){cardNumber -> goToPay(cardNumber)}
        cardRV.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun goToPay(cardNumber: ByteArray) {
        val intent = Intent(requireContext(), PayWithCardActivity()::class.java)
        intent.putExtra("card", cardNumber)

        startActivity(intent)
    }


    private fun setUserInitials() {
        userInitials.text = homeUseCase.getUserInitials()
    }

    private fun setUserGreeting() {
        userGreeting.text =
            getString(R.string.hello).plus(homeUseCase.getUserName() ?: "Eldar").plus("!")
    }

    private fun setListeners() {
        openWindowToLogOut()
        closeWindowToLogOut()
        logOut()
    }

    private fun openWindowToLogOut() {
        userInitials.setOnClickListener {
            if(binding.clLogOut.isVisible) {
                binding.clLogOut.visibility = View.GONE
            }else{
                binding.clLogOut.visibility = View.VISIBLE
            }
        }
    }

    private fun closeWindowToLogOut() {
        btnClose.setOnClickListener {
            binding.clLogOut.visibility = View.GONE
        }
    }

    private fun logOut() {
        btnLogOut.setOnClickListener {
            homeUseCase.logOut()
            startActivity(Intent(activity, LogInActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun hideNoCards() {
        binding.clNoCardsAdded.visibility = View.GONE
    }

    private fun showNoCards() {
        binding.clNoCardsAdded.visibility = View.VISIBLE
    }

    private fun showRVCards() {
        binding.rvCards.visibility = View.VISIBLE
    }
}