package com.mctech.stocktradetracking.feature.timeline_balance.edit_period

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.testing.BaseViewModelTest
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertFirst
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertFlow
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertThat
import com.mctech.architecture.mvvm.x.core.testing.extentions.testLiveData
import com.mctech.architecture.mvvm.x.core.testing.testScenario
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.EditPeriodCase
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TimelineBalanceEditViewModelTest : BaseViewModelTest() {
    private val editPeriodCase          = mock<EditPeriodCase>()
    private val currentTimelineBalance  = TimelineBalanceFactory.single()

    private lateinit var viewModel      : TimelineBalanceEditViewModel

    @Before
    fun `before each test`() {
        viewModel = TimelineBalanceEditViewModel(editPeriodCase)
    }

    @Test
    fun `should initialize component`() = viewModel.currentPeriodState.testLiveData(
        assertion = {
            it.assertFlow(ComponentState.Initializing)
            verifyZeroInteractions(editPeriodCase)
        }
    )

    @Test
    fun `should show current timeline`() = viewModel.currentPeriodState.testLiveData(
        action = {
            viewModel.interact(
                TimelineBalanceEditInteraction.OpenPeriodDetails(
                    currentTimelineBalance
                )
            )
        },
        assertion = {
            it.assertFlow(
                ComponentState.Initializing,
                ComponentState.Success(currentTimelineBalance)
            )
        }
    )

    @Test
    fun `should navigate back`() = viewModel.commandObservable.testLiveData(
        action = {
            viewModel.interact(
                TimelineBalanceEditInteraction.EditPeriod("Test", 10.0, 30.0, 1111.0)
            )
        },
        assertion = {
            it.assertFirst().isEqualTo(TimelineBalanceEditCommand.NavigationBack)
        }
    )

    @Test
    fun `should edit timeline balance`() = testScenario(
        action = {
            viewModel.interact(
                TimelineBalanceEditInteraction.OpenPeriodDetails(
                    currentTimelineBalance
                )
            )
            viewModel.interact(
                TimelineBalanceEditInteraction.EditPeriod("Test", 10.0, 30.0, 1111.0)
            )
        },
        assertions = {
            val balanceSpy = argumentCaptor<TimelineBalance>()
            verify(editPeriodCase).execute(
                eq(currentTimelineBalance),
                balanceSpy.capture(),
                eq(1111.0)
            )
            verifyNoMoreInteractions(editPeriodCase)

            val balance = balanceSpy.firstValue
            balance.periodTag.assertThat().isEqualTo("Test")
            balance.periodInvestment.assertThat().isEqualTo(10.0)
            balance.periodProfit.assertThat().isEqualTo(30.0)
        }
    )

    @Test
    fun `should not edit timeline balance`() = testScenario(
        action = {
            viewModel.interact(
                TimelineBalanceEditInteraction.EditPeriod("Test", 10.0, 30.0, 1111.0)
            )
        },
        assertions = {
            verifyZeroInteractions(editPeriodCase)
        }
    )
}