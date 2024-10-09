package com.jawa.emailsender.helper;

import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;

public class RefreshableInputStreamResource implements InputStreamSource {

	private final Resource resource;

	public RefreshableInputStreamResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return resource.getInputStream();
	}

	public DataSource getDataSource(String name) throws IOException {
		return new ByteArrayDataSource(getInputStream(), name);
	}
}
