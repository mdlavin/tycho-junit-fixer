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

import java.util.jar.Manifest;

import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;

class ManifestFileUpdater {
	
	private final static String ENTRY_IMPORT_PACKAGE = "Import-Package";
	private final static String ENTRY_REQUIRE_BUNDLE = "Require-Bundle";
	
	private final static String JUNIT_BUNDLE = "org.junit4";

    public void update(Manifest manifest) throws MojoExecutionException {
    	boolean requiresJunitBundle = false;
    	
    	String importPackageEntry = manifest.getMainAttributes().getValue(ENTRY_IMPORT_PACKAGE);
    	if (importPackageEntry != null) {
			ManifestElement[] elements = parseHeader(ENTRY_IMPORT_PACKAGE, importPackageEntry);
			for (ManifestElement element : elements) {
				if ("org.junit".equals(element.getValue())) {
					requiresJunitBundle = true;
				}
			}
    	}
    	
    	if (requiresJunitBundle) {
	    	String requireBundleEntry = manifest.getMainAttributes().getValue(ENTRY_REQUIRE_BUNDLE);
	    	if (requireBundleEntry != null) {
	    		boolean needsNewRequires = true;
				ManifestElement[] elements = parseHeader(ENTRY_REQUIRE_BUNDLE, requireBundleEntry);
				for (ManifestElement element : elements) {
					if (JUNIT_BUNDLE.equals(element.getValue())) {
						// Already depends on the bundle, no update needed
						needsNewRequires = false;
					}
				}
				
				if (needsNewRequires) {
					manifest.getMainAttributes().putValue(ENTRY_REQUIRE_BUNDLE, requireBundleEntry + "," + JUNIT_BUNDLE);
				}
	    	} else {
	    		manifest.getMainAttributes().putValue(ENTRY_REQUIRE_BUNDLE, JUNIT_BUNDLE);
	    	}
    	}
    }
    
    private ManifestElement[] parseHeader(String header, String value) throws MojoExecutionException {
    	try {
			return ManifestElement.parseHeader(header, value);
		} catch (BundleException e) {
			throw new MojoExecutionException("The manifest file could not be parsed", e);
		}
    }
	
}
