package com.example.sneakerhead.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerhead.R

class SizeAdapter(context: Context,val arr:ArrayList<String>,val onClickSize:SizeOnClickInterface):RecyclerView.Adapter<SizeAdapter.Views>() {
    class Views(itemView:View):RecyclerView.ViewHolder(itemView) {
val btnSize=itemView.findViewById<Button>(R.id.btnSizeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Views {
       return Views(LayoutInflater.from(parent.context).inflate(R.layout.size_item,parent,false))
    }

    override fun getItemCount(): Int {
      return arr.size
    }

    override fun onBindViewHolder(holder: Views, position: Int) {
      holder.btnSize.text = arr[position]
 holder.btnSize.setOnClickListener {
     onClickSize.onClick(holder.btnSize,position)
 }
    }

}

interface SizeOnClickInterface {
  fun onClick(button: Button,position: Int)

}
