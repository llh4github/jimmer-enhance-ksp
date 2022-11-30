package llh4github.jimmer.example.model

import org.babyfish.jimmer.sql.*

/**
 *
 * <p>Created At 2022/11/28 14:35
 * @author llh
 */
@Entity
@Table(name = "book_store")
interface BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    @Key
    val name: String

    val webSite: String?

    @OneToMany(mappedBy = "store")
    val books: List<Book>
}