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
	
	@Mock
	private GalleryRepository galleryDao;
	
	@InjectMocks
	private BrowsingService service;
	
	private static final int SMARTGALLERY_KEY = 12345;
	private static final int NONEXISTING_SMARTGALLERY_KEY  = 00000;
	
	private static final String GALLERY_KEY = "TestGallery";
	private static final String NONEXISTING_GALLERY_KEY = "NotAGallery";

	@BeforeEach
	public void setMockOutput() {
		lenient().when(smartGalleryDao.findSmartGalleryBySmartGalleryID(anyInt()))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SMARTGALLERY_KEY)) {
				SmartGallery smartGallery = new SmartGallery();
				smartGallery.setSmartGalleryID(SMARTGALLERY_KEY);
				return smartGallery;
			} else {
				return null;
			}
		});
		
		lenient().when(galleryDao.findGalleryByGalleryName(anyString()))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(GALLERY_KEY)) {
				Gallery gallery = new Gallery();
				gallery.setGalleryName(GALLERY_KEY);
				return gallery;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(smartGalleryDao.save(any(SmartGallery.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(galleryDao.save(any(Gallery.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	/* ============================================
	 *  			SMARTGALLERY TESTS
	 * ============================================
	 */
	
	@Test
	public void testCreateSmartGallery() {
		assertEquals(0, service.getAllSmartGalleries().size());

		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(SMARTGALLERY_KEY);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(smartGallery);
		assertEquals(SMARTGALLERY_KEY, smartGallery.getSmartGalleryID());
	}
	
	@Test
	public void testCreateSmartGalleryAlreadyExists() {
		SmartGallery smartGallery1 = service.createSmartGallery(SMARTGALLERY_KEY);
		String error = null;
		SmartGallery smartGallery2 = null;
		assertEquals(1, service.getAllSmartGalleries().size());
		try {
			smartGallery2 = service.createSmartGallery(SMARTGALLERY_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("A smartGallery with that ID already exists", error);
		assertEquals(1, service.getAllSmartGalleries().size());
		assertNotNull(smartGallery1);
		assertNull(smartGallery2);
	}

	@Test
	public void testCreateSmartGalleryIdIsZero() {
		int ID = 0;
		String error = null;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(smartGallery);
		// check error
		assertEquals("SmartGallery ID must not be zero", error);
	}
		
	@Test
	public void testGetSmartGallery() {
		assertEquals(SMARTGALLERY_KEY, service.getSmartGalleryByID(SMARTGALLERY_KEY).getSmartGalleryID());
	}

	@Test
	public void testGetNonExistingSmartGallery() {
		assertNull(service.getSmartGalleryByID(NONEXISTING_SMARTGALLERY_KEY));
	}
	
	/* ============================================
	 *  				GALLERY TESTS
	 * ============================================
	 */
	
	@Test
	public void testCreateGallery() {
		assertEquals(0, service.getAllGalleries().size());

		Gallery gallery = null;
		try {
			gallery = service.createGallery(GALLERY_KEY, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(gallery);
		assertEquals(GALLERY_KEY, service.getGalleryByName(GALLERY_KEY));
	}
	
	@Test
	public void testCreateGalleryAlreadyExists() {
		SmartGallery smartGallery = new SmartGallery();
		Gallery gallery1 = service.createGallery(GALLERY_KEY, smartGallery, 20);
		String error = null;
		Gallery gallery2 = null;
		assertEquals(1, service.getAllGalleries().size());
		try {
			gallery2 = service.createGallery(GALLERY_KEY, smartGallery, 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("A gallery with that name already exists", error);
		assertEquals(1, service.getAllGalleries().size());
		assertNotNull(gallery1);
		assertNull(gallery2);
	}

	@Test
	public void testCreateGalleryNameIsNull() {
		String galleryName = null;
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}
	
	@Test
	public void testCreateGalleryNameIsEmpty() {
		String galleryName = "";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}
	
	@Test
	public void testCreateGalleryNameIsSpaces() {
		String galleryName = " ";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}
	
	@Test
	public void testCreateGallerySmartGalleryIsNull() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery(GALLERY_KEY, null, 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertNotNull("A SmartGallery is need to create a Gallery", error);
	}
	
	@Test
	public void testCreateGalleryCommisionSmallerThanZero() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery(GALLERY_KEY, new SmartGallery(), -2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertNotNull("The commission percentage must be between 0 and 100 inclusive", error);
	}
	
	@Test
	public void testCreateGalleryCommisionLargerThan100() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery(GALLERY_KEY, new SmartGallery(), 105);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertNotNull("The commission percentage must be between 0 and 100 inclusive", error);
	}
	
	@Test
	public void testGetGallery() {
		assertEquals(GALLERY_KEY, service.getGalleryByName(GALLERY_KEY).getGalleryName());
	}

	@Test
	public void testGetNonExistingGallery() {
		assertNull(service.getGalleryByName(NONEXISTING_GALLERY_KEY));
	}
	
	
}	








