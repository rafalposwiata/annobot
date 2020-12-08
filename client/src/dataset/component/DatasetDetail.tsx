import {DatasetInfo} from "../model/dataset_model";
import {Button, Table} from 'antd';
import {datasetService} from "../service/DatasetService";
import React = require("react");

export interface DatasetDetailProps {
    datasetId: number;
}

export interface DatasetDetailState {
    info: DatasetInfo,
}

export class DatasetDetail extends React.Component<DatasetDetailProps, DatasetDetailState> {

    constructor(props: Readonly<DatasetDetailProps>) {
        super(props);
        this.state = {info: {items: []}};

        this.handleExport = this.handleExport.bind(this);
    }

    public componentDidMount(): void {
        const id = this.props.datasetId;
        if (id) {
            datasetService.datasetItems(id).then(info => {
                this.setState({...this.state, info: info});
            })
        }
    }

    private info(): React.ReactElement {
        const info = this.state.info;
        return <h3>Dataset: {info.name}</h3>;
    }

    private iaa(): React.ReactElement {
        const iaa = this.state.info.iaa || {value: 0, unknown: true};
        let iaaValue = iaa.unknown ? '-' : Math.round(iaa.value * 100) / 100;
        return <div style={{fontSize: '1.5rem', fontWeight: 500}}>IAA: {iaaValue} (Cohen kappa)</div>
    }

    private export(): React.ReactElement {
        return <div style={{display: 'flex', justifyContent: 'flex-end', marginRight: '1rem'}}>
            <Button type="primary" onClick={this.handleExport}>
                Export
            </Button>
        </div>;
    }

    private handleExport(event: any) {
        event.preventDefault();
        const url = datasetService.downloadUrl(this.state.info.id || -1);
        window.open(url,'_blank');
        window.open(url);
    }

    private items(): React.ReactElement {
        const columns = [
            {
                title: 'Text',
                dataIndex: 'text',
                key: 'text',
            },
            {
                title: 'Label',
                dataIndex: 'label',
                key: 'label',
            },
            {
                title: 'No. annotations',
                dataIndex: 'noAnnotations',
                key: 'noAnnotations',
            },
        ];

        const data = this.state.info.items.map(item => {
            return {
                key: item.datasetItemId,
                text: item.content,
                label: item.goldLabel,
                noAnnotations: item.annotations.length
            }
        });

        return <Table columns={columns} dataSource={data} style={{marginLeft: '-1rem'}}/>;
    }

    render(): React.ReactElement {
        return (
            <div className="page-content">
                {this.info()}
                {this.iaa()}
                {this.export()}
                {this.items()}
            </div>
        );
    }
}