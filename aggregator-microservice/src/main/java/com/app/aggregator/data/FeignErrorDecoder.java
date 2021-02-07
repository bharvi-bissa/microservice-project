package com.app.aggregator.data;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			// some operation
			break;
		case 404: {
			return new ResponseStatusException(HttpStatus.valueOf(response.status()),"Route Not Found");
		}
		default:
			new Exception(response.reason());
		}
		return null;
	}

}
