package com.example.submission2.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission2.Adapter.AdapterSectionPager
import com.example.submission2.ViewModel.DetailVM
import com.example.submission2.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url "
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailVM



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl= intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()

        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailVM::class.java)
        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvNamaDetail.text = it.name
                    tvUsernameDetail.text = it.login
                    tvCompanyDetail.text = it.company
                    tvEmailDetail.text = it.email
                    tvFollowersDetail.text = "${it.followers} Followers"
                    tvFollowingDetail.text = "${it.following} Follwing"
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivDetailProfil)
                }
            }
        }

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.tgFavorite.isChecked = true
                        isCheck = true
                    }else{
                        binding.tgFavorite.isChecked = false
                        isCheck = false
                    }
                }
            }
        }

        binding.tgFavorite.setOnClickListener {
            isCheck = !isCheck
            if (isCheck){
                viewModel.addFavorite(username!!,id,avatarUrl!!)
            }else{
                viewModel.removeFavorite(id)
            }
            binding.tgFavorite.isChecked = isCheck
        }

        val sectionPagerAdpter = AdapterSectionPager(this,supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdpter
            tabs.setupWithViewPager(viewPager)
        }
    }
}