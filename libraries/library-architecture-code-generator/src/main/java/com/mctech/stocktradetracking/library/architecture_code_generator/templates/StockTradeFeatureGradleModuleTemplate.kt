package com.mctech.stocktradetracking.library.architecture_code_generator.templates

import com.mctech.architecture.generator.generator.blankLine
import com.mctech.architecture.generator.generator.printTabulate
import com.mctech.architecture.generator.generator.writeFile
import com.mctech.architecture.generator.path.ModuleFilePath
import com.mctech.architecture.generator.templates.presentation.module.GradleModuleTemplate

class StockTradeFeatureGradleModuleTemplate(moduleFilePath: ModuleFilePath) :
  GradleModuleTemplate(moduleFilePath) {

  override fun generate() = writeFile(this) { output ->
    output.println("apply plugin: 'com.android.library'")
    output.println("apply plugin: 'kotlin-android'")
    output.println("apply plugin: 'kotlin-kapt'")
    output.blankLine()

    output.println("dependencies {")
    output.printTabulate("implementation project(path: submodulesPlatform.domain)")
    output.printTabulate("implementation project(path: submodulesLibraries.designSystem)")
    output.blankLine()

    output.printTabulate("// Personal libs")
    output.printTabulate("implementation \"com.mctech.architecture.mvvm:x-core:\$MCTECH_MVVM_ARCHITECTURE\"")
    output.printTabulate("implementation \"com.mctech.architecture.mvvm:x-core-ktx:\$MCTECH_MVVM_ARCHITECTURE\"")
    output.printTabulate("implementation \"com.mctech.architecture.mvvm:x-view-ktx:\$MCTECH_MVVM_ARCHITECTURE\"")
    output.printTabulate("implementation \"com.mctech.architecture.mvvm:x-core-databinding:\$MCTECH_MVVM_ARCHITECTURE\"")
    output.printTabulate("implementation \"com.mctech.architecture.mvvm:x-core-testing:\$MCTECH_MVVM_ARCHITECTURE\"")
    output.blankLine()

    output.printTabulate("// Platform")
    output.printTabulate("implementation \"androidx.appcompat:appcompat:\$ANDROID_APCOMPACT\"")
    output.printTabulate("implementation \"org.jetbrains.kotlin:kotlin-stdlib-jdk8:\$KOTLIN_VERSION\"")
    output.blankLine()

    output.printTabulate("// Android ARQ")
    output.printTabulate("implementation \"androidx.lifecycle:lifecycle-runtime-ktx:\$ANDROID_LIFECYCLE\"")
    output.printTabulate("implementation \"androidx.lifecycle:lifecycle-extensions:\$ANDROID_LIFECYCLE_EXTENSION\"")
    output.printTabulate("implementation \"androidx.lifecycle:lifecycle-viewmodel-ktx:\$ANDROID_LIFECYCLE\"")
    output.printTabulate("implementation \"androidx.lifecycle:lifecycle-livedata-ktx:\$ANDROID_LIFECYCLE\"")
    output.blankLine()

    output.printTabulate("// Navigation")
    output.printTabulate("implementation \"androidx.navigation:navigation-fragment-ktx:\$GOOGLE_NAVIGATION_VERSION\"")
    output.printTabulate("implementation \"androidx.navigation:navigation-ui-ktx:\$GOOGLE_NAVIGATION_VERSION\"")
    output.blankLine()

    output.printTabulate("// View")
    output.printTabulate("implementation \"androidx.recyclerview:recyclerview:\$VIEW_RECYCLER_VIEW\"")
    output.printTabulate("implementation \"com.google.android.material:material:\$VIEW_MATERIAL_DESIGN\"")
    output.printTabulate("implementation \"androidx.constraintlayout:constraintlayout:\$VIEW_CONSTRAINT_LAYOUT\"")

    output.printTabulate("// KOIN")
    output.printTabulate("implementation \"org.koin:koin-android:\$KOIN\"")
    output.printTabulate("implementation \"org.koin:koin-androidx-scope:\$KOIN\"")
    output.println("}")
  }
}