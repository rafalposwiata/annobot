import * as React from "react";
import {datasetService} from "../service/DatasetService";
import {Input, Upload, Button, Icon} from "antd";
import Dragger from "antd/lib/upload/Dragger";

export interface DatasetState {
    datasetName?: string;
}

export class DatasetAdd extends React.Component<any, DatasetState> {

    constructor(props: Readonly<any>) {
        super(props);
        this.state = {};

        this.handleDatasetNameChange = this.handleDatasetNameChange.bind(this);
        this.uploadUrl = this.uploadUrl.bind(this);
        this.afterUpload = this.afterUpload.bind(this);
    }

    private handleDatasetNameChange(event: any) {
        this.setState({...this.state, datasetName: event.target.value});
    }

    private uploadUrl(): string {
        return datasetService.uploadUrl(this.state.datasetName || '');
    }

    private afterUpload(info: any): void {
        if (info.file.status === 'done') {
            this.props.history.push(`/dashboard/dataset/list`);
        }
    }

    private upload(): React.ReactElement {
        if (this.state.datasetName) {
            return <Dragger action={this.uploadUrl}
                            onChange={this.afterUpload}>
                <p className="ant-upload-drag-icon">
                    <Icon type="inbox"/>
                </p>
                <p className="ant-upload-text">Click or drag file with dataset</p>
                <p className="ant-upload-hint">
                    Support for text files.
                </p>
            </Dragger>
        }
        return <span/>;
    }

    render(): React.ReactElement {
        return (
            <div>
                <div className="page-header">Adding dataset</div>
                <div className="page-content" style={{width: 500}}>
                    <p><span className="field-label">Dataset name:</span>
                        <Input placeholder="Name"
                               required={true}
                               style={{width: '10rem'}}
                               value={this.state.datasetName}
                               onChange={this.handleDatasetNameChange}/>
                    </p>
                    {this.upload()}
                </div>
            </div>
        );
    }
}