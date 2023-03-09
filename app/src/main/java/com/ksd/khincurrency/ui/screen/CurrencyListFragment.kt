package com.ksd.khincurrency.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ksd.khincurrency.databinding.FragmentCurrencyListBinding
import com.ksd.khincurrency.R
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

        with(binding) {
            // Set up list of users
            currencyList.layoutManager =
                GridLayoutManager(requireContext(), 3)
            currencyList.adapter = currencyListAdapter.apply {
                onCurrencyClickListener = { currency ->
                    // For more complex app, Jetpack navigation or a better navigation system that works across modules could be implemented
                    /*startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra("base", Base)
                    })*/
                }
            }

            searchView.queryHint = getString(R.string.currency_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            "onQueryTextSubmit "+  query, Toast.LENGTH_SHORT
                        ).show()
                    }
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if (query.isNotEmpty()) {
                        // Other check process for query ?
                        Toast.makeText(
                            context,
                            "onQueryTextChange  " + query, Toast.LENGTH_SHORT
                        ).show()
                    }
                    return false
                }
            })
        }

        setUpBaseSpinner()

        viewModel.currencyList.observe(viewLifecycleOwner) { currencyList ->
            Timber.d("List ===0000==> ${currencyList.size}")
            currencyListAdapter.submitList(currencyList)

        }
    }

    private fun setUpBaseSpinner() {
        val languages = resources.getStringArray(R.array.currency_base)
        val adapterBase = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, languages
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
                    getString(R.string.app_name) + " " +
                            "" + languages[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

}