export interface DatasetShortInfo {
    id?: number;
    name?: string;
}

export interface DatasetInfo extends DatasetShortInfo {
    items: DatasetItem[];
    iaa?: IAA;
}

export interface DatasetItem {
    datasetItemId: number;
    datasetId: number;
    content: string;
    goldLabel: string;
    annotations: DatasetItemAnnotation[];
}

export interface DatasetItemAnnotation {
    conversationId: string;
    annotation: string;
}

export interface IAA {
    value: number;
    unknown: boolean;
}