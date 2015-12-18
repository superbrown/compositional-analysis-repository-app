package gov.energy.nbc.car.dao.mongodb.singleCellCollectionApproach;

import gov.energy.nbc.car.settings.ISettings;
import gov.energy.nbc.car.dao.IDataCategoryDAO;
import gov.energy.nbc.car.dao.IDatasetDAO;
import gov.energy.nbc.car.dao.IRowDAO;
import gov.energy.nbc.car.dao.dto.IDeleteResults;
import gov.energy.nbc.car.dao.mongodb.AbsDAO;
import gov.energy.nbc.car.dao.mongodb.DataCategoryDAO;
import gov.energy.nbc.car.model.IDataCategoryDocument;
import gov.energy.nbc.car.model.IDatasetDocument;
import gov.energy.nbc.car.model.IRowCollection;
import gov.energy.nbc.car.model.mongodb.document.DataCategoryDocument;
import gov.energy.nbc.car.model.mongodb.document.DatasetDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Set;

public class s_DatasetDAO extends AbsDAO implements IDatasetDAO
{
    public static final String COLLECTION_NAME = "dataset";
    protected DataCategoryDAO dataCategoryDAO;
    protected IRowDAO rowDAO;

    public s_DatasetDAO(ISettings settings) {

        super(COLLECTION_NAME, settings);

        rowDAO = new s_RowDAO(settings);
        dataCategoryDAO = new DataCategoryDAO(settings);
        makeSureTableColumnsIRelyUponAreIndexed();
    }

    public IDatasetDocument getDataset(String id) {

        DatasetDocument metadata = (DatasetDocument) getOneWithId(id);
        return metadata;
    }

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
                append(DataCategoryDocument.ATTR_KEY__COLUMN_NAMES, columnNamesFromTheDatabase);

        dataCategoryDAO.updateOne(dataCategoryDocument.getId(), dataToBeUpdated);
    }

    @Override
    public IDeleteResults delete(ObjectId objectId) {

        IDeleteResults deleteResults = super.delete(objectId);

        IDeleteResults deleteResultsForRows = rowDAO.deleteRowsAssociatedWithDataset(objectId);
        deleteResults.addAll(deleteResultsForRows);

        return deleteResults;
    }

    @Override
    protected Document createDocumentOfTypeDAOHandles(Document document) {

        return new DatasetDocument(document);
    }

    public IRowDAO getRowDAO() {
        return rowDAO;
    }

    public IDataCategoryDAO getDataCategoryDAO() {
        return dataCategoryDAO;
    }

    private static boolean HAVE_MADE_SURE_TABLE_COLUMNS_ARE_INDEXED = false;

    protected void makeSureTableColumnsIRelyUponAreIndexed() {

        if (HAVE_MADE_SURE_TABLE_COLUMNS_ARE_INDEXED == false) {

            getCollection().createIndex(new Document().append(DatasetDocument.ATTR_KEY__ID, 1));
        }
    }
}
