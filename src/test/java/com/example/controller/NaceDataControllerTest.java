package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.NaceData;
import com.example.service.NaceDataService;

class NaceDataControllerTest {

	NaceDataService mockNaceDataService = mock(NaceDataService.class);
	NaceDataController naceDataController = new NaceDataController(mockNaceDataService);

	public static final NaceData naceData = NaceData.builder().orderId(1L).level(1).code(1L).parent("Parent 1")
			.description("Description 1").thisItemIncludes("Includes 1").thisItemAlsoIncludes("Also Includes 1")
			.rulings("Rulings 1").thisItemExcludes("Excludes 1").referenceToISICRev4(4).build();

	@Test
	@Description("Test successful NACE data insertion")
	void testSaveSuccessful() {

		when(mockNaceDataService.saveNaceData(naceData)).thenReturn(naceData);
		assertEquals(ResponseEntity.status(HttpStatus.CREATED).body("NACE data inserted successfully"),
				naceDataController.createNaceDetails(naceData));

	}

	@Test
	@Description("Test failed NACE data insertion")
	void testSaveFailed() {
		
		when(mockNaceDataService.saveNaceData(naceData)).thenReturn(null);
		assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert NACE data"),
				naceDataController.createNaceDetails(naceData));

	}

	@Test
	@Description("Test failed NACE data insertion with empty data")
	void testSaveFailEmptyData() {
		NaceData naceData = NaceData.builder().build();

		when(mockNaceDataService.saveNaceData(naceData)).thenReturn(naceData);
		assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert NACE data"),
				naceDataController.createNaceDetails(naceData));

	}

	@Test
    @Description("Test handling an exception during NACE data insertion")
    void testCreateNaceDetailsWithException() {
        when(mockNaceDataService.saveNaceData(naceData)).thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<String> response = naceDataController.createNaceDetails(naceData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error: Simulated exception", response.getBody());
    }

	@Test
	@Description("Test successful retrieval of NACE data by Order")
	void testGetNaceDetailsFound() {
		Long naceOrder = 1L;
		when(mockNaceDataService.getNaceData(naceOrder)).thenReturn(naceData);

		ResponseEntity<?> response = naceDataController.getNaceDetails(naceOrder);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(naceData, response.getBody());
	}

	@Test
	@Description("Test retrieval of NACE data by code when not found")
	void testGetNaceDetailsNotFound() {
		Long naceOrder = 1L;

		when(mockNaceDataService.getNaceData(naceOrder)).thenReturn(null);

		ResponseEntity<?> response = naceDataController.getNaceDetails(naceOrder);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("NACE code not found", response.getBody());
	}

}
