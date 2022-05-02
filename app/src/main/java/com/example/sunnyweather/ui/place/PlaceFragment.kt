package com.example.sunnyweather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.FragmentPlaceBinding

class PlaceFragment : Fragment(){

    val viewModel by lazy {
        ViewModelProvider(this).get(PlcaeViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter
    private var _binding : FragmentPlaceBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        binding.recycleView.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.listPlace)
        binding.recycleView.adapter =adapter
        binding.searchPlaceEdit.addTextChangedListener {
            editable -> val content = editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                binding.recycleView.visibility=View.GONE
                binding.bgImageView.visibility=View.VISIBLE
                viewModel.listPlace.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner) {
            result -> val place = result.getOrNull()
            if(place != null){
                binding.recycleView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.listPlace.clear()
                viewModel.listPlace.addAll(place)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}