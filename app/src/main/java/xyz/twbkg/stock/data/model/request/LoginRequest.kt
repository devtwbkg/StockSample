package xyz.twbkg.stock.data.model.request

data class LoginRequest(
        var usernameOrEmail: String = "",
        var password: String = ""
) {
}