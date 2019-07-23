package com.test.xrayapis;

import static com.test.xrayapis.XrayAPIs.BASE_URL;
import static com.test.xrayapis.XrayAPIs.TEST_EXECUTION_GET_URL;
import static com.test.xrayapis.XrayAPIs.TEST_RUN_GET_URL;
import static com.test.xrayapis.XrayAPIs.TEST_RUN_STATUS_PUT_URL;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XrayAPIIntegration {

	public List<TestExecution> getTestExecution() throws URISyntaxException {
		String exc = TEST_EXECUTION_GET_URL;
		String api = exc.replace("execid", "TP-5");
		URI u = new URI(BASE_URL + api);

		HttpHeaders headers = new HttpHeaders();
		headers.set(org.apache.http.HttpHeaders.AUTHORIZATION, "Basic dGhpbmtwYWxtOlRoaW5rQDEyMw==");
		HttpEntity h = new HttpEntity<T>(headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> exchange = rt.exchange(u, HttpMethod.GET, h, String.class);
		try {
			TestExecution[] readValue = new ObjectMapper().readValue(exchange.getBody(), TestExecution[].class);
			return Arrays.asList(readValue);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public String updateTestCaseStatus(int testRunId, String status) throws URISyntaxException {
		String testRunPutUrl = TEST_RUN_STATUS_PUT_URL;
		String replacedUrl = testRunPutUrl.replace("id", "" + testRunId);

		HttpHeaders headers = new HttpHeaders();
		headers.set(org.apache.http.HttpHeaders.AUTHORIZATION, "Basic dGhpbmtwYWxtOlRoaW5rQDEyMw==");
		URIBuilder b = new URIBuilder(BASE_URL + replacedUrl);
		URI u = b.addParameter("status", status).build();
		HttpEntity httpEntity = new HttpEntity<>(headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> exchange = rt.exchange(u, HttpMethod.PUT, httpEntity, String.class);
		System.err.println(exchange.getStatusCode());
		return exchange.getBody();
	}

	public TestRun getTestRun(String testKey) throws URISyntaxException {
		String testRunGetUrl = TEST_RUN_GET_URL;

		HttpHeaders headers = new HttpHeaders();
		headers.set(org.apache.http.HttpHeaders.AUTHORIZATION, "Basic dGhpbmtwYWxtOlRoaW5rQDEyMw==");
		HttpEntity httpEntity = new HttpEntity<>(headers);
		URIBuilder b = new URIBuilder(BASE_URL + testRunGetUrl);
		b.addParameter("testIssueKey", testKey);
		b.addParameter("testExecIssueKey", "TP-5");
		URI url = b.build();
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> exchange = rt.exchange(url, HttpMethod.GET, httpEntity, String.class);
		try {
			System.out.println(exchange.getBody());
			return new ObjectMapper().readValue(exchange.getBody(), TestRun.class);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
