package llh4github.jimmer.example

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id

/**
 *
 * <p>Created At 2022/11/7 11:42
 * @author llh
 */
@Entity
interface Role {

    @Id
    val id: Int

    val name: String

}