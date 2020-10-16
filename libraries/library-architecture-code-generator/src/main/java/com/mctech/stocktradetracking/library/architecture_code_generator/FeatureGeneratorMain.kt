package com.mctech.stocktradetracking.library.architecture_code_generator

import com.mctech.stocktradetracking.library.architecture_code_generator.features.StockShareFeature
import com.mctech.stocktradetracking.library.architecture_code_generator.features.TimelineBalanceFeature

// It is just a library I built to avoid writing that all boilerplate every time we create a new feature module.
// For mor information about the library check it out here: https://github.com/MayconCardoso/ArchitectureBoilerplateGenerator
fun main() {
  StockShareFeature.create()
  TimelineBalanceFeature.create()
}