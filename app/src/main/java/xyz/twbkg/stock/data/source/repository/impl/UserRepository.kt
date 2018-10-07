package xyz.twbkg.stock.data.source.repository.impl

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import xyz.twbkg.stock.data.model.db.User
import xyz.twbkg.stock.data.source.local.user.UserLocalDataSource
import xyz.twbkg.stock.data.source.repository.UserDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        val localDataSource: UserLocalDataSource
) : UserDataSource {
    override fun findUserLoggedIn(): Flowable<User> {
        return localDataSource.findUserLoggedIn()
    }

    override fun update(user: User): Completable {
        return localDataSource.update(user)
    }

    override fun findLastSignIn(): Flowable<String> {
        return localDataSource.findLastSignIn()
    }

    override fun save(user: User): Completable {
        return localDataSource.save(user)
    }

    override fun deleteAll(): Completable {
        return localDataSource.deleteAll()
    }
}