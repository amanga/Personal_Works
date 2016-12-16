package com.bunge.app.consumer.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;

import com.bunge.app.consumer.EmployeeJobConsumer;
import com.bunge.app.domain.EmployeeJob;
import com.bunge.app.utils.EmployeeJobConstants;

public class EmployeeJobConsumerImpl implements EmployeeJobConsumer {

	public void synch() throws Exception {
		Edm edm = readEdm(EmployeeJobConstants.SERVICE_URL);

		/*
		 * ODataFeed oDataFeed = readFeed(edm, EmployeeJobConstants.SERVICE_URL,
		 * EmployeeJobConstants.APPLICATION_XML,
		 * EmployeeJobConstants.ENTITY_SET_NAME);
		 */

		ODataFeed oDataFeed = readFeed(edm, EmployeeJobConstants.SERVICE_URL,
				EmployeeJobConstants.APPLICATION_XML,
				EmployeeJobConstants.ENTITY_SET_NAME);

		boolean print = true;
		int index = 0;
		List<EmployeeJob> employeeJobs = new ArrayList<EmployeeJob>();
		System.out.println(oDataFeed.getEntries().size());
		for (ODataEntry entry : oDataFeed.getEntries()) {
			/*
			 * if (print) { System.out.println((++index)+".)"+
			 * entry.getProperties()); } prettyPrint(entry); print = false;
			 */
			Map<String, Object> properties = entry.getProperties();
			EmployeeJob employeeJob = new EmployeeJob();
			employeeJob.setEmplStatus((String) properties.get("emplStatus"));
			employeeJob.setStartDate((Calendar) properties.get("startDate"));
			employeeJob.setEndDate((Calendar) properties.get("endDate"));
			employeeJob.setUserId((String) properties.get("userId"));
			employeeJob.setEvent((String) properties.get("event"));
			employeeJob.setEventReason((String) properties.get("eventReason"));
			employeeJob.setSource((String) properties.get("customString6")); // source
			employeeJob.setCompany((String) properties.get("company")); // source

			System.out.println((++index) + ".)" + employeeJob);
		}
	}

	private Edm readEdm(String serviceUrl) throws IOException, ODataException {
		InputStream content = execute(serviceUrl
				+ EmployeeJobConstants.SEPARATOR
				+ EmployeeJobConstants.METADATA_QUERY_STR,
				EmployeeJobConstants.APPLICATION_XML,
				EmployeeJobConstants.HTTP_METHOD_GET);

		return EntityProvider.readMetadata(content, false);
	}

	private ODataFeed readFeed(Edm edm, String serviceUri, String contentType,
			String entitySetName) throws IOException, ODataException {
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		// String uri = createUri(serviceUri, entitySetName, null);

		String uri = createUri(serviceUri, entitySetName,
				EmployeeJobConstants.FILTER_QUERY_STR,
				EmployeeJobConstants.TOPRECORDS_QUERY_STR,
				EmployeeJobConstants.SELECT_QUERY_STR);
		InputStream content = execute(uri, contentType,
				EmployeeJobConstants.HTTP_METHOD_GET);

		// System.out.println(IOUtils.toString(content));
		EdmEntitySet edmEntitySet = entityContainer.getEntitySet(entitySetName);

		ODataFeed odataFeed = EntityProvider.readFeed(contentType,
				edmEntitySet, content, EntityProviderReadProperties.init()
						.build());

		return odataFeed;
	}

	private ODataDeltaFeed readDeltaFeed(Edm edm, String serviceUri,
			String contentType, String entitySetName)
			throws EntityProviderException, IOException, EdmException {

		String uri = createUri(serviceUri, entitySetName,
				EmployeeJobConstants.FILTER_QUERY_STR,
				EmployeeJobConstants.TOPRECORDS_QUERY_STR,
				EmployeeJobConstants.SELECT_QUERY_STR);
		InputStream content = execute(uri, contentType,
				EmployeeJobConstants.HTTP_METHOD_GET);

		// EdmEntityContainer entityContainer =
		// edm.getEntityContainer("Container1");
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		// String uri = createUri(serviceUri, entitySetName, null);

		// System.out.println(IOUtils.toString(content));
		EdmEntitySet edmEntitySet = entityContainer.getEntitySet(entitySetName);

		ODataDeltaFeed deltaFeed = EntityProvider.readDeltaFeed(contentType,
				edmEntitySet, content, EntityProviderReadProperties.init()
						.build());

		return deltaFeed;
	}

	private String createUri(String serviceUri, String entitySetName,
			String filter, String top, String select) {
		final StringBuilder finalUri = new StringBuilder(serviceUri);
		finalUri.append(EmployeeJobConstants.SEPARATOR);
		finalUri.append(entitySetName).append(EmployeeJobConstants.SEPARATOR);
		try {
			if (select != null) {
				finalUri.append(
						((finalUri.indexOf("?") > 0) ? "&" : "?") + "$select=")
						.append(URLEncoder.encode(select, "UTF-8"));
			}
			if (filter != null) {
				finalUri.append(
						((finalUri.indexOf("?") > 0) ? "&" : "?") + "$filter=")
						.append(URLEncoder.encode(filter, "UTF-8"));
			}
			if (top != null) {
				finalUri.append(
						((finalUri.indexOf("?") > 0) ? "&" : "?") + "$top=")
						.append(URLEncoder.encode(top, "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		return finalUri.toString();
	}

	private String createUri_original(String serviceUri, String entitySetName,
			String expand) {
		final StringBuilder finalUri = new StringBuilder(serviceUri);
		finalUri.append(EmployeeJobConstants.SEPARATOR);
		finalUri.append(entitySetName);
		if (expand != null) {
			finalUri.append(EmployeeJobConstants.SEPARATOR).append("$expand=")
					.append(expand);
		}
		return finalUri.toString();
	}

	private InputStream execute(String relativeUri, String contentType,
			String httpMethod) throws IOException {
		HttpURLConnection urlConnection = initializeConnection(relativeUri,
				contentType, httpMethod);
		urlConnection.connect();
		checkStatus(urlConnection);

		InputStream content = urlConnection.getInputStream();

		return content;

	}

	private HttpURLConnection initializeConnection(String uri,
			String contentType, String httpMethod) throws IOException,
			MalformedURLException {
		URL url = new URL(uri);

		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		urlConnection
				.addRequestProperty(
						"Authorization",
						"Basic "
								+ Base64.encodeBase64String((EmployeeJobConstants.USERNAME
										+ "@"
										+ EmployeeJobConstants.COMPANY_ID
										+ ":" + EmployeeJobConstants.PASSWORD)
										.getBytes()));

		urlConnection.setRequestProperty(
				EmployeeJobConstants.HTTP_HEADER_CONTENT_TYPE, contentType);

		urlConnection.setRequestMethod(httpMethod);

		return urlConnection;
	}

	private HttpURLConnection connect(String uri, String contentType,
			String httpMethod) throws IOException {
		HttpURLConnection urlConnection = initializeConnection(uri,
				contentType, httpMethod);
		urlConnection.connect();

		return urlConnection;
	}

	private HttpStatusCodes checkStatus(HttpURLConnection urlConnection)
			throws IOException {
		HttpStatusCodes httpStatusCode = HttpStatusCodes
				.fromStatusCode(urlConnection.getResponseCode());

		if (400 <= httpStatusCode.getStatusCode()
				&& httpStatusCode.getStatusCode() <= 599) {
			throw new RuntimeException("Connection failed with status "
					+ httpStatusCode.getStatusCode() + " "
					+ httpStatusCode.toString());
		}
		return httpStatusCode;
	}
}
