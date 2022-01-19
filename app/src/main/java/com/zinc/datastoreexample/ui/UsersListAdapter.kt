package com.zinc.datastoreexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinc.datastoreexample.databinding.ItemUserBinding
import com.zinc.datastoreexample.model.UserInfo

class UsersListAdapter(val deleteListener: (UserInfo) -> Unit) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private val usersInfoList = arrayListOf<UserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListAdapter.ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: UsersListAdapter.ViewHolder, position: Int) {
        holder.bind(usersInfoList[position]) {
            deleteListener(it)
        }
    }

    override fun getItemCount(): Int {
        return usersInfoList.size
    }

    fun addList(list: List<UserInfo>) {
        usersInfoList.clear()
        usersInfoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserInfo, deleteButtonClicked: (UserInfo) -> Unit) {
            binding.user = user
            binding.deleteButton.setOnClickListener {
                deleteButtonClicked(user)
            }
        }
    }
}