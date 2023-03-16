package com.example.submission2.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Adapter.AdapterUser
import com.example.submission2.DataBase.User
import com.example.submission2.DataBase.favoriteUser
import com.example.submission2.ViewModel.FavoriteVM
import com.example.submission2.databinding.ActivityFavoriteBinding

class favoriteActivity : AppCompatActivity() {

    private lateinit var adapter: AdapterUser
    private lateinit var viewModel : FavoriteVM
    private lateinit var binding: ActivityFavoriteBinding

    private fun mapList(users: List<favoriteUser>): ArrayList<User>{
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return  listUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = AdapterUser()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)
        adapter.setOnItemClickCallback(object :AdapterUser.OnItemClickCallback{
            override fun onItemCliked(data: User) {
                Intent(this@favoriteActivity,DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL,data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@favoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }
}