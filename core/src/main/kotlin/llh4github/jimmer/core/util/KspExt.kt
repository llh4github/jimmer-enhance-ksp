package llh4github.jimmer.core.util

import com.google.devtools.ksp.symbol.KSAnnotation
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