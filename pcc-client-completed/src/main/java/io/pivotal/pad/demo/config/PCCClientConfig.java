package io.pivotal.pad.demo.config;

import java.util.Properties;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
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
    public Region<String, String> customerRegionBean(@Autowired ClientCache clientCache) {

    	ClientRegionFactory<String, String> customerRegionFactory = clientCache
				.createClientRegionFactory(ClientRegionShortcut.PROXY);
		Region<String, String> customerRegion = customerRegionFactory.create("Customer");

		return customerRegion;
    }



	@Bean(name="cacheManager")
	public GemfireCacheManager createGemfireCacheManager(@Autowired ClientCache gemfireCache) {

		GemfireCacheManager gemfireCacheManager = new GemfireCacheManager();
		gemfireCacheManager.setCache(gemfireCache);

		return gemfireCacheManager;
	}

}
