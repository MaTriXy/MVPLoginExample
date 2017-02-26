package tech.thdev.mvploginexample.view.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import tech.thdev.base.view.BasePresenterActivity
import tech.thdev.mvploginexample.R
import tech.thdev.mvploginexample.data.source.login.LoginRepository
import tech.thdev.mvploginexample.view.login.presenter.LoginContract
import tech.thdev.mvploginexample.view.login.presenter.LoginPresenter

/**
 * Created by tae-hwan on 2/17/17.
 */

class LoginActivity : BasePresenterActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    override fun onCreatePresenter() = LoginPresenter()

    override fun showProgress() {
        login_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        login_progress.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter?.context = this
        presenter?.loginRepository = LoginRepository

        password.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == R.id.login || i == EditorInfo.IME_NULL) {
                login()
                return@setOnEditorActionListener true
            }
            false
        }

        email_sign_in_button.setOnClickListener {
            login()
        }

//        btn_logout.setOnClickListener {
//            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
//        }
        btn_logout.setOnClickListener {
            presenter?.logout()
        }
    }

    private fun login() {
        presenter?.loginUser(password = password.text, email = email.text)
    }

    override fun updatePasswordError(string: String) {
        password.error = string
    }

    override fun updateEmailError(string: String) {
        email.error = string
    }

    override fun failLoginError(string: String) {
        password.error = string
    }

    override fun successLogin(email: String) {
        login_form.visibility = View.GONE
        rl_logout_container.visibility = View.VISIBLE
        tv_email.text = email
    }

    override fun successLogout() {
        login_form.visibility = View.VISIBLE
        rl_logout_container.visibility = View.GONE
    }
}