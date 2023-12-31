package com.chiwawa.lionheart.common.exception.model;

import com.chiwawa.lionheart.common.exception.ErrorCode;

public class InternalServerException extends LionHeartException {

	public InternalServerException(String message) {
		super(message, ErrorCode.INTERNAL_SERVER_EXCEPTION);
	}

	public InternalServerException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

}
