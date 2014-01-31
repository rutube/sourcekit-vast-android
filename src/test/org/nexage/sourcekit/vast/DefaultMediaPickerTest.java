package org.nexage.sourcekit.vast;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nexage.sourcekit.util.DefaultMediaPicker;
import org.nexage.sourcekit.vast.processor.VASTMediaPicker;
import org.nexage.sourcekit.vast.model.VASTMediaFile;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DefaultMediaPickerTest {

	@Test
	public void testMimeTypeValid() {

		// test to check for valid mime type
		VASTMediaPicker picker = new DefaultMediaPicker(100, 100);
		VASTMediaFile res = null;

		VASTMediaFile m1 = new VASTMediaFile();
		m1.setDelivery("progressive");
		m1.setType("video/mp4");
		m1.setWidth(new BigInteger("100"));
		m1.setHeight(new BigInteger("100"));
		m1.setId("1");

		VASTMediaFile m2 = new VASTMediaFile();
		m2.setDelivery("progressive");
		m2.setType("video/x-mp4");
		m2.setWidth(new BigInteger("100"));
		m2.setHeight(new BigInteger("100"));
		m2.setId("1");

		
		List<VASTMediaFile> lst1 = new ArrayList<VASTMediaFile>();
		List<VASTMediaFile> lst2 = new ArrayList<VASTMediaFile>();

		lst1.add(m1);
		lst2.add(m2);

		res = picker.pickVideo(lst1);
		assertTrue(res.getId().equals("1"));
		
		
		res = picker.pickVideo(lst2);
		assertTrue(res.getId().equals("1"));
	}
	
	@Test
	public void testMimeTypeInvalid() {

		// test for invalid mime types
		VASTMediaPicker picker = new DefaultMediaPicker(100, 100);
		VASTMediaFile res = null;

		VASTMediaFile m1 = new VASTMediaFile();
		m1.setDelivery("progressive");
		m1.setType("image/mp4");
		m1.setWidth(new BigInteger("100"));
		m1.setHeight(new BigInteger("100"));
		m1.setId("1");

		VASTMediaFile m2 = new VASTMediaFile();
		m2.setDelivery("progressive");
		m2.setType("");
		m2.setWidth(new BigInteger("100"));
		m2.setHeight(new BigInteger("100"));
		m2.setId("1");
		
		VASTMediaFile m3 = new VASTMediaFile();
		m3.setDelivery("progressive");
		m3.setType("abc");
		m3.setWidth(new BigInteger("100"));
		m3.setHeight(new BigInteger("100"));
		m3.setId("1");

		
		List<VASTMediaFile> lst1 = new ArrayList<VASTMediaFile>();
		List<VASTMediaFile> lst2 = new ArrayList<VASTMediaFile>();
		List<VASTMediaFile> lst3 = new ArrayList<VASTMediaFile>();

		lst1.add(m1);
		lst2.add(m2);
		lst3.add(m3);

		res = picker.pickVideo(lst1);
		assertNull(res);
		
		res = picker.pickVideo(lst2);
		assertNull(res);
		
		res = picker.pickVideo(lst3);
		assertNull(res);
	}
	
	@Test
	public void testArea1() {

		VASTMediaPicker picker = new DefaultMediaPicker(100, 100);
		VASTMediaFile res = null;

		VASTMediaFile m1 = new VASTMediaFile();
		m1.setDelivery("progressive");
		m1.setType("video/mp4");
		m1.setWidth(new BigInteger("100"));
		m1.setHeight(new BigInteger("100"));
		m1.setId("1");

		VASTMediaFile m2 = new VASTMediaFile();
		m2.setDelivery("progressive");
		m2.setType("video/x-mp4");
		m2.setWidth(new BigInteger("102"));
		m2.setHeight(new BigInteger("102"));
		m2.setId("2");
		
		VASTMediaFile m3 = new VASTMediaFile();
		m3.setDelivery("progressive");
		m3.setType("video/mp4");
		m3.setWidth(new BigInteger("200"));
		m3.setHeight(new BigInteger("1"));
		m3.setId("3");

		VASTMediaFile m4 = new VASTMediaFile();
		m4.setDelivery("progressive");
		m4.setType("video/x-mp4");
		m4.setWidth(new BigInteger("10"));
		m4.setHeight(new BigInteger("10"));
		m4.setId("4");

		
		List<VASTMediaFile> lst = new ArrayList<VASTMediaFile>();
		
		lst.add(m1);
		lst.add(m2);
		lst.add(m3);
		lst.add(m4);

		res = picker.pickVideo(lst);
		assertTrue(res.getId().equals("1"));
	}
	
	@Test
	public void testArea2() {

		VASTMediaPicker picker = new DefaultMediaPicker(100, 100);
		VASTMediaFile res = null;

		VASTMediaFile m1 = new VASTMediaFile();
		m1.setDelivery("progressive");
		m1.setType("video/mp4");
		m1.setWidth(new BigInteger("101"));
		m1.setHeight(new BigInteger("101"));
		m1.setId("1");

		VASTMediaFile m2 = new VASTMediaFile();
		m2.setDelivery("progressive");
		m2.setType("video/x-mp4");
		m2.setWidth(new BigInteger("102"));
		m2.setHeight(new BigInteger("102"));
		m2.setId("2");
		
		VASTMediaFile m3 = new VASTMediaFile();
		m3.setDelivery("progressive");
		m3.setType("video/mp4");
		m3.setWidth(new BigInteger("200"));
		m3.setHeight(new BigInteger("1"));
		m3.setId("3");

		VASTMediaFile m4 = new VASTMediaFile();
		m4.setDelivery("progressive");
		m4.setType("video/x-mp4");
		m4.setWidth(new BigInteger("10"));
		m4.setHeight(new BigInteger("10"));
		m4.setId("4");

		
		List<VASTMediaFile> lst = new ArrayList<VASTMediaFile>();
		
		lst.add(m1);
		lst.add(m2);
		lst.add(m3);
		lst.add(m4);

		res = picker.pickVideo(lst);
		assertTrue(res.getId().equals("1"));
	}
	
	@Test
	public void testArea3() {

		VASTMediaPicker picker = new DefaultMediaPicker(100, 100);
		VASTMediaFile res = null;

		VASTMediaFile m1 = new VASTMediaFile();
		m1.setDelivery("progressive");
		m1.setType("video/mp4");
		m1.setWidth(new BigInteger("103"));
		m1.setHeight(new BigInteger("103"));
		m1.setId("1");

		VASTMediaFile m2 = new VASTMediaFile();
		m2.setDelivery("progressive");
		m2.setType("video/x-mp4");
		m2.setWidth(new BigInteger("102"));
		m2.setHeight(new BigInteger("102"));
		m2.setId("2");
		
		VASTMediaFile m3 = new VASTMediaFile();
		m3.setDelivery("progressive");
		m3.setType("video/mp4");
		m3.setWidth(new BigInteger("200"));
		m3.setHeight(new BigInteger("1"));
		m3.setId("3");

		VASTMediaFile m4 = new VASTMediaFile();
		m4.setDelivery("progressive");
		m4.setType("video/x-mp4");
		m4.setWidth(new BigInteger("101"));
		m4.setHeight(new BigInteger("101"));
		m4.setId("4");

		
		List<VASTMediaFile> lst = new ArrayList<VASTMediaFile>();
		
		lst.add(m1);
		lst.add(m2);
		lst.add(m3);
		lst.add(m4);

		res = picker.pickVideo(lst);
		assertTrue(res.getId().equals("4"));
	}
}
