package yapp.rating.auth

interface AuthService {
    fun login(loginType: LoginType, token: String): AuthToken
}
