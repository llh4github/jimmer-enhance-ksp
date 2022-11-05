package llh4github.jimmer.example

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id

/**
 *
 *
 * Created At 2022/11/5 17:48
 * @author llh
 */
@Entity
interface User {
    @Id
    @GeneratedValue
    val id: Int

    val name: String
}