package org.mifosplatform.infrastructure.documentmanagement.data;

public class DocumentTypeData {
	private Integer id;
	private String name;
	private String label;

	public DocumentTypeData(final Integer id, final String name,
			final String label) {
		super();
		this.id = id;
		this.name = name;
		this.label = label;
	}

	public static DocumentTypeData instance(final Integer id,
			final String name, final String label) {
		return new DocumentTypeData(id, name, label);
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

}
