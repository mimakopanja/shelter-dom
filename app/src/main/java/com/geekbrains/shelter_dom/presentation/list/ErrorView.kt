package com.geekbrains.shelter_dom.presentation.list

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ErrorView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String,
                  withRetry: Boolean = false,
                  callRetry: () -> Unit = {})

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(messageId: Int, withRetry: Boolean = false, callRetry: () -> Unit = {})
}