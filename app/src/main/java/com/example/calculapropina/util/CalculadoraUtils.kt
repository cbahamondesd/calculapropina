package com.example.calculapropina.util

object CalculadoraUtils {
    fun calcularPropina(monto: Double, porcentaje: Double): Double {
        return monto * porcentaje
    }

    fun calcularTotal(monto: Double, porcentaje: Double): Double {
        return monto + calcularPropina(monto, porcentaje)
    }
}