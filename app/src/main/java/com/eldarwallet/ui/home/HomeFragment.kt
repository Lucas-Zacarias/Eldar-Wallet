package com.eldarwallet.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.eldarwallet.R
import com.eldarwallet.databinding.FragmentHomeBinding
import com.eldarwallet.domain.usecases.HomeUseCase
import com.eldarwallet.ui.login.LogInActivity
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
}