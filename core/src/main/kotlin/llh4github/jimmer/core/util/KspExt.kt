package llh4github.jimmer.core.util

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlin.reflect.KClass

fun Sequence<KSAnnotation>.hasAnno(anno: KClass<out Annotation>): Boolean {
    return this.filter {
        val foundAnno = it.annotationType.toTypeName()
        val targetAnno = anno.asClassName()
        foundAnno == targetAnno
    }.count() > 0
}

fun Sequence<KSAnnotation>.hasAnyAnno(annos: List<KClass<out Annotation>>): Boolean {
    return annos.any {
        this.hasAnno(it)
    }
}

fun Sequence<KSAnnotation>.notHasAnno(anno: KClass<out Annotation>): Boolean {
    return !hasAnno(anno)
}

fun KSTypeArgument.qualifiedNameStr(): String {
    val pkg = this.type?.resolve()?.declaration?.packageName?.asString()
    val ty = type?.resolve()?.declaration?.simpleName?.asString()
    return "$pkg.$ty"
}

fun KSTypeArgument.typePkg(): String? {
    return this.type?.resolve()?.declaration?.packageName?.asString()
}

fun KSTypeArgument.typeName(): String? {
    return type?.resolve()?.declaration?.simpleName?.asString()
}