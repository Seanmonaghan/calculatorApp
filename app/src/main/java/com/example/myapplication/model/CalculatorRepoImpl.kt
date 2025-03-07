package com.example.myapplication.model

class CalculatorRepoImpl : CalculatorRepo {
    override fun add(a: Double, b: Double): Double {
        return a + b
    }

    override fun subtract(a: Double, b: Double): Double {
        return a - b
    }

    override fun multiply(a: Double, b: Double): Double {
        return a * b
    }

    override fun divide(a: Double, b: Double): Double {
        return if (b == 0.0) Double.NaN else a / b
    }
}