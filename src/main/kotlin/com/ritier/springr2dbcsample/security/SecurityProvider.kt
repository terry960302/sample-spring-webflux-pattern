package com.ritier.springr2dbcsample.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityProvider(val env : Environment) {

//    @Autowired
//    private lateinit var env : Environment
    private val jwtSecret = env.getProperty<String>("spring.security.jwt.secret")
    private val expiration = env.getProperty<Long>("spring.security.jwt.expiration")

    companion object{
        val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256
    }

    suspend fun generateToken(userId: Long): String {
        val claims: Claims = Jwts.claims()
        claims["user_id"] = userId
        return Jwts.builder().setClaims(claims).setExpiration(Date(System.currentTimeMillis() + expiration!!))
            .signWith(SIGNATURE_ALG, jwtSecret)
            .compact()
    }

    suspend fun validateToken(token: String): Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    suspend fun parseUserId(token: String): Long {
        val claims: Claims = getAllClaims(token)
        return claims["user_id"] as Long
    }

//    suspend fun getAuthentication(userId : Long) : Authentication{
////        val userDetails= userCredential
//    }

    suspend fun hashPassword(password: String): String {
        val round = env.getProperty<Int>("spring.security.password.hash.round")
        return BCrypt.hashpw(password, BCrypt.gensalt(round!!))
    }

    suspend fun validatePassword(plainPassword : String, hashedPassword : String): Boolean{
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }

    fun getAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).body
    }


}