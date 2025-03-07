package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.CalculatorRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.toDoubleOrNull

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculatorRepo: CalculatorRepo
) : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            state = state.copy(number1 = state.number1 + number)
        } else {
            state = state.copy(number2 = state.number2 + number)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".")) {
            state = state.copy(number1 = state.number1 + ".")
        } else if (!state.number2.contains(".")) {
            state = state.copy(number2 = state.number2 + ".")
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotEmpty()) {
            state = state.copy(operation = operation)
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                CalculatorOperation.Add -> calculatorRepo.add(number1, number2)
                CalculatorOperation.Subtract -> calculatorRepo.subtract(number1, number2)
                CalculatorOperation.Multiply -> calculatorRepo.multiply(number1, number2)
                CalculatorOperation.Divide -> calculatorRepo.divide(number1, number2)
                null -> return
            }
            state = state.copy(
                number1 = result.toString(),
                number2 = "",
                operation = null
            )
        }
    }

    fun delayedAddition() {
        calculatorRepo.add(1.0, 2.0)
    }

    companion object {
        const val CALCULATOR_DISPLAY_TAG = "calculator_display_tag"
    }
}

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null
)

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data object Decimal : CalculatorAction()
    data object Clear : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    data object Calculate : CalculatorAction()
}

enum class CalculatorOperation {
    Add, Subtract, Multiply, Divide
}