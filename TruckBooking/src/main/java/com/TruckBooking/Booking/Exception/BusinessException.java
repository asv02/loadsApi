package com.TruckBooking.Booking.Exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String s) {
		super(BusinessException.generateMessage(s));
	}

	private static String generateMessage(String entity) {
		return "Error: " + entity;
	}

//    private static <K, V> Map<K, V> toMap(
//            Class<K> keyType, Class<V> valueType, Object... entries) {
//        if (entries.length % 2 == 1)
//            throw new IllegalArgumentException("Invalid entries");
//        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
//                .collect(HashMap::new,
//                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
//                        Map::putAll);
//    }

}
