package com.lifepharmacy.application.network.endpoints

import com.lifepharmacy.application.model.general.GeneralResponseModel
import com.lifepharmacy.application.model.product.ProductDetails
import com.lifepharmacy.application.model.rewards.RewardModel
import com.lifepharmacy.application.utils.URLs
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RewardsApi {

  @GET(URLs.REWARD_LIST)
  suspend fun getReswardsList(): Response<GeneralResponseModel<RewardModel>>

}
