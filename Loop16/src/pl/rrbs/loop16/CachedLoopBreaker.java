package pl.rrbs.loop16;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

import pl.rrbs.loop16.breaker.GenericLoopBreaker;

/**
 * Stores breakers using weak references to provided keys
 *
 * @param <E>
 *            Type of exception that breakers throw
 * @param <T>
 *            Type of breaker to be stored
 */
public class CachedLoopBreaker<E extends Exception, T extends GenericLoopBreaker<E>> {
	private Map<Object, T> breakers = Collections.synchronizedMap(new WeakHashMap<>());
	private Function<Integer, T> newLoopBreaker;

	/**
	 * 
	 * @param newLoopBreaker
	 *            Function creating new instances of breakers, as argument it
	 *            will be provided with <strong>count</strong> that can be used
	 *            in i.e. {@link GenericLoopBreaker#GenericLoopBreaker}
	 */
	public CachedLoopBreaker(Function<Integer, T> newLoopBreaker) {
		this.newLoopBreaker = newLoopBreaker;
	}

	/**
	 * Performs a tick on a loop breaker assigned to given key. If no breaker is
	 * present it is initialized. Thread safe.
	 * 
	 * @param key
	 *            used to store and retrieve breakers (separate key -> separate
	 *            loop breaker)
	 * @param count
	 *            used for initialization of breaker
	 * @throws E
	 */
	public void tick(Object key, int count) throws E {
		if (!breakers.containsKey(key)) {
			breakers.put(key, newLoopBreaker.apply(count));
		}
		breakers.get(key).tick();
	}

	/**
	 * Force forget breaker. Helpful when keys are long living objects and weak
	 * reference won't ever be removed. Or if you want to force create new loop
	 * breaker for the same key. Thread safe.
	 * 
	 * @param key
	 *            used to store and retrieve breakers (separate key -> separate
	 *            loop breaker)
	 */
	public void forget(Object key) {
		breakers.remove(key);
	}
}
