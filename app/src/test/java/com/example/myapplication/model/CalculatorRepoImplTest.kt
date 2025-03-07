package com.example.myapplication.model

import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CalculatorRepoImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test add operation`() = runTest {
        val calculator = CalculatorRepoImpl()
        val result = calculator.add(5.0, 3.0)
        assertEquals(8.0, result, 0.0)
    }

    @Test
    fun `test add operation - fail if result does not match expected`() = runTest {

        // ARRANGE
        val calculator = CalculatorRepoImpl()

        // ACT
        val result = calculator.add(5.0, 3.0)

        // ASSERT
        assertNotEquals(7.0, result, 0.0)
    }

    @Test
    fun `test subtract operation`() = runTest {
        val calculator = CalculatorRepoImpl()
        val result = calculator.subtract(10.0, 4.0)
        assertEquals(6.0, result, 0.0)
    }

    @Test
    fun `test multiply operation`() = runTest {
        val calculator = CalculatorRepoImpl()
        val result = calculator.multiply(6.0, 7.0)
        assertEquals(42.0, result, 0.0)
    }

    @Test
    fun `test divide operation`() = runTest {
        val calculator = CalculatorRepoImpl()
        val result = calculator.divide(15.0, 3.0)
        assertEquals(5.0, result, 0.0)
    }

    @Test
    fun `test divide by zero`() = runTest {
        val calculator = CalculatorRepoImpl()
        val result = calculator.divide(10.0, 0.0)
        assertEquals(Double.NaN, result, 0.0)
    }
}