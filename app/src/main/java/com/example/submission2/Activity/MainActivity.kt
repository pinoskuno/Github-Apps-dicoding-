package com.example.submission2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Adapter.AdapterUser
import com.example.submission2.DataBase.User
import com.example.submission2.Prefrence.PrefHelper
import com.example.submission2.R
import com.example.submission2.ViewModel.MainVM
import com.example.submission2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainVM
    private lateinit var adapter: AdapterUser
    private val pref by lazy { PrefHelper(context = this) }

    private fun searchUser(){
        binding.apply {
            val query = etSearch.text.toString()
            if (query.isEmpty())return
            showLoading(true)
            viewModel.setSearchUser(query)
        }
    }

    private fun dataUser(){
        binding.apply {
            rvPengguna.layoutManager = LinearLayoutManager(this@MainActivity)
            rvPengguna.setHasFixedSize(true)
            rvPengguna.adapter = adapter
            btnSearch.setOnClickListener {
                searchUser()
            }
            etSearch.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBarMain.visibility = View.VISIBLE
        }else{
            binding.progressBarMain.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(pref.getBoolean("pref_is_dark_mode")){
            true->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.rvPengguna.setHasFixedSize(true)
        adapter = AdapterUser()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object :AdapterUser.OnItemClickCallback{
            override fun onItemCliked(data: User) {
                Intent(this@MainActivity,DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL,data.avatar_url)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainVM::class.java]
        dataUser()
        viewModel.getSearchUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(
                    false
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(
            item.itemId
        ) {
            R.id.menu_favorite -> {
                Intent(this, favoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.menu_setting -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}