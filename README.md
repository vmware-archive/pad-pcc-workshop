# pad-pcc-workshop

## Getting Started with PCC Client

###### Step 1: Create PCC Client project

1. Create a spring boot app from Spring Initilizer or through Spring Tool Suite IDE

	http://start.spring.io

2. Add GemFire and Web project dependencies 

 Note: We will be using Spring-data-gemfire project for connecting to PCC clients


3. Add the below Spring cloud GemFire connector dependencies. This will simplify retrieving PCC connection information and creating PCC Client Cache.

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-cloudfoundry-connector</artifactId>
</dependency>

<dependency>
	<groupId>io.pivotal.spring.cloud</groupId>
	<artifactId>spring-cloud-gemfire-cloudfoundry-connector</artifactId>
	<version>1.1.0.RELEASE</version>
</dependency>

<dependency>
	<groupId>io.pivotal.spring.cloud</groupId>
	<artifactId>spring-cloud-gemfire-spring-connector</artifactId>
	<version>1.1.0.RELEASE</version>
</dependency>
```

###### Step 2: Creation of PCC client cache using cloud connectors

1. Configure PCC client cache with PCC native serializer (PDX). We will create an instance of GemfireServiceConnectorConfig for configuring PCC Client

```
    GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
    gemfireConfig.setPoolSubscriptionEnabled(true);
    gemfireConfig.setPdxSerializer(new ReflectionBasedAutoSerializer(".*"));
    gemfireConfig.setPdxReadSerialized(false);

```
2. Create PCC client cache bean

```
Cloud cloud = new CloudFactory().getCloud();
ClientCache clientCache = cloud.getSingletonServiceConnector(ClientCache.class,  createGemfireConnectorConfig());

```

###### Step 3: Create a client side region bean with name `Customer`

```
ClientRegionFactory<String, PdxInstance> customerRegionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
Region<String, PdxInstance> customerRegion = customerRegionFactory.create("Customer");
```

Note: For complete implementation, please refer to `PCCClientCloudConfig.java`


###### Step 4: CRUD operations using PCC APIs - get(), put(), getAll(), putAll(), destroy()


## Create PCC OnDemand service
Services can be created through Apps Manager Marketplace or by executing cf cli commands

###### Step 1: create a PCC OnDemand service in your org & space

```
cf create-service p-cloudcache extra-small pcc-dev-cluster

```

###### Step 2: Create service key for retrieving connection information for GFSH cli

```
cf create-service-key pcc-dev-cluster devkey
```

###### Step 3: Retrieve url for PCC cli (GFSH) and corresponding credentials 

```
cf service-key pcc-dev-cluster devkey
```

###### Step 4: Login into to PCC cli (GFSH)

```
connect --use-http=true --url=http://gemfire-xxxx-xxx-xx-xxxx.system.excelsiorcloud.com/gemfire/v1 --user=operator --password=*******
```

###### Step 5: create PCC region with name `Customer`

Note: Region name created on PCC server and client should match

```
create region --name=Customer --type=PARTITION_REDUNDANT
```

## Bind PCC Client with PCC service

###### Bind to PCC service by specifying service name in the manifest.yml

```
---
applications:
- name: PCC-Client
  random-route: false
  path: target/PCC-Client-0.0.1-SNAPSHOT.jar
  services:
  - pcc-dev-cluster
```


