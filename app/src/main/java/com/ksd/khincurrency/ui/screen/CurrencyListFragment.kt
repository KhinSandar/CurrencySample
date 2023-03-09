package com.ksd.khincurrency.ui.screen

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ksd.khincurrency.databinding.FragmentCurrencyListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding
    private val viewModel by viewModels<CurrencyListViewModel>()

    private val currencyListAdapter = CurrencyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadData()

        with(binding) {
            // Set up list of users
            currencyList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            currencyList.adapter = currencyListAdapter.apply {
                onCurrencyClickListener = { currency ->
                    // For more complex app, Jetpack navigation or a better navigation system that works across modules could be implemented
                    /*startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra("base", Base)
                    })*/
                }
            }
            currencyList.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lastVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    // viewModel.loadNextItemsIfNeeded(lastVisibleItem, currencyListAdapter.itemCount)
                }
            })
        }

        setUpBaseSpinner()

        viewModel.currencyList.observe(viewLifecycleOwner) { currencyList ->
            if (currencyList.isNotEmpty()) {
                currencyListAdapter.insertToList(currencyList)
            }
        }
    }

    private fun setUpBaseSpinner() {
        val languages = resources.getStringArray(com.ksd.khincurrency.R.array.currency_base)
        val adapterBase = context?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_item, languages
            )
        }
        binding.currencyBase.adapter = adapterBase
        binding.currencyBase.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context,
                    getString(com.ksd.khincurrency.R.string.app_name) + " " +
                            "" + languages[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

}