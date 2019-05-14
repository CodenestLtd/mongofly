### What is MongFly?

MongFly allows you to manage changes of your mongo documents and propagate these changes in sync with your code changes when you perform deployments.

### Add MongFly to your project
```xml
<dependency>
    <groupId>uk.codenest</groupId>
	<artifactId>mongofly</artifactId>
	<version>1.0.3.RELEASE</version>
</dependency>
```

Maven repo for releases - http://repo1.maven.org/maven2

Internal versions - https://oss.sonatype.org/content/groups/public


### Or download mongfly from
repo1.maven.org - http://repo1.maven.org/maven2/uk/codenest/mongofly

### Travis Continuous Integration Build Status

Hopefully this thing is routinely green. Travis-CI monitors new code to this project and tests it on a variety of JDKs.

[![Build Status](https://travis-ci.org/CodenestLtd/mongofly.svg?branch=master)](https://travis-ci.org/CodenestLtd/mongofly)

## License
Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0

## Release code
```$xslt
./gradlew clean build uploadArchives closeAndReleaseRepository
```
