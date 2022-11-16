package com.example.project_soccer_app.adapter

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.project_soccer_app.R
import com.example.project_soccer_app.databinding.ItemProfileImageBinding
import com.example.project_soccer_app.data.model.ProfileImageData
import com.example.project_soccer_app.viewModel.UserDataViewModel
import com.example.project_soccer_app.viewModel.UserDataViewModellFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterProfileImageList(val context: Context, val list: List<ProfileImageData>): RecyclerView.Adapter<ProfileImageHolder>() {
    var items = list
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImageHolder {
        var bind = ItemProfileImageBinding.inflate(LayoutInflater.from(context), parent, false )
        return ProfileImageHolder(context, bind)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProfileImageHolder, position: Int) {
        var item = items.get(position)
        holder.setItem(item)

        // 클릭한 아이템만 색상이 다르도록 함
        holder.bind.profileImageItemLayout.setOnClickListener(){
            for(pos in 0 until items.size){
                if(pos == position){
                    items[pos].selected = true
                }
                else{
                    items[pos].selected = false
                }
            }
            notifyDataSetChanged() // 전체 리사이클러뷰 강제 업데이트
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // 유저의 프로필 변경점을 fragment가 알수 있게 하기 위해 사용 (fragment는 정보를 전달받아 서버에 업데이트)
    fun getProfileImageDataList():List<ProfileImageData>{
        return items
    }

}

class ProfileImageHolder(val context: Context, var bind: ItemProfileImageBinding):RecyclerView.ViewHolder(bind.root){

    fun setItem(item: ProfileImageData){
        bind.profileImageView.setImageDrawable(item.image)
        if(item.selected == true){
            bind.profileImageItemLayout.setBackgroundResource(R.drawable.round_yellow_background)
        }
        else{
            bind.profileImageItemLayout.setBackgroundResource(R.drawable.round_white_background)
        }
    }
}