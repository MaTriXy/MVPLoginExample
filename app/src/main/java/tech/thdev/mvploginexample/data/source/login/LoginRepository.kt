package tech.thdev.mvploginexample.data.source.login

import tech.thdev.mvploginexample.data.User

/**
 * Created by tae-hwan on 2/17/17.
 */

object LoginRepository : LoginSource {

    private val loginLocalDataSource = LoginLocalDataSource()

    override fun dumpLogin(user: User)
            = loginLocalDataSource.dumpLogin(user)
}