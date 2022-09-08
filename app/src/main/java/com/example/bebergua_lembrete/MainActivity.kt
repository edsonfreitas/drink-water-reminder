package com.example.bebergua_lembrete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.bebergua_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private  lateinit var btn_calcular: Button
    private  lateinit var txt_resultado_ml: TextView
    private lateinit var ic_redefinir_dados: ImageView

    private  lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private  var resultadoML = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        btn_calcular.setOnClickListener {

            if(edit_peso.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if(edit_idade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            }else{
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalML(peso, idade)
               resultadoML = calcularIngestaoDiaria.ResultadoML()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text = formatar.format(resultadoML)+ " "+ "ml"
            }
        }

        ic_redefinir_dados.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i ->
                    edit_peso.setText("")
                    edit_idade.setText("")
                    txt_resultado_ml.text = ""
                })

            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->

        })
            val dialog = alertDialog.create()
            dialog.show()
        }
    }

    private fun IniciarComponentes(){
        edit_peso = findViewById(R.id.edit_peso)
        edit_idade = findViewById(R.id.edit_idade)
        btn_calcular = findViewById(R.id.btn_calcular)
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
        ic_redefinir_dados = findViewById(R.id.ic_redefinir)
    }
}