package tw.broccolihuang.mcdonaldscoupons.api.getStickerList

import tw.broccolihuang.mcdonaldscoupons.api.common.SourceInfo

data class GetStickerListRequest(
    val access_token: String,
    val source_info: SourceInfo
)