package io.pivotal.pad.demo.config;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

@Configuration
@Profile("cloud")
public class PCCClientCloudConfig {


	public ServiceConnectorConfig createGemfireConnectorConfig() {

		GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
		gemfireConfig.setPoolSubscriptionEnabled(true);
		gemfireConfig.setPdxSerializer(new ReflectionBasedAutoSerializer(".*"));
		gemfireConfig.setPdxReadSerialized(false);

		return gemfireConfig;
	}

	public ClientCache getGemfireClientCache() throws Exception {

		Cloud cloud = new CloudFactory().getCloud();
		ClientCache clientCache = cloud.getSingletonServiceConnector(ClientCache.class, createGemfireConnectorConfig());

		return clientCache;
	}

	@Bean
	public Region<String, String> customerRegion(@Autowired ClientCache clientCache) {

		ClientRegionFactory<String, String> customerRegionFactory = clientCache
				.createClientRegionFactory(ClientRegionShortcut.PROXY);
		Region<String, String> customerRegion = customerRegionFactory.create("Customer");

		return customerRegion;
	}

}
