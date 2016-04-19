package pl.rrbs.loop16.breaker;

import pl.rrbs.loop16.exception.SilentLoopBreak;

/**
 * Breaker that can be used inside i.e. lambda expressions which cannot throw
 * normal exceptions
 *
 */
public class SilentLoopBreaker extends GenericLoopBreaker<SilentLoopBreak> {
	public SilentLoopBreaker(int count) {
		super(count, SilentLoopBreak::new);
	}
}
