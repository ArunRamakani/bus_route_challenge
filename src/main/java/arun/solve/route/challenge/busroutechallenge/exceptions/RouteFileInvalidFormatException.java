/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.exceptions;

/**
 * 
 * RouteFileInvalidFormatException - Exception class to hold exception happened
 * during validation and its error type
 * 
 **/
@SuppressWarnings("serial")
public class RouteFileInvalidFormatException extends Exception {

	// Holds validation error type enum
	private FileErrorType errorType;

	public FileErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(FileErrorType errorType) {
		this.errorType = errorType;
	}

	public RouteFileInvalidFormatException(String message, FileErrorType errorType) {
		super(message);
		this.errorType = errorType;
	}

}
