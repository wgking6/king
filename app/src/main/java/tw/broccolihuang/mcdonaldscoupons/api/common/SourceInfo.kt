package tw.broccolihuang.mcdonaldscoupons.api.common

data class SourceInfo(
        val app_version: String,
        val device_time: String,
        val device_uuid: String,
        val model_id: String,
        val os_version: String,
        val platform: String
)