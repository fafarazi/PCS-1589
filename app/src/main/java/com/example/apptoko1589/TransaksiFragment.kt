package com.example.apptoko1589

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptoko1589.adapter.ProdukAdapter
import com.example.apptoko1589.adapter.TransaksiAdapter
import com.example.apptoko1589.api.BaseRetrofit
import com.example.apptoko1589.response.cart.Cart
import com.example.apptoko1589.response.produk.ProdukResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class TransaksiFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)
        getProduk(view)

        return view
    }

    fun getProduk(view:View){
        val token = LoginActivity.sessionManager.getString("TOKEN")

        api.getProduk(token.toString()).enqueue(object : Callback<ProdukResponse> {
            override fun onResponse(
                call: Call<ProdukResponse>,
                response: Response<ProdukResponse>
            ) {
                Log.d("ProdukData",response.body().toString())

                val rv = view.findViewById(R.id.rv_transaksi) as RecyclerView

                rv.setHasFixedSize(true)
                rv.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = TransaksiAdapter(response.body()!!.data.produk)
                rv.adapter = rvAdapter

                rvAdapter.callbackInterface = object : CallbackInterface{
                    override fun passResultCallback(total: String, cart: ArrayList<Cart>) {
                        val txtTotalBayar = activity?.findViewById<TextView>(R.id.txtTotalBayar)

                        val localeID =  Locale("in", "ID")
                        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

                        txtTotalBayar?.setText(numberFormat.format(total.toDouble()).toString())

                        Log.d("MyCart", cart.toString())
                    }

                }
            }

            override fun onFailure(call: Call<ProdukResponse>, t: Throwable) {
                Log.e("ProdukError",t.toString())
            }

        })
    }


}