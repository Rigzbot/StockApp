package com.rishik.stockapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rishik.stockapp.R
import com.rishik.stockapp.adapters.NewsAdapter
import com.rishik.stockapp.adapters.NewsClick
import com.rishik.stockapp.databinding.FragmentHomeBinding
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access viewModel after onViewCreated"
        }
        ViewModelProvider(this, HomeViewModel.Factory(activity.application)).get(HomeViewModel::class.java)
    }

    private var viewModelAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsList.observe(viewLifecycleOwner, Observer<List<News>> { news ->
            news?.apply {
                viewModelAdapter?.news = news
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        //Set lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModelAdapter = NewsAdapter(NewsClick {
            //Generate Intent to read article
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }
}