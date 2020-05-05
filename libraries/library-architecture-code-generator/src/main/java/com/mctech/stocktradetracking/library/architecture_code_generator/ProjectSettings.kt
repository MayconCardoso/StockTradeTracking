package com.mctech.stocktradetracking.library.architecture_code_generator

import com.mctech.architecture.generator.class_contract.Package
import com.mctech.architecture.generator.path.ModuleDefaultLayers
import com.mctech.architecture.generator.path.ModuleFilePath
import com.mctech.architecture.generator.settings.FeatureSettings
import com.mctech.architecture.generator.settings.PresentationMode
import com.mctech.architecture.generator.settings.ProjectSettings
import com.mctech.architecture.generator.strategy.FileDuplicatedStrategy
import com.mctech.stocktradetracking.library.architecture_code_generator.templates.StockTradeFeatureGradleModuleTemplate

object ProjectSettings {

    val featureSettings by lazy {
        FeatureSettings(
            createDependencyInjectionModules = false,
            createBothRemoteAndLocalDataSources = false,
            presentationViewModel = PresentationMode.Fragment,
            projectSettings = ProjectSettings(
                basePackageName = Package("com.mctech.stocktradetracking")
            ),
            fileDuplicatedStrategy = FileDuplicatedStrategy.Ignore,
            featureDuplicatedStrategy = FileDuplicatedStrategy.Ignore
        )
    }

    // ==========================================================================
    // DO NOT CHANGE THE CUSTOM TEMPLATES - IT WILL CREATE THE RIGHT FILE FOR THE CURRENT PROJECT
    // ==========================================================================
    val presentationBuildGradle by lazy {
        StockTradeFeatureGradleModuleTemplate(ModuleDefaultLayers.GeneratedFeature.moduleFile)
    }
    val baseArchitecturePath by lazy {
        ModuleFilePath(
            moduleLocation = "",
            gradleModuleName = "]",
            packageValue = Package("com.mctech.architecture.mvvm.x.core")
        )
    }
}