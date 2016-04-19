package pl.rrbs.loop16.breaker;

import pl.rrbs.loop16.exception.LoopBreak;

/**
 * Common case breaker
 *
 */
public class LoopBreaker extends GenericLoopBreaker<LoopBreak> {
	public LoopBreaker(int count) {
		super(count, LoopBreak::new);
	}
}
