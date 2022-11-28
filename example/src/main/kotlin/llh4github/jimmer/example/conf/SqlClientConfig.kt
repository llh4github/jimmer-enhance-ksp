package llh4github.jimmer.example.conf

import org.apache.logging.log4j.kotlin.Logging
import org.babyfish.jimmer.sql.DraftInterceptor
import org.babyfish.jimmer.sql.cache.CacheFactory
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.newKSqlClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.Connection
import javax.sql.DataSource
/**
 *
 * <p>Created At 2022/11/28 15:14
 * @author llh
 */
@Configuration
class SqlClientConfig :Logging{

    @Bean
    fun sqlClient(
        dataSource: DataSource,
        @Value("\${spring.datasource.url}") jdbcUrl: String,
        interceptors: List<DraftInterceptor<*>>,
//        cacheFactory: CacheFactory? // Optional dependency
    ): KSqlClient {
        return newKSqlClient {
            setConnectionManager {
                /*
                 * It's very important to use
                 *      "org.springframework.jdbc.datasource.DataSourceUtils"!
                 * This is spring transaction aware ConnectionManager
                 */
                val con: Connection = DataSourceUtils.getConnection(dataSource)
                try {
                    proceed(con)
                } finally {
                    DataSourceUtils.releaseConnection(con, dataSource)
                }
            }


            setExecutor {
                /*
                 * Log SQL and variables
                 */
                logger.info("\nExecute sql : ${sql}\nvariables: $variables")
                proceed()
            }

            addDraftInterceptors(interceptors)
        }
    }
}