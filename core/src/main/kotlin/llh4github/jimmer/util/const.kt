package llh4github.jimmer.util

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import org.babyfish.jimmer.sql.*

/**
 * jimmer 框架中的常用注解
 */
object JimmerAnno {
    val entity = Entity::class
    val transient = Transient::class
    val id = Id::class
    val manyToOne = ManyToOne::class
    val manyToMany = ManyToMany::class
    val oneToOne = OneToOne::class
    val oneTyMany = OneToMany::class
}

/**
 * jimmer框架中类与方法
 */
object JimmerMember {
    val newFun = MemberName("org.babyfish.jimmer.kt", "new")
    val eqFun = MemberName("org.babyfish.jimmer.sql.kt.ast.expression", "eq")
    val ktSqlClient = ClassName("org.babyfish.jimmer.sql.kt", "KSqlClient")
    val saveMode = ClassName("org.babyfish.jimmer.sql.ast.mutation", "SaveMode")

    val valueInFun = MemberName("org.babyfish.jimmer.sql.kt.ast.expression","valueIn")
    fun byFunc(draftClass: String): ClassName {
        return ClassName(draftClass, "by")
    }
}

/**
 * 关系映射注解
 */
//val relationAnnoList = listOf(Jimmer.manyToOne, Jimmer.manyToMany, Jimmer.oneToOne, Jimmer.oneTyMany)
/**
 * 下列注解暂时不生成对应字段
 */
val ignoreAnnoList =
    listOf(JimmerAnno.manyToOne, JimmerAnno.manyToMany, JimmerAnno.oneToOne, JimmerAnno.oneTyMany, JimmerAnno.transient)