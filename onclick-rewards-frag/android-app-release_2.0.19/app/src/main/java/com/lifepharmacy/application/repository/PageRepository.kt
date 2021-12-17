package com.lifepharmacy.application.repository

import com.lifepharmacy.application.base.BaseRepository
import com.lifepharmacy.application.model.general.GeneralResponseModel
import com.lifepharmacy.application.model.profile.UpdateUserRequestModel
import com.lifepharmacy.application.model.request.NumberOTPRequest
import com.lifepharmacy.application.model.request.NumberOTPVerifyRequest
import com.lifepharmacy.application.model.response.VerifyOTPResponse
import com.lifepharmacy.application.network.endpoints.AccountApi
import com.lifepharmacy.application.network.endpoints.PageApi
import com.lifepharmacy.application.utils.HandleNetworkCallBack
import com.lifepharmacy.application.utils.NetworkUtils
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PageRepository
@Inject constructor(val networkUtils: NetworkUtils, private val api: PageApi) :
  BaseRepository() {


  suspend fun requestPage(slug: String) =
    getResult({ api.getPage(slug) }, networkUtils)

}