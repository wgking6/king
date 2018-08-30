package tw.broccolihuang.mcdonaldscoupons

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import io.reactivex.rxkotlin.subscribeBy
import tw.broccolihuang.mcdonaldscoupons.firestore.Config
import tw.broccolihuang.mcdonaldscoupons.api.getCouponList.GetCouponList
import tw.broccolihuang.mcdonaldscoupons.api.getItem.GetItem
import tw.broccolihuang.mcdonaldscoupons.api.getStickerList.GetStickerList
import tw.broccolihuang.mcdonaldscoupons.firestore.Account
import tw.broccolihuang.mcdonaldscoupons.firestore.FireStore

class MainActivity : AppCompatActivity() {
    lateinit var tvShow: TextView
    lateinit var config: Config
    lateinit var accounts: ArrayList<Account>
    lateinit var menu: Menu
    lateinit var fireStore: FireStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvShow = findViewById(R.id.tv_show)

        fireStore = FireStore()

        fireStore.getConfig(object : FireStore.Callback {
            override fun <T> onSuccess(obj: T) {
                config = obj as Config

                fireStore.getAccount(object : FireStore.Callback {
                    override fun <T> onSuccess(obj: T) {
                        accounts = obj as ArrayList<Account>
                        menu.findItem(R.id.menu_add_account).setVisible(true)
//                        menu.findItem(R.id.menu_get_item).setVisible(true)
                        menu.findItem(R.id.menu_show).setVisible(true)
                    }
                })
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_account -> {
                val dialogAddAccount = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
                AlertDialog.Builder(this)
                        .setView(dialogAddAccount)
                        .setTitle("Add account")
                        .setPositiveButton("Submit", {dialog, whichButton ->
                            var account = Account()
                            account.access_token = (dialogAddAccount.findViewById(R.id.et_access_token) as EditText).text.toString()
                            account.account = (dialogAddAccount.findViewById(R.id.et_account) as EditText).text.toString()
                            account.password = (dialogAddAccount.findViewById(R.id.et_password) as EditText).text.toString()
                            account.name = (dialogAddAccount.findViewById(R.id.et_name) as EditText).text.toString()
                            fireStore.addAccount(account, object : FireStore.Callback {
                                override fun <T> onSuccess(obj: T) {
                                    Toast.makeText(this@MainActivity, "Finish", Toast.LENGTH_SHORT).show()
                                }
                            })
                        })
                        .setNegativeButton("cancel", null)
                        .show()
                return true
            }
            R.id.menu_get_item -> {
                tvShow.text = ""
                for (account in accounts){
                    GetItem().load(config, account.access_token)
                            ?.subscribeBy(
                                    onNext = {
                                        for (coupon in it.results.coupons){
                                            tvShow.text = tvShow.text.toString() + coupon.object_info.title + "\n"
                                        }
                                    },
                                    onError = {println(it.message)}
                            )
                }
                return true
            }
            R.id.menu_show -> {
                tvShow.text = ""
                for (account in accounts){
                    if (TextUtils.isEmpty(account.password)) {
                        tvShow.text = tvShow.text.toString() + account.account + " | 密碼問" + account.name + "\n"
                    } else {
                        tvShow.text = tvShow.text.toString() + account.account + " | " + account.password + "\n"
                    }
                }

                for (account in accounts){
                    GetCouponList().load(config, account.access_token)
                            ?.subscribeBy(
                                    onNext = {
                                        var ansCoupon  = ""
                                        for (coupon in it.results.coupons) {
                                            if (it.results.current_datetime < coupon.object_info.redeem_end_datetime && coupon.status == 1) {
                                                var newCoupon: String
                                                if (coupon.object_info.title.contains("_")) {
                                                    newCoupon = coupon.object_info.redeem_end_datetime.substring(5, 10) + "  " + coupon.object_info.title.substring(0, coupon.object_info.title.indexOf("_"))
                                                } else {
                                                    newCoupon = coupon.object_info.redeem_end_datetime.substring(5, 10) + "  " + coupon.object_info.title
                                                }
                                                for (replaceText in config.replace_texts) {
                                                    newCoupon = newCoupon.replace(replaceText, "")
                                                }
                                                ansCoupon += "\n        " + newCoupon
                                            }
                                        }
                                        tvShow.text = tvShow.text.substring(0, tvShow.text.indexOf("\n", tvShow.text.indexOf(account.account))) + ansCoupon + tvShow.text.substring(tvShow.text.indexOf("\n", tvShow.text.indexOf(account.account)))
                                    },
                                    onError = {println(it.message)}
                            )
                    GetStickerList().load(config, account.access_token)
                            ?.subscribeBy(
                                    onNext = {
                                        tvShow.text = tvShow.text.substring(0, tvShow.text.indexOf("\n", tvShow.text.indexOf(account.account))) + " (" + it.results.stickers.size + "點):" + tvShow.text.substring(tvShow.text.indexOf("\n", tvShow.text.indexOf(account.account)))
                                    },
                                    onError = {println(it.message)}
                            )
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
