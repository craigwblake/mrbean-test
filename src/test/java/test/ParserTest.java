package com.gses.config;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.*;
import com.fasterxml.jackson.module.mrbean.*;
import java.io.*;
import org.junit.*;

import static org.junit.Assert.*;

public class ParserTest {

	protected final ObjectMapper mapper = new ObjectMapper();

	public ParserTest() throws Exception {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(new MrBeanModule());
	}

	public interface Bean {
		String getField();
		void setField(String field);
	}

	@Test
	public void testBean() throws Exception {
		final String input = "{\"field\": \"testing\"}";
		final Bean bean = mapper.readValue(new StringBufferInputStream(input), Bean.class);

		assertEquals("testing", bean.getField());

		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		mapper.writeValue(output, bean);
		output.close();

		assertEquals(input, new String(output.toString()));
	}
}
