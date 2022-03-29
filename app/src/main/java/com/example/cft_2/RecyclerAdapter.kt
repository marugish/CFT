package com.example.cft_2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_2.Model.CentralBank
import java.lang.String.format
import java.util.*


class RecyclerAdapter(val items: ArrayList<CentralBank>) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    val myArrayOfImages = arrayOf(R.drawable.ic_aud, R.drawable.ic_azn,
        R.drawable.ic_gbp, R.drawable.ic_amd, R.drawable.ic_byn,R.drawable.ic_bgn,
        R.drawable.ic_brl, R.drawable.ic_huf, R.drawable.ic_hkd, R.drawable.ic_dkk,
        R.drawable.ic_usd, R.drawable.ic_eur, R.drawable.ic_inr, R.drawable.ic_kzt,
        R.drawable.ic_cad, R.drawable.ic_kgs, R.drawable.ic_cny, R.drawable.ic_mdl,
        R.drawable.ic_nok, R.drawable.ic_pln, R.drawable.ic_ron, R.drawable.ic_xdr,
        R.drawable.ic_sgd, R.drawable.ic_tjs, R.drawable.ic_try, R.drawable.ic_tmt,
        R.drawable.ic_uzs, R.drawable.ic_uah, R.drawable.ic_czk, R.drawable.ic_sek,
        R.drawable.ic_chf, R.drawable.ic_zar, R.drawable.ic_krw, R.drawable.ic_jpy)


    class CustomViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
        //var itemKode: TextView
        //var itemKategori: TextView
        //var itemIsi: TextView
        //var itemIsi:TextView = itemView.findViewById(R.id.isiPertanyaan)
        var itemCharCode:TextView = itemView.findViewById(R.id.CharCode)
        var itemName:TextView = itemView.findViewById(R.id.Name)
        var itemNominal:TextView = itemView.findViewById(R.id.Nominal)
        var itemRates:TextView = itemView.findViewById(R.id.Rates)
        var itemChange:TextView = itemView.findViewById(R.id.Change)
        val image =  itemView.findViewById<ImageView>(R.id.imageCurrency)


       // init {
            //itemKode = itemView.findViewById(R.id.kodePertanyaan)
            //itemKategori = itemView.findViewById(R.id.kategori)
            //itemIsi = itemView.findViewById(R.id.isiPertanyaan)

            /*itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, DetailPertanyaan::class.java).apply {
                    putExtra("NUMBER", position)
                    putExtra("CODE", itemKode.text)
                    putExtra("CATEGORY", itemKategori.text)
                    putExtra("CONTENT", itemIsi.text)
                }
                context.startActivity(intent)
            }*/
        //}
    }

    override fun getItemCount(): Int {
        return items[0].infovaluteslist.size//kode.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.recycle_view, parent, false)
        return CustomViewHolder(cellForRow)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: CustomViewHolder, i: Int) {
        val item = items[0].infovaluteslist[i]

        holder.itemCharCode.text = item.CharCode
        holder.itemName.text = item.name
        holder.itemNominal.text = "Nominal: " + item.nominal
        holder.itemRates.text = "Rates: " + item.value

        val difference = item.value - item.previous
        holder.itemChange.text = "Change: " + format("%.4f", difference)
        //holder.item.infovaluteslist[]
        holder.image.setImageResource(myArrayOfImages[i])



    }

}

