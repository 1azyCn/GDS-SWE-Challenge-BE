package com.challenge.gdsswechallengebe.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseListEnvelope<T>{

	/**
	 * 
	 */;
	private List<T> list;
	private long totalItems;
	
	public ResponseListEnvelope(List<T> list, Integer totalItems) {
		this.list = list;
		this.totalItems = totalItems;
	}
}
