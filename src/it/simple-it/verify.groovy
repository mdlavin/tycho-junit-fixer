File manifestFile = new File( basedir, "META-INF/MANIFEST.MF" );

assert new String(manifestFile.readBytes(), "UTF-8").contains("Require-Bundle: org.junit4");
