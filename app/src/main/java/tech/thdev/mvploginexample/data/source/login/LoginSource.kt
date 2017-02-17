package tech.thdev.mvploginexample.data.source.login

import io.reactivex.Flowable
import tech.thdev.mvploginexample.data.User

/**
 * Created by tae-hwan on 2/17/17.
 */

interface LoginSource {

    fun dumpLogin(user: User): Flowable<Boolean>
}