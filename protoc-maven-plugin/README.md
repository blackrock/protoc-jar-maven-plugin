protoc-jar-maven-plugin
=======================
Simple maven plugin using [protoc-jar](https://github.com/blackrock/protoc-jar-maven-plugin/tree/main/protoc-jar) embedded protoc compile.
Provides some portability across the major platforms (Linux, Mac/OSX, and Windows). 
At build time, the plugin detects the platform and executes the corresponding protoc binary.

* Support for FreeBSD on x86 platform (freebsd-x86_64), thanks [kjopek](https://github.com/kjopek)
* Support for Solaris on x86 platform (sunos-x86_64), thanks [siepkes](https://github.com/siepkes)
* Support for Linux on POWER8 platform (linux-ppcle_64), now from Google
  * Older versions (up to 3.6.0), thanks to [Apache SystemML](https://github.com/apache/systemml) folks ([nakul02](https://github.com/nakul02))
* Support for Linux on ARM platform (linux-aarch_64), now from Google
  * Older versions (2.4.1, 2.6.1, 3.4.0), thanks [garciagorka](https://github.com/garciagorka)

Binaries
* https://repo.maven.apache.org/maven2/com/google/protobuf/protoc/

#### Credits

Originally based on
* https://github.com/igor-petruk/protobuf-maven-plugin

Forked from
* https://github.com/os72/protoc-jar-maven-plugin
