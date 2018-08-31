package tw.broccolihuang.mcdonaldscoupons.firestore

data class Account(
    val access_token: String = "",
    val account: String = "",
    val password: String = "",
    val name: String = ""
)