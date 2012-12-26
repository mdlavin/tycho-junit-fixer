package org.mdlavin.tycho.junit.fixer;

/*
 * Copyright 2012 Matt Lavin (matt.lavin@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import org.junit.Assert;
import org.junit.Test;

public class ManifestFileUpdaterTest {

	private Manifest loadManifest(String name) throws IOException {
		InputStream input = getClass().getResourceAsStream(name);
		try { 
			return new Manifest(input);
		} finally {
			input.close();
		}
	}
	
	@Test
	public void noRequiredBundles() throws Exception {
		Manifest manifest = loadManifest("noRequiredBundles.MF");
		new ManifestFileUpdater().update(manifest);
		Assert.assertEquals("Require-Bundle header", "org.junit4", manifest.getMainAttributes().getValue("Require-Bundle"));
	}
	
	@Test
	public void existingRequiredBundles() throws Exception {
		Manifest manifest = loadManifest("existingRequiredBundles.MF");
		new ManifestFileUpdater().update(manifest);
		Assert.assertEquals("Require-Bundle header", "test,org.junit4", manifest.getMainAttributes().getValue("Require-Bundle"));
	}
	
}
