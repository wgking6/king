package tw.broccolihuang.mcdonaldscoupons.api.getCouponList

import tw.broccolihuang.mcdonaldscoupons.api.common.SourceInfo

data class GetCouponListRequest(
    val access_token: String,
    val source_info: SourceInfo
)