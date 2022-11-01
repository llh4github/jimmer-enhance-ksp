package llh4github.jimmer.generator

import com.squareup.kotlinpoet.*
import llh4github.jimmer.model.ClassDefinition
import llh4github.jimmer.util.JimmerMember

/**
 * 辅助data class生成器
 * <p>Created At 2022/10/25 14:18
 * @author llh
 */
class DataClassGen(private val classDefinition: ClassDefinition) {

    fun buildSupportClass(): FileSpec {
        val typeSpec = TypeSpec.classBuilder(classDefinition.supportClassName)
            .addModifiers(KModifier.DATA)
        val constructorFun = FunSpec.constructorBuilder()
        val propertyList = mutableListOf<PropertySpec>()
        classDefinition.fields.forEach {
            val type = ClassName(it.typePackage, it.typeName).copy(true)
            val propertySpec = PropertySpec.builder(it.name, type)
                .mutable(true)
                .initializer(it.name)
                .build()
            propertyList.add(propertySpec)

            constructorFun.addParameter(
                ParameterSpec.builder(it.name, type)
                    .defaultValue("null")
                    .build()
            )
        }
        val primaryConstructor = constructorFun.build()
        return FileSpec.builder(classDefinition.packageName, classDefinition.supportClassName)
            .addImport(classDefinition.packageName, classDefinition.className)
            .addType(
                typeSpec
                    .addAnnotation(AnnotationSpec.builder(Suppress::class)
                        .apply {
                            addMember("\"RedundantVisibilityModifier\"")
                            addMember("\"Unused\"")
                        }
                        .build())
                    .addProperties(propertyList)
                    .primaryConstructor(primaryConstructor)
                    .addFunction(toModelFun(classDefinition))
//                    .addType(
//                        TypeSpec.companionObjectBuilder()
//                            .addFunction(updateByIdFun(classDefinition))
//                            .build()
//                    )
                    .build()
            ).build()
    }

    private fun toModelFun(classDefinition: ClassDefinition): FunSpec {
        val newFun = JimmerMember.newFun
        val model = ClassName(classDefinition.packageName, classDefinition.className)
        val addStatement = FunSpec.builder("toDbModel")
            .addKdoc("转换为数据库模型类（Jimmer框架）\n")
            .addKdoc("仅转换非空字段")
            .returns(model)
            .addCode("return ")
            .addStatement("%M(%L::class).by{", newFun, classDefinition.className)
        classDefinition.fields.forEach {
            addStatement.addStatement("this@%L.%L?.let{", classDefinition.supportClassName, it.name)
                .addStatement("%L = it", it.name)
                .addStatement("}")
        }
        val funSpec = addStatement.addStatement("}")
        return funSpec.build()

    }

    @Deprecated("此方法转移到dao层辅助类中实现")
    private fun updateByIdFun(classDefinition: ClassDefinition): FunSpec {

        val db = JimmerMember.ktSqlClient
        val model = ClassName(classDefinition.packageName, classDefinition.supportClassName)
        val eqFunc = JimmerMember.eqFun
        val funSpec = FunSpec.builder("updateById")
            .addKdoc("根据主键更新非空字段值\n")
            .addKdoc(" [model] 待更新的数据。以主键作为更新条件，调用时需要确认主键的存在性")
            .addParameter("db", db)
            .addParameter("model", model)
            .returns(Int::class)
            .addCode("return db.createUpdate(%L::class){", classDefinition.className)

        classDefinition.fields.forEach {
            if (!it.isPrimaryKey) {
                funSpec.beginControlFlow("if(model.%L != null)", it.name)
                    .addStatement("set(table.%L , model.%L!!)", it.name, it.name)
                    .endControlFlow()
            }
        }
        classDefinition.fields
            .filter { it.isPrimaryKey }
            .forEach {
                funSpec.addStatement("where(table.%L %M model.%L!!)", it.name, eqFunc, it.name)
            }
        funSpec.addCode("}.execute()")
        TypeSpec.companionObjectBuilder()
            .build()
        return funSpec.build()
    }
}