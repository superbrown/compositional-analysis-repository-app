package gov.energy.nrel.dataRepositoryApp.dao.mongodb.abandonedApproaches.multipleCellCollectionsApproach;

import gov.energy.nrel.dataRepositoryApp.settings.ISettings;
import gov.energy.nrel.dataRepositoryApp.dao.IDataCategoryDAO;
import gov.energy.nrel.dataRepositoryApp.dao.IDatasetDAO;
import gov.energy.nrel.dataRepositoryApp.dao.dto.IDeleteResults;
import gov.energy.nrel.dataRepositoryApp.dao.mongodb.AbsDAO;
import gov.energy.nrel.dataRepositoryApp.dao.mongodb.DataCategoryDAO;
import gov.energy.nrel.dataRepositoryApp.model.IDataCategoryDocument;
import gov.energy.nrel.dataRepositoryApp.model.IDatasetDocument;
import gov.energy.nrel.dataRepositoryApp.model.IRowCollection;
import gov.energy.nrel.dataRepositoryApp.model.mongodb.document.DataCategoryDocument;
import gov.energy.nrel.dataRepositoryApp.model.mongodb.document.DatasetDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Set;

public class m_DatasetDAO extends AbsDAO implements IDatasetDAO {

    public static final String COLLECTION_NAME = "dataset";
    protected DataCategoryDAO dataCategoryDAO;
    protected m_RowDAO rowDAO;

    public m_DatasetDAO(ISettings settings) {

        super(COLLECTION_NAME, settings);

        rowDAO = new m_RowDAO(settings);
        dataCategoryDAO = new DataCategoryDAO(settings);
        makeSureTableColumnsIRelyUponAreIndexed();
    }

    @Override
    public IDatasetDocument getDataset(String id) {

        DatasetDocument datasetDocument = (DatasetDocument) getOneWithId(id);
        return datasetDocument;
    }

    @Override
    public ObjectId add(IDatasetDocument datasetDocument, IRowCollection data) {

        ObjectId objectId = add(datasetDocument);

        rowDAO.add(objectId, datasetDocument, data);

        String dataCategory = datasetDocument.getMetadata().getDataCategory();
        Set columnNames = data.getColumnNames();
        associateColumnNamesToTheDataCategory(dataCategory, columnNames);

        return objectId;
    }

    protected void associateColumnNamesToTheDataCategory(String dataCategory, Set columnNames) {

        IDataCategoryDocument dataCategoryDocument = dataCategoryDAO.getByName(dataCategory);

        if (dataCategoryDocument == null) {

            dataCategoryDocument = new DataCategoryDocument();
            dataCategoryDocument.setName(dataCategory);
            ObjectId objectId = dataCategoryDAO.add(dataCategoryDocument);
            dataCategoryDocument = dataCategoryDAO.get(objectId);
        }

        Set<String> columnNamesFromTheDatabase = dataCategoryDocument.getColumnNames();
        columnNamesFromTheDatabase.addAll(columnNames);

        Document dataToBeUpdated = new Document().
                append(DataCategoryDocument.MONGO_KEY__COLUMN_NAMES, columnNamesFromTheDatabase);

        dataCategoryDAO.updateOne(dataCategoryDocument.getId(), dataToBeUpdated);
    }

    @Override
    public IDeleteResults delete(ObjectId objectId) {

        IDeleteResults deleteResults = rowDAO.deleteRowsAssociatedWithDataset(objectId);

        IDeleteResults deleteResultForDataset = super.delete(objectId);
        deleteResults.addAll(deleteResultForDataset);

        if (deleteResults.wasAcknowledged() == false) {
            return deleteResults;
        }

        return deleteResults;
    }

    protected Document createDocumentOfTypeDAOHandles(Document document) {

        return new DatasetDocument(document);
    }

    @Override
    public m_RowDAO getRowDAO() {
        return rowDAO;
    }

    @Override
    public IDataCategoryDAO getDataCategoryDAO() {
        return dataCategoryDAO;
    }

    private static boolean HAVE_MADE_SURE_TABLE_COLUMNS_ARE_INDEXED = false;

    protected void makeSureTableColumnsIRelyUponAreIndexed() {

        if (HAVE_MADE_SURE_TABLE_COLUMNS_ARE_INDEXED == false) {

            getCollection().createIndex(new Document().append(DatasetDocument.MONGO_KEY__ID, 1));
        }
    }
}
