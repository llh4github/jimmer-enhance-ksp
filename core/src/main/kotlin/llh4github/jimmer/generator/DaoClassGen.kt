package llh4github.jimmer.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import llh4github.jimmer.model.ClassDefinition
import llh4github.jimmer.util.JimmerMember

/**
 *
 *
 * Created At 2022/10/25 20:31
 * @author llh
 */
class DaoClassGen(private val classDefinition: ClassDefinition) {

    private val dbVar = "db"
    private val modelSupport = ClassName(classDefinition.packageName, classDefinition.supportClassName)
    private val model = ClassName(classDefinition.packageName, classDefinition.className)
    private val modelVar = "model"
    fun buildDataClass(): FileSpec {
        val typeSpec = TypeSpec.classBuilder(classDefinition.daoClassName)
            .addModifiers(KModifier.ABSTRACT)
            .addFunction(updateByIdFun())
            .addAnnotation(AnnotationSpec.builder(Suppress::class)
                .apply {
                    addMember("\"RedundantVisibilityModifier\"")
                    addMember("\"Unused\"")
                }
                .build())
            .addFunction(insertBySupportFun())
            .addFunction(getByIdFun())
            .addFunction(getByIdsFun())
            .addFunction(deleteByIdFun())
            .addFunction(deleteByIdsFun())
            .addProperty(
                PropertySpec
                    .builder(dbVar, JimmerMember.ktSqlClient)
                    .addModifiers(KModifier.ABSTRACT)
                    .mutable(false)
                    .build()
            )
        val fieldNames = classDefinition.fields.map { it.name }.toList()
        return FileSpec.builder(classDefinition.assistPackageName(), classDefinition.daoClassName)
            .addType(typeSpec.build())
            .addImport(classDefinition.packageName, classDefinition.className)
            .addImport(classDefinition.packageName, fieldNames)
            .addImport(classDefinition.packageName, "by")
            .build()
    }


    private fun updateByIdFun(): FunSpec {
        val funSpec = FunSpec.builder("updateByIdSupport")
            .addParameter(modelVar, modelSupport)
            .returns(Int::class)
            .addCode("return db.createUpdate(%L::class){", classDefinition.className)
            .addCode("\n")
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
                funSpec.addStatement("where(table.%L %M model.%L!!)", it.name, JimmerMember.eqFun, it.name)
            }
        funSpec.addCode("}.execute()")
        TypeSpec.companionObjectBuilder()
            .build()
        return funSpec.build()
    }

    private fun insertBySupportFun(): FunSpec {

        val builder = FunSpec.builder("insertBySupport")
            .addKdoc("通过辅助类插入数据")
            .addParameter(modelVar, modelSupport)
            .returns(Int::class)
            .addStatement("val result = $dbVar.entities.save(")
            .addStatement("%M(%T::class).by{", JimmerMember.newFun, model)
        classDefinition.fields.forEach {
            builder.beginControlFlow("if(model.%L != null)", it.name)
                .addStatement("%L = model.%L!!", it.name, it.name)
                .endControlFlow()
        }
        builder.addStatement("}")
        val funSpec = builder
            .addStatement("){")
            .addStatement("setMode(%T.INSERT_ONLY)", JimmerMember.saveMode)
            .addStatement("}")
            .addStatement("return result.totalAffectedRowCount")
            .build()


        return funSpec
    }

    private fun getByIdFun(): FunSpec {
        val builder = FunSpec.builder("getById")
            .addKdoc("根据[id]列表查询数据")
            .addParameter("id", Int::class)
            .returns(model.copy(true))
            .addStatement("return db.entities.findById(%T::class,id)", model)
        return builder.build()
    }

    private fun getByIdsFun(): FunSpec {
        val builder = FunSpec.builder("getById")
            .addKdoc("根据[ids]列表查询数据")
            .addParameter("ids", List::class.parameterizedBy(Int::class))
            .addStatement("return db.entities.findByIds(%T::class,ids)", model)
        return builder.build()
    }

    private fun deleteByIdsFun(): FunSpec {
        val builder = FunSpec.builder("deleteByIds")
            .addKdoc("根据[ids]列表删除数据")
            .addParameter("ids", List::class.parameterizedBy(Int::class))
            .returns(Int::class)
            .addStatement("val result = db.createDelete(%T::class){", model)
            .addStatement("where(table.id %M ids )", JimmerMember.valueInFun)
            .addStatement("}")
            .addStatement(".execute()")
            .addStatement("return result")
        return builder.build()
    }

    private fun deleteByIdFun(): FunSpec {
        val builder = FunSpec.builder("deleteById")
            .addKdoc("根据[id]列表删除数据")
            .addParameter("id", Int::class)
            .returns(Int::class)
            .addStatement("val result = db.createDelete(%T::class){", model)
            .addStatement("where(table.id eq id )")
            .addStatement("}")
            .addStatement(".execute()")
            .addStatement("return result")
        return builder.build()
    }
}