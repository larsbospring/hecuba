package dk.gibon.hecuba.commands;

import java.util.function.Function;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

public class HostCommand<T> extends AbstractCassandraCommand<T> {
	private final Host host;
	private final Function<Host, T> operation;

	public HostCommand(Session session, Host host, Function<Host, T> operation) {
		super("HostCommand", session);
		this.host = host;
		this.operation = operation;
	}

	@Override
	protected T run() throws Exception {
		return operation.apply(host);
	}
}
