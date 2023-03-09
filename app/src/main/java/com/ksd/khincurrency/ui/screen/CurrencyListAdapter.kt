package com.ksd.khincurrency.ui.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.khinsampleapp.common.textOrGone
import com.ksd.khincurrency.databinding.ViewCurrencyListEntryBinding

class CurrencyListAdapter : ListAdapter<CurrencyListItemUiState, CurrencyListAdapter.UserListViewHolder>(DiffItemCallBack) {

    var onCurrencyClickListener: ((CurrencyListItemUiState) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserListViewHolder(
            ViewCurrencyListEntryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
            onCurrencyClickListener
        )

    object DiffItemCallBack: DiffUtil.ItemCallback<CurrencyListItemUiState>() {
        override fun areItemsTheSame(
            oldItem: CurrencyListItemUiState,
            newItem: CurrencyListItemUiState
        ): Boolean {
            return oldItem.baseName == newItem.baseName
        }

        override fun areContentsTheSame(
            oldItem: CurrencyListItemUiState,
            newItem: CurrencyListItemUiState
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.update(getItem(position))
    }


    class UserListViewHolder(
        private val binding: ViewCurrencyListEntryBinding,
        private val onUserClickListener: ((CurrencyListItemUiState) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun update(currency: CurrencyListItemUiState) {
            with(binding) {
                base.textOrGone(currency.baseName)
                rateValue.textOrGone(currency.rateValue.toString())

                root.setOnClickListener {
                    onUserClickListener?.invoke(currency)
                }
            }
        }
    }
}