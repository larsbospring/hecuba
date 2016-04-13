package hecuba

import dk.gibon.hecuba.services.CassandraService

class ClusterController {
    CassandraService cassandraService
    String hosts

    def index() {
        [ 'hosts' : hosts]
    }


    def connect() {
        System.err.println("CONNECTING TO " + params.hosts)
        hosts = params.hosts
        def connect = cassandraService.connect(params.hosts)

        [ 'hosts' : hosts(connect.clusterName)]
    }

    def List<String> hosts(String clustner) {
        System.err.println("LISTING HOSTS IN CLUSTER")
        cassandraService.cluster(clustner).onAllHosts({host -> host.getAddress().toString()}).toList().toBlocking().first()
    }
}
