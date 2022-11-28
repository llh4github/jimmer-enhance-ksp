package llh4github.jimmer.example.jimmer

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 *
 * <p>Created At 2022/11/28 15:26
 * @author llh
 */
@SpringBootTest
class EnhanceKspTest : BaseTest() {

    @Autowired
    private lateinit var db: KSqlClient

    @Test
    fun save() {
        db.entities.save(
            randomBook().toDbModel()
        ) {
            setMode(SaveMode.INSERT_ONLY)
        }
        db.entities.save(
            randomAuthor().toDbModel()
        ) {
            setMode(SaveMode.INSERT_ONLY)
        }
    }
}