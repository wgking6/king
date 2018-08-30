package tw.broccolihuang.mcdonaldscoupons.api.getStickerList

data class GetStickerListResponse(
    val rc: Int,
    val rm: String,
    val results: Results
)

data class Results(
    val stickers: List<Sticker>
)

data class Sticker(
    val sticker_id: Int,
    val type: String,
    val status: Int,
    val object_info: ObjectInfo,
    val obtain_datetime: String
)

data class ObjectInfo(
    val object_id: Int,
    val image: Image,
    val title: String,
    val expire_datetime: String
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int
)
