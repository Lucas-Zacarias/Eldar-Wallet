package com.eldarwallet.domain.models

sealed class NewCardResult {
    object Error: NewCardResult()

    object Success: NewCardResult()

    object EmptyFields: NewCardResult()

    object OwnerNameInvalid: NewCardResult()

    object OwnerInvalid: NewCardResult()

    object NumberInvalid: NewCardResult()

    object MonthInvalid: NewCardResult()

    object YearInvalid: NewCardResult()

    object CardAlreadyAdded: NewCardResult()

    object CVCLengthInvalid: NewCardResult()

    object CardTypeInvalid: NewCardResult()
}
