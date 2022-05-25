package com.example.networkretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkretrofit.databinding.ItemRecylcerBinding

class CustomAdapter: RecyclerView.Adapter<Holder>() {
    var userList: Repository? = null//어댑터에 사용할 데이터 컬렉션
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecylcerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {//목록에 뿌려지는 아이템 그려줌.
        val user = userList?.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size?: 0
    }
}
class Holder(val binding: ItemRecylcerBinding): RecyclerView.ViewHolder(binding.root){
    fun setUser(user: RepositoryItem?) {
        user?.let{
            binding.textName.text = user.name//이름
            binding.textId.text = user.node_id//아이디 세팅
            Glide.with(binding.imageAvatar).load(user.owner.avatar_url).into(binding.imageAvatar)//아바타는 Glide 사용해서 이미지뷰에 세팅
        }
    }
}