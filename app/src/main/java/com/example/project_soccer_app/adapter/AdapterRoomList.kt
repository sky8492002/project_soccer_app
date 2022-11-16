package com.example.project_soccer_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_soccer_app.databinding.ItemRoomBinding
import com.example.project_soccer_app.data.model.RoomData

class AdapterRoomList(val context: Context): RecyclerView.Adapter<RoomHolder>() {
    var items = mutableListOf<RoomData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        var bind = ItemRoomBinding.inflate(LayoutInflater.from(context), parent, false)
        return RoomHolder(context, bind)
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        var item = items.get(position)
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRoomList(roomList:List<RoomData>){
        items = roomList.toMutableList()
        notifyDataSetChanged()
    }
}

class RoomHolder(val context: Context, var bind: ItemRoomBinding): RecyclerView.ViewHolder(bind.root){
    fun setItem(item: RoomData){
        bind.locateTextView.setText(item.locate)
    }
}