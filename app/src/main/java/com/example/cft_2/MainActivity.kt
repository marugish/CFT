package com.example.cft_2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cft_2.Model.CentralBank
import com.example.cft_2.Model.Currency
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection


val valutesList: ArrayList<CentralBank> = ArrayList()
val infovaluteslist: ArrayList<Currency> = ArrayList()
val myArrayOfValute = arrayOf(
    "AUD",
    "AZN",
    "GBP",
    "AMD",
    "BYN",
    "BGN",
    "BRL",
    "HUF",
    "HKD",
    "DKK",
    "USD",
    "EUR",
    "INR",
    "KZT",
    "CAD",
    "KGS",
    "CNY",
    "MDL",
    "NOK",
    "PLN",
    "RON",
    "XDR",
    "SGD",
    "TJS",
    "TRY",
    "TMT",
    "UZS",
    "UAH",
    "CZK",
    "SEK",
    "CHF",
    "ZAR",
    "KRW",
    "JPY"
)



class MainActivity : AppCompatActivity() {

    class SomeTask : Runnable {
        private var currentValue = ""
        fun getContent(path: String): String {
            var connection: HttpsURLConnection? = null
            try {
                val url = URL(path)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.readTimeout = 10000
                connection.connect()
                val json = connection.inputStream.bufferedReader().readText()
                return json
            } catch (ex: IOException){
                connection?.disconnect()
                return ""
            }
        }
        fun getCurrentValue(): String {
            return currentValue
        }
        override fun run() {
            try {
                while (true) {
                    currentValue = getContent("https://www.cbr-xml-daily.ru/daily_json.js")
                    if (currentValue != "") {
                        break
                    } else {
                        Thread.sleep(500)
                    }
                }
            } catch (e: InterruptedException) {
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}
