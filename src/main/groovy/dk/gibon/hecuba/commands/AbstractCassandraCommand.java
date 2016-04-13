package dk.gibon.hecuba.commands;

import com.datastax.driver.core.Session;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public abstract class AbstractCassandraCommand<T> extends HystrixCommand<T> {

	protected Session session;

	public AbstractCassandraCommand(String key, Session session) {
		super(HystrixCommandGroupKey.Factory.asKey(key));
		this.session = session;
	}
}
