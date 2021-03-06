[Cassandra] integration test example
====================================

This project demonstrates how you can write unit and integration tests for code that works with Cassandra and have coverage report for it.

How to run
----------

    git clone https://github.com/ash2k/cassandra-integration-test.git
    cd ./cassandra-integration-test
    mvn clean verify

After this you'll get `./target/site/jacoco` directory with coverage report.

How it works
------------

Unit tests are written with help of [TestNG] and [Mockito] and run by [maven-surefire-plugin]. Nothing special here.

Integration tests are also powered by TestNG which is executed by [maven-failsafe-plugin] in this case.

Network ports are reserved by [build-helper-maven-plugin]'s `reserve-network-port` goal. This lowers the probability that build will fail because of used network ports (e.g. Cassandra already runs on building host).

Cassandra node is started at the `pre-integration-test` [Maven build lifecycle] phase and is stopped at `post-integration-test` phase. This is done by [cassandra-maven-plugin]'s `start` and `stop` goals respectively. Also, before the integration tests are run, Cassandra's data directory is wiped out by execution of `delete` goal of cassandra-maven-plugin.

Code coverage report is generated by [jacoco-maven-plugin].

[Cassandra]: http://cassandra.apache.org/
[TestNG]: http://testng.org/
[Mockito]: http://code.google.com/p/mockito/
[maven-surefire-plugin]: http://maven.apache.org/plugins/maven-surefire-plugin/
[maven-failsafe-plugin]: http://maven.apache.org/plugins/maven-failsafe-plugin/
[build-helper-maven-plugin]: http://mojo.codehaus.org/build-helper-maven-plugin/
[Maven build lifecycle]: http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference
[cassandra-maven-plugin]: http://mojo.codehaus.org/cassandra-maven-plugin/
[jacoco-maven-plugin]: http://www.eclemma.org/jacoco/
