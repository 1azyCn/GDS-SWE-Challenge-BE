package com.challenge.gdsswechallengebe.dto.response;

import java.util.HashMap;
import java.util.Map;

public final class CommonResponseBody {

	public static final String KEY_RESULT = "result";
	public static final String KEY_ERROR_CODE = "errorCode";
	public static final String KEY_ERROR_MESSAGE = "errorMessage";
	public static final String KEY_DATA = "data";

	public static final String KEY_RESULT_SUCCESS = "Success";
	public static final String KEY_RESULT_FAIL = "Fail";

    public static final String ERR_MISSING_INVALID_FIELD = "Field<%s> is missing or has an invalid value";
	public static final String ERR_DEFAULT_NULL_MSG = "<%s> is null";

	private Map<String, Object> jsonObj;

	public CommonResponseBody() {
		jsonObj = new HashMap<String, Object>();
		jsonObj.put(KEY_RESULT, "");
		jsonObj.put(KEY_ERROR_CODE, "");
		jsonObj.put(KEY_ERROR_MESSAGE, "");
		jsonObj.put(KEY_DATA, new HashMap<>());
	}

	public void setResult(String result) {
		jsonObj.put(KEY_RESULT, result);
	}

	public String getResult() {
		return jsonObj.get(KEY_RESULT).toString();
	}

	public void setErrorCode(String errorCode) {
		jsonObj.put(KEY_ERROR_CODE, errorCode);
	}

	public String getErrorCode() {
		return jsonObj.get(KEY_ERROR_CODE).toString();
	}

	public void setErrorMessage(String errorMessage) {
		jsonObj.put(KEY_ERROR_MESSAGE, errorMessage);
	}

	public String getErrorMessage() {
		return jsonObj.get(KEY_ERROR_MESSAGE).toString();
	}

	public void setData(Map<String, Object> data) {
		jsonObj.put(KEY_DATA, data);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getData() {
		return jsonObj.get(KEY_DATA) != null ? (Map<String, Object>) jsonObj.get(KEY_DATA) : null;
	}

	public String getJSONString() {
		return jsonObj.toString();
	}

	public Map<String, Object> getMap() {
		return jsonObj;
	}

}
