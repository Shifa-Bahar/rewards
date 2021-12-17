package com.lifepharmacy.application.ui.rewards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lifepharmacy.application.R
import com.lifepharmacy.application.base.BaseFragment
import com.lifepharmacy.application.databinding.FragmentVouchers2Binding
import com.lifepharmacy.application.databinding.FragmentVouchersBinding
import com.lifepharmacy.application.enums.ScrollingState
import com.lifepharmacy.application.managers.analytics.RewardsListScreenOpen
import com.lifepharmacy.application.managers.analytics.voucherDetailScreenOpen
import com.lifepharmacy.application.managers.analytics.voucherListScreenOpen
import com.lifepharmacy.application.model.vouchers.VoucherModel
import com.lifepharmacy.application.network.Result
import com.lifepharmacy.application.repository.RewardsRepository
import com.lifepharmacy.application.ui.profile.viewmodel.ProfileViewModel
import com.lifepharmacy.application.ui.rewards.adapters.ClickItemRewards
import com.lifepharmacy.application.ui.rewards.adapters.RewardsAdapter
import com.lifepharmacy.application.ui.rewards.viewmodels.RewardsViewModel
import com.lifepharmacy.application.ui.utils.ScrollStateListener
import com.lifepharmacy.application.ui.utils.topbar.ClickTool
import com.lifepharmacy.application.ui.vouchers.adapters.ClickItemVoucher
import com.lifepharmacy.application.ui.vouchers.adapters.VouchersAdapter
import com.lifepharmacy.application.ui.vouchers.viewmodels.VouchersViewModel
import com.lifepharmacy.application.utils.AnalyticsUtil
import com.lifepharmacy.application.utils.universal.Logger
import com.lifepharmacy.application.utils.universal.RecyclerPagingListener
import com.lifepharmacy.application.utils.universal.RecyclerViewPagingUtil
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class VouchersFragment2 : BaseFragment<FragmentVouchers2Binding>(), ClickItemRewards, ClickTool,
  RecyclerPagingListener {
  private val viewModel: RewardsViewModel by activityViewModels()
  private val profileViewModel: ProfileViewModel by activityViewModels()

  lateinit var RewardsAdapter: RewardsAdapter
  private var layoutManager: GridLayoutManager? = null
  private lateinit var recyclerViewPagingUtil: RecyclerViewPagingUtil
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewModel.appManager.analyticsManagers.RewardsListScreenOpen()
    if (view == null) {
      mView = super.onCreateView(inflater, container, savedInstanceState)
      initUI()
      observers()
    }

    return mView
  }

  private fun initUI() {

    binding.toolbarTitle.click = this
    binding.toolbarTitle.tvToolbarTitle.text = "Rewards"
    initPagination()
  }

  private fun initPagination() {

    RewardsAdapter = RewardsAdapter(requireActivity(), this)
    layoutManager = GridLayoutManager(requireContext(), 1)
    binding.rvItems.layoutManager = layoutManager
    recyclerViewPagingUtil = RecyclerViewPagingUtil(
      binding.rvItems,
      layoutManager!!, this
    )
    binding.rvItems.adapter = RewardsAdapter
    binding.rvItems.addOnScrollListener(recyclerViewPagingUtil)
    binding.rvItems.post { // Call smooth scroll
      binding.rvItems.scrollToPosition(0)
    }
    resetSkip()
  }


  private fun observers() {
    if (profileViewModel.isLoggedIn.get() != true) {
      findNavController().navigate(R.id.nav_login_sheet)
    }
    viewModel.appManager.storageManagers.getRew().observe(viewLifecycleOwner, Observer {
      it?.let {
//        binding.showEmpty = it.vouchers.isNullOrEmpty()
//        hideLoading()
        vouchersAdapter.setDataChanged(it.vouchers)
        viewModel.termsAndConditions.value = it.termsConditions
//        when (it.status) {
//          Result.Status.SUCCESS -> {
//            binding.showEmpty = it.data?.data?.vouchers.isNullOrEmpty()
//            hideLoading()
//            vouchersAdapter.setDataChanged(it.data?.data?.vouchers)
//            viewModel.termsAndConditions.value = it.data?.data?.termsConditions
//          }
//          Result.Status.ERROR -> {
//            hideLoading()
//            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//          }
//          Result.Status.LOADING -> {
//            showLoading()
//
//          }
//        }
      }
    })
  }

  override fun getLayoutRes(): Int {
    return R.layout.fragment_vouchers2
  }

  override fun permissionGranted(requestCode: Int) {

  }

  override fun onClickBack() {
    findNavController().navigateUp()
  }

  override fun onClickVoucher(voucherModel: VoucherModel) {
    viewModel.selectedVoucher.value = voucherModel
    findNavController().navigate(R.id.voucherDetailFragment)
  }
  private fun resetSkip(){
    recyclerViewPagingUtil.skip = 0
    viewModel.skip = 0
  }

  override fun onNextPage(skip: Int, take: Int) {
    viewModel.skip = skip
    viewModel.take = take
    observers()
  }
}
