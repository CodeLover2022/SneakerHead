package com.example.sneakerhead.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakerhead.Models.CartModel
import com.example.sneakerhead.R
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.play.integrity.internal.c
import kotlinx.android.synthetic.main.cartproduct_item.view.*

class CartAdapter(val context: Context,val cartList:ArrayList<CartModel>):RecyclerView.Adapter<CartAdapter.CartView>() {
    class CartView(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartView {
       return CartView(LayoutInflater.from(parent.context).inflate(R.layout.cartproduct_item,parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: CartView, position: Int) {
        holder.itemView.tvCartProductName.text = cartList[position].name
        holder.itemView.tvCartProductPrice.text = "₹${cartList[position].price}"
        holder.itemView.tvCartItemCount.text = cartList[position].quantity.toString()
        holder.itemView.tvCartProductSize.text = cartList[position].size.toString()
        Glide.with(context!!).load(cartList[position].imageUrl).into(holder.itemView.ivCartProduct)
        var count=holder.itemView.tvCartItemCount.text.toString().toInt()
        holder.itemView.btnCartItemAdd.setOnClickListener {
            count++;
            holder.itemView.tvCartItemCount.text=count.toString()
        }
        holder.itemView.btnCartItemMinus.setOnClickListener {
            count--;
            holder.itemView.tvCartItemCount.setText(count)
        }
    }
}