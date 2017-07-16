/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.exceptions;

// Enum to hold different error types of Bus Route File validation
public enum FileErrorType {
	FILE_READE_ISSUE,
	INVALID_MINIMUM_ROUTE_TOKENS,
	DUPLICATE_ROUTE,
    DUPLICATE_STATION_IN_ROUTE,
    ROUTE_COUNT_MISSMATCH,
    INVALID_ROUTE_COUNT,
    MAX_STATION_COUNT_EXCEEDS,
    MAX_STATION_COUNT_EXCEEDS_PER_ROUTE,
    INVALID_CONTENT_IN_FILE;
}
