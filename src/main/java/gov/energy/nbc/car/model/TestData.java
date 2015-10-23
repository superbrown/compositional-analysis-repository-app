package gov.energy.nbc.car.model;

import gov.energy.nbc.car.model.common.StoredFile;
import gov.energy.nbc.car.model.document.SpreadsheetDocument;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *     W A R N I N G !
 *
 * Exercise caution when changing values in this class as unit tests have been written with these values in mind.
 */
public class TestData {

    public static Date date_1 = new Date();
    public static Date date_2 = new Date();
    public static Date date_3 = new Date();
    public static Date date_4 = new Date();

    public static Object[][] spreadsheetValues_1;
    public static SpreadsheetDocument spreadsheetDocument_1;

    public static Object[][] spreadsheetValues_2;
    public static SpreadsheetDocument spreadsheetDocument_2;

    public static String sampleType;

    public static ObjectId objectId_1;
    public static Date submissionDate_1;
    public static String submitter_1;
    public static String chargeNumber_1;
    public static String projectName_1;
    public static String comments_1;
    public static StoredFile originallyUploadedFile_1;
    public static List<StoredFile> attachments_1;
    public static List<String> tags_1;

    public static ObjectId objectId_2;
    public static Date submissionDate_2;
    public static String submitter_2;
    public static String chargeNumber_2;
    public static String projectName_2;
    public static String comments_2;
    public static StoredFile originallyUploadedFile_2;
    public static List<StoredFile> attachments_2;
    public static List<String> tags_2;

    public static final String ALGEA = "algea";

    static {
        sampleType = ALGEA;

        submissionDate_1 = new Date();
        submitter_1 = "Submitter 1";
        chargeNumber_1 = "Charge Number 1";
        projectName_1 = "Project Name 1";
        comments_1 = "Comment 1";
        tags_1 = Arrays.asList("tag 1", "tag 2", "tag 3");
        originallyUploadedFile_1 = new StoredFile("{ originalFileName: \"Spreadsheet 1.xls\", storageLocation : \"uploadedFiles/2015-10-20_08_12_23_124/Spreadsheet 1.xls\"}");
        attachments_1 = null;

        submissionDate_2 = new Date();
        submitter_2 = "Submitter 2";
        chargeNumber_2 = "Charge Number 3";
        projectName_2 = "Project Name 3";
        comments_2 = "Comment 2";
        tags_2 = Arrays.asList("tag 2", "tag 2", "tag 3");
        originallyUploadedFile_2 = new StoredFile("{ originalFileName: \"Spreadsheet 2.xls\", storageLocation : \"uploadedFiles/2015-10-20_08_02_00_231/Spreadsheet 2.xls\"}");
        attachments_2 = Arrays.asList(
                new StoredFile("{ originalFileName: \"name_3\", storageLocation : \"uuid_3\"}"),
                new StoredFile("{ originalFileName: \"name_4\", storageLocation : \"uuid_4\"}"),
                new StoredFile("{ originalFileName: \"name_5\", storageLocation : \"uuid_5\"}"));


        spreadsheetValues_1 = new Object[][]{
                {
                        "Some Column Name",
                        "String Values Column Name" ,
                        "Date Values Column Name",
                        "Float Values Column Name",
                        "Integer Values Column Name",
                        "Varying Value Types Column Name"},
                {1, "String 1", date_1, 1.11, 1, date_1},
                {2, "String 2", date_2, 3.33, 2, "String"},
                {3, "String 3", date_3, 3.33, 3, 1000},
        };

        spreadsheetValues_2 = new Object[][]{
                {
                        "Some Column Name",
                        "Date Values Column Name",
                        "Float Values Column Name",
                        "Integer Values Column Name",
                        "Additional Column Name 1",
                        "Additional new Column Name 2"},
                {1, date_1, 1.22, 1, "a1", "b1"},
                {2, date_2, 2.11, 2, "a2", "b2"},
                {3, date_3, 3.33, 3, "a3", "b3"},
                {4, date_4, 4.55, 4, "a4", "b4"},
        };


        spreadsheetDocument_1 = new SpreadsheetDocument(
                sampleType,
                submissionDate_1,
                submitter_1,
                chargeNumber_1,
                projectName_1,
                comments_1,
                originallyUploadedFile_1,
                attachments_1,
                toListOfLists(spreadsheetValues_1));

        spreadsheetDocument_2 = new SpreadsheetDocument(
                sampleType,
                submissionDate_2,
                submitter_2,
                chargeNumber_2,
                projectName_2,
                comments_2,
                originallyUploadedFile_2,
                attachments_2,
                toListOfLists(spreadsheetValues_2));
        }

    private static List<List> toListOfLists(Object[][] array) {

        List<List> listOfLists = new ArrayList();

        for (Object[] item : array) {

            List objectList = Arrays.asList(item);
            listOfLists.add(objectList);
        }

        return listOfLists;
    }
}
