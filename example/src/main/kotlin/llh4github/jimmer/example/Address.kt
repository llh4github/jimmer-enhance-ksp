package llh4github.jimmer.example

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne

/**
 *
 * <p>Created At 2022/11/7 11:44
 * @author llh
 */
@Entity
interface Address {

    @Id
    val id: Int


    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User?
}