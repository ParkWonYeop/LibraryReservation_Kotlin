package com.example.libraryReservationKotlin.common.config

import com.example.libraryReservationKotlin.common.routingDataSource.DataBaseProperty
import com.example.libraryReservationKotlin.common.routingDataSource.RoutingDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Profile("!test")
@Configuration
class DataSourceConfiguration(private val dataBaseProperty: DataBaseProperty) {
    @Bean
    fun writerDataSource(): HikariDataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = dataBaseProperty.driverClassName
        hikariDataSource.jdbcUrl = dataBaseProperty.writerUrl
        hikariDataSource.username = dataBaseProperty.writerUsername
        hikariDataSource.password = dataBaseProperty.writerPassword
        return hikariDataSource
    }

    @Bean
    fun readerDataSource(): HikariDataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = dataBaseProperty.driverClassName
        hikariDataSource.jdbcUrl = dataBaseProperty.readerUrl
        hikariDataSource.username = dataBaseProperty.readerUsername
        hikariDataSource.password = dataBaseProperty.readerPassword
        return hikariDataSource
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
