package com.example.calculapropina

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.example.calculapropina.util.CalculadoraUtils

class MainActivity : AppCompatActivity() {

    private lateinit var edtMonto: EditText
    private lateinit var rgPorcentaje: RadioGroup
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpiar: Button
    private lateinit var tvPropina: TextView
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtMonto = findViewById(R.id.edtMonto)
        rgPorcentaje = findViewById(R.id.rgPorcentaje)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotal = findViewById(R.id.tvTotal)

        btnCalcular.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
            calcularPropina()
            deshabilitarCampos(false)
        }

        btnLimpiar.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
            limpiarCampos()
            deshabilitarCampos(true)
        }
    }

    private fun deshabilitarCampos(valor: Boolean) {
        edtMonto.isEnabled = valor
        rgPorcentaje.isEnabled = valor

        // (Des)habilita los RadioButtons individualmente
        for (i in 0 until rgPorcentaje.childCount) {
            val radioButton = rgPorcentaje.getChildAt(i)
            radioButton.isEnabled = valor
        }
    }

    private fun calcularPropina() {
        val montoTexto = edtMonto.text.toString()
        if (montoTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese monto para aplicar propina  ", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoTexto.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val porcentaje = when (rgPorcentaje.checkedRadioButtonId) {
            R.id.rb10 -> 0.10
            R.id.rb15 -> 0.15
            R.id.rb20 -> 0.20
            else -> {
                Toast.makeText(this, "Debe seleccionar porcentaje", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val propina = CalculadoraUtils.calcularPropina(monto, porcentaje)
        val total = CalculadoraUtils.calcularTotal(monto, porcentaje)

        tvPropina.text = "Total Propina: $%.2f".format(propina)
        tvTotal.text = "Total + propina: $%.2f".format(total)
    }

    private fun limpiarCampos() {
        edtMonto.text.clear()
        rgPorcentaje.clearCheck()
        tvPropina.text = "Total Propina: $0.00"
        tvTotal.text = "Total + propina: $0.00"
    }
}
