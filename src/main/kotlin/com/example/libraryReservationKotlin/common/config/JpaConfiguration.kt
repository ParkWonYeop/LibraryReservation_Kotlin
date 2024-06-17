package com.example.libraryReservationKotlin.common.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@EnableJpaRepositories(basePackages = ["com.example.libraryReservationKotlin"])
@EntityScan(basePackages = ["com.example.libraryReservationKotlin"])
@Configuration
@EnableTransactionManagement
class JpaConfiguration(
    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val auto: String,
    @Value("\${spring.datasource.database-name}")
    private val databaseName: String,
) {
    @Bean
    fun entityManagerFactory(
        @Qualifier("dataSource") dataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean().apply {
        this.dataSource = dataSource
        setPackagesToScan("com.example.libraryReservationKotlin")
        jpaVendorAdapter = jpaVendorAdapter()
        persistenceUnitName = "entityManager"
        setJpaProperties(hibernateProperties())
    }

    private fun jpaVendorAdapter(): JpaVendorAdapter = HibernateJpaVendorAdapter().also {
        if (databaseName == "mysql") {
            it.setShowSql(false)
            it.setDatabase(Database.MYSQL)
            it.setDatabasePlatform("org.hibernate.dialect.MySQLDialect")
        } else if (databaseName == "h2") {
            it.setShowSql(true)
            it.setDatabase(Database.H2)
            it.setDatabasePlatform("org.hibernate.dialect.H2Dialect")
        }
    }

    private fun hibernateProperties(): Properties = Properties().apply {
        setProperty("hibernate.hbm2ddl.auto", auto)
        setProperty("hibernate.format_sql", "true")
    }

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory")
        entityManagerFactory: LocalContainerEntityManagerFactoryBean,
    ): JpaTransactionManager = JpaTransactionManager().apply {
        this.entityManagerFactory = entityManagerFactory.`object`
    }
}
