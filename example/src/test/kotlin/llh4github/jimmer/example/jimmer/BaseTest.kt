package llh4github.jimmer.example.jimmer

import com.apifan.common.random.source.NumberSource
import com.apifan.common.random.source.OtherSource
import com.apifan.common.random.source.PersonInfoSource
import llh4github.jimmer.example.model.AuthorSupport
import llh4github.jimmer.example.model.BookSupport
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * <p>Created At 2022/11/28 15:13
 * @author llh
 */
abstract class BaseTest {

    fun randomBook(): BookSupport {
        val price = NumberSource.getInstance().randomDouble(0.0, 1000.0)
        return BookSupport(
            name = OtherSource.getInstance().randomChinese(4),
            edition = NumberSource.getInstance().randomInt(1, 99),
            price = BigDecimal(price).setScale(2, RoundingMode.CEILING)
        )
    }

    fun randomAuthor(): AuthorSupport {
        return AuthorSupport(
            englishName = PersonInfoSource.getInstance().randomEnglishName(),
            chineseName = PersonInfoSource.getInstance().randomChineseName(),
            gender = NumberSource.getInstance().randomInt(0, 4),
        )
    }
}