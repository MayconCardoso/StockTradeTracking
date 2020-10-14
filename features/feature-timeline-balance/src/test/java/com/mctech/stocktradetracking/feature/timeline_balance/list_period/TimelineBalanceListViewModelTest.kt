package com.mctech.stocktradetracking.feature.timeline_balance.list_period

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.testing.BaseViewModelTest
import com.mctech.architecture.mvvm.x.core.testing.extentions.TestLiveDataScenario.Companion.testLiveDataScenario
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertFlow
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.timeline_balance.TimelineBalanceError
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.GetCurrentPeriodBalanceCase
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TimelineBalanceListViewModelTest : BaseViewModelTest() {
  private val loaderUseCase = mock<GetCurrentPeriodBalanceCase>()
  private val expectedList = TimelineBalanceFactory.listOf(10)
  private val expectedException = TimelineBalanceError.UnknownExceptionException

  private lateinit var viewModel: TimelineBalanceListViewModel

  @Before
  fun `before each test`() {
    viewModel = TimelineBalanceListViewModel(loaderUseCase)
  }

  @Test
  fun `should initialize components`() = testLiveDataScenario {
    assertLiveDataFlow(viewModel.periodList) {
      it.assertFlow(ComponentState.Initializing)
    }

    assertLiveDataFlow(viewModel.finalBalance) {
      it.assertFlow(ComponentState.Initializing)
    }
  }

  @Test
  fun `should return list`() = testLiveDataScenario {
    whenThisScenario {
      whenever(loaderUseCase.execute()).thenReturn(Result.Success(expectedList))
    }

    onThisAction {
      viewModel.interact(TimelineBalanceListInteraction.LoadTimelineComponent)
    }

    assertLiveDataFlow(viewModel.periodList) {
      it.assertFlow(
        ComponentState.Initializing,
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedList)
      )
    }

    assertLiveDataFlow(viewModel.finalBalance) {
      it.assertFlow(
        ComponentState.Initializing,
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedList.first())
      )
    }
  }

  @Test
  fun `should return error`() = testLiveDataScenario {
    whenThisScenario {
      whenever(loaderUseCase.execute()).thenReturn(Result.Failure(expectedException))
    }

    onThisAction {
      viewModel.interact(TimelineBalanceListInteraction.LoadTimelineComponent)
    }

    assertLiveDataFlow(viewModel.periodList) {
      it.assertFlow(
        ComponentState.Initializing,
        ComponentState.Loading.FromEmpty,
        ComponentState.Error(expectedException)
      )
    }

    assertLiveDataFlow(viewModel.finalBalance) {
      it.assertFlow(
        ComponentState.Initializing,
        ComponentState.Loading.FromEmpty,
        ComponentState.Error(expectedException)
      )
    }
  }
}