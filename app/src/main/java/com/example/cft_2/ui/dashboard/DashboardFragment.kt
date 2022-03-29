package com.example.cft_2.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cft_2.*
import com.example.cft_2.Model.CentralBank
import com.example.cft_2.Model.Currency
import org.json.JSONException
import org.json.JSONObject


class DashboardFragment : Fragment() {
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
    val myArrayOfValute2=arrayOf("RUB")
    private lateinit var dashboardViewModel: DashboardViewModel
    var item1: String = ""
    var item2: String = ""
    var item3: String = "0.0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val selection: TextView = itemView.findViewById(R.id.ResultString)

        val someTask = MainActivity.SomeTask()
        val someThread = Thread(someTask)
        someThread.start()
        Thread.sleep(1000)
        val jsonString:String = someTask.getCurrentValue()
        parseJson(jsonString)

        val spinner1: Spinner = itemView.findViewById(R.id.spinner1)
        val spinner2: Spinner = itemView.findViewById(R.id.spinner2)
        val editText: EditText = itemView.findViewById(R.id.editText)
        val res: TextView= itemView.findViewById(R.id.ResultString)

        editText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // perform action on key press
                    item3 = editText.text.toString()
                    //Вызов функции по конвертации
                    convertation(res)
                    // clear focus and hide cursor from edit text
                    editText.clearFocus()
                    editText.isCursorVisible = false
                    return true
                }
                return false
            }
        })



        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        val adapter1: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, myArrayOfValute)
        // Определяем разметку для использования при выборе элемента
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapter2: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, myArrayOfValute2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Применяем адаптер к элементу spinner
        spinner1.adapter = adapter1
        spinner2.adapter = adapter2

        val itemSelectedListener1: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Получаем выбранный объект
                    item1 = parent.getItemAtPosition(position) as String
                    //Вызов функции по конвертации
                    convertation(res)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinner1.onItemSelectedListener = itemSelectedListener1

        val itemSelectedListener2: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Получаем выбранный объект
                    item2 = parent.getItemAtPosition(position) as String
                    //Вызов функции по конвертации
                    convertation(res)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinner2.onItemSelectedListener = itemSelectedListener2
    }

    private fun convertation(res:TextView)
    {
        var index:Int = -1
        for(i in 0 until myArrayOfValute.size)
        {
            if (item1 == myArrayOfValute[i])
            {
                index=i
                break
            }
        }
        //преобразовать в число
        val amount: Double = item3.toDouble()
        val sum = amount* valutesList[0].infovaluteslist[index].value
        res.text = java.lang.String.format("%.4f", sum)

    }

    private fun parseJson(jsonString: String) {
        try {

            val obj = JSONObject(jsonString)
            val date = obj.getString("Date")
            val previousDate = obj.getString("PreviousDate")
            val previousURL = obj.getString("PreviousURL")
            val timestamp = obj.getString("PreviousDate")

            val obj_valute = obj.getString("Valute")
            val objValute = JSONObject(obj_valute)

            for (i in 0 until myArrayOfValute.size)
            {
                if (objValute.has(myArrayOfValute[i]))
                {
                    val valute = objValute.getJSONObject(myArrayOfValute[i])

                    val id: String = valute.getString("ID")
                    val numCode: String = valute.getString("NumCode")
                    val CharCode: String = valute.getString("CharCode")
                    val nominal: Int = valute.getInt("Nominal")
                    val name: String = valute.getString("Name")
                    val value: Double = valute.getDouble("Value")
                    val previous: Double = valute.getDouble("Previous")

                    val info = Currency(id, numCode, CharCode, nominal, name, value, previous)
                    infovaluteslist.add(info)
                }
            }

            val cb = CentralBank(date, previousDate, previousURL, timestamp, infovaluteslist)
            valutesList.add(cb)
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }
    }
}
