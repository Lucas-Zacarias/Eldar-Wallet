package com.eldarwallet.ui.addnewcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eldarwallet.databinding.FragmentAddNewCardBinding

class AddNewCardFragment : Fragment() {
    private var _binding: FragmentAddNewCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

}