package com.example.bebergua_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.renderscript.ScriptGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.bebergua_lembrete.databinding.ActivityMainBinding
import com.example.bebergua_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private  lateinit var btn_calcular: Button
    private  lateinit var txt_resultado_ml: TextView
    private lateinit var ic_redefinir_dados: ImageView
    private lateinit var btn_lembrete: Button
    private  lateinit var  btn_alarme: Button
    private lateinit var txt_hora: TextView
    private  lateinit var txt_minuto: TextView

    private  lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private  var resultadoML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
       //IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()
        //val btn_calcular = binding.btnCalcular

        binding.btnCalcular.setOnClickListener {

            if(binding.editPeso.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if(binding.editIdade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            }else{
                //com o binding
                val peso = binding.editPeso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()

                // Sem o binding
                //val peso = edit_peso.text.toString().toDouble()
                //val idade = edit_idade.text.toString().toInt()

                calcularIngestaoDiaria.CalcularTotalML(peso, idade)
               resultadoML = calcularIngestaoDiaria.ResultadoML()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                binding.txtResultadoMl.text = formatar.format(resultadoML)+ " "+ "ml"
            }
        }

            binding.icRedefinir.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i ->
                    binding.editPeso.setText("")
                    binding.editIdade.setText("")
                    binding.txtResultadoMl.text = ""

                })

            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->

        })
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.btnDefinirLembrete.setOnClickListener {

            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtual = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d",hourOfDay)
                binding.txtMinuto.text = String.format("%02d", minutes)
            }, horaAtual, minutosAtual, true )
            timePickerDialog.show()
        }

        binding.btnDefinirAlarme.setOnClickListener{

            if(!binding.txtHora.text.toString().isEmpty() && !binding.txtMinuto.text.toString().isEmpty()){
                 val  intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.txtHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, binding.txtMinuto.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }

            }
        }

    }


   /* private fun IniciarComponentes(){
        edit_peso = findViewById(R.id.edit_peso)
        edit_idade = findViewById(R.id.edit_idade)
        btn_calcular = findViewById(R.id.btn_calcular)
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
        ic_redefinir_dados = findViewById(R.id.ic_redefinir)
        btn_lembrete = findViewById(R.id.btn_definir_lembrete)
        btn_alarme = findViewById(R.id.btn_definir_alarme)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minuto = findViewById(R.id.txt_minuto)
    }*/
}