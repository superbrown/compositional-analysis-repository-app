package gov.energy.nbc.spreadsheet.model.document;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import gov.energy.nbc.spreadsheet.model.AbstractDocument;
import gov.energy.nbc.spreadsheet.model.common.Data;
import gov.energy.nbc.spreadsheet.model.common.Metadata;
import gov.energy.nbc.spreadsheet.model.common.StoredFile;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class SpreadsheetDocument extends AbstractDocument {

    public static final String ATTRIBUTE_KEY__METADATA = "metadata";
    public static final String ATTRIBUTE_KEY__DATA = "data";

    public SpreadsheetDocument(String json) {
        super(json);
    }

    public SpreadsheetDocument(Object object) {
        super(object);
    }

    public SpreadsheetDocument(Metadata metadata, Data data) {

        init(metadata, data);
    }

    public SpreadsheetDocument(String sampleType,
                               Date submissionDate,
                               String submitter,
                               String chargeNumber,
                               String projectName,
                               String comments,
                               StoredFile originalUploadedFile,
                               List<StoredFile> attachments,
                               List<List<Object>> spreadsheetContent) {

        Metadata metadata = new Metadata(
                sampleType,
                submissionDate,
                submitter,
                chargeNumber,
                projectName,
                comments,
                originalUploadedFile,
                attachments);

        Data data = new Data(spreadsheetContent);

        init(metadata, data);
    }

    protected void init(String json) {

        BasicDBObject parsedJson = (BasicDBObject) JSON.parse(json);

        ObjectId objectId = parsedJson.getObjectId(ATTRIBUTE_KEY__ID);
        if (objectId != null) {
            put(ATTRIBUTE_KEY__ID, objectId);
        }
        Metadata metadata = new Metadata(JSON.serialize(parsedJson.get(ATTRIBUTE_KEY__METADATA)));
        Data data = new Data(JSON.serialize(parsedJson.get(ATTRIBUTE_KEY__DATA)));

        init(metadata, data);
    }

    private void init(Metadata metadata, Data data) {

        put(ATTRIBUTE_KEY__METADATA, metadata);
        put(ATTRIBUTE_KEY__DATA, data);
    }

    public Metadata getMetadata() {
        return (Metadata) get(ATTRIBUTE_KEY__METADATA);
    }

    public Data getData() {
        return (Data) get(ATTRIBUTE_KEY__DATA);
    }
}
