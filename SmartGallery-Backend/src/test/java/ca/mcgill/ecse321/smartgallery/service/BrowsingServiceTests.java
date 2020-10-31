package ca.mcgill.ecse321.smartgallery.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.service.BrowsingService;


@ExtendWith(MockitoExtension.class)
public class BrowsingServiceTests {
	
	@Mock
	private SmartGalleryRepository smartGalleryDao;
	
	@InjectMocks
	private BrowsingService service;
	
	private static final int SMARTGALLERY_KEY = 12345;
	private static final int NONEXISTING_KEY  = 00000;

	@BeforeEach
	public void setMockOutput() {
		lenient().when(smartGalleryDao.findSmartGalleryBySmartGalleryID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SMARTGALLERY_KEY)) {
				SmartGallery smartGallery = new SmartGallery();
				smartGallery.setSmartGalleryID(SMARTGALLERY_KEY);
				return smartGallery;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(smartGalleryDao.save(any(SmartGallery.class))).thenAnswer(returnParameterAsAnswer);
//		lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
//		lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateSmartGallery() {
		assertEquals(0, service.getAllSmartGalleries().size());

		int ID = 532525;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(ID);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(smartGallery);
		assertEquals(ID, smartGallery.getSmartGalleryID());
	}

	@Test
	public void testCreateSmartGalleryNull() {
		Integer ID = null;
		String error = null;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(smartGallery);
		// check error
		assertEquals("SmartGallery ID cannot be empty!", error);
	}

//	@Test
//	public void testCreateSmartGalleryEmpty() {
//		Integer ID = "";
//		String error = null;
//		SmartGallery smartGallery = null;
//		try {
//			smartGallery = service.createSmartGallery(ID);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//		assertNull(smartGallery);
//		// check error
//		assertEquals("Person name cannot be empty!", error);
//	}

//	@Test
//	public void testCreatePersonSpaces() {
//
//	}
	
	@Test
	public void testGetSmartGallery() {
		assertEquals(SMARTGALLERY_KEY, service.getSmartGalleryByID(SMARTGALLERY_KEY).getSmartGalleryID());
	}

	@Test
	public void testGetNonExistingPerson() {
//		assertNull(service.getGalleryByName(GALLERY_KEY));
	}
	
	
	
}	








