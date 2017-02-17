package tech.thdev.mvploginexample.view.login.presenter

import android.content.Context
import android.text.Editable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import tech.thdev.base.presenter.AbstractPresenter
import tech.thdev.mvploginexample.R
import tech.thdev.mvploginexample.data.User
import tech.thdev.mvploginexample.data.source.login.LoginRepository
import java.util.concurrent.TimeUnit

/**
 * Created by tae-hwan on 2/17/17.
 */

class LoginPresenter : LoginContract.Presenter, AbstractPresenter<LoginContract.View>() {

    override lateinit var context: Context
    override lateinit var loginRepository: LoginRepository
    private val loginSubject: PublishSubject<TempData> = PublishSubject.create()
    private lateinit var loginDisposable: Disposable

    init {
        initLogin()
    }

    private fun initLogin() {
        loginDisposable = loginSubject
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(
                        { validLogin(it.email, it.password) },
                        { view?.failLoginError(context.getString(R.string.fail_login)) }
                )
    }

    private fun validLogin(email: Editable, password: Editable) {
        if (email.isNullOrEmpty()) {
            view?.updateEmailError(context.getString(R.string.error_field_required))
            return
        }

        if (!isPasswordValid(password.toString())) {
            view?.updatePasswordError(context.getString(R.string.error_invalid_password))
            return
        }

        if (!isEmailValid(email.toString())) {
            view?.updateEmailError(context.getString(R.string.error_invalid_email))
            return
        }

        view?.showProgress()

        loginRepository.dumpLogin(User(email.toString(), password.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (!it) {
                        view?.failLoginError(context.getString(R.string.error_field_required))
                        return@filter false
                    }
                    it
                }
                .doOnComplete {
                    view?.hideProgress()
                }
                .subscribe {
                    view?.successLogin(email.toString())
                }
    }

    override fun loginUser(email: Editable, password: Editable) {
        loginSubject.onNext(TempData(email, password))
    }

    private fun isEmailValid(email: String?)
        = email?.contains("@") ?: false

    private fun isPasswordValid(password: String?)
        = !password.isNullOrEmpty() || password?.let { it.length > 3 } ?: false

    override fun logout() {

    }

    data class TempData(val email: Editable, val password: Editable)
}