package tech.thdev.mvploginexample.data.source.login

import io.reactivex.Flowable
import tech.thdev.mvploginexample.data.User
import java.util.concurrent.TimeUnit

/**
 * Created by tae-hwan on 2/17/17.
 */

class LoginLocalDataSource : LoginSource {

    /**
     * A dummy authentication store containing known user names and passwords.
     */
    val DUMMY_CREDENTIALS: List<User> = mutableListOf(User("test@test.com", "1234"), User("test2@test.com", "4321"))

    override fun dumpLogin(user: User): Flowable<Boolean> {
        return Flowable.just(user)
                .delay(2000, TimeUnit.MILLISECONDS)
                .map {
                    Flowable.fromIterable(DUMMY_CREDENTIALS)
                            .map {
                                it.email == user.email && it.password == user.password
                            }
                            .filter {
                                it
                            }
                            .count()
                }
                .map {
                    android.util.Log.d("TEMP", "count : ${it.blockingGet()}")
                    it.blockingGet() == 1L
                }
    }
}