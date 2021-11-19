package com.rishik.stockapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rishik.stockapp.R
import com.rishik.stockapp.adapters.NewsAdapter
import com.rishik.stockapp.adapters.NewsClick
import com.rishik.stockapp.adapters.SearchAdapter
import com.rishik.stockapp.databinding.FragmentHomeBinding
import com.rishik.stockapp.domain.Stocks
import com.rishik.stockapp.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    private var stockList = ArrayList<Stocks>()

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access viewModel after onViewCreated"
        }
        ViewModelProvider(
            this,
            HomeViewModel.Factory(activity.application)
        ).get(HomeViewModel::class.java)
    }

    private var viewModelNewsAdapter: NewsAdapter? = null
    private var viewModelStocksAdapter: SearchAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsList.observe(viewLifecycleOwner, { news ->
            news?.apply {
                viewModelNewsAdapter?.news = news
            }
        })

        viewModel.stockList.observe(viewLifecycleOwner, {
            it.apply {
                stockList = it as ArrayList<Stocks>
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.expanded.value!!)
                    viewModel.searchingFalse()
                else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
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

        viewModelNewsAdapter = NewsAdapter(NewsClick {
            //Generate Intent to read article
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        })

        viewModelStocksAdapter = SearchAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelNewsAdapter
        }

        binding.searchRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelStocksAdapter
        }

        binding.searchBarView.setOnClickListener {
            binding.searchBar.findViewById<View>(R.id.search_button).performClick()
            viewModel.searchingTrue()
        }

        //TODO(show resent searches instead of complete list)
        viewModelStocksAdapter!!.setData(stockList)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val tempArr = ArrayList<Stocks>()

                for (stock in stockList) {
                    if (stock.description.lowercase()
                            .contains(newText.toString().lowercase()) || stock.symbol.lowercase()
                            .contains(newText.toString().lowercase())
                    ) {
                        tempArr.add(stock)
                    }
                }
                viewModelStocksAdapter!!.setData(tempArr)
                viewModelStocksAdapter!!.notifyDataSetChanged()
                return true
            }
        })
        return binding.root
    }
}