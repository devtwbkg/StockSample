package xyz.twbkg.stock.data.source.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow
import xyz.twbkg.stock.data.model.db.User

interface UserDataSource {

    fun findUserLoggedIn(): Flowable<User>

    fun findLastSignIn(): Flowable<String>

    fun save(user: User): Completable

    fun update(user: User): Completable

    fun deleteAll(): Completable
}