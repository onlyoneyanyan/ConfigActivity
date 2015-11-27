package cn.nekocode.configactivity

import android.app.Application
import cn.nekocode.configactivity.data.local.Storage

/**
 * Created by nekocode on 2015/7/22.
 */
public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        Storage.init(this)
    }

    companion object {
        private var instanceTmp: App? = null

        public val instance: App by lazy {
            instanceTmp!!
        }
    }

}
