protoc-jar-maven-plugin
=======================
Simple maven plugin to compile .proto files into protobuf code using [protoc-jar](https://github.com/blackrock/protoc-jar-maven-plugin/tree/main/protoc-jar) embedded protoc compiler (multi-platform executable protoc JAR).

Provides some portability across the major platforms (Linux, Mac/OSX, and Windows). 

Supports embedded protoc versions 2.4.1, 2.5.0, 2.6.1, 3.11.4, 3.25.1, and any binaries (protoc and protoc plugins) available for download from maven central. Also supports pre-installed protoc binary.

## Installation

* Requires Maven 3.8.x to build.

## Usage

Sample usage - compile in main cycle into `target/generated-sources`, add generated sources to project, use default `protoc` version and default `src/main/protobuf` source folder:
```xml
<plugin>
	<groupId>io.github.blackrock</groupId>
	<artifactId>protoc-jar-maven-plugin</artifactId>
	<version>1.0.1</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>run</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

Sample usage - compile in main cycle into `target/generated-sources`, add generated sources to project, add all .proto sources to generated jar, include .proto files from direct maven dependencies, include additional imports:
```xml
<plugin>
	<groupId>io.github.blackrock</groupId>
	<artifactId>protoc-jar-maven-plugin</artifactId>
	<version>1.0.1</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>run</goal>
			</goals>
			<configuration>
				<addProtoSources>all</addProtoSources>
				<includeMavenTypes>direct</includeMavenTypes>
				<includeDirectories>
					<include>src/main/more_proto_imports</include>
				</includeDirectories>
				<inputDirectories>
					<include>src/main/protobuf</include>
				</inputDirectories>
			</configuration>
		</execution>
	</executions>
</plugin>
```

Sample usage - download protoc and plugin binaries from maven repo, multiple output targets (example: gRPC):
```xml
<plugin>
	<groupId>io.github.blackrock</groupId>
	<artifactId>protoc-jar-maven-plugin</artifactId>
	<version>1.0.1</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>run</goal>
			</goals>
			<configuration>
				<protocArtifact>com.google.protobuf:protoc:3.0.0</protocArtifact>
				<inputDirectories>
					<include>src/main/resources</include>
				</inputDirectories>
				<outputTargets>
					<outputTarget>
						<type>java</type>
					</outputTarget>
					<outputTarget>
						<type>grpc-java</type>
						<pluginArtifact>io.grpc:protoc-gen-grpc-java:1.0.1</pluginArtifact>
					</outputTarget>
				</outputTargets>
			</configuration>
		</execution>
	</executions>
</plugin>
```

Sample usage - javalite, multiple output targets:
```xml
<plugin>
	<groupId>io.github.blackrock</groupId>
	<artifactId>protoc-jar-maven-plugin</artifactId>
	<version>1.0.1</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>run</goal>
			</goals>
			<configuration>
				<inputDirectories>
					<include>src/main/resources</include>
				</inputDirectories>
				<outputTargets>
					<outputTarget>
						<type>java</type>
						<outputOptions>lite</outputOptions>
					</outputTarget>
					<outputTarget>
						<type>python</type>
						<outputOptions>lite</outputOptions>
					</outputTarget>
				</outputTargets>
			</configuration>
		</execution>
	</executions>
</plugin>
```

Sample usage - compile in test cycle, multiple output targets, don't alter project (`<addSources>: none`):
```xml
<plugin>
	<groupId>io.github.blackrock</groupId>
	<artifactId>protoc-jar-maven-plugin</artifactId>
	<version>1.0.1</version>
	<executions>
		<execution>
			<phase>generate-test-sources</phase>
			<goals>
				<goal>run</goal>
			</goals>
			<configuration>
				<protocVersion>2.4.1</protocVersion>
				<inputDirectories>
					<include>src/test/resources</include>
				</inputDirectories>
				<outputTargets>
					<outputTarget>
						<type>java</type>
						<addSources>none</addSources>
						<outputDirectory>src/test/java</outputDirectory>
					</outputTarget>
					<outputTarget>
						<type>descriptor</type>
						<addSources>none</addSources>
						<outputDirectory>src/test/resources</outputDirectory>
					</outputTarget>
				</outputTargets>
			</configuration>
		</execution>
	</executions>
</plugin>
```

## Contributing

### [Contributing](./CONTRIBUTING.md)
### [Code of conduct](./CODE_OF_CONDUCT.md)
### [Support](./SUPPORT.md)
### [Service Level Agreement](./SUPPORT.md)

## Contact

GitHub Issues: https://github.com/blackrock/protoc-jar-maven-plugin/issues

### 

Copyright © 2017-2024 os72, licensed under [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

Copyright © 2024 BlackRock, Inc. Distributed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

Forked from https://github.com/os72/protoc-jar-maven-plugin and https://github.com/os72/protoc-jar