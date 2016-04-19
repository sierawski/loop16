package pl.rrbs.loop16.breaker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Base class for breakers.
 *
 * @param <E>
 *            Type of exception to be thrown when ticks exceeded given count
 */
public class GenericLoopBreaker<E extends Exception> {
	private AtomicInteger count;
	private Supplier<E> newException;

	/**
	 * 
	 * @param count
	 *            How many iterations are allowed
	 * @param newException
	 *            Supplier of exception that will be thrown when iterations
	 *            number was exceeded
	 */
	public GenericLoopBreaker(int count, Supplier<E> newException) {
		this.count = new AtomicInteger(count);
		this.newException = newException;
	}

	/**
	 * Method used to count how many iterations have occurred. Thread safe.
	 * 
	 * @throws E
	 *             when iterations exceeded the value provided in constructor
	 */
	public void tick() throws E {
		if (count.decrementAndGet() < 0) {
			throw newException.get();
		}
	}
}
