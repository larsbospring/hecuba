package dk.gibon.hecuba.commands

import spock.lang.Specification

class CassandraClusterSessionTest extends Specification {

    void "test connection to cluster"() {
        when:
            CassandraClusterSession sut = CassandraClusterSession.create("127.0.0.1")

        then:
            new ClusterMetadataCommand(sut.session).execute().getClusterName() != null
    }

    void "test on each host"() {
        when:
        CassandraClusterSession sut = CassandraClusterSession.create("127.0.0.1")

        then:

        sut.onAllHosts({host -> host.address.toString() + ": " + host.getState()})
                .toBlocking()
                .forEach({ System.err.println(it)})
    }

    void "test some each hosts"() {
        when:
        CassandraClusterSession sut = CassandraClusterSession.create("127.0.0.1")

        then:

        sut.onHosts({ false }, {host -> host.address.toString() + ": " + host.getState()})
                .toBlocking()
                .forEach({ System.err.println(it)})
    }
}
