package llh4github.jimmer.example.jimmer

import llh4github.jimmer.example.dao.BookDao
import llh4github.jimmer.example.model.BookStoreSupport
import llh4github.jimmer.example.model.BookSupport
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

/**
 *
 * <p>Created At 2022/11/28 15:26
 * @author llh
 */
@SpringBootTest
class EnhanceKspTest : BaseTest() {

    @Autowired
    private lateinit var bookDao: BookDao


    @Test
    fun saveRelation() {
        val storeSupport = BookStoreSupport(
            name = "Test Store2",
            webSite = "a.b.c.html"
        )
        val book = BookSupport(
            name = "Good Book",
            edition = 3,
            price = BigDecimal(3.4),
            store = storeSupport
        )
        val authorList = listOf(randomAuthor(), randomAuthor())
        book.authors = authorList

        bookDao.insertRelationBySupport(book)
    }
}