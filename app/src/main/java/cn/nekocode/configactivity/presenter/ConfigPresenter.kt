package cn.nekocode.configactivity.presenter

import cn.nekocode.baseframework.presenter.helper.Presenter
import cn.nekocode.configactivity.data.UserConfig
import cn.nekocode.configactivity.data.local.Storage

/**
 * Created by nekocode on 2015/11/27.
 */
class ConfigPresenter(val itf: ConfigPresenter.ViewInterface): Presenter {
    interface ViewInterface {
        fun setWeather(config: UserConfig)
    }

    val CONFIG_KEY = "user_config"
    val config: UserConfig by lazy {
        var config: UserConfig? = Storage[CONFIG_KEY]
        if(config == null) {
            config = UserConfig("TempUser", "male")
        }
        config!!
    }

    fun onViewCreated() {
        itf.setWeather(config)
    }

    fun onGenderChanged(gender: String) {
        config.gender = gender
        Storage[CONFIG_KEY] = config
    }

    fun onNickNameChanged(nickName: String) {
        config.name = nickName
        Storage[CONFIG_KEY] = config
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destory() {
    }
}