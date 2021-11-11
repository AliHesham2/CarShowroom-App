package com.example.cars.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.util.PopUpMsg
import com.example.cars.databinding.FragmentCarBinding


class CarFragment : Fragment() {
    private lateinit var binding:FragmentCarBinding
    private lateinit var viewModel: CarFragmentViewModel
    private var  loadMore : Boolean = true
    private var isLoading = false
    private var isScrolling = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //initialization
        binding = FragmentCarBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val viewModelFactory = CarFragmentViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CarFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.Cars.adapter = CarAdapter(CarAdapter.OnClickListener{

        })

        //observers
        viewModel.error.observe(this.viewLifecycleOwner,{
            if(it != null){
                PopUpMsg.alertMsg(this.requireView(),it)
            }
        })

        viewModel.loadMore.observe(this.viewLifecycleOwner,{
            loadMore = it == true
        })

        viewModel.loading.observe(this.viewLifecycleOwner,{
            if(it == true){
                isLoading= true
                binding.paginationProgressBar.visibility = View.VISIBLE
            }else{
                isLoading= false
                binding.paginationProgressBar.visibility = View.GONE
            }
        })

        //pagination
        binding.Cars.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(loadMore){
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                    val isNotAtBeginning = firstVisibleItemPosition >= 0
                    val isTotalMoreThanVisible = totalItemCount >= 2
                    val shouldPaginate =   isAtLastItem && isNotAtBeginning &&
                            isTotalMoreThanVisible && isScrolling && !isLoading
                    if(shouldPaginate) {
                        if(loadMore){
                            viewModel.callCars()
                            isScrolling = false
                        }
                    }
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })

       return binding.root
    }

}