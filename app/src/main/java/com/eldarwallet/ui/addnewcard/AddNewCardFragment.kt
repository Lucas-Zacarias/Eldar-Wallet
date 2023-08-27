package com.eldarwallet.ui.addnewcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eldarwallet.R
import com.eldarwallet.databinding.FragmentAddNewCardBinding
import com.eldarwallet.domain.models.CardInput
import com.eldarwallet.domain.models.CardType
import com.eldarwallet.domain.models.NewCardResult
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCardFragment : Fragment() {
    private var _binding: FragmentAddNewCardBinding? = null
    private val binding get() = _binding!!
    private val addNewCardViewModel: AddNewCardViewModel by viewModels()
    private lateinit var cardImage: ImageView
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var number: EditText
    private lateinit var expirationMonth: EditText
    private lateinit var expirationYear: EditText
    private lateinit var cvc: EditText
    private lateinit var btnAddNewCard: MaterialButton
    private var cardType: CardType = CardType.NOT_DEFINED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViews()
        setListener()
        setCardNumberChangeListener()
        setObservers()
    }

    private fun getViews() {
        cardImage = binding.ivCardImage
        name = binding.etName.editText!!
        surname = binding.etSurname.editText!!
        number = binding.etNumber.editText!!
        expirationMonth = binding.etExpirationMonth.editText!!
        expirationYear = binding.etExpirationYear.editText!!
        cvc = binding.etCVC.editText!!
        btnAddNewCard = binding.mbAddNewCard
    }

    private fun setListener() {
        addNewCard()
    }

    private fun addNewCard() {
        btnAddNewCard.setOnClickListener {
            addNewCardViewModel.validateAddNewCard(
                CardInput(
                    name = name.text.toString(),
                    surname = surname.text.toString(),
                    number = number.text.toString(),
                    month = expirationMonth.text.toString(),
                    year = expirationYear.text.toString(),
                    cvc = cvc.text.toString(),
                    type = cardType
                )
            )
        }
    }

    private fun setObservers() {
        addNewCardViewModel.cardType.observe(viewLifecycleOwner) {
            setCardType(it)
        }

        addNewCardViewModel.addNewCardState.observe(viewLifecycleOwner) { result ->
            showResultAddNewCard(result)
        }
    }

    private fun setCardNumberChangeListener() {
        number.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do something when the text is changed
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before the text is changed
            }

            override fun afterTextChanged(s: Editable?) {
                addNewCardViewModel.setCardType(s.toString())
            }
        })
    }

    private fun setCardType(cardType: CardType) {
        this.cardType = cardType

        when (cardType) {
            CardType.VISA -> cardImage.setImageResource(R.drawable.visa)

            CardType.MASTERCARD -> cardImage.setImageResource(R.drawable.mastercard)

            CardType.AMERICAN_EXPRESS -> cardImage.setImageResource(R.drawable.american_express)

            else -> cardImage.setImageResource(R.drawable.blank_card)
        }
    }

    private fun showResultAddNewCard(result: NewCardResult) {
        when (result) {
            NewCardResult.Success -> { cleanFields() }

            NewCardResult.EmptyFields -> setAlertAddNewCardProblem("Completar todos los campos")

            NewCardResult.OwnerNameInvalid, NewCardResult.OwnerInvalid -> setAlertAddNewCardProblem("Cargar la tarjeta a tu nombre")

            NewCardResult.NumberInvalid, NewCardResult.CardTypeInvalid -> setAlertAddNewCardProblem("Usar un número de tarjeta válido")

            NewCardResult.MonthInvalid -> setAlertAddNewCardProblem("No usar un mes de vencimiento anterior a la fecha actual")

            NewCardResult.YearInvalid -> setAlertAddNewCardProblem("No usar un año de vencimiento anterior a la fecha actual")

            NewCardResult.CVCLengthInvalid -> setAlertAddNewCardProblem("Usar un código de seguridad válido")

            NewCardResult.CardAlreadyAdded -> setAlertAddNewCardProblem("Tarjeta ya cargada")

            NewCardResult.Error  -> setAlertAddNewCardProblem("No pudimos detectar el problema")
        }
    }

    private fun cleanFields() {
        Toast.makeText(requireContext(), "Tarjeta añadida", Toast.LENGTH_LONG).show()
        cardImage.setImageResource(R.drawable.blank_card)
        name.setText("")
        surname.setText("")
        number.setText("")
        expirationMonth.setText("")
        expirationYear.setText("")
        cvc.setText("")
    }

    private fun setAlertAddNewCardProblem(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("No pudimos añadir tu nueva tarjeta")
            .setMessage("Revisá: $message")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }
}