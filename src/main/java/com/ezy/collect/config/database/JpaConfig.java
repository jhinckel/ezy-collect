package com.ezy.collect.config.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum JpaConfig {

    OPEN_IN_VIEW("spring.jpa.open-in-view", "spring.jpa.open-in-view"),
    FORMAT_SQL("spring.jpa.properties.hibernate.format_sql", "spring.jpa.properties.hibernate.format_sql"),
    SHOW_SQL("spring.jpa.hibernate.naming.physical-strategy", "spring.jpa.show-sql"),
    DIALECT("hibernate.dialect", "spring.jpa.database-platform"),
    PHYSICAL_STRATEGY("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

    private String jpaPropertyKey;
    private String configKey;

}
