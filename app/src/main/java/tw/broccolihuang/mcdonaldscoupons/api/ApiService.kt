package tw.broccolihuang.mcdonaldscoupons.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import tw.broccolihuang.mcdonaldscoupons.api.getItem.GetItemRequest
import tw.broccolihuang.mcdonaldscoupons.api.getItem.GetItemResponse
import tw.broccolihuang.mcdonaldscoupons.api.getCouponList.GetCouponListRequest
import tw.broccolihuang.mcdonaldscoupons.api.getCouponList.GetCouponListResponse
import tw.broccolihuang.mcdonaldscoupons.api.getStickerList.GetStickerListRequest
import tw.broccolihuang.mcdonaldscoupons.api.getStickerList.GetStickerListResponse

interface ApiService {
    @POST("/lottery/get_item")
    fun getItem(@Body body: GetItemRequest): Observable<GetItemResponse>

    @POST("/coupon/get_list")
    fun getCouponList(@Body body: GetCouponListRequest): Observable<GetCouponListResponse>

    @POST("/sticker/get_list")
    fun getStickerList(@Body body: GetStickerListRequest): Observable<GetStickerListResponse>
}
