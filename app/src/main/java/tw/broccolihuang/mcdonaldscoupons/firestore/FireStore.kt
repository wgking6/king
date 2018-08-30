package tw.broccolihuang.mcdonaldscoupons.firestore

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FireStore {
    val db = FirebaseFirestore.getInstance()

    val collectionConfig = db.collection("Config")
    val collectionAccounts = db.collection("Accounts")

    val documentConfig = collectionConfig.document("config")

    fun addAccount(account: Account, callback: Callback) {
        collectionAccounts.document(account.account).set(account).addOnCompleteListener(OnCompleteListener<Void> { task ->
            if (task.isSuccessful) {
                callback.onSuccess(null)
            }
        })
    }

    fun getConfig(callback: Callback) {
        collectionConfig.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful && task.result != null && task.result.documents != null) {
                callback.onSuccess(task.result.documents.get(0).toObject(Config::class.java))
            }
        })
        documentConfig.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
            if (task.isSuccessful && task.result != null) {
                callback.onSuccess(task.result.toObject(Config::class.java))
            }
        })
    }

    fun getAccount(callback: Callback) {
        collectionAccounts.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful && task.result != null && task.result.documents != null) {
                var ans = ArrayList<Account>()
                for (temp in task.result.documents) {
                    ans.add(temp.toObject(Account::class.java)!!)
                }
                callback.onSuccess(ans)
            }
        })
    }

    interface Callback {
        fun <T> onSuccess(obj: T)
    }
}