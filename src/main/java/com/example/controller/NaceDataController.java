package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.NaceData;
import com.example.service.NaceDataService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class for managing NACE data.
 */
@RestController
@RequestMapping("/api/nace")
public class NaceDataController {

	private final NaceDataService naceDataService;

	/**
	 * Constructs a new instance of the NaceDataController.
	 *
	 * @param naceDataService The service responsible for handling NACE data
	 *                        operations.
	 */
	public NaceDataController(NaceDataService naceDataService) {
		this.naceDataService = naceDataService;
	}

	/**
	 * Creates a new NACE data entry.
	 *
	 * @param naceData The NACE data to be created.
	 * @return The HTTP status code indicating the result of the operation (201 for
	 *         success, 204 for no content).
	 */
	@PostMapping("/putNaceDetails")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "NACE data inserted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseEntity<String> createNaceDetails(@RequestBody NaceData naceData) {

		try {
			NaceData naceDataObj = naceDataService.saveNaceData(naceData);
			if (naceDataObj != null && naceDataObj.getOrderId() != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("NACE data inserted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert NACE data");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error: " + e.getMessage());
		}
	}

	/**
	 * Retrieves NACE data for a given NACE code.
	 *
	 * @param code The NACE code to retrieve data for.
	 * @return The NACE data or null if not found.
	 */
	@GetMapping("/getNaceDetails/{orderId}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "NACE details retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "NACE code not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<?> getNaceDetails(@PathVariable Long orderId) {
		NaceData naceData = naceDataService.getNaceData(orderId);
		if (naceData != null) {
			return ResponseEntity.status(HttpStatus.OK).body(naceData);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NACE code not found");
		}
	}

	/**
	 * Retrieves all NACE data entries.
	 *
	 * @return A list of all NACE data entries.
	 * 
	 *         @GetMapping("/getAllNaceData") public List<NaceData> getAllNaceData()
	 *         { return naceDataService.getAllNaceData(); }
	 */
}