package pl.rrbs.loop16;

import pl.rrbs.loop16.breaker.LoopBreaker;
import pl.rrbs.loop16.breaker.SilentLoopBreaker;
import pl.rrbs.loop16.exception.LoopBreak;
import pl.rrbs.loop16.exception.SilentLoopBreak;

/**
 * Class to be use the breaker functionalities as single lines of codes, without
 * the need to manually create any object.
 *
 */
public class StaticLoopBreaker {
	private StaticLoopBreaker() {
	}

	private static CachedLoopBreaker<LoopBreak, LoopBreaker> breakers = new CachedLoopBreaker<>(LoopBreaker::new);
	private static CachedLoopBreaker<SilentLoopBreak, SilentLoopBreaker> silentBreakers = new CachedLoopBreaker<>(
			SilentLoopBreaker::new);

	/**
	 * Checks whether number of iterations when tick happened for given key
	 * aren't greater than given count. If so, it throws. Thread safe.
	 * 
	 * @param key
	 *            assigned to single instance of loop breaker (loop breakers for
	 *            <strong>tick</strong> and {@link #siletTick} are separate)
	 * @param count
	 *            how many iterations are allowed
	 * @throws LoopBreak
	 *             when iterations exceeded count
	 */
	static public void tick(Object key, int count) throws LoopBreak {
		breakers.tick(key, count);
	}

	/**
	 * {@link #tick} method that throws unchecked exception. Thread safe.
	 * 
	 * @param key
	 *            assigned to single instance of loop breaker (loop breakers for
	 *            {@link #tick} and siletTick are separate)
	 * @param count
	 *            how many iterations are allowed
	 * @throws SilentLoopBreak
	 *             when iterations exceeded count
	 */
	static public void silentTick(Object key, int count) throws SilentLoopBreak {
		silentBreakers.tick(key, count);
	}

	/**
	 * Forgets breakers assigned to key for both {@link #tick} and
	 * {@link #silentTick}
	 * 
	 * @param key
	 */
	static public void forget(Object key) {
		breakers.forget(key);
		silentBreakers.forget(key);
	}
}
