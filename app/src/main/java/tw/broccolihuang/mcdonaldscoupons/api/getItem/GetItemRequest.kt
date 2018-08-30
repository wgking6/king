package tw.broccolihuang.mcdonaldscoupons.api.getItem

import tw.broccolihuang.mcdonaldscoupons.api.common.SourceInfo

data class GetItemRequest(
    val access_token: String,
    val source_info: SourceInfo
)