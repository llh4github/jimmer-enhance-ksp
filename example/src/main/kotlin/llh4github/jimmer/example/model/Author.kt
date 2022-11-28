package llh4github.jimmer.example.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 *
 * <p>Created At 2022/11/28 14:36
 * @author llh
 */
@Entity
@Table(name = "author")
interface Author {
    @Id
    val id: Long

    @Key
    val firstName: String

    @Key
    val lastName: String

    val gender: Int

    @ManyToMany(mappedBy = "authors")
    val books: List<Book>
}