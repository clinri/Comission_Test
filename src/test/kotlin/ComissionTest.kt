import org.junit.Assert.assertEquals
import org.junit.Test


class CommissionCalculatorTest {

    @Test
    fun testMastercardCommissionWithinLimits() {
        val result = calculateCommission("Mastercard", 70_000, 10_000, 30_000)
        assertEquals(0, result)
    }

    @Test
    fun testMastercardCommissionOutsideLimits() {
        val result = calculateCommission("Mastercard", 76_000, 10_000, 80_000)
        assertEquals((80_000 * 0.006).toInt() + 20, result)
    }

    @Test
    fun testVisaCommission() {
        val result = calculateCommission("Visa", 500_000, 10_000, 50_000)
        assertEquals(maxOf((50_000 * 0.0075).toInt(), 35), result)
    }

    @Test
    fun testMirCommission() {
        val result = calculateCommission("Мир", 500_000, 10_000, 50_000)
        assertEquals(maxOf((50_000 * 0.0075).toInt(), 35), result)
    }

    @Test
    fun testVkPayCommission() {
        val result = calculateCommission("VK Pay", 10_000, 5_000, 10_000)
        assertEquals(0, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedDailyLimit() {
        calculateCommission("Mastercard", 70_000, 151_000, 160_000)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedMonthlyLimit() {
        calculateCommission("Mastercard", 601_000, 10_000, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedVkPayDailyLimit() {
        calculateCommission("VK Pay", 10_000, 16_000, 20_000)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedVkPayMonthlyLimit() {
        calculateCommission("VK Pay", 41_000, 10_000, 10_000)
    }

    @Test
    fun testMastercardBoundaryDailyLimit() {
        val result = calculateCommission("Mastercard", 70_000, 100_000, 50_000)
        assertEquals(0, result)
    }

    @Test
    fun testVisaBoundaryCommission() {
        val result = calculateCommission("Visa", 100_000, 100_000, 20_000)
        assertEquals(maxOf((20_000 * 0.0075).toInt(), 35), result)
    }

    @Test
    fun testMirExceedMonthlyLimit() {
        calculateCommission("Мир", 599_000, 99_000, 1)
    }

    @Test
    fun testMastercardWithZeroAmount() {
        val result = calculateCommission("Mastercard", 600_000, 0, 0)
        assertEquals(20, result)
    }

    @Test
    fun testVisaWithZeroAmount() {
        val result = calculateCommission("Visa", 600_000, 0, 0)
        assertEquals(35, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidCardType() {
        calculateCommission("InvalidCardType", 10_000, 10_000, 10_000)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedingDailyLimit() {
        calculateCommission("Mastercard", 70_000, 200_000, 150_000)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testExceedingMonthlyLimit() {
        calculateCommission("Mastercard", 600_000, 10_000, 500_000)
    }

    @Test
    fun testVkPayCommissionExceedsDailyLimit() {
        val result = calculateCommission("VK Pay", 15_000, 10_000, 4_000)
        assertEquals(0, result)
    }
}
