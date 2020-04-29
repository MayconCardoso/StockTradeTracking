package com.mctech.stocktradetracking.library.architecture_code_generator.features

import com.mctech.architecture.generator.builder.ComponentStateBuilder
import com.mctech.architecture.generator.builder.FeatureGenerator
import com.mctech.architecture.generator.builder.UseCaseBuilder
import com.mctech.architecture.generator.builder.newFeature
import com.mctech.architecture.generator.class_contract.Parameter
import com.mctech.architecture.generator.class_contract.Type
import com.mctech.stocktradetracking.library.architecture_code_generator.ProjectSettings

object StockShareFeature {
    fun create() {

        FeatureGenerator(
            settings = ProjectSettings.featureSettings,
            featureName = "StockShare"
        ).newFeature {
            // ==========================================================================
            // DO NOT CHANGE THE CUSTOM TEMPLATES
            // IT WILL CREATE THE RIGHT FILE FOR THE CURRENT PROJECT
            // ==========================================================================
            presentationBuildGradle         = ProjectSettings.presentationBuildGradle
            baseArchitecturePath            = ProjectSettings.baseArchitecturePath


            addUseCase {
                UseCaseBuilder(
                    isDaggerInjectable = false,
                    name = "GetStockShareListCase",
                    returnType = Type.ResultOf(
                        Type.GeneratedEntity
                    )
                )
            }

            addUseCase {
                UseCaseBuilder(
                    isDaggerInjectable = false,
                    name = "BuyStockShareCase",
                    parameters = listOf(
                        Parameter(
                            name = "share",
                            type = Type.GeneratedEntity
                        )
                    ),
                    returnType = Type.Unit
                )
            }

            addUseCase {
                UseCaseBuilder(
                    isDaggerInjectable = false,
                    name = "SellStockShareCase",
                    parameters = listOf(
                        Parameter(
                            name = "share",
                            type = Type.GeneratedEntity
                        ),
                        Parameter(
                            name = "value",
                            type = Type.Double
                        )
                    ),
                    returnType = Type.Unit
                )
            }

            addUseCase {
                UseCaseBuilder(
                    isDaggerInjectable = false,
                    name = "EditStockShareValueCase",
                    parameters = listOf(
                        Parameter(
                            name = "shareCode",
                            type = Type.String
                        ),
                        Parameter(
                            name = "value",
                            type = Type.Double
                        )
                    ),
                    returnType = Type.Unit
                )
            }

            addComponentState {
                ComponentStateBuilder(
                    name = "shareList",
                    type = Type.GeneratedEntity
                )
            }
        }
    }
}