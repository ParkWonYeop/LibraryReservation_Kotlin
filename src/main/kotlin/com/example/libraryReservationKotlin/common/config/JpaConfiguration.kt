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
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("com.example.libraryReservationKotlin")
        entityManagerFactoryBean.jpaVendorAdapter = jpaVendorAdapter()
        entityManagerFactoryBean.persistenceUnitName = "entityManager"
        entityManagerFactoryBean.setJpaProperties(hibernateProperties())
        return entityManagerFactoryBean
    }

    private fun jpaVendorAdapter(): JpaVendorAdapter {
        val hibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        hibernateJpaVendorAdapter.setShowSql(false)
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL)
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect")
        return hibernateJpaVendorAdapter
    }

    private fun hibernateProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", auto)
        properties.setProperty("hibernate.format_sql", "true")
        return properties
    }

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory")
        entityManagerFactory: LocalContainerEntityManagerFactoryBean,
    ): JpaTransactionManager {
        val jpaTransactionManager = JpaTransactionManager()
        jpaTransactionManager.entityManagerFactory = entityManagerFactory.`object`
        return jpaTransactionManager
    }
}
