package cz.linksoft.hr.test.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.linksoft.hr.test.IntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class ApplicationDataResourceProviderTest {

	@Autowired
	private ApplicationDataResourceProvider dataResourceProvider;

	@Test
	public void providedDataFileResourcesAreReadable() throws IOException {
		for (Resource resource : dataResourceProvider.getDataFileResources()) {
			String filename = resource.getFilename();
			assertTrue("File does not exist: " + filename, resource.exists());
			assertTrue("File is not readable: " + filename, resource.isReadable());
			File file;
			try {
				file = resource.getFile();
			} catch (IOException e) {
				throw new AssertionError("Cannot get file from resource with " + filename + ": " + resource, e);
			}
			assertThat("Size of the file " + file, file.length(), not(equalTo(0)));
		}
	}

}