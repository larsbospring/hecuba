package dk.gibon.hecuba.commands;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

import rx.Observable;
import rx.schedulers.Schedulers;

public class CassandraClusterSession implements Closeable {
	private final Session session;

	public CassandraClusterSession(Session session) {
		this.session = session;
	}

	@Override
	public void close() throws IOException {
		session.close();
	}

	public String getClusterName() {
		return session.getCluster().getClusterName();
	}

	/**
	 * Invoke {@code operation} on the hosts matching {@code filter}
	 * @param filter The filtering predicate
	 * @param operation The operation to execute
	 * @param <T> The return type for each operation
	 * @return An observable
	 */
	public <T> Observable<T> onHosts(Predicate<Host> filter, Function<Host, T> operation) {
		final Observable<Observable<T>> observables = Observable
				.just(session.getCluster())
				.observeOn(Schedulers.io())
				.flatMapIterable(c -> c.getMetadata().getAllHosts())
				.filter(filter::test)
				.map(host -> new HostCommand<>(session, host, operation).toObservable());

		return Observable.merge(observables);
	}

	/**
	 * Invoke {@code operation} on all hosts
	 * @param operation The operation to execute
	 * @param <T> The return type for each operation
	 * @return An observable
	 */
	public <T> Observable<T> onAllHosts(Function<Host, T> operation) {
		return onHosts(host -> true, operation);
	}

	public static CassandraClusterSession create(String... hosts) {
		return new CassandraClusterSession(Cluster.builder().addContactPoints(hosts).build().newSession());
	}
}
