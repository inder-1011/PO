package com.pb.td.po.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class OnBoardingResponseBuilder {

	public static Response buildResponse(OnBoardingErrorResponse error, StatusType statusType) {
		Response.ResponseBuilder responseBuilder = Response.status(statusType).header("Content-Type", "application/json");
		return responseBuilder.entity(error).build();
	}

}
