/**
 * 
 */
package com.anz.common.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import com.anz.common.error.ExceptionMessage;
import com.google.gson.Gson;

/**
 * @author sanketsw
 *
 */
public class TransformUtils {
	

	
	/**
	 * Convert the JSON string to a Java Pojo Object
	 * @param <T>
	 * @param jsonString
	 * @param pojoClassType
	 * @return Java Pojo Object
	 */
	public static <T> T fromJSON(String jsonString, Class<T> pojoClassType) {
		T obj = new Gson().fromJson(jsonString, pojoClassType);
		return obj;
	}
	
	/**
	 * Convert Java Pojo into a JSON string
	 * @param pojo
	 * @return JSON string
	 */
	public static String toJSON(Object pojo) {
		String out = new Gson().toJson(pojo);
		return out;
	}

	/**
	 * Converts a string message to a BLOB
	 * @param message
	 * @return BLOB
	 */
	public static String getBlob(String message) {
		byte[] bytes = message.getBytes(); 
	    BigInteger bigInt = new BigInteger(bytes);
	    String hexString = bigInt.toString(16); // 16 is the radix
	    return hexString;
	}
	
	/**
	 * Convert an object to XML
	 * @param source source object of type T
	 * @param type Class of Type T
	 * @return
	 * @throws JAXBException 
	 */
	public static <T> String convertToXml(T source, Class<T> type) throws JAXBException {
        String result;
        StringWriter sw = new StringWriter();

        
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller marshaller = context.createMarshaller();
        QName qName = new QName(source.getClass().getSimpleName());
        JAXBElement<T> root = new JAXBElement<T>(qName, type, source);
        marshaller.marshal(root, sw);
        result = sw.toString();

        return result;
    }
	
	/**
	 * Convert an object to XML
	 * @param source source object of type T
	 * @param type Class of Type T
	 * @return
	 * @throws JAXBException 
	 */
	public static <T> T convertFromXml(String inputXml, Class<T> type) throws JAXBException {
        
        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<T> root = unmarshaller.unmarshal(new StreamSource(new StringReader(inputXml)), type);
        T result = root.getValue();
        
        return result;
    }
	
	/**
	 * Convert to ANZ plunk compatible Alert format separated by a separator
	 * @param e exceptionMessage object
	 * @return String
	 */
	public static String toSplunkAlertFormat(ExceptionMessage e) {
		String separator = " ";
		String result = e.getBroker().getRegion();
		result = result + separator + e.getBroker().getForwarder();
		result = result + separator + e.getBroker().getHostName();
		result = result + separator + e.getId();
		result = result + separator + e.getBroker().getClassValue();
		result = result + separator + e.getStatus().getSeverity();
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
		result = result + separator + format.format(e.getTimestamp());;
		result = result + separator + "'IA'";
		result = result + separator + e.getIncidentArea();
		result = result + separator + "'Description :'";
		result = result + separator + e.getStatus().getDescr();
		return result;
	}

}
