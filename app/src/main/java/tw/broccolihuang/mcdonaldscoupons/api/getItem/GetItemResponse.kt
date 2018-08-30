package tw.broccolihuang.mcdonaldscoupons.api.getItem

data class GetItemResponse(
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
    val status: Int,
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


//data class GetItemResponse(
//        val rc: Int,
//        val rm: String,
//        val results: Results
//)
//
//data class Results(
//        val coupon: Coupon
//)
//
//data class Coupon(
//        val coupon_id: Int,
//        val type: String,
//        val status: Int,
//        val object_info: ObjectInfo
//)
//
//data class ObjectInfo(
//        val object_id: Int,
//        val image: Image,
//        val title: String,
//        val redeem_end_datetime: String
//)
//
//data class Image(
//        val url: String,
//        val width: Int,
//        val height: Int
//)