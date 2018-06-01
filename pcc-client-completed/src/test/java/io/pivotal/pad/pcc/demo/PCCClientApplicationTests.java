package io.pivotal.pad.pcc.demo;

import io.pivotal.pad.demo.config.PCCClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest(classes=PCCClientConfig.class)
public class PCCClientApplicationTests {

	@Test
	public void contextLoads() {
	}

}
