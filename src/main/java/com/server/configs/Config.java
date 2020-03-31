package com.server.configs;

import com.server.utils.PropertiesForConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;

/**
 * created by xev11
 */
/*


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.server")
@EnableJpaRepositories(basePackages = "com.server.repos")
@PropertySource(value = "jdbc.properties")
public class Config {


    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("jdbc.password")
    private String password;

    @Bean(name = "dataSource")
    public DataSource dataSource(){

        try {
            SimpleDriverDataSource source = new SimpleDriverDataSource();
            Class<? extends Driver> aClass = (Class<? extends Driver>) Class.forName(driverClassName);
            source.setDriverClass(aClass);
            source.setUrl(url);
            source.setUsername(user);
            source.setPassword("");
            return source;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.server");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(PropertiesForConfigs.hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new JpaTransactionManager(entityManagerFactory());
    }


}


*/

