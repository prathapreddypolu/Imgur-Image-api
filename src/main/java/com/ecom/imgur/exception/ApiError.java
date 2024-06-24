package com.ecom.imgur.exception;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ApiError.
 */
@Data
@Builder
public class ApiError {
	
	String code;
	String message;

	/**
	 * Instantiates a new api error.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public ApiError(String code,String message) {
		this.code = code;
		this.message = message;
	}
}
