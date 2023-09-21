package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import com.example.model.NaceData;
import com.example.repository.NaceDataRepository;

import jakarta.persistence.EntityNotFoundException;


class NaceDataServiceTest {

	NaceDataRepository mockNaceDataRepository = mock(NaceDataRepository.class);
	NaceDataService naceDataService = new NaceDataService(mockNaceDataRepository);

	private static final NaceData naceData = NaceData.builder().orderId(1L).level(1).code(1L).parent("Parent 1")
			.description("Sample Description").thisItemIncludes("Includes 1").thisItemAlsoIncludes("Also Includes 1")
			.rulings("Rulings 1").thisItemExcludes("Excludes 1").referenceToISICRev4(4).build();

	@Test
	@Description("Test successful save: Should save and return a NaceData object")
	void testSaveNaceData() { 
		 when(mockNaceDataRepository.save(naceData)).thenReturn(naceData);
		 assertEquals( naceData.getOrderId() ,naceDataService.saveNaceData(naceData).getOrderId()); 
  	}

	@Test
	@Description("Test save failure: Should return null on save failure")
	void testSaveFail() { 	 		 	
		
		when(mockNaceDataRepository.save(naceData)).thenReturn(null);
		 assertEquals( null ,  naceDataService.saveNaceData(naceData));
		 
  	}
	
	@Test
	@Description("Test getNaceData: Should retrieve a NaceData object by code")
    public void testGetNaceData() {
        Long orderId = 1L;
        String description = "Sample Description";

        when(mockNaceDataRepository.findById(1L)).thenReturn(Optional.of(naceData));
        when(mockNaceDataRepository.findById(2L)).thenReturn(Optional.empty());

       
        assertEquals(orderId, naceDataService.getNaceData(1L).getOrderId());
        assertEquals(description, naceDataService.getNaceData(1L).getDescription());

        // Test the case where the NaceData is not found (EntityNotFoundException should be thrown)
        assertThrows(EntityNotFoundException.class, () -> naceDataService.getNaceData(2L));
    }

}
