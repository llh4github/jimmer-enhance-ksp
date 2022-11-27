package llh4github.jimmer.example

import org.babyfish.jimmer.sql.*

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

    @OneToOne(mappedBy = "address")
    val address: Address?

    @ManyToMany
    @JoinTable(name = "link_user_role", joinColumnName = "user_id", inverseJoinColumnName = "role_id")
    val roles: List<Role>
}