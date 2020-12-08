import {api} from "../../api";
import {DatasetInfo, DatasetShortInfo} from "../model/dataset_model";

class DatasetService {

    public upload(datasetName: string, formData: FormData): Promise<string> {
        return api.postFile(`/dataset/upload/${datasetName}`, formData);
    }

    public uploadUrl(datasetName: string): string {
        return `${api.url}/dataset/upload/${datasetName}`;
    }

    public downloadUrl(datasetId: number): string {
        return `${api.url}/dataset/download/${datasetId}`;
    }

    public datasets(): Promise<DatasetShortInfo[]> {
        return api.get('/dataset');
    }

    public datasetItems(datasetId: number): Promise<DatasetInfo> {
        return api.get(`/dataset/${datasetId}`);
    }

    public delete(datasetId: number): Promise<string> {
        return api.delete(`/dataset`, {datasetId: datasetId});
    }
}

export const datasetService = new DatasetService();