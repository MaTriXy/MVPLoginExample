package tech.thdev.mvploginexample.view.login.presenter

import android.content.Context
import android.text.Editable
import tech.thdev.base.presenter.BasePresenter
import tech.thdev.base.presenter.BaseView
import tech.thdev.mvploginexample.data.source.login.LoginRepository

/**
 * Created by tae-hwan on 2/17/17.
 */

interface LoginContract {

    interface View : BaseView {

        fun showProgress()

        fun hideProgress()
        fun updatePasswordError(string: String)
        fun updateEmailError(string: String)
        fun failLoginError(string: String)
        fun successLogin(email: String)
        fun successLogout()
    }

    interface Presenter : BasePresenter<View> {

        var context: Context
        var loginRepository: LoginRepository

        fun loginUser(email: Editable, password: Editable)

        fun logout()
    }
}