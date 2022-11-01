package llh4github.jimmer.model

/**
 *
 * <p>Created At 2022/10/22 16:29
 * @author llh
 */
data class ClassDefinition(
    /**
     * 类名
     */
    val className: String,
    /**
     * 包名
     */
    val packageName: String,
    /**
     * 字段列表
     */
    val fields: List<FieldDefinition> = mutableListOf(),
    val doc: String? = null,
) {
    /**
     * 辅助类名
     */
    val supportClassName = "${className}Support"

    /**
     * dao层辅助类
     */
    val daoClassName = "${className}DaoAssist"

    /**
     * 查询辅助类层包名
     */
    fun assistPackageName(): String {
        return "${packageName}.assist"
    }

    val draftClassName = "${className}Draft"
    val draftClasQualifier = "${packageName}.${className}Draft"
}
