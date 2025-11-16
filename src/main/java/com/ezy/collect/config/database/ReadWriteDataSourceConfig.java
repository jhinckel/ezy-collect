package com.ezy.collect.config.database;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryReadWrite",
        transactionManagerRef = "transactionManagerReadWrite",
        basePackages = {
                "com.ezy.collect.repository",
        }
)
public class ReadWriteDataSourceConfig extends AbstractDatabaseConfig {

    public final static String PERSISTENCE_UNIT_NAME = "read-write";

    @Primary
    @Bean(name = "readWriteDataSourceProperties")
    @ConfigurationProperties("spring.datasource.read-write")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @FlywayDataSource
    @Bean(name = "readWriteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read-write")
    public DataSource dataSource(@Qualifier("readWriteDataSourceProperties") DataSourceProperties properties) {
        HikariConfig hikariConfig = getHikariConfig(properties, PERSISTENCE_UNIT_NAME);
        DataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactoryReadWrite")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("readWriteDataSource") DataSource dataSource) {
        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSource);
            setPersistenceProviderClass(HibernatePersistenceProvider.class);
            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            setPackagesToScan(MODEL_PACKAGES);
            setJpaProperties(getJpaProperties());
        }};
    }

    @Primary
    @Bean(name = "transactionManagerReadWrite")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryReadWrite") EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
    }

}
