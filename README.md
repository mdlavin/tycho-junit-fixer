Motivation for tycho-junit-fixer
================================

A Maven plugin to avoid NPEs in Tycho's JUnit running code.  When working with OSGi projects, I prefer to use
Import-Package entries in the MANIFEST.MF file rather than Require-Bundle because importing packages is more tolerant 
of project refactoring in the future.  However, Tycho currently had a problem with running JUnit 4 based tests with 
Import-Package instead of Require-Bundle.  The Tycho bug originally comes from Surefire, [bug 825](http://jira.codehaus.org/browse/SUREFIRE-825), and was fixed in
Surefire 1.12.1.  However, as of Tycho 0.16.0 the a version of Surefire with the fix has not been adopted yet and is
tracked in [Eclipse defect 369266](https://bugs.eclipse.org/bugs/show_bug.cgi?id=369266).

Using tycho-junit-fixer
=======================

Using the maven plugin allows you to leave Import-Package in your test projects while working in Eclipse and the plugin
will automatically 'fix' the MANIFEST.MF files before Tycho starts to use them.  To use the plugin, just add the
following entry to your pom.xml file:

```
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.mdlavin</groupId>
        <artifactId>tycho.junit.fixer</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <executions>
          <execution>
            <id>add-junit-bundle-dependency</id>
            <phase>generate-sources</phase>
            <goals>
              <goal>add-junit-bundle-dependency</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  </build>
```

Code quality
============
The code is currently good enough for my automated builds, but no extensive testing has been done to make it generally
useful.  Please contribute patches if you find changes that would help you.
