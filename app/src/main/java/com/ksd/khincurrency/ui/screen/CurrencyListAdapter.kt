package com.ksd.khincurrency.ui.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.khinsampleapp.common.textOrGone
import com.ksd.khincurrency.databinding.ViewCurrencyListEntryBinding

class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.UserListViewHolder>() {

    private val currencyList: MutableList<String> = mutableListOf()

    var onCurrencyClickListener: ((String) -> Unit)? = null

    fun insertToList(currency: List<String>) {
        val currentUserCount = currencyList.size
        currencyList.addAll(currency)
        notifyItemRangeInserted(currentUserCount, currencyList.size)
    }

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

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.update(currencyList[position])
    }

    override fun getItemCount() = currencyList.size

    class UserListViewHolder(
        private val binding: ViewCurrencyListEntryBinding,
        private val onUserClickListener: ((String) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun update(currency: String) {
            with(binding) {
                // userListEntryId.text = "UserID-${currency.id.toString()}"
                userListEntryLogin.textOrGone(currency)
                // userListEntryUrl.text = user.htmlUrl
                /*Glide.with(root.context)
                    .load(Uri.parse(user.avatarUrl))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(userListEntryAvatar)*/

                root.setOnClickListener {
                    onUserClickListener?.invoke(currency)
                }
            }
        }
    }
}