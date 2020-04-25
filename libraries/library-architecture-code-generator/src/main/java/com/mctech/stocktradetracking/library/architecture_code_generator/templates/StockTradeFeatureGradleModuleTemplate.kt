package com.mctech.stocktradetracking.library.architecture_code_generator.templates

import com.mctech.architecture.generator.generator.blankLine
import com.mctech.architecture.generator.generator.printTabulate
import com.mctech.architecture.generator.generator.writeFile
import com.mctech.architecture.generator.path.ModuleFilePath
import com.mctech.architecture.generator.templates.presentation.module.GradleModuleTemplate

class StockTradeFeatureGradleModuleTemplate(moduleFilePath: ModuleFilePath) : GradleModuleTemplate(moduleFilePath) {

    override fun generate() = writeFile(this) { output ->
        output.println("apply plugin: 'com.android.library'")
        output.println("apply plugin: 'kotlin-android'")
        output.println("apply plugin: 'kotlin-android-extensions'")
        output.println("apply plugin: 'kotlin-kapt'")
        output.blankLine()

        output.println("dependencies {")
        output.printTabulate("implementation project(path: submodulesPlatform.domain)")
        output.blankLine()

        output.printTabulate("// Personal libs")
        output.printTabulate("implementation globalDependencies.mvvmArchitectureCore")
        output.printTabulate("implementation globalDependencies.mvvmArchitectureCoreKtx")
        output.printTabulate("implementation globalDependencies.mvvmArchitectureViewKtx")
        output.printTabulate("implementation globalDependencies.mvvmArchitectureCoreDatabinding")
        output.printTabulate("implementation globalDependencies.mvvmArchitectureCoreTesting")
        output.blankLine()

        output.printTabulate("// Platform")
        output.printTabulate("implementation globalDependencies.appCompact")
        output.printTabulate("implementation globalDependencies.kotlinStdLib")
        output.blankLine()

        output.printTabulate("// Android ARQ")
        output.printTabulate("implementation globalDependencies.lifeCycleLiveRuntime")
        output.printTabulate("implementation globalDependencies.lifeCycleLiveExtensions")
        output.printTabulate("implementation globalDependencies.lifeCycleViewModel")
        output.printTabulate("implementation globalDependencies.lifeCycleLiveData")
        output.blankLine()

        output.printTabulate("// View")
        output.printTabulate("implementation globalDependencies.recyclerView")
        output.printTabulate("implementation globalDependencies.materialDesign")
        output.printTabulate("implementation globalDependencies.constraintLayout")
        output.println("}")
    }
}