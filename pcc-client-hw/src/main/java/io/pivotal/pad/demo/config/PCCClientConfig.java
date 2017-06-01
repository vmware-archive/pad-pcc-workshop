package io.pivotal.pad.demo.config;

import java.util.Properties;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.xml.GemfireConstants;

@Profile("local")
@Configuration
public class PCCClientConfig {

	@Bean
    Properties gemfireProperties(@Value("${gemfire.log.level:config}") String logLevel,
                                 @Value("${application.name:GemFireClientDemo") String applicationName) {
        Properties gemfireProperties = new Properties();

        gemfireProperties.setProperty("name", applicationName);
        gemfireProperties.setProperty("log-level", logLevel);

        return gemfireProperties;
    }

    PdxSerializer pdxSerializer() {

        PdxSerializer pdxSerializer = new ReflectionBasedAutoSerializer(".*");
        return pdxSerializer;
    }

    @Bean(name = GemfireConstants.DEFAULT_GEMFIRE_CACHE_NAME)
    ClientCache gemfireCache(@Qualifier("gemfireProperties") Properties gemfireProperties,
                             @Value("${gemfire.locator.host:localhost}") String locatorHost,
                             @Value("${gemfire.locator.port:10334}") int locatorPort) {

        ClientCache gemfireCache = new ClientCacheFactory(gemfireProperties)
                .addPoolLocator(locatorHost, locatorPort)
                .setPdxSerializer(pdxSerializer())
                .setPdxReadSerialized(false).create();

        return gemfireCache;
    }

	@Bean
    public ClientRegionFactoryBean<String, String> customerRegion (@Autowired ClientCache gemfireCache) {

        ClientRegionFactoryBean<String, String> customerRegion =
        		new ClientRegionFactoryBean<String, String>();

        customerRegion.setCache(gemfireCache);
        customerRegion.setClose(false);
        customerRegion.setShortcut(ClientRegionShortcut.PROXY);
        customerRegion.setLookupEnabled(true);
        return customerRegion;
    }

}
