package com.mctech.stocktradetracking.feature.timeline_balance.add_period

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.testing.BaseViewModelTest
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertFirst
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertThat
import com.mctech.architecture.mvvm.x.core.testing.extentions.testLiveData
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.CreatePeriodCase
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TimelineBalanceAddViewModelTest : BaseViewModelTest(){
    private val createPeriodCase    = mock<CreatePeriodCase>()
    private lateinit var viewModel  : TimelineBalanceAddViewModel

    @Before
    fun `before each test`() {
        viewModel = TimelineBalanceAddViewModel(createPeriodCase)
    }

    @Test
    fun `should navigate back`() = viewModel.commandObservable.testLiveData(
        action = {
            viewModel.interact(TimelineBalanceAddInteraction.CreatePeriod(
                period = "",
                monthInvestment = 0.0,
                monthProfit = 0.0
            ))
        },
        assertion = {
            it.assertFirst().isEqualTo(TimelineBalanceAddCommand.NavigationBack)
        }
    )

    @Test
    fun `should save timeline balance`() = testScenario(
        action = {
            viewModel.interact(TimelineBalanceAddInteraction.CreatePeriod(
                period = "Test",
                monthInvestment = 10.0,
                monthProfit = 30.0
            ))
        },
        assertions = {
            val balanceSpy = argumentCaptor<TimelineBalance>()
            verify(createPeriodCase).execute(balanceSpy.capture())
            verifyNoMoreInteractions(createPeriodCase)

            val balance = balanceSpy.firstValue
            balance.periodTag.assertThat().isEqualTo("Test")
            balance.periodInvestment.assertThat().isEqualTo(10.0)
            balance.periodProfit.assertThat().isEqualTo(30.0)
        }
    )

    @Test
    fun `should not change anything`() = testScenario(
        action = {
            viewModel.interact(object : UserInteraction{})
        },
        assertions = {
            verifyZeroInteractions(createPeriodCase)
        }
    )
}