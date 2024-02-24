package com.example.dollarinfo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import com.example.dollarinfo.data.DollarResponse
import com.example.dollarinfo.data.DollarURUResponse
import com.example.dollarinfo.databinding.ActivityMainBinding
import com.example.dollarinfo.service.DollarService
import com.example.dollarinfo.ui.DollarARGActivity
import com.example.dollarinfo.ui.DollarEURActivity
import com.example.dollarinfo.ui.DollarURUActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Collections
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dollarArgentina = 0.0f
    private var dateUpdate: String? = null
    private var dollarUruguay = 0.0f
    private var dollarEUR = 0.0f
    private val dollar = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDollarsValuesARG()
        getDollarValuesURU()
        getDollarValueEUR()
        updateButton()
        deleteEditTextToClick()
        convertMoney()


    }

    private fun convertMoney() {
        binding.convertEEUU.setOnClickListener {
            val value = binding.etEeuuValue.text.toString()
            if (numeroValido(value)) {
                val inputValue = value.toFloat()
                val valorUru = inputValue * dollarUruguay
                binding.etUruValue.setText(roundToTwoDecimals(valorUru))
                val valorARG = inputValue * dollarArgentina
                binding.etArgValue.setText(roundToTwoDecimals(valorARG))
                val valorEUR = inputValue * (dollarArgentina / dollarEUR)
                binding.etEurValue.setText(valorEUR.toString())

            } else {
                Toast.makeText(this, "valores no validos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.convertARG.setOnClickListener {
            val value = binding.etArgValue.text.toString()
            if (numeroValido(value)) {
                val inputValue = value.toFloat()
                val valorUru = (inputValue / dollarArgentina) * dollarUruguay
                binding.etUruValue.setText(roundToTwoDecimals(valorUru))
                val valorEEUU = inputValue / dollarArgentina
                binding.etEeuuValue.setText(roundToTwoDecimals(valorEEUU))
                val valorEUR = inputValue / dollarEUR
                binding.etEurValue.setText(valorEUR.toString())
            } else {
                Toast.makeText(this, "valores no validos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.convertEUR.setOnClickListener {
            val value = binding.etEurValue.text.toString()
            if (numeroValido(value)) {
                val inputValue = value.toFloat()
                val valorEEUU = (dollar / (dollarArgentina / dollarEUR)) * inputValue
                val valorARG = inputValue * dollarEUR
                val valorURU = valorEEUU * dollarUruguay
                binding.etEeuuValue.setText(roundToTwoDecimals(valorEEUU))
                binding.etUruValue.setText(roundToTwoDecimals(valorURU))
                binding.etArgValue.setText(roundToTwoDecimals(valorARG))

            } else {
                Toast.makeText(this, "valores no validos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.convertURU.setOnClickListener {
            val value = binding.etUruValue.text.toString()
            if (numeroValido(value)) {
                val inputValue = value.toFloat()
                val valorEEUU = inputValue / dollarUruguay
                val valorARG = (inputValue / dollarUruguay) * dollarArgentina
                val valorEUR = valorEEUU * (dollarArgentina / dollarEUR)
                binding.etEeuuValue.setText(roundToTwoDecimals(valorEEUU))
                binding.etArgValue.setText(roundToTwoDecimals(valorARG))
                binding.etEurValue.setText(valorEUR.toString())

            } else {
                Toast.makeText(this, "valores no validos", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun deleteEditTextToClick() {
        binding.etEeuuValue.setOnClickListener {
            binding.etEeuuValue.text.clear()
        }

        binding.etArgValue.setOnClickListener {
            binding.etArgValue.text.clear()

        }

        binding.etUruValue.setOnClickListener {
            binding.etUruValue.text.clear()
        }

        binding.etEurValue.setOnClickListener {
            binding.etEurValue.text.clear()
        }
    }

    private fun getDollarValueEUR() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java)
                .getDollarValueEUR("/v1/cotizaciones/eur")
            val dollarEURResponse = call.body()
            withContext(Dispatchers.Main) {
                updateDollarValueEUR(call, dollarEURResponse)
            }
        }
    }

    private fun updateDollarValueEUR(
        call: Response<DollarResponse>,
        dollarEURResponse: DollarResponse?
    ) {
        if (call.isSuccessful) {
            DollarService.dollarEURValue = dollarEURResponse
            dollarEUR = dollarEURResponse!!.saleValue.toFloat()
            setValuesDefault()
        }
    }

    private fun getDollarValuesURU() {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofitUru().create(APIService::class.java).getDollarValueURU("currency/latest")
            val dollarUru = call.body()
            withContext(Dispatchers.Main) {
                updateDollarValueURU(call, dollarUru)
            }

        }
    }

    private fun updateDollarValueURU(
        call: Response<DollarURUResponse>,
        dollarUru: DollarURUResponse?
    ) {
        if (call.isSuccessful) {
            val values = dollarUru?.listDollarUruResponse
            DollarService.listDollarURU = values!!
            val usdDollarUru = values["USD"]
            dollarUruguay = usdDollarUru?.sell!!.toFloat()
            setValuesDefault()
        } else {
            showError()
        }
    }

    private fun updateButton() {
        binding.reset.setOnClickListener {
            getDollarsValuesARG()
            getDollarValuesURU()
            getDollarValueEUR()
        }
    }

    private fun getDollarsValuesARG() {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(APIService::class.java).getDollarValueListARG("/v1/dolares")
            val dollarBlue = call.body()
            withContext(Dispatchers.Main) {
                updateDollarValueARG(call, dollarBlue)
            }
        }

    }

    private fun updateDollarValueARG(
        call: Response<List<DollarResponse>>,
        dollarBlue: List<DollarResponse>?
    ) {
        if (call.isSuccessful) {
            val list: List<DollarResponse>? = dollarBlue
            if (list != null) {
                DollarService.listDollarsARG = Collections.emptyList()
                DollarService.listDollarsARG = list
                dollarArgentina = DollarService.findDollarBlueSale().toFloat()
                dateUpdate = DollarService.finDollarBlueSaleDateUpdate()
                setValuesDefault()
            }

        } else {
            showError()
        }
    }

    private fun showError() {
        Toast.makeText(this, "ROMPIO TODO", Toast.LENGTH_SHORT).show()
    }

    private fun setValuesDefault() {
        binding.etArgValue.setText(dollarArgentina.toString())
        updateDate()
        binding.etUruValue.setText(dollarUruguay.toString())
        binding.etEeuuValue.setText(dollar.toString())
        binding.etEurValue.setText(roundToTwoDecimals((dollarArgentina / dollarEUR)))
    }

    private fun updateDate() {
        if (dateUpdate != null) {
            val date = dateUpdate!!.substring(0, 10)
            val calendar = Calendar.getInstance()
//            val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val formato = SimpleDateFormat("HH:mm", Locale.getDefault())
            val hora = formato.format(calendar.time)
            supportActionBar?.title = "Actualización: $date , $hora hs"
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dolarapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitUru(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cotizaciones-brou-v2-e449.fly.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun validateFloatingNumber(numero: String): Boolean {
        return numero.isNotEmpty() && numero.matches("\\d{1,8}\\.\\d+".toRegex())
    }

    private fun validarNumeroEntero(numero: String): Boolean {
        return numero.isNotEmpty() && numero.matches("[0-9]*".toRegex())
    }

    private fun numeroValido(value: String): Boolean {

        return validarNumeroEntero(value) || validateFloatingNumber(value)

    }

    private fun roundToTwoDecimals(number: Float): String {
        return String.format("%.2f", number)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val date = dateUpdate!!.substring(0, 10)
        val calendar = Calendar.getInstance()
        val formato = SimpleDateFormat("HH:mm", Locale.getDefault())
        val hora = formato.format(calendar.time)
        when (item.itemId) {
            R.id.item_dollarDetail -> {
                val intent = Intent(this, DollarARGActivity::class.java)
                intent.putExtra("DATE", date)
                intent.putExtra("TIME", hora)
                startActivity(intent)
                return true
            }

            R.id.item_uruDetail -> {
                val intent = Intent(this, DollarURUActivity::class.java)
                intent.putExtra("DATE", date)
                intent.putExtra("TIME", hora)
                startActivity(intent)
                return true
            }

            R.id.item_euroDetail -> {
                val intent = Intent(this, DollarEURActivity::class.java)
                intent.putExtra("DATE", date)
                intent.putExtra("TIME", hora)
                startActivity(intent)
                return true
            }

            R.id.item_exit -> {
                showExitConfirmation()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showExitConfirmation() {
        val message = "¿ Quieres salir de la mejor applicación ?"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación de salida")
            .setMessage(message)
            .setPositiveButton("Sí") { _, _ ->
                finishAffinity()
            }
            .setNegativeButton("No", null).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitConfirmation()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}