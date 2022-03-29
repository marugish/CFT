package com.example.cft_2.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_2.*
import com.example.cft_2.Model.CentralBank
import com.example.cft_2.Model.Currency
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val someTask = MainActivity.SomeTask()
        val someThread = Thread(someTask)
        someThread.start()
        Thread.sleep(1000)
        val jsonString:String = someTask.getCurrentValue()
        parseJson(jsonString)

        card_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter(valutesList)
        }
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
