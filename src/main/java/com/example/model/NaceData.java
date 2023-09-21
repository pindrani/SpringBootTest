package com.example.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "nace_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NaceData {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "level")
	private int level;

	@Column(name = "code", unique = true, nullable = false)
	private Long code;

	@Column(name = "parent")
	private String parent;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "this_item_includes")
	private String thisItemIncludes;

	@Column(name = "this_item_also_includes")
	private String thisItemAlsoIncludes;

	@Column(name = "rulings")
	private String rulings;

	@Column(name = "this_item_excludes")
	private String thisItemExcludes;

	@Column(name = "reference_to_isic_rev4")
	private int referenceToISICRev4;
}