package com.eldarwallet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eldarwallet.R
import com.eldarwallet.databinding.ItemCardBinding
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.models.CardType
import com.eldarwallet.domain.usecases.EncryptionUseCase

class CardsAdapter(
    private val cardList: List<Card>,
    private val cardClickListener: (ByteArray) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CardViewHolder((cardBinding))
    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        val cardType = EncryptionUseCase.decrypt(card.type)
        val cardNumber = EncryptionUseCase.decrypt(card.number)

        holder.binding.ivCard.setImageResource(
            when (cardType) {
                CardType.VISA.toString() -> R.drawable.visa
                CardType.AMERICAN_EXPRESS.toString() -> R.drawable.american_express
                CardType.MASTERCARD.toString() -> R.drawable.mastercard
                else -> R.drawable.blank_card
            }
        )

        holder.binding.tvCardNumber.text =
            if (cardNumber.length % 4 == 0)
                "**** **** **** ${cardNumber.takeLast(4)}"
            else "**** ****** ${cardNumber.takeLast(5)}"

        holder.binding.cvCardInfo.setOnClickListener {
            cardClickListener(card.number)
        }
    }
}

class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)