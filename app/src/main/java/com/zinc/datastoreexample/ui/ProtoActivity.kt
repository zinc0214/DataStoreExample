package com.zinc.datastoreexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zinc.datastoreexample.R
import com.zinc.datastoreexample.data.userDataStore
import com.zinc.datastoreexample.databinding.ActivityProtoBinding
import com.zinc.datastoreexample.model.UserInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProtoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProtoBinding
    private lateinit var adapter: UsersListAdapter
    private var usersInfo: List<UserInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_proto)
        loadUsersInfo()
        setUpViews()
    }

    private fun setUpViews() {
        binding.setAddButtonClicked {
            val nickname = binding.nickNameEditTextView.text.toString()
            val phoneNumber = binding.phoneEditTextView.text.toString()
            val id = usersInfo.size

            addUserInfo(
                UserInfo.newBuilder().setId(id).setNickname(nickname).setPhone(phoneNumber).build()
            )
        }

        adapter = UsersListAdapter {
            deleteUserInfo(it)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setUpList() {
        adapter.addList(usersInfo)
    }

    private fun loadUsersInfo() {
        lifecycleScope.launch {
            this@ProtoActivity.userDataStore.data.collect {
                usersInfo = it.usersList
                setUpList()
            }
        }
    }

    private fun addUserInfo(user: UserInfo) {
        lifecycleScope.launch {
            this@ProtoActivity.userDataStore.updateData { users ->
                users.toBuilder()
                    .addUsers(user)
                    .build()
            }
        }
    }

    private fun deleteUserInfo(user: UserInfo) {
        lifecycleScope.launch {
            this@ProtoActivity.userDataStore.updateData { users ->
                val removeIndex = usersInfo.indexOfFirst { it == user }
                users.toBuilder()
                    .removeUsers(removeIndex)
                    .build()
            }
        }
    }
}