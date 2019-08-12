package ar.com.fjs.callcenter;

import java.util.Random;

public class DispatcherUtils {

	private static final int CALL_DURATION_SECONDS_MIN = 5;
	private static final int CALL_DURATION_SECONDS_MAX = 10;

	private static Random random;

	static {
		random = new Random();
	}

	private DispatcherUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static int randomDurationCall() {
		return random.nextInt((CALL_DURATION_SECONDS_MAX - CALL_DURATION_SECONDS_MIN) + 1) + CALL_DURATION_SECONDS_MIN;
	}
}
