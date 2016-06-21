package com.anz.common.transform;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.anz.common.dataaccess.models.iib.ErrorStatusCode;
import com.anz.common.error.ExceptionMessage;

public class TransformUtilsTest {

	@Test
	public void testXML() throws JAXBException {
		ErrorStatusCode code =  new ErrorStatusCode();
		code.setCode("100");
		code.setDescr("Internal Exception");
		code.setException("InternalException");
		code.setSeverity("CRITICAL");
		
		String xml = TransformUtils.convertToXml(code, ErrorStatusCode.class);
		System.out.println(xml);
		assertNotNull(xml);
		
		ErrorStatusCode code2 = TransformUtils.convertFromXml(xml, ErrorStatusCode.class);
		System.out.println(code2.getCode());
		assertNotNull(code2);
	}
	
}
