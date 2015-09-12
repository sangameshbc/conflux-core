package org.mifosplatform.infrastructure.documentmanagement.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.RoutingDataSource;
import org.mifosplatform.infrastructure.documentmanagement.contentrepository.ContentRepositoryFactory;
import org.mifosplatform.infrastructure.documentmanagement.data.DocumentClassificationData;
import org.mifosplatform.infrastructure.documentmanagement.data.DocumentTypeData;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.interestratechart.data.InterestRateChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class DocumentTypesReadPlatformServiceImpl implements
		DocumentTypesReadPlatformService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DocumentTypesReadPlatformServiceImpl(
			final PlatformSecurityContext context,
			final RoutingDataSource dataSource,
			final ContentRepositoryFactory documentStoreFactory) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Collection<DocumentClassificationData> retrieveAllDocumentTypes() {
		final DocumentClassificationDataExtractor mapper = new DocumentClassificationDataExtractor();
//		final DocumentTypeMapper mapper = new DocumentTypeMapper();
		final String sql = "select " + mapper.schema() + " and d.id=? ";
		Collection<DocumentClassificationData> documentTypeDatas = this.jdbcTemplate
				.query(sql, mapper);
		return documentTypeDatas;
	}

	final class DocumentTypeMapper implements RowMapper<DocumentClassificationData> {

		public DocumentTypeMapper() {
			// TODO Auto-generated constructor stub
		}

		public String schema() {
			return "  docCl.id as docClassId, docCl.key as documentKey, docCl.min_mandatory_docs As minMandatoryDocuments,"
					+ " docCl.description, docCl.status, di.id as DocumentTypeId, di.name as documentName, di.label As documentLabel FROM m_document_classification docCl "
					+ " JOIN doc_identifiers_mapping docMap ON docMap.document_classification_id = docCl.id "
					+ " JOIN document_identifiers di  ON di.id = docMap.document_identifier_id WHERE docCl.status = 'true' ";
		}

		@Override
		public DocumentClassificationData mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			final String documentName = rs.getString("documentName");
			final Long documentKey = JdbcSupport.getLong(rs, "documentKey");
			final String documentType = rs.getString("documentType");
			final Integer minMandatoryDocuments = JdbcSupport.getInteger(rs,
					"minMandatoryDocuments");
			final String description = rs.getString("description");
			final String status = null;
			final Collection<DocumentTypeData> documentTypeData = null;

			return DocumentClassificationData.instance(documentName,
					documentKey, documentType, minMandatoryDocuments,
					description, status, documentTypeData);
		}
	}
	
	private static final class DocumentClassificationDataExtractor implements ResultSetExtractor<Collection<DocumentClassificationData>> {

        DocumentTypeDataMapper documentMapper = new DocumentTypeDataMapper();
        public String schema() {
			return "  docCl.id as docClassId, docCl.key as documentKey,docCl.name as documentType, docCl.min_mandatory_docs As minMandatoryDocuments,"
					+ " docCl.description, docCl.status, di.id as DocumentTypeId, di.name as documentName, di.label As documentLabel FROM m_document_classification docCl "
					+ " JOIN doc_identifiers_mapping docMap ON docMap.document_classification_id = docCl.id "
					+ " JOIN document_identifiers di  ON di.id = docMap.document_identifier_id WHERE docCl.status = 'true' ";
		}

        
        @Override
        public Collection<DocumentClassificationData> extractData(ResultSet rs) throws SQLException, DataAccessException {

            List<DocumentClassificationData> documentDataList = new ArrayList<>();

            DocumentClassificationData documentClassificationData = null;
            Long docClsId = null;
            int index = 0;
            while (rs.next()) {
            	Long tmp = rs.getLong("docClassId");
            	// first row or when interest rate chart id changes
                if (index == 0 || docClsId != null || !tmp.equals(docClsId)) {
                	documentClassificationData = getDocumentClassification(rs);
                    documentDataList.add(documentClassificationData);
                }
                DocumentTypeData docType = getDocumentType(rs);
//                documentClassificationData = updateDocTypeList(rs);
                
                index++;
            }
            return documentDataList;
        }


		private DocumentClassificationData getDocumentClassification(
				ResultSet rs) {
			//get values
			final String documentName = rs.getString("documentName");
			final Long documentKey = JdbcSupport.getLong(rs, "documentKey");
			final Long minMandatoryDocuments = JdbcSupport.getLong(rs, "minMandatoryDocuments");
			final String documentType = rs.getString("documentType");
			final String description = rs.getString("description");
			final String status = rs.getString("status");
			DocumentTypeData docTypeData = getDocumentType(rs);
			
			
			DocumentClassificationData docClassData = DocumentClassificationData.instance(documentName, documentKey, documentType, 
					minMandatoryDocuments, description, status, docTypeData);
			return docClassData;
		}

    }
	
	public static DocumentTypeData getDocumentType(ResultSet rs){
		//get values
		Integer id = null;
		String documentName = null;
		String documentLabel = null;
		try {
			id = rs.getInt("DocumentTypeId");
			documentName = rs.getString("documentName");
			documentLabel = rs.getString("documentLabel");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DocumentTypeData.instance(id, documentName, documentLabel);
	}
	
	private static final class DocumentTypeDataMapper implements RowMapper<DocumentTypeData> {

		public DocumentTypeDataMapper() {

        }

        public DocumentTypeData mapRow(ResultSet rs, @SuppressWarnings("unused") int rowNum) throws SQLException {

        	final Integer documentId = JdbcSupport.getInteger(rs, "documentName");
            final String documentName = rs.getString("documentName");
            final String documentLabel = rs.getString("documentLabel");
            
            return DocumentTypeData.instance(documentId, documentName, documentLabel);
        }

	}

}