package llh4github.jimmer

import com.facebook.ktfmt.format.Formatter
import com.facebook.ktfmt.format.FormattingOptions
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import llh4github.jimmer.generator.DaoClassGen
import llh4github.jimmer.generator.DataClassGen
import llh4github.jimmer.model.ClassDefinition
import llh4github.jimmer.model.ContextGen
import llh4github.jimmer.model.FieldDefinition
import llh4github.jimmer.util.JimmerAnno
import llh4github.jimmer.util.hasAnno
import llh4github.jimmer.util.hasAnyAnno
import llh4github.jimmer.util.ignoreAnnoList
import org.jetbrains.kotlin.konan.file.use
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 *
 * Created At 2022/10/22 16:23
 * @author llh
 */
class ModelEnhanceProcessor(private val context: ContextGen) : SymbolProcessor {
    private val processed = AtomicBoolean()
    private val log = context.logger
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!processed.compareAndSet(false, true)) {
            return emptyList()
        }
        resolver.getAllFiles()
            .flatMap { it.declarations.filterIsInstance<KSClassDeclaration>() }
            .filter { it.annotations.hasAnno(JimmerAnno.entity) }
            .flatMap {
                val fields = parseFieldDefinition(it.getAllProperties())
                val classDefinition = parseClassDefinition(it, fields)
                val dataClass = DataClassGen(classDefinition).buildSupportClass()
                val daoClass = DaoClassGen(classDefinition).buildDataClass()
                listOf(daoClass, dataClass)
            }.forEach {
                val file = context.codeGenerator.createNewFile(
                    Dependencies(
                        aggregating = false,
                    ), it.packageName, it.name
                )
                file.writer(Charsets.UTF_8).use { out ->
                    out.write(formatCode(it, context.logger))
                }
            }
        log.info("jimmer model enhance plugin end --- ")
        return emptyList()
    }

    private fun parseClassDefinition(
        declaration: KSClassDeclaration,
        fields: List<FieldDefinition>? = null
    ): ClassDefinition {
        val packageName = declaration.packageName.asString()
        val className = declaration.simpleName.asString()
        return ClassDefinition(
            className, packageName,
            fields = fields ?: mutableListOf<FieldDefinition>(),
            doc = declaration.docString
        )
    }

    private fun parseFieldDefinition(properties: Sequence<KSPropertyDeclaration>): List<FieldDefinition> {
        return properties.filter { !it.annotations.hasAnyAnno(ignoreAnnoList) }
            .map {
                parseFieldDefinition(it)
            }.toList()
    }

    private fun parseFieldDefinition(property: KSPropertyDeclaration): FieldDefinition {
        val fieldName = property.simpleName.asString()

        val isPrimaryKey = property.annotations.hasAnno(JimmerAnno.id)
        val doc = property.docString
        val tyName = property.type.resolve().declaration.simpleName.asString()
        val typePackage = property.type.resolve().declaration.packageName.asString()

        val complexTypeStr = if (property.type.resolve().isComplexType()) {
            property.type.toTypeName().toString()
        } else {
            null
        }
        return FieldDefinition(
            name = fieldName,
            typeName = tyName,
            typePackage = typePackage,
            complexTypeStr = complexTypeStr,
            doc = doc,
            isPrimaryKey = isPrimaryKey
        )

    }

    /**
     * 是否为复杂类型
     */
    private fun KSType.isComplexType(): Boolean {
        return this.declaration.typeParameters.isNotEmpty()
    }


    private fun formatCode(fileSpec: FileSpec, logger: KSPLogger): String {
        // Use the Kotlin official code style.
        val options = FormattingOptions(style = FormattingOptions.Style.GOOGLE, maxWidth = 120, blockIndent = 4)
        // Remove tailing commas in parameter lists.
        val code = fileSpec.toString().replace(Regex(""",\s*\)"""), ")")
        return try {
            Formatter.format(options, code)
        } catch (e: Exception) {
            logger.exception(e)
            code
        }
    }
}