package com.example.apptoko1589.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptoko1589.CallbackInterface
import com.example.apptoko1589.R
import com.example.apptoko1589.response.cart.Cart
import com.example.apptoko1589.response.produk.Produk
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransaksiAdapter(val listProduk: List<Produk>): RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    var callbackInterface: CallbackInterface? = null
    var total : Int = 0
    var cart : ArrayList<Cart> = arrayListOf<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi,parent, false)
        return TransaksiAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaksiAdapter.ViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.txtNamaProduk.text = produk.nama

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        holder.txtHarga.text = numberFormat.format(produk.harga.toDouble()).toString()

        holder.btnPlus.setOnClickListener{
            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value+1

            holder.txtQty.setText(new_value.toString())

            total = total + produk.harga.toString().toInt()

            callbackInterface?.passResultCallback(total.toString(),cart)
        }

        holder.btnMinus.setOnClickListener{
            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value-1

            if(new_value>=0){
                holder.txtQty.setText(new_value.toString())
                total = total - produk.harga.toString().toInt()
            }

            callbackInterface?.passResultCallback(total.toString(),cart)
        }


    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class ViewHolder (itemViem : View) : RecyclerView.ViewHolder(itemViem){
        val txtNamaProduk = itemViem.findViewById(R.id.txtNamaProduk) as TextView
        val txtHarga = itemViem.findViewById(R.id.txtHarga) as TextView
        val txtQty = itemViem.findViewById(R.id.txtQty) as TextView
        val btnPlus = itemViem.findViewById(R.id.btnPlus) as ImageButton
        val btnMinus = itemViem.findViewById(R.id.btnMinus) as ImageButton
    }


}