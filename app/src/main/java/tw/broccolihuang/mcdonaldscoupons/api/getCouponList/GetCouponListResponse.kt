package tw.broccolihuang.mcdonaldscoupons.api.getCouponList

data class GetCouponListResponse(
    val rc: Int,
    val rm: String,
    val results: Results
)

data class Results(
    val coupons: List<Coupon>,
    val current_datetime: String
)

data class Coupon(
    val coupon_id: Int,
    val type: String,
    val status: Int, //1=未過期 2=使用中、已過期
    val object_info: ObjectInfo
)

data class ObjectInfo(
    val object_id: Int,
    val image: Image,
    val title: String,
    val redeem_end_datetime: String
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int
)