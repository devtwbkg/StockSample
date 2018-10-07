package xyz.twbkg.stock.data.model.request

import xyz.twbkg.stock.data.model.LoggedUser
import javax.inject.Inject

data class RequestHeader @Inject constructor(
        var loggedUser: LoggedUser
) {
}