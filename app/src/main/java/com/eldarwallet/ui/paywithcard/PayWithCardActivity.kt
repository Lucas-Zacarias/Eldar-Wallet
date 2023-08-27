package com.eldarwallet.ui.paywithcard

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.eldarwallet.R
import com.eldarwallet.databinding.ActivityPayWithCardBinding
import com.eldarwallet.domain.models.CardInput
import com.eldarwallet.domain.models.CardType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayWithCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayWithCardBinding
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private lateinit var frontCard: CardView
    private lateinit var backCard: CardView
    private lateinit var btnFlipToCardData: ImageView
    private lateinit var btnFlipToCardImage: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var ivCardImage: ImageView
    private lateinit var ownerData: TextView
    private lateinit var cardNumber: TextView
    private lateinit var expirationDate: TextView
    private lateinit var cvcNumber: TextView

    private val payWithCardViewModel: PayWithCardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPayWithCardBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        getViews()
        setObservable()
        setAnimation()
        setListeners()
    }

    private fun getViews() {
        frontCard = binding.cvCardImage
        backCard = binding.cvCardData
        btnFlipToCardData = binding.ivFlipToCardData
        btnFlipToCardImage = binding.ivFlipToCardImage
        btnBack = binding.ivGoBack
        ivCardImage = binding.ivCardImage
        ownerData = binding.tvCardOwner
        cardNumber = binding.tvCardNumber
        expirationDate = binding.tvCardExpirationDate
        cvcNumber = binding.tvCardCVC
    }

    private fun setObservable() {
        payWithCardViewModel.getCardData(getCardNumberExtra())
        payWithCardViewModel.cardData.observe(this) {
            setCardData(it)
        }
    }

    private fun getCardNumberExtra(): ByteArray {
        val extras = intent.extras!!
        return extras.getByteArray("card")!!
    }

    private fun setCardData(card: CardInput) {
        ivCardImage.setImageResource(
            when (card.type) {
                CardType.VISA -> R.drawable.visa
                CardType.AMERICAN_EXPRESS -> R.drawable.american_express
                CardType.MASTERCARD -> R.drawable.mastercard
                else -> R.drawable.blank_card
            }
        )

        ownerData.text = getString(R.string.owner, card.name, card.surname)

        cardNumber.text = card.number

        expirationDate.text = card.month.plus("/").plus(card.year)

        cvcNumber.text = getString(R.string.cvc_mayus, card.cvc)
    }

    private fun setAnimation() {
        val scale = applicationContext.resources.displayMetrics.density

        frontCard.cameraDistance = 8000 * scale
        backCard.cameraDistance = 8000 * scale

        frontAnim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.card_front_animator
        ) as AnimatorSet

        backAnim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.card_back_animator
        ) as AnimatorSet

        btnFlipToCardData.setOnClickListener {
            frontAnim.setTarget(frontCard)
            backAnim.setTarget(backCard)
            frontAnim.start()
            backAnim.start()
            btnFlipToCardData.isEnabled = false
            btnFlipToCardImage.isEnabled = true
        }

        btnFlipToCardImage.setOnClickListener {
            frontAnim.setTarget(backCard)
            backAnim.setTarget(frontCard)
            backAnim.start()
            frontAnim.start()
            btnFlipToCardImage.isEnabled = false
            btnFlipToCardData.isEnabled = true
        }
    }

    private fun setListeners() {
        goBack()
    }

    private fun goBack() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}