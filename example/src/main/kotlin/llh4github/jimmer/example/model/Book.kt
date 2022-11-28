package llh4github.jimmer.example.model

import org.babyfish.jimmer.sql.*
import java.math.BigDecimal

/**
 *
 * <p>Created At 2022/11/28 14:36
 * @author llh
 */
@Entity
@Table(name = "book")
interface Book {

    @Id
    val id: Long

    @Key
    val name: String

    @Key
    val edition: Int

    val price: BigDecimal

    @ManyToOne
    val store: BookStore?

    @ManyToMany
    @JoinTable(
        name = "book_author_link",
        joinColumnName = "book_id",
        inverseJoinColumnName = "author_id"
    )
    val authors: List<Author>

}