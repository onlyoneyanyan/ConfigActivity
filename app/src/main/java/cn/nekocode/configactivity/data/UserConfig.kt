package cn.nekocode.configactivity.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by nekocode on 2015/11/2.
 */

data class UserConfig(var name: String, var gender: String): Parcelable {

    constructor(source: Parcel): this(source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.name)
        dest?.writeString(this.gender)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<UserConfig> = object : Parcelable.Creator<UserConfig> {
            override fun createFromParcel(source: Parcel): UserConfig {
                return UserConfig(source)
            }

            override fun newArray(size: Int): Array<UserConfig?> {
                return arrayOfNulls(size)
            }
        }
    }
}