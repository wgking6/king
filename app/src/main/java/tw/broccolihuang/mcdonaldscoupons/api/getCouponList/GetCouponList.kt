package tw.broccolihuang.mcdonaldscoupons.api.getCouponList

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tw.broccolihuang.mcdonaldscoupons.api.ApiService
import tw.broccolihuang.mcdonaldscoupons.api.BuildApi
import tw.broccolihuang.mcdonaldscoupons.firestore.Config

class GetCouponList {
    fun load(config: Config, access_token: String): Observable<GetCouponListResponse>? {
        return BuildApi().create(ApiService::class.java, config.api_base_url)!!
                .getCouponList(GetCouponListRequest(access_token, BuildApi.getSourceInfo(config)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}