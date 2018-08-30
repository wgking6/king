package tw.broccolihuang.mcdonaldscoupons.api.getStickerList

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tw.broccolihuang.mcdonaldscoupons.api.ApiService
import tw.broccolihuang.mcdonaldscoupons.api.BuildApi
import tw.broccolihuang.mcdonaldscoupons.firestore.Config

class GetStickerList {
    fun load(config: Config, access_token: String): Observable<GetStickerListResponse>? {
        return BuildApi().create(ApiService::class.java, config.api_base_url)!!
                .getStickerList(GetStickerListRequest(access_token, BuildApi.getSourceInfo(config)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}