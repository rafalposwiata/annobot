package com.annobot.dataset.model;

import java.util.List;

public class DatasetInfo extends DatasetShortInfo {

    private List<DatasetItem> items;
    private IAA iaa;

    public DatasetInfo(Long id, String name, List<DatasetItem> items, IAA iaa) {
        super(id, name);
        this.items = items;
        this.iaa = iaa;
    }

    public List<DatasetItem> getItems() {
        return items;
    }

    public void setItems(List<DatasetItem> items) {
        this.items = items;
    }

    public IAA getIaa() {
        return iaa;
    }

    public void setIaa(IAA iaa) {
        this.iaa = iaa;
    }
}
