package com.eldarwallet.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eldarwallet.databinding.FragmentGenerateQrBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateQRFragment : Fragment() {
    private var _binding: FragmentGenerateQrBinding? = null
    private val binding get() = _binding!!
    private lateinit var generatedQR: ImageView

    private val generateQRViewModel: GenerateQRViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenerateQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViews()
        setObservables()
        generateQRViewModel.generateQR()
    }

    private fun getViews() {
        generatedQR = binding.ivQRGenerated
    }

    private fun setObservables() {
        generateQRViewModel.qrBitmap.observe(viewLifecycleOwner){
            if(it != null) {
                generatedQR.setImageBitmap(it)
                hideChargingQR()
                showGeneratedQR()
            }else{
                hideGeneratedQR()
                showChargingQR()
            }
        }
        generateQRViewModel.generateQR()
    }

    private fun showChargingQR() {
        binding.clChargingQR.visibility = View.VISIBLE
    }

    private fun hideChargingQR() {
        binding.clChargingQR.visibility = View.GONE
    }

    private fun showGeneratedQR() {
        binding.cvGeneratedQR.visibility = View.VISIBLE
    }

    private fun hideGeneratedQR() {
        binding.cvGeneratedQR.visibility = View.GONE
    }
}