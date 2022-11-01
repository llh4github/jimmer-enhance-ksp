package llh4github.jimmer.model

import com.squareup.kotlinpoet.ClassName

/**
 *
 * <p>Created At 2022/10/22 16:28
 * @author llh
 */
data class FieldDefinition(
    /**
     * 字段名
     */
    val name: String,

    /**
     * 类型包名
     */
    val typePackage: String,

    /**
     * 字段类型名
     */
    val typeName: String,


    val complexTypeStr: String? = null,

    /**
     * 此字段是否为主键
     */
    val isPrimaryKey: Boolean = false,
    /**
     * 字段的注释
     */
    val doc: String? = null,
) {
    val isComplexType = complexTypeStr != null

    fun toClassName(): ClassName {
        return ClassName(typePackage, typeName)
    }
}
