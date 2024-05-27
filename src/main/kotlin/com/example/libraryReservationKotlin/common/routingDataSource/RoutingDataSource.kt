package com.example.libraryReservationKotlin.common.routingDataSource

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): String {
        val isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        return if (isReadOnly) {
            "reader"
        } else {
            "writer"
        }
    }
}
