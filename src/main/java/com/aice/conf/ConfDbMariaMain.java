package com.aice.conf;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Primary
@Configuration
@MapperScan(
    basePackages="com.aice.repo"
//    ,sqlSessionFactoryRef="sqlSessionFactory"
    ,sqlSessionTemplateRef="sqlSessionTemplate"
)
@EnableTransactionManagement(
    //interface를 생성 하지 않고 class를 주입해도 되도록
    proxyTargetClass = true
)
public class ConfDbMariaMain {
    /*@Profile({"local"})
    @Bean(name="dataSource")
    public DataSource dataSourceLocal() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:mariadb://3.34.145.169:3306/aice?autoReconnect=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("be_config_manager");
        dataSource.setPassword("847BD8B7135AA8C0084EDF98D2D230DC7767070B3DA7480FF9096F07BD5D1583");
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(1);
        dataSource.setMaxIdle(1);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //이거 중요함 (설정 안하면 롤백 안됨)
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }*/

    /*@Profile({"dev"})
    @Bean(name="dataSource")
    public DataSource dataSourceDev() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:mariadb://10.0.11.30:3306/aice?autoReconnect=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("be_config_manager");
        dataSource.setPassword("847BD8B7135AA8C0084EDF98D2D230DC7767070B3DA7480FF9096F07BD5D1583");
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(1);
        dataSource.setMaxIdle(1);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //이거 중요함 (설정 안하면 롤백 안됨)
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }*/

    /*@Profile({"idc","prod"})
    @Bean(name="dataSource")
    public DataSource dataSourceIdc() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:mariadb://workcenter.cn3vo2h0sfkr.ap-northeast-2.rds.amazonaws.com:3306/aice?useSSL=false&amp;characterEncoding=utf8");
        dataSource.setUsername("be_config_manager");
        dataSource.setPassword("847BD8B7135AA8C0084EDF98D2D230DC7767070B3DA7480FF9096F07BD5D1583");
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(1);
        dataSource.setMaxIdle(1);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //이거 중요함 (설정 안하면 롤백 안됨)
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }*/

    /*@Profile({"release"})
    @Bean(name="dataSource")
    public DataSource dataSourceRelease() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:mariadb://10.20.27.240:3306/aice?useSSL=false&amp;characterEncoding=utf8");
        dataSource.setUsername("be_config_manager");
        dataSource.setPassword("847BD8B7135AA8C0084EDF98D2D230DC7767070B3DA7480FF9096F07BD5D1583");
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(1);
        dataSource.setMaxIdle(1);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //이거 중요함 (설정 안하면 롤백 안됨)
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }*/

    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(
        @Qualifier("dataSource") DataSource dataSource
    ) {
        DataSourceTransactionManager txmanager = new DataSourceTransactionManager(dataSource);
        txmanager.setNestedTransactionAllowed(false);
        return txmanager;
    }

    @Bean(name="mybatisConfig")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
        conf.setCacheEnabled(false);
        conf.setCallSettersOnNulls(true);
        conf.setUseGeneratedKeys(true);
        conf.setDefaultExecutorType(ExecutorType.REUSE);
        conf.setSafeResultHandlerEnabled(false);
        conf.setMapUnderscoreToCamelCase(true);
        return conf;
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
        @Qualifier("dataSource") DataSource dataSource
        ,@Qualifier("mybatisConfig") org.apache.ibatis.session.Configuration mybatisConfig
    ) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setConfiguration(mybatisConfig);
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.aice.dao");
        return (SqlSessionFactory)sessionFactory.getObject();
    }

//    @Bean(name="sqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(
//        @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory
//    ) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
