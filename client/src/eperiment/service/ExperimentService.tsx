import {Info} from "../model/experiment_model";
import {api} from "../../api";

class ExperimentService {

    public sendInfo(info: Info): Promise<any> {
        return api.post("/statistics", info);
    }
}

export const experimentService = new ExperimentService();