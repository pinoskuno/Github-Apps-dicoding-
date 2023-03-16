package com.example.submission2.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Activity.DetailActivity
import com.example.submission2.Adapter.AdapterUser
import com.example.submission2.R
import com.example.submission2.ViewModel.FollowersVM
import com.example.submission2.databinding.FragmentDetailBinding

class FollowersF : Fragment(R.layout.fragment_detail){

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersVM
    private lateinit var adapter: AdapterUser
    private lateinit var username: String
    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBarMain.visibility = View.VISIBLE
        }else{
            binding.progressBarMain.visibility = View.GONE
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments
        username = argument?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentDetailBinding.bind(view)


        adapter = AdapterUser()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvDetailFollow.setHasFixedSize(true)
            rvDetailFollow.layoutManager = LinearLayoutManager(activity)
            rvDetailFollow.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowersVM::class.java)
        viewModel.setlisfFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}