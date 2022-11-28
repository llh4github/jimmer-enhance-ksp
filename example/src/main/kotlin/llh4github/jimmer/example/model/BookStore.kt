package llh4github.jimmer.example.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 *
 * <p>Created At 2022/11/28 14:35
 * @author llh
 */
@Entity
@Table(name = "book_store")
interface BookStore {
    @Id
    val id: Long

    @Key
    val name: String

    val website: String?

    @OneToMany(mappedBy = "store")
    val books: List<Book>
}