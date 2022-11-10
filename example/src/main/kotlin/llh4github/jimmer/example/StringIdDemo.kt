package llh4github.jimmer.example

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id

/**
 *
 * <p>Created At 2022/11/10 20:10
 * @author llh
 */
@Entity
interface StringIdDemo {

    @Id
    val id: String

    val age: Int
}