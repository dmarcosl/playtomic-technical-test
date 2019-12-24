package com.playtomic.tests.wallet.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean("converterService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @SuppressWarnings({"unchecked"})
    private Set<Converter<?, ?>> getConverters() {
        return new HashSet<Converter<?, ?>>((Collection) context
                .getBeansWithAnnotation(ConverterService.class).values());
    }
}
