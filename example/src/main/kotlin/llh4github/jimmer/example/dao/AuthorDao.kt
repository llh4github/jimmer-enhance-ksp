package llh4github.jimmer.example.dao

import llh4github.jimmer.example.model.assist.AuthorDaoAssist
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Component

/**
 *
 * <p>Created At 2022/11/29 15:53
 * @author llh
 */
@Component
class AuthorDao(database: KSqlClient) : AuthorDaoAssist(database) {
}