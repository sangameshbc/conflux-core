package org.mifosplatform.infrastructure.documentmanagement.service;

import java.util.Collection;

import org.mifosplatform.infrastructure.documentmanagement.data.DocumentClassificationData;

public interface DocumentTypesReadPlatformService {
	
	Collection<DocumentClassificationData> retrieveAllDocumentTypes();
}
