package com.mctech.stocktradetracking.library.architecture_code_generator.features

import com.mctech.architecture.generator.builder.ComponentStateBuilder
import com.mctech.architecture.generator.builder.FeatureGenerator
import com.mctech.architecture.generator.builder.UseCaseBuilder
import com.mctech.architecture.generator.class_contract.Parameter
import com.mctech.architecture.generator.class_contract.Type
import com.mctech.stocktradetracking.library.architecture_code_generator.ProjectSettings

object TimelineBalanceFeature {
  fun create() {

    FeatureGenerator.newFeature(
      settings = ProjectSettings.featureSettings,
      featureName = "TimelineBalance"
    ) {
      // ==========================================================================
      // DO NOT CHANGE THE CUSTOM TEMPLATES
      // IT WILL CREATE THE RIGHT FILE FOR THE CURRENT PROJECT
      // ==========================================================================
      presentationBuildGradle = ProjectSettings.presentationBuildGradle
      baseArchitecturePath = ProjectSettings.baseArchitecturePath

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "CreatePeriodCase",
          parameters = listOf(
            Parameter(
              name = "period",
              type = Type.GeneratedEntity
            )
          ),
          returnType = Type.Unit
        )
      }

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "EditPeriodCase",
          parameters = listOf(
            Parameter(
              name = "period",
              type = Type.GeneratedEntity
            )
          ),
          returnType = Type.Unit
        )
      }

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "GetListOfPeriodsBalanceCase",
          returnType = Type.ResultOf(
            Type.ListOfGeneratedEntity
          )
        )
      }

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "GetPeriodTransactionsCase",
          parameters = listOf(
            Parameter(
              name = "period",
              type = Type.GeneratedEntity
            )
          ),
          returnType = Type.ResultOf(
            Type.ListOfGeneratedEntity
          )
        )
      }

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "DepositMoneyCase",
          parameters = listOf(
            Parameter(
              name = "amount",
              type = Type.Double
            )
          ),
          returnType = Type.Unit
        )
      }

      addUseCase {
        UseCaseBuilder(
          isDaggerInjectable = false,
          name = "WithdrawMoneyCase",
          parameters = listOf(
            Parameter(
              name = "amount",
              type = Type.Double
            )
          ),
          returnType = Type.Unit
        )
      }

      addComponentState {
        ComponentStateBuilder(
          name = "periodList",
          type = Type.ListOfGeneratedEntity
        )
      }
    }
  }
}