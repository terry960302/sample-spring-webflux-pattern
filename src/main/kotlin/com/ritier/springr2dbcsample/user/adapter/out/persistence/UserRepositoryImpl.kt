package com.ritier.springr2dbcsample.user.adapter.out.persistence

import com.ritier.springr2dbcsample.shared.constants.sql.UserQueries
import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.user.adapter.out.persistence.converter.UserReadConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val userReadConverter: UserReadConverter
) : UserRepository {
    override suspend fun count(): Long =
        databaseClient.sql(UserQueries.COUNT_USERS)
            .map { row -> row.get("count", Long::class.java)!! }
            .awaitSingle()

    override suspend fun save(user: User): User {
        return if (user.id.value == 0L) {
            val insertedRow = databaseClient.sql(UserQueries.INSERT_USER)
                .bind("username", user.username)
                .bind("email", user.email.value)
                .bind("age", user.age)
                .bind("createdAt", user.createdAt)
                .map { row -> row } // Row 그대로 반환
                .awaitSingle()

            val newId = insertedRow.get("user_id", Long::class.javaObjectType)!!
            findById(UserId(newId))!!
        } else {
            update(user)
        }
    }

    override suspend fun findById(id: UserId): User? =
        databaseClient.sql(UserQueries.SELECT_USER_BY_ID)
            .bind("id", id.value)
            .map { row -> userReadConverter.convert(row) }
            .awaitSingleOrNull()

    override suspend fun findByEmail(email: String): User? =
        databaseClient.sql(UserQueries.SELECT_USER_BY_EMAIL)
            .bind("email", email)
            .map { row -> userReadConverter.convert(row) }
            .awaitSingleOrNull()

    override suspend fun findAll(): Flow<User> =
        databaseClient.sql(UserQueries.SELECT_ALL_USERS)
            .map { row -> userReadConverter.convert(row) }
            .flow()

    override suspend fun findByUsername(username: String): Flow<User> =
        databaseClient.sql(UserQueries.SELECT_USERS_BY_USERNAME)
            .bind("username", "%$username%")
            .map { row -> userReadConverter.convert(row) }
            .flow()

    override suspend fun deleteById(id: UserId): Boolean {
        val rowsAffected = databaseClient.sql(UserQueries.DELETE_USER_BY_ID)
            .bind("id", id.value)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
        return rowsAffected > 0
    }

    override suspend fun update(user: User): User =
        databaseClient.sql(UserQueries.UPDATE_USER)
            .bind("username", user.username)
            .bind("email", user.email.value)
            .bind("age", user.age)
            .bind("id", user.id.value)
            .map { row -> userReadConverter.convert(row) }
            .awaitSingle()

    // 🟢 타입 문제 해결된 findByIds
    suspend fun findByIds(ids: List<UserId>): List<User> {
        if (ids.isEmpty()) return emptyList()

        val userIds = ids.map { it.value }
        return databaseClient.sql(UserQueries.SELECT_USERS_BY_IDS)
            .bind("userIds", userIds.toTypedArray())
            .map { row -> userReadConverter.convert(row) }
            .all()
            .collectList()
            .awaitSingle()
    }

    suspend fun existsByEmail(email: String): Boolean {
        val count = databaseClient.sql(UserQueries.EXISTS_USER_BY_EMAIL)
            .bind("email", email)
            .map { row -> row.get("count", Long::class.java)!! }
            .awaitSingle()
        return count > 0
    }
}
