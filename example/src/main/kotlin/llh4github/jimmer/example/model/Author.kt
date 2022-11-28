package llh4github.jimmer.example.model

import org.babyfish.jimmer.sql.*

/**
 *
 * <p>Created At 2022/11/28 14:36
 * @author llh
 */
@Entity
@Table(name = "author")
interface Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val englishName: String

    @Key
    val chineseName: String

    val gender: Int

    @ManyToMany(mappedBy = "authors")
    val books: List<Book>
}