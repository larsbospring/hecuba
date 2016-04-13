package dk.gibon.hecuba.commands;

import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class ClusterMetadataCommand extends AbstractCassandraCommand<Metadata> {

	public ClusterMetadataCommand(Session session) {
		super("ClusterInfo", session);
	}

	@Override
	protected Metadata run() throws Exception {
		return session.getCluster().getMetadata();
	}
}
