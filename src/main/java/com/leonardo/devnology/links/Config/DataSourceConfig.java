package com.leonardo.devnology.links.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://db.nymdivqjcixfjbmylgda.supabase.co:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("y1RcsomQqDAOT1x9");
        return dataSource;
    }
}
