package org.xlp.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xlp.json.JsonObject;
import org.xlp.json.utils.JsonHelper;
import org.xlp.zip.UnZip;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void testApp() throws IOException
    {
        assertTrue( true );
       /* UnZip unZip = new UnZip("F:\\tt\\1.xlsx");
        unZip.unZip("F:\\apk\\20200218培训资料");*/
       //System.out.println(ZipUtils.toZip("E:\\下载文件", "F:\\", true));
       /* Zip zip = new Zip("F:\\新建文件夹");
        zip.toZip("F:\\", true);*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("key1", "value1");
        map2.put("key2", "value2");
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("key1", "value1");
        map3.put("key2", "value2");
        //map.put("map2", map2);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        list.add(map3);
        map2.put("list", list);
       // XLPMapToXmlUtil.setXmlTagType(XMLTagType.TO_UPPER);
       // System.out.println(XLPMapToXmlUtil.mapToXml(map2));
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("vv", 33);
        jsonObject.put("ee", 33);
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.put("vv", ">kk");
        jsonObject1.put("ee", 33);
        jsonObject.put("json", jsonObject1);
        jsonObject.put("arr", map2);
       // JsonHelper.setXmlTagType(XMLTagType.TO_UPPER);
        //System.out.println(JsonHelper.toXMLString(jsonObject, "xml"));
    }
    public static void main(String[] args) {
        for (char c = '\u03b1'; c <= '\u03c9'; c++) {
            System.out.println(c + "=" + (int)c + " " 
            	+ Character.toUpperCase(c) + "=" + (int)Character.toUpperCase(c));
        }
        System.out.println(new Date().getTime());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli());
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 7, 9, 10, 17);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2018, 7, 8, 17, 16);
        
        System.out.println(XLPDateUtil.calculateResultDays(calendar, calendar2));
    }
}
