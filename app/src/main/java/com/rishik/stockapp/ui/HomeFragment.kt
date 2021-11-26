package com.rishik.stockapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rishik.stockapp.R
import com.rishik.stockapp.adapters.*
import com.rishik.stockapp.databinding.FragmentHomeBinding
import com.rishik.stockapp.domain.Stock
import com.rishik.stockapp.util.Resource
import com.rishik.stockapp.viewmodels.HomeViewModel
import com.rishik.stockapp.workManager.RefreshDataWorker
import dagger.hilt.android.AndroidEntryPoint

//TODO(show resent searches instead of complete list)
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var stockList = ArrayList<Stock>()
    private var stockListSmall = ArrayList<Stock>()

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var viewModelNewsAdapter: NewsAdapter
    private lateinit var viewModelStocksAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)

        // Set lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeValues()
        enqueueWorker()
    }

    private fun setupViews() {
        viewModelNewsAdapter = NewsAdapter(NewsClick {
            //Generate Intent to read article
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        })

        //TODO(Navigate to new fragment and display stock details)
        viewModelStocksAdapter = SearchAdapter(SearchClick {
            val action = HomeFragmentDirections.actionHomeFragmentToStockFragment(it.symbol, it.description)
            view?.findNavController()?.navigate(action)
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelNewsAdapter
        }

        binding.searchRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelStocksAdapter
        }

        val searchButton by lazy {
            binding.searchBar.findViewById<View>(R.id.search_button)
        }
        binding.searchBarView.setOnClickListener {
            searchButton.performClick()
            viewModel.searchingTrue()
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val tempArr = ArrayList<Stock>()

                for (stock in stockList) {
                    if(tempArr.size >= 8) break
                    if (stock.description.lowercase()
                            .contains(newText.toString().lowercase()) || stock.symbol.lowercase()
                            .contains(newText.toString().lowercase())
                    ) {
                        tempArr.add(stock)
                    }
                }
                viewModelStocksAdapter.submitList(tempArr)
                viewModelStocksAdapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun observeValues() {
        viewModel.news.observe(viewLifecycleOwner, { result ->
            viewModelNewsAdapter.submitList(result.data)

            binding.loadingSpinner.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            binding.textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
            binding.textViewError.text = result.error?.localizedMessage
        })

        viewModel.stocks.observe(viewLifecycleOwner, {
            stockList = it.data as ArrayList<Stock>
        })

        viewModel.smallListStocks.observe(viewLifecycleOwner, {
            viewModelStocksAdapter.submitList(it)
            stockListSmall = it as ArrayList<Stock>
        })
    }

    private fun enqueueWorker() {
        val request = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        WorkManager.getInstance(requireContext().applicationContext).enqueue(request)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}