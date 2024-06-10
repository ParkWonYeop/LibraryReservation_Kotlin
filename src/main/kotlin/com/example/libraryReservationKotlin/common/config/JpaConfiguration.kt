package com.example.libraryReservationKotlin.common.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@Profile("!test")
class JpaConfiguration(
    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val auto: String,
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

    private fun jpaVendorAdapter(): JpaVendorAdapter = HibernateJpaVendorAdapter().apply {
        setShowSql(false)
        setDatabase(Database.MYSQL)
        setDatabasePlatform("org.hibernate.dialect.MySQLDialect")
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
