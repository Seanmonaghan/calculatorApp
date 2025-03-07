package com.example.myapplication.ui.compose

import android.provider.Telephony.Mms.Addr
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.viewmodel.CalculatorAction
import com.example.myapplication.ui.viewmodel.CalculatorOperation
import com.example.myapplication.ui.viewmodel.CalculatorState
import com.example.myapplication.ui.viewmodel.CalculatorViewModel.Companion.CALCULATOR_DISPLAY_TAG

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "${state.number1} ${state.operation?.let { getOperationSymbol(it) } ?: ""} ${state.number2}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
                .testTag(CALCULATOR_DISPLAY_TAG),
            textAlign = TextAlign.End,
            fontSize = 48.sp,
            color = Color.Black
        )
        CalculatorButtons(onAction = onAction)
    }
}

@Composable
fun CalculatorButtons(onAction: (CalculatorAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAction(CalculatorAction.Clear) }) {
                Text("C")
            }
            Button(onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }) {
                Text("/")
            }
            Button(onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }) {
                Text("*")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAction(CalculatorAction.Number(7)) }) {
                Text("7")
            }
            Button(onClick = { onAction(CalculatorAction.Number(8)) }) {
                Text("8")
            }
            Button(onClick = { onAction(CalculatorAction.Number(9)) }) {
                Text("9")
            }
            Button(onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }) {
                Text("-")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAction(CalculatorAction.Number(4)) }) {
                Text("4")
            }
            Button(onClick = { onAction(CalculatorAction.Number(5)) }) {
                Text("5")
            }
            Button(onClick = { onAction(CalculatorAction.Number(6)) }) {
                Text("6")
            }
            Button(onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }) {
                Text("+")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAction(CalculatorAction.Number(1)) }) {
                Text("1")
            }
            Button(onClick = { onAction(CalculatorAction.Number(2)) }) {
                Text("2")
            }
            Button(onClick = { onAction(CalculatorAction.Number(3)) }) {
                Text("3")
            }
            Button(onClick = { onAction(CalculatorAction.Calculate) }) {
                Text("=")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAction(CalculatorAction.Number(0)) }) {
                Text("0")
            }
            Button(onClick = { onAction(CalculatorAction.Decimal) }) {
                Text(".")
            }
        }
    }
}

fun getOperationSymbol(operation: CalculatorOperation): String {
    return when (operation) {
        CalculatorOperation.Add -> "+"
        CalculatorOperation.Subtract -> "-"
        CalculatorOperation.Multiply -> "*"
        CalculatorOperation.Divide -> "/"
    }
}