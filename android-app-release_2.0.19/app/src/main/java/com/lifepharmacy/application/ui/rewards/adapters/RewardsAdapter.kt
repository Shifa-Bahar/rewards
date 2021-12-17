package com.lifepharmacy.application.ui.rewards.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lifepharmacy.application.R
import com.lifepharmacy.application.databinding.ItemRewardsBinding
import com.lifepharmacy.application.databinding.ItemVouchersBinding
import com.lifepharmacy.application.model.rewards.RewardModel
import com.lifepharmacy.application.model.vouchers.VoucherModel

class RewardsAdapter(context: Activity?, private val onItemTapped: ClickItemRewards) :
  RecyclerView.Adapter<RewardsAdapter.ItemViewHolder>() {
  var arrayList: ArrayList<RewardModel>? = ArrayList()
  var activity: Activity? = context
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding: ItemRewardsBinding = DataBindingUtil.inflate(
      LayoutInflater.from(activity),
      R.layout.item_rewards,
      parent, false
    )
    return ItemViewHolder(binding.root)
  }

  override fun getItemCount(): Int {
    return if (null != arrayList) arrayList!!.size else 0
//        return 8
  }

  class ItemViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var binding: ItemRewardsBinding? = DataBindingUtil.bind(itemView)
  }

  fun setDataChanged(order: ArrayList<RewardModel>?) {
    arrayList?.clear()
    if (order != null) {
      arrayList?.addAll(order)
    }
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    val item = arrayList!![position]
    holder.binding?.item = item
    holder.binding?.click = onItemTapped
  }

}