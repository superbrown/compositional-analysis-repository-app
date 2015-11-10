package gov.energy.nbc.car.bo;

import gov.energy.nbc.car.bo.exception.DeletionFailure;
import gov.energy.nbc.car.dao.IDatasetDAO;
import gov.energy.nbc.car.dao.dto.FileAsRawBytes;
import gov.energy.nbc.car.dao.dto.StoredFile;
import gov.energy.nbc.car.utilities.fileReader.exception.InvalidValueFoundInHeader;
import gov.energy.nbc.car.utilities.fileReader.exception.UnsupportedFileExtension;

import java.io.File;
import java.util.Date;
import java.util.List;


public interface IDatasetBO {

    String addDataset(
            TestMode testMode,
            String dataCategory,
            Date submissionDate,
            String submitter,
            String projectName,
            String chargeNumber,
            String comments,
            StoredFile dataFile,
            String nameOfWorksheetContainingTheData,
            List<StoredFile> attachmentFiles)
            throws UnsupportedFileExtension, InvalidValueFoundInHeader;

        String getDataset(TestMode testMode, String datasetId);

        String getAllDatasets(TestMode testMode);

        long deleteDataset(TestMode testMode,
                           String datasetId) throws DeletionFailure;

        String addDataset(
                TestMode testMode,
                String dataCategory,
                Date submissionDate,
                String submitter,
                String projectName,
                String chargeNumber,
                String comments,
                FileAsRawBytes dataFile,
                String nameOfSheetContainingData,
                List<FileAsRawBytes> attachmentFiles)
                                                          throws UnsupportedFileExtension, InvalidValueFoundInHeader;

        String addDataset(TestMode testMode,
                          String metadataJson,
                          File file,
                          String nameOfWorksheetContainingTheData)
                                                                  throws UnsupportedFileExtension, InvalidValueFoundInHeader;

        IDatasetDAO getDatasetDAO(TestMode testMode);
}
