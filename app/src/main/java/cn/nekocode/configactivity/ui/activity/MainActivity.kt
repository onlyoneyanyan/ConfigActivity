package cn.nekocode.configactivity.ui.activity

import android.os.Message
import cn.nekocode.configactivity.ui.activity.helper.SingleFragmentActivity
import cn.nekocode.configactivity.ui.fragment.ConfigFragment

public class MainActivity : SingleFragmentActivity() {

    override val fragmentClass = ConfigFragment::class.java

    override val fragmentBundle = {
        null
    }

    override fun afterCreate() {
        toolbar.title = "UserConfigActivity"
    }

    override fun handler(msg: Message) {
    }
}
