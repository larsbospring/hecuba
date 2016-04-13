package dk.gibon.hecuba.services

import com.google.common.collect.Maps
import dk.gibon.hecuba.commands.CassandraClusterSession

class CassandraService {
    private CassandraClusterSession session
    private Map<String, CassandraClusterSession> sessions = Maps.newConcurrentMap()

    CassandraClusterSession connect(String[] hosts) {
        def newSession = CassandraClusterSession.create(hosts)
        sessions.compute(newSession.clusterName, { clusterName, existingSession ->
            if (existingSession) {
                System.err.println("ALREADY SESSION...")
                newSession.close()
                return existingSession
            }
            newSession
        })
    }

    CassandraClusterSession cluster(String cluster) {
        sessions.get(cluster)
    }



}
