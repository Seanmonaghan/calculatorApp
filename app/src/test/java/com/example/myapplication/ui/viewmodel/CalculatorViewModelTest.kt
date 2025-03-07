package com.example.myapplication.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.model.CalculatorRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CalculatorViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CalculatorViewModel

    // Can also write as:
    // @RelaxedMockK
    // private lateinit var repository: CalculatorRepo
    private val mockRepository: CalculatorRepo = mockk(relaxed = true)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CalculatorViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `test add operation`() = runTest {
        // ARRANGE
        every { mockRepository.add(a = 5.0, b = 3.0) } returns 8.0


        // ACT
        viewModel.onAction(CalculatorAction.Number(5))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        viewModel.onAction(CalculatorAction.Number(3))
        viewModel.onAction(CalculatorAction.Calculate)

        //ASSERT
        Assert.assertEquals("8.0", viewModel.state.number1)
        verify { mockRepository.add(5.0, 3.0) }
    }

    @Test
    fun `test add operation - fail if result does not match expected`() = runTest {
        every { mockRepository.add(5.0, 3.0) } returns 8.0

        viewModel.onAction(CalculatorAction.Number(5))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        viewModel.onAction(CalculatorAction.Number(3))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertNotEquals("7.0", viewModel.state.number1)
    }

    @Test
    fun `test subtract operation`() = runTest {
        every { mockRepository.subtract(10.0, 4.0) } returns 6.0


        viewModel.onAction(CalculatorAction.Number(10))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
        viewModel.onAction(CalculatorAction.Number(4))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertEquals("6.0", viewModel.state.number1)
    }

    @Test
    fun `test subtract operation - fail if result does not match expected`() = runTest {
        viewModel.onAction(CalculatorAction.Number(10))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
        viewModel.onAction(CalculatorAction.Number(4))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertNotEquals("5.0", viewModel.state.number1)
    }

    @Test
    fun `test multiply operation`() = runTest {
        every { mockRepository.multiply(6.0, 7.0) } returns 42.0

        viewModel.onAction(CalculatorAction.Number(6))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        viewModel.onAction(CalculatorAction.Number(7))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertEquals("42.0", viewModel.state.number1)
    }

    @Test
    fun `test multiply operation - fail if result does not match expected`() = runTest {
        every { mockRepository.multiply(6.0, 7.0) } returns 42.0

        viewModel.onAction(CalculatorAction.Number(6))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        viewModel.onAction(CalculatorAction.Number(7))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertNotEquals("41.0", viewModel.state.number1)
    }



    @Test
    fun `test divide operation`() = runTest {
        every { mockRepository.divide(15.0, 3.0) } returns 5.0

        viewModel.onAction(CalculatorAction.Number(15))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        viewModel.onAction(CalculatorAction.Number(3))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertEquals("5.0", viewModel.state.number1)
    }

    @Test
    fun `test divide operation - fail if result does not match expected`() = runTest {
        every { mockRepository.divide(15.0, 3.0) } returns 5.0

        viewModel.onAction(CalculatorAction.Number(15))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        viewModel.onAction(CalculatorAction.Number(3))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertNotEquals("4.0", viewModel.state.number1)
    }

    @Test
    fun `test divide by zero`() = runTest {
        every { mockRepository.divide(10.0, 0.0) } returns Double.NaN

        viewModel.onAction(CalculatorAction.Number(10))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        viewModel.onAction(CalculatorAction.Number(0))
        viewModel.onAction(CalculatorAction.Calculate)

        Assert.assertEquals("NaN", viewModel.state.number1)
    }

    @Test
    fun `test clear operation`() = runTest {
        every { mockRepository.divide(10.0, 0.0) } returns Double.NaN
        viewModel.onAction(CalculatorAction.Number(5))
        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        viewModel.onAction(CalculatorAction.Number(3))
        viewModel.onAction(CalculatorAction.Clear)

        Assert.assertEquals("", viewModel.state.number1)
        Assert.assertEquals("", viewModel.state.number2)
        Assert.assertEquals(null, viewModel.state.operation)
    }

    @Test
    fun `test decimal operation`() = runTest {
        viewModel.onAction(CalculatorAction.Number(5))
        viewModel.onAction(CalculatorAction.Decimal)
        viewModel.onAction(CalculatorAction.Number(3))

        Assert.assertEquals("5.3", viewModel.state.number1)
    }
}