package com.mctech.stocktradetracking.library.architecture_code_generator

import com.mctech.architecture.generator.builder.ComponentStateBuilder
import com.mctech.architecture.generator.builder.FeatureGenerator
import com.mctech.architecture.generator.builder.UseCaseBuilder
import com.mctech.architecture.generator.builder.newFeature
import com.mctech.architecture.generator.class_contract.Package
import com.mctech.architecture.generator.class_contract.Parameter
import com.mctech.architecture.generator.class_contract.Type
import com.mctech.architecture.generator.path.ModuleDefaultLayers
import com.mctech.architecture.generator.settings.FeatureSettings
import com.mctech.architecture.generator.settings.PresentationMode
import com.mctech.architecture.generator.settings.ProjectSettings
import com.mctech.architecture.generator.strategy.FileDuplicatedStrategy

// It is just a library I built to avoid writing that all boilerplate every time we create a new feature module.
// For mor information about the library check it out here: https://github.com/MayconCardoso/ArchitectureBoilerplateGenerator
fun main() {
    val featureSettings = FeatureSettings(
        createDependencyInjectionModules = false,
        createBothRemoteAndLocalDataSources = false,
        presentationViewModel = PresentationMode.Fragment,
        projectSettings = ProjectSettings(
            basePackageName = Package("com.mctech.stocktradetracking")
        ),
        fileDuplicatedStrategy = FileDuplicatedStrategy.Replace
    )

    // Just change the name of the feature we are creating in the 'featureName' parameter.
    // If you just wanna create the files with empty body remove every UseCase and LiveData below.
    // Creating use cases the generator will create not only the use cases but also the methods on repositories and data sources.
    // Again, for more information check the library on github.
    FeatureGenerator(
        settings = featureSettings,
        featureName = "Dda"
    ).newFeature {

        // ==========================================================================
        // DO NOT CHANGE THE CUSTOM TEMPLATES
        // IT WILL CREATE THE RIGHT FILE FOR THE CURRENT PROJECT
        // ==========================================================================
        val generatedFeatureModuleFile = ModuleDefaultLayers.GeneratedFeature.moduleFile

        // ==========================================================================
        // YOU CAN CHANGE EVERYTHING BELOW HERE. REMOVE, ADD, UPDATE, WHATEVER.
        // ==========================================================================
        addUseCase {
            UseCaseBuilder(
                name = "GetDDASubscriptionStatusCase",
                returnType = Type.ResultOf(
                    Type.GeneratedEntity
                )
            )
        }

        addUseCase {
            UseCaseBuilder(
                name = "GetDDASubscriptionContractCase",
                returnType = Type.ResultOf(
                    Type.String
                )
            )
        }

        addUseCase {
            UseCaseBuilder(
                name = "GetDDAPaymentsByState",
                parameters = listOf(
                    Parameter(
                        name = "associatedId",
                        type = Type.Int
                    ),
                    Parameter(
                        name = "type",
                        type = Type.String
                    ),
                    Parameter(
                        name = "startDate",
                        type = Type.String
                    ),
                    Parameter(
                        name = "endDate",
                        type = Type.String
                    )
                ),
                returnType = Type.ResultOf(
                    Type.ListOfGeneratedEntity
                )
            )
        }

        addUseCase {
            UseCaseBuilder(
                name = "GetDDAAssociatedUsersCase",
                returnType = Type.ListOfGeneratedEntity
            )
        }


        addUseCase {
            UseCaseBuilder(
                name = "DoDDAPaymentCase",
                parameters = listOf(
                    Parameter(
                        name = "payment",
                        type = Type.GeneratedEntity
                    )
                ),
                returnType = Type.ResultOf(Type.Boolean)
            )
        }

        addComponentState {
            ComponentStateBuilder(
                name = "subscriptionState",
                type = Type.GeneratedEntity
            )
        }

        addComponentState {
            ComponentStateBuilder(
                name = "listOfPayments",
                type = Type.ListOfGeneratedEntity
            )
        }
    }
}