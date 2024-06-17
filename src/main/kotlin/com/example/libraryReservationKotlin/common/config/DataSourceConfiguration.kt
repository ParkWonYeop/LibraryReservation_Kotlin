package com.example.libraryReservationKotlin.common.config

import com.example.libraryReservationKotlin.common.routingDataSource.DataBaseProperty
import com.example.libraryReservationKotlin.common.routingDataSource.RoutingDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration(private val dataBaseProperty: DataBaseProperty) {
    @Bean("writerDataSource")
    fun writerDataSource(): HikariDataSource = HikariDataSource().apply {
        driverClassName = dataBaseProperty.driverClassName
        jdbcUrl = dataBaseProperty.writerUrl
        username = dataBaseProperty.writerUsername
        password = dataBaseProperty.writerPassword
    }

    @Bean("readerDataSource")
    fun readerDataSource(): HikariDataSource = HikariDataSource().apply {
        driverClassName = dataBaseProperty.driverClassName
        jdbcUrl = dataBaseProperty.readerUrl
        username = dataBaseProperty.readerUsername
        password = dataBaseProperty.readerPassword
    }

    @Bean
    fun routingDataSource(
        @Qualifier("writerDataSource") writerDataSource: DataSource,
        @Qualifier("readerDataSource") readerDataSource: DataSource,
    ): DataSource {
        val routingDataSource = RoutingDataSource()

        val datasourceMap = HashMap<Any, Any>()

        datasourceMap["writer"] = writerDataSource
        datasourceMap["reader"] = readerDataSource

        routingDataSource.setTargetDataSources(datasourceMap)

        routingDataSource.setDefaultTargetDataSource(writerDataSource)

        return routingDataSource
    }

    @Primary
    @Bean
    fun dataSource(
        @Qualifier("routingDataSource") routingDataSource: DataSource,
    ) = LazyConnectionDataSourceProxy(routingDataSource)
}
