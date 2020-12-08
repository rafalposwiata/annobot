import * as React from "react";
import {datasetService} from "../service/DatasetService";
import {Link} from "react-router-dom";
import {Avatar, Card, Empty, Icon, Tooltip} from "antd";
import {DatasetShortInfo} from "../model/dataset_model";

const {Meta} = Card;

export interface DatasetListState {
    datasets: DatasetShortInfo[]
}

export class DatasetList extends React.Component<any, DatasetListState> {

    constructor(props: Readonly<any>) {
        super(props);
        this.state = {datasets: []};
    }

    public componentDidMount(): void {
        datasetService.datasets().then(datasets => {
            this.setState({...this.state, datasets: datasets});
        })
    }

    private datasets(): React.ReactElement {
        let groups = [];
        let elements = [];
        let count = 1;

        const datasetsNumber = this.state.datasets.length;
        for (let i = 0; i <= datasetsNumber; i++) {
            if (i == datasetsNumber) {
                elements.push(this.addCard());
            } else {
                elements.push(this.datasetCard(this.state.datasets[i]));
            }
            if (count % 3 == 0) {
                groups.push(<div>{elements}</div>);
                elements = [];
            }
            count++;
        }
        if (elements.length > 0) {
            groups.push(<div>{elements}</div>);
        }
        return <div>
            {groups.length == 0 ? <Empty/> : groups}
        </div>;
    }

    private datasetCard(dataset: DatasetShortInfo): React.ReactElement {
        return (<Card style={{width: 300, marginBottom: '2rem', marginRight: '2rem', display: 'inline-block'}}
                      actions={[
                          <Tooltip title="edit conversation schema">
                              <Link to={"/dashboard/dataset/list/" + dataset.id}><Icon type="edit" key="edit" /></Link>
                          </Tooltip>,
                          <Tooltip title="delete">
                              <div onClick={() => this.delete(dataset.id || -1)}><Icon type="delete" key="delete" /></div>
                          </Tooltip>
                      ]}>
            <Meta
                avatar={
                    <Avatar style={{backgroundColor: '#2e5077'}} icon="database"/>
                }
                title={<Link to={"/dashboard/dataset/list/" + dataset.id}>{dataset.name}</Link>}
                description=""
            />
        </Card>)
    }

    private addCard(): React.ReactElement {
        return (<Link to={"/dashboard/dataset/add"}><Card style={{width: 300, marginBottom: '2rem', marginRight: '2rem', display: 'inline-block',
            paddingBottom: '3rem', paddingTop: '0.25rem', borderStyle: 'dashed'}}>
            <span style={{display: 'block', textAlign: 'center', position: 'relative', top: '1rem', color: 'black'}} >
                <Icon type="plus" key="plus" style={{marginRight: '0.2rem'}}/>add new</span>
        </Card>
        </Link>);
    }

    private delete(datasetId: number) {
        datasetService.delete(datasetId).then(response => {
            const datasets = this.state.datasets.filter(dataset => dataset.id !== datasetId);
            this.setState({...this.state, datasets: datasets});
        });
    }

    render(): React.ReactElement {
        return (
            <div>
                <div className="page-header">Datasets</div>
                <div className="page-content">
                    {this.datasets()}
                </div>
            </div>
        );
    }
}