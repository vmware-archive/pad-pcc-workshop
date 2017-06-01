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

        // Configure PCC client cache with PCC native serializer (PDX)

        return gemfireConfig;
    }

    public ClientCache getGemfireClientCache() throws Exception {

    	// Create PCC client cache bean using cloud connectors
		ClientCache clientCache = null;

        return clientCache;
    }


	@Bean
	public Region<String, String> customerRegion(@Autowired ClientCache clientCache) {

		// Create a client side region bean with name Customer
		Region<String, String> customerRegion = null;

		return customerRegion;
	}

}
