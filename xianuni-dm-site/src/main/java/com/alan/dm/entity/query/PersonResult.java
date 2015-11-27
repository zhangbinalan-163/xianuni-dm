package com.alan.dm.entity.query;

/**
 * Created by apple on 15/11/27.
 */
public class PersonResult {
    private boolean includeOrgnization=true;
    private boolean includeApplierInfo;
    private boolean includeActivitistInfo;
    private boolean includeIntentionInfo;
    private boolean includePrepareInfo;
    private boolean includeNormalInfo;

    public boolean isIncludeOrgnization() {
        return includeOrgnization;
    }

    public void setIncludeOrgnization(boolean includeOrgnization) {
        this.includeOrgnization = includeOrgnization;
    }

    public boolean isIncludeApplierInfo() {
        return includeApplierInfo;
    }

    public void setIncludeApplierInfo(boolean includeApplierInfo) {
        this.includeApplierInfo = includeApplierInfo;
    }

    public boolean isIncludeActivitistInfo() {
        return includeActivitistInfo;
    }

    public void setIncludeActivitistInfo(boolean includeActivitistInfo) {
        this.includeActivitistInfo = includeActivitistInfo;
    }

    public boolean isIncludeIntentionInfo() {
        return includeIntentionInfo;
    }

    public void setIncludeIntentionInfo(boolean includeIntentionInfo) {
        this.includeIntentionInfo = includeIntentionInfo;
    }

    public boolean isIncludePrepareInfo() {
        return includePrepareInfo;
    }

    public void setIncludePrepareInfo(boolean includePrepareInfo) {
        this.includePrepareInfo = includePrepareInfo;
    }

    public boolean isIncludeNormalInfo() {
        return includeNormalInfo;
    }

    public void setIncludeNormalInfo(boolean includeNormalInfo) {
        this.includeNormalInfo = includeNormalInfo;
    }
}
