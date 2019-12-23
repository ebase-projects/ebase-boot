package me.dwliu.framework.core.tool.util;

import javax.xml.bind.*;
import java.io.*;

/**
 * jaxb操作。
 *
 * @author ray
 */
public class JAXBUtil {

    /**
     * XML转换为POJO类型
     *
     * @param xml
     * @param clsToUnbound
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("rawtypes")
    public static Object unmarshall(String xml, Class clsToUnbound) throws JAXBException, UnsupportedEncodingException {
        JAXBContext jc = JAXBContext.newInstance(clsToUnbound);

        return unmarshall(jc, xml);
    }

    /**
     * XML转换为POJO类型
     *
     * @param jc
     * @param xml
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    private static Object unmarshall(JAXBContext jc, String xml) throws JAXBException, UnsupportedEncodingException {
        Unmarshaller u = jc.createUnmarshaller();
        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        return u.unmarshal(is);
    }

    /**
     * 从流中反序列化对象。
     *
     * @param cls   需要反序列化的对象类型。
     * @param xmlIs 流对象
     * @return 经过反序列化的对象实例。
     * @throws JAXBException
     */
    public static Object unmarshall(InputStream xmlIs, Class<?> cls) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Object obj = unmarshaller.unmarshal(xmlIs);
        return obj;
    }

    public Object unmarshall(String xml, Class<? extends Object>... classes) throws JAXBException, IOException {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        JAXBContext jc = JAXBContext.newInstance(classes);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        Object obj = unmarshaller.unmarshal(is);
        return obj;
    }

    /**
     * POJO类型转换为XML
     *
     * @param serObj
     * @param clsToBound
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("rawtypes")
    public static String marshall(Object serObj, Class clsToBound) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(clsToBound);
        return marshall(jc, serObj);
    }

    /**
     * POJO类型转换为XML
     *
     * @param jc
     * @param serObj
     * @return
     * @throws JAXBException
     * @throws PropertyException
     */
    private static String marshall(JAXBContext jc, Object serObj) throws JAXBException, PropertyException {
        StringWriter out = new StringWriter();
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, System.getProperty("file.encoding"));
        m.marshal(serObj, out);
        String tmp = out.toString();
        return tmp;
    }

    /**
     * 将类序列化到流中。
     *
     * @param contextPath 需要序列化到类名
     * @param obj         需要序列化的实例对象
     * @param stream      需要序列化到的流对象。
     * @throws JAXBException
     */
    public static void marshall(String contextPath, Object obj, OutputStream stream) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(contextPath);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(obj, stream);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));

    }

}
