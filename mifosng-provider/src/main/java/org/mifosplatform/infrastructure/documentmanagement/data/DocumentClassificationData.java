package org.mifosplatform.infrastructure.documentmanagement.data;

import java.util.Collection;

public class DocumentClassificationData {
	private final String documentName;
	private final Long documentKey;
	private final String documentType;
	private final Integer minMandatoryDocuments;
	private final String description;
	private final String status;
	private final Collection<DocumentTypeData> documentTypeData;

	public DocumentClassificationData(final String documentName,
			final Long documentKey, final String documentType,
			final Integer minMandatoryDocuments, final String description,
			final String status,
			final Collection<DocumentTypeData> documentTypeData) {
		super();
		this.documentName = documentName;
		this.documentKey = documentKey;
		this.documentType = documentType;
		this.minMandatoryDocuments = minMandatoryDocuments;
		this.description = description;
		this.status = status;
		this.documentTypeData = documentTypeData;
	}

	public static DocumentClassificationData instance(
			final String documentName, final Long documentKey,
			final String documentType, final Integer minMandatoryDocuments,
			final String description, final String status,
			final Collection<DocumentTypeData> documentTypeData) {
		return new DocumentClassificationData(documentName, documentKey,
				documentType, minMandatoryDocuments, description, status,
				documentTypeData);
	}

	public String getStatus() {
		return status;
	}

	public Long getDocumentKey() {
		return documentKey;
	}

	public Integer getMinMandatoryDocuments() {
		return minMandatoryDocuments;
	}

	public String getDescription() {
		return description;
	}

	public String getDocumentName() {
		return documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public Collection<DocumentTypeData> getDocumentTypeData() {
		return documentTypeData;
	}

}
