package llh4github.jimmer.core

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import llh4github.jimmer.core.model.ContextGen

/**
 *
 *
 * Created At 2022/10/22 16:55
 * @author llh
 */
class ModelEnhanceProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val context = ContextGen(
            logger = environment.logger,
            codeGenerator = environment.codeGenerator
        )
        environment.logger.info("ModelEnhanceProcessorProvider plugin start --- ")
        return ModelEnhanceProcessor(context)
    }
}