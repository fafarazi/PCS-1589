package com.example.apptoko1589

import com.example.apptoko1589.response.cart.Cart

interface CallbackInterface {
    fun passResultCallback(total:String,cart:ArrayList<Cart>)
}