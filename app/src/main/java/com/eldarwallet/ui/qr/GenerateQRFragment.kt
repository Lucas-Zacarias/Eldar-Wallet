package com.eldarwallet.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eldarwallet.databinding.FragmentGenerateQrBinding

class GenerateQRFragment : Fragment() {
    private var _binding: FragmentGenerateQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenerateQrBinding.inflate(inflater, container, false)
        return binding.root
    }
}