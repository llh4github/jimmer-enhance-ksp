package llh4github.jimmer.model

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger

/**
 * 生成器上下文
 * <p>Created At 2022/10/22 16:32
 * @author llh
 */
data class ContextGen(
    val logger: KSPLogger,
    val codeGenerator: CodeGenerator,
)
