package gov.energy.nbc.car.bo;

import gov.energy.nbc.car.settings.ISettings;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbsBusinessObjects implements IBusinessObjects {

    @Autowired
    protected ISettings settings;

    protected IDataCategoryBO dataCategoryBO;
    protected IDatasetBO datasetBO;
    protected IRowBO rowBO;
    protected IPhysicalFileBO physicalFileBO;
    protected ITestDataBO testDataBO;


    public AbsBusinessObjects(ISettings settings) {

        this.settings = settings;
        init();
    }

    protected abstract void init();

    @Override
    public ISettings getSettings() {
        return settings;
    }

    @Override
    public IDataCategoryBO getDataCategoryBO() {
        return dataCategoryBO;
    }

    @Override
    public IDatasetBO getDatasetBO() {
        return datasetBO;
    }

    @Override
    public IPhysicalFileBO getPhysicalFileBO() {
        return physicalFileBO;
    }

    @Override
    public IRowBO getRowBO() {
        return rowBO;
    }

    @Override
    public ITestDataBO getTestDataBO() {
        return testDataBO;
    }
}
