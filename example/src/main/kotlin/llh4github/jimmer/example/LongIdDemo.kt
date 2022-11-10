package llh4github.jimmer.example

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id

/**
 *
 * <p>Created At 2022/11/10 20:03
 * @author llh
 */
@Entity
interface LongIdDemo {

    @Id
    val id: Long

    val name: String
}