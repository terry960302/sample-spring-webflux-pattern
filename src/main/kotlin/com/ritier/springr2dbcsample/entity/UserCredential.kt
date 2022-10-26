package com.ritier.springr2dbcsample.entity

import com.ritier.springr2dbcsample.dto.common.Role
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table("user_credentials")
class UserCredential(
    @Id
    @Column("user_credential_id") val id: Long?,
    @Column("user_id") val userId: Long?,
    @Column("role") val role : Role,
    @Column("email") val email: String,
    @Column("password") val password: String,
)

//class UserCred : UserDetails{
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getPassword(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun getUsername(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isEnabled(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//}