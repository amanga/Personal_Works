package com.bunge.icc.tibco.adapter.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.tibco.sdk.tools.MTrace;

public class AdapterLogHandler extends Handler {

	private MTrace mTrace;
	public AdapterLogHandler(MTrace trace){
		this.mTrace = trace;
	}
	
	@Override
	public void publish(LogRecord record) {
		// TODO Auto-generated method stub
		String messageCode;
		Object[] params;

		messageCode = record.getLevel().getName();

		ResourceBundle rbundle = record.getResourceBundle();

		if (rbundle != null) {

			// If a resource bundle is associated with the Handler,
			// the localized message will be retrieved from it
			// and the variable substitution will be done.

			MessageFormat mformat;

			mformat = new MessageFormat(rbundle.getString(record.getMessage()));
			params = new Object[] {
					record.getSourceClassName(),
					record.getSourceMethodName(),
					mformat.format(record.getParameters() != null ? record
							.getParameters() : new Object[] {}) };

		} else if (record.getParameters() != null) {

			// If there is no resource bundle associated with the handler,
			// but there is params for variable substitution, it will
			// be done at the MTrace level.

			MessageFormat mformat;

			mformat = new MessageFormat(record.getMessage());
			params = new Object[] { record.getSourceClassName(),
					record.getSourceMethodName(),
					mformat.format(record.getParameters()) };

		} else {

			// Otherwise, the message will be used as is
			// and the code sent to MTrace will be the log level itself

			messageCode = record.getLevel().getName();
			params = new Object[] { record.getSourceClassName(),
					record.getSourceMethodName(), record.getMessage() };
		}

		mTrace.trace(messageCode, null, params);

		if (record.getThrown() != null)
			mTrace.trace(record.getLevel().getName(), record.getThrown(), null);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub		
	}

}
