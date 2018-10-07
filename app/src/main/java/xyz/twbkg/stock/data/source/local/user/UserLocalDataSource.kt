package xyz.twbkg.stock.data.source.local.user

import io.reactivex.Completable
import io.reactivex.Flowable
import xyz.twbkg.stock.data.model.db.User
import xyz.twbkg.stock.data.source.repository.UserDataSource
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
        var dao: UserDao
) : UserDataSource {

    override fun findUserLoggedIn(): Flowable<User> {
        return Flowable.fromCallable { dao.findUserLogin() }
    }

    override fun findLastSignIn(): Flowable<String> {
        return Flowable.fromCallable { dao.findLastSignIn() }
    }

    override fun save(user: User): Completable = Completable.fromAction {
        dao.save(user)
    }

    override fun update(user: User): Completable = Completable.fromAction {
        dao.update(user)
    }

    override fun deleteAll(): Completable = Completable.fromAction {
        dao.deleteAll()
    }
}