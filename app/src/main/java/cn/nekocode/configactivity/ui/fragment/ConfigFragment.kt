package cn.nekocode.configactivity.ui.fragment

import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import butterknife.bindView

import cn.nekocode.configactivity.R
import cn.nekocode.configactivity.data.UserConfig
import cn.nekocode.configactivity.presenter.ConfigPresenter
import cn.nekocode.configactivity.ui.activity.MainActivity
import cn.nekocode.configactivity.ui.view.GenderTextView
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onFocusChange

public class ConfigFragment : Fragment(), ConfigPresenter.ViewInterface {

    val scrollView: ScrollView by bindView(R.id.scrollView)

    val tvNickName: TextView by bindView(R.id.nickNameTextView)
    val etNickName: EditText by bindView(R.id.nickNameEditText)
    val btnNickName: View by bindView(R.id.nickNameBtn)

    val tvGender: GenderTextView by bindView(R.id.genderTextView)
    val btnGender: View by bindView(R.id.genderBtn)

    val inputMethodManager by lazy {
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    var firstLayout = true
    var bottomNavigationBarHeight = 0

    val configPresenter = ConfigPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configPresenter.onViewCreated()
        etNickName.visibility = View.INVISIBLE

        btnNickName.onClick {
            if (etNickName.visibility === View.VISIBLE) {
                hideSoftKeyboard()
                return@onClick
            }

            tvNickName.visibility = View.INVISIBLE
            etNickName.visibility = View.VISIBLE

            etNickName.setText(tvNickName.text)
            etNickName.setSelection(tvNickName.text.length)
            etNickName.requestFocus()
            inputMethodManager.showSoftInput(etNickName, InputMethodManager.SHOW_FORCED)
        }


        btnGender.onClick {
            tvGender.toggle()
            configPresenter.onGenderChanged(tvGender.selected)
        }


        val rootView = activity.window.decorView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val h = rootView.rootView.bottom - r.bottom

            if (firstLayout) {
                // 如果有导航栏的话，计算出底部导航栏的高度
                bottomNavigationBarHeight = h
                firstLayout = false
            }

            if (h == bottomNavigationBarHeight) {
                // 软键盘未打开
                etNickName.visibility = View.INVISIBLE
                tvNickName.visibility = View.VISIBLE

            } else {
                // 软键盘打开
                (activity as MainActivity).runDelayed({
                    if (etNickName.visibility === View.VISIBLE) {
                        tvNickName.visibility = View.INVISIBLE
                        scrollToBottom(btnNickName)
                    }
                }, 100)

            }
        }


        etNickName.onFocusChange {
            view, hasFocus ->
            if(hasFocus == false) {
                val nickName = etNickName.text.toString()
                if (nickName.trim({ it <= ' ' }).length != 0) {
                    tvNickName.text = nickName

                    configPresenter.onNickNameChanged(nickName)
                }
            }
        }
    }

    override fun setWeather(config: UserConfig) {
        tvNickName.text = config.name
        tvGender.selected = config.gender
    }

    private fun hideSoftKeyboard() {
        if (etNickName.visibility === View.VISIBLE) {
            inputMethodManager.hideSoftInputFromWindow(etNickName.windowToken, 0)
        }
    }

    private fun scrollToBottom(childView: View) {
        val childBottom = childView.bottom
        val targetScrollY = childBottom - scrollView.height
        scrollView.scrollTo(0, targetScrollY)
    }
}
