fun calculateCommission(
    cardType: String,
    monthlyTransfers: Int,
    dailyTransfers: Int,
    transferAmount: Int
): Int {
    val dailyLimit = 150_000
    val monthlyLimit = 600_000
    val vkDailyLimit = 15_000
    val vkMonthlyLimit = 40_000
    val mastercardMaestroMonthlyLimit = 75_000

    if (transferAmount > dailyLimit) {
        throw IllegalArgumentException("Сумма перевода превышает дневной лимит")
    }
    if (transferAmount + dailyTransfers > dailyLimit) {
        throw IllegalArgumentException("Сумма перевода превышает дневной лимит")
    }
    if (monthlyTransfers + transferAmount > monthlyLimit) {
        throw IllegalArgumentException("Сумма перевода превышает месячный лимит")
    }
    if (cardType == "VK Pay") {
        if (transferAmount > vkDailyLimit) {
            throw IllegalArgumentException("Сумма перевода превышает дневной лимит для VK Pay")
        }
        if (monthlyTransfers + transferAmount > vkMonthlyLimit) {
            throw IllegalArgumentException("Сумма перевода превышает месячный лимит для VK Pay")
        }
    }

    return when (cardType) {
        "Mastercard", "Maestro" -> {
            if (transferAmount >= 300 && monthlyTransfers <= mastercardMaestroMonthlyLimit) {
                0
            } else {
                (transferAmount * 0.006).toInt() + 20
            }
        }
        "Visa", "Мир" -> {
            val commission = (transferAmount * 0.0075).toInt()
            if (commission < 35) 35 else commission
        }
        "VK Pay" -> 0
        else -> throw IllegalArgumentException("Неизвестный тип карты")
    }
}


fun main() {
    val cardType = "Mastercard"
    val previousMonthlyTransfers = 70_000
    val previousDailyTransfers = 0
    val transferAmount = 4_000

    try {
        val commission = calculateCommission(cardType, previousMonthlyTransfers, previousDailyTransfers, transferAmount)
        println("Комиссия составит: $commission руб.")
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}
