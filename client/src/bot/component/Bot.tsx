import * as React from "react";
import {
    AskAboutSomethingElement,
    ConversationSchemaElement,
    DatasetElement,
    ExternalClient,
    ExternalClientConfig,
    FacebookConfig,
    Label,
    LabelingSchema,
    ModelPredictionElement,
    NextStepCondition,
    SimpleMessageElement,
    Type,
    UserInputType
} from "../model/bot_model";
import {Link} from "react-router-dom";
import {Button, Collapse, Icon, Input, Modal, Radio, Select, Switch} from 'antd';
import {botService} from '../service/BotService'
import {EditableTagGroup} from "../../common/component/EditableTagGroup";
import {DatasetInfo} from "../../dataset/model/dataset_model";
import {datasetService} from "../../dataset/service/DatasetService";

const {Panel} = Collapse;
const {TextArea} = Input;
const {Option} = Select;

export interface BotProps {
    editMode: boolean;
    botName: string;
    readonly: boolean;
}

export interface BotState {
    name: string;
    mlModelUrl?: string;
    externalClientsConfigs: ExternalClientConfig[];
    conversationSchema: ConversationSchemaElement[];

    modalVisible: boolean;
    saveModalVisible: boolean;
    saved: boolean;
    newComponentName?: string;
    newComponentType?: Type;

    otherSettings: boolean;
    activeSamplingMode: string;
    onlineLearningMode: string;

    datasets: DatasetInfo[];
}

export class Bot extends React.Component<BotProps, BotState> {

    constructor(props: Readonly<BotProps>) {
        super(props);
        this.state = {
            name: '', otherSettings: false, modalVisible: false, saveModalVisible: false, saved: false,
            externalClientsConfigs: [
                {type: ExternalClient.FACEBOOK}
            ], conversationSchema: [], datasets: [], activeSamplingMode: '', onlineLearningMode: ''
        };

        this.showModal = this.showModal.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeNewComponent = this.handleChangeNewComponent.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.toggleOtherSettings = this.toggleOtherSettings.bind(this);
    }

    public componentDidMount(): void {
        if (this.props.editMode) {
            botService.getConfig(this.props.botName).then(config => {
                this.setState({
                    ...this.state, name: config.name, mlModelUrl: config.mlModelUrl,
                    saved: true, conversationSchema: config.conversationSchema,
                    externalClientsConfigs: config.externalClientsConfigs
                });
            });
        }
        datasetService.datasets().then(datasets => {
            this.setState({...this.state, datasets: datasets});
        })
    }

    private mainSection(): React.ReactElement {
        let readOnlyInfo = <span></span>;
        if (this.props.readonly) {
            readOnlyInfo = <span style={{float: 'right', paddingTop: '1rem', color: '#ff9712'}}>
                Bot configuration for COLING demonstrations (readonly)
            </span>;
        }
        let externalConfigs = [];
        for (let externalClientConfig of this.state.externalClientsConfigs) {
            if (externalClientConfig.type == ExternalClient.FACEBOOK) {
                let facebookConfig: FacebookConfig = externalClientConfig;
                externalConfigs.push(<h4 key="facebook_config">
                    <span style={{textDecoration: "underline"}}>Facebook configuration</span>
                </h4>)
                externalConfigs.push(<p><span className="field-label">Verify token:</span>
                    <Input placeholder="e.g. token1234" key={facebookConfig.type + "_verifyToken"}
                           required={true}
                           field-name="verifyToken"
                           disabled={this.props.readonly}
                           value={facebookConfig.verifyToken}
                           onChange={this.onValueChange.bind({root: this, element: facebookConfig})}/>
                </p>);
                externalConfigs.push(<p><span className="field-label">Page access token:</span>
                    <Input placeholder="e.g. AsZA34nOMNmDCa65VqaQWFOIiiZC3KHWAvObxRFHTMVMLIdTtAGw"
                           type={'password'}
                           key={facebookConfig.type + "_pageAccessToken"}
                           required={true}
                           field-name="pageAccessToken"
                           disabled={this.props.readonly}
                           value={facebookConfig.pageAccessToken}
                           onChange={this.onValueChange.bind({root: this, element: facebookConfig})}/>
                </p>);
            }
        }

        let linkToChat = null;
        if (this.state.saved){
            linkToChat = <div style={{position: 'relative'}}>
                <Link to={"/chat/" + this.state.name} target="_blank" style={{
                    position: 'absolute', right: 0,
                    fontSize: '2rem', bottom: '1rem'
                }}><Icon type="message" key="message"/></Link>
            </div>;
        }

        return (<div>
            {readOnlyInfo}
            <h3>General bot configuration</h3>
            {linkToChat}
            <p><span className="field-label">Bot name:</span>
                <Input placeholder="name"
                       required={true}
                       disabled={this.props.readonly}
                       value={this.state.name}
                       onChange={(event) => {
                           this.setState({...this.state, name: event.target.value});
                       }}/>
            </p>
            <p><span className="field-label">ML model service url:</span>
                <Input placeholder="url"
                       required={true}
                       disabled={this.props.readonly}
                       value={this.state.mlModelUrl}
                       onChange={(event) => {
                           this.setState({...this.state, mlModelUrl: event.target.value});
                       }}/>
            </p>
            {externalConfigs}
        </div>)
    }

    private createElement(element: ConversationSchemaElement): React.ReactElement {
        if (element.type == Type.DATASET) {
            return this.dataset(element as DatasetElement);
        } else if (element.type == Type.ASK_ABOUT_SOMETHING) {
            return this.askAbout(element as AskAboutSomethingElement);
        } else if (element.type == Type.MODEL_PREDICTION) {
            return this.modelPrediction(element as ModelPredictionElement);
        }

        return this.simpleMessage(element as SimpleMessageElement);
    }

    private nextSteps(element: ConversationSchemaElement): React.ReactElement {
        if (element.nextSteps == undefined || element.nextSteps.length == 0) {
            return <span>default&nbsp;
                <a onClick={() => {
                    element.nextSteps = [{condition: NextStepCondition.AFTER_COMPLETE, elementName: ""}]
                    this.updateElement(this, element);
                }}>edit</a></span>
        } else {
            let nextStep = element.nextSteps[0];

            let n = <span></span>;
            if (nextStep.condition == NextStepCondition.AFTER_N_SAMPLES) {
                n = <span>
                        <span className="field-label">N:</span>
                        <Input field-name="n" style={{width: 200}}
                               disabled={this.props.readonly}
                               value={nextStep.n}
                               onChange={this.onValueChange.bind({root: this, element: nextStep})}/>
                </span>;
            }

            return <div>
                <span className="field-label">Condition:</span>
                <Select defaultValue={NextStepCondition.AFTER_COMPLETE} style={{width: 200}}
                        value={nextStep.condition}
                        field-name="condition"
                        disabled={this.props.readonly}
                        onChange={v => {
                            nextStep.condition = v;
                            this.updateElement(this, nextStep);
                        }}>
                    <Option value={NextStepCondition.AFTER_COMPLETE}>after complete</Option>
                    <Option value={NextStepCondition.AFTER_N_SAMPLES}>after n samples</Option>
                </Select>
                {n}
                <span className="field-label">Element name:</span>
                <Input field-name="elementName" style={{width: 200}}
                       disabled={this.props.readonly}
                       value={nextStep.elementName}
                       onChange={this.onValueChange.bind({root: this, element: nextStep})}/>
            </div>;
        }
    }

    private simpleMessage(element: SimpleMessageElement): React.ReactElement {
        return <div>
            <span className="field-label">Possible messages:</span>
            <TextArea rows={3} key={element.id + "_textArea"}
                      field-name="possibleMessages"
                      disabled={this.props.readonly}
                      value={element.possibleMessages}
                      onChange={this.onValueChange.bind({root: this, element: element})}/>
            <span className="short-field-label">Wait for answer:</span>
            <Switch key={element.id + "_switch"}
                    field-name="waitForAnswer"
                    disabled={this.props.readonly}
                    onClick={(v => {
                        element.waitForAnswer = v;
                        this.updateElement(this, element);
                    })}
                    checked={element.waitForAnswer}/>
        </div>;
    }

    private askAbout(element: AskAboutSomethingElement): React.ReactElement {
        let validationMessage = element.validator != 'none' ? (<div>
            <span className="field-label">Validation message:</span>
            <Input key={element.id + "_input"}
                   field-name="validationMessage"
                   disabled={this.props.readonly}
                   value={element.validationMessage}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
        </div>) : null;

        return <div>
            <span className="field-label">Question:</span>
            <Input key={element.id + "_input"}
                   field-name="possibleMessages"
                   disabled={this.props.readonly}
                   value={element.possibleMessages}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <span className="field-label">Validator:</span>
            <Select defaultValue="none" style={{width: 120}}
                    value={element.validator}
                    field-name="validator"
                    disabled={this.props.readonly}
                    onChange={v => {
                        element.validator = v;
                        this.updateElement(this, element);
                    }}>
                <Option value="none">-</Option>
                <Option value="confirmation">confirmation</Option>
                <Option value="number">number</Option>
            </Select>
            {validationMessage}
        </div>;
    }

    private datasetSelect(element: DatasetElement | ModelPredictionElement): React.ReactElement {
        const options = [<Option value={-1}>-</Option>];
        for (let dataset of this.state.datasets) {
            options.push(<Option value={dataset.id}>{dataset.name}</Option>)
        }

        return (<Select defaultValue={null} style={{width: 120}}
                        value={element.datasetId}
                        field-name="datasetName"
                        disabled={this.props.readonly}
                        onChange={(v: number) => {
                            element.datasetId = v;
                            this.updateElement(this, element);
                        }}>
            {options}
        </Select>);
    }

    private userInputTYpeSelect(element: DatasetElement): React.ReactElement {
        return <Select defaultValue="" style={{width: 120}}
                       value={element.userInputType}
                       field-name="userInputType"
                       disabled={this.props.readonly}
                       onChange={v => {
                           element.userInputType = v;
                           this.updateElement(this, element);
                       }}>
            <Option value="">-</Option>
            <Option value={UserInputType.LABEL}>label</Option>
            <Option value={UserInputType.TEXT}>text</Option>
            <Option value={UserInputType.NUMBER}>number</Option>
            <Option value={UserInputType.IMAGE}>image</Option>
        </Select>;
    }

    private models(size): React.ReactElement {
        return <Select style={{width: size}}>
            <Option value="">-</Option>
            <Option value="SVM_binary">SVM_binary</Option>
            <Option value="LSTM_binary">LSTM_binary</Option>
            <Option value="BiLSTM_binary">BiLSTM_binary</Option>
            <Option value="CNN_binary">CNN_binary</Option>
            <Option value="SVM_multiclass">SVM_multiclass</Option>
            <Option value="LSTM_multiclass">LSTM_multiclass</Option>
            <Option value="BiLSTM_multiclass">BiLSTM_multiclass</Option>
            <Option value="CNN_multiclass">CNN_multiclass</Option>
        </Select>;
    }

    private labelingSchemaSelect(element: DatasetElement): React.ReactElement {
        return <Select defaultValue="" style={{width: 120}}
                       value={element.labelingSchema}
                       field-name="labelSchema"
                       disabled={this.props.readonly}
                       onChange={v => {
                           element.labelingSchema = v;
                           this.updateElement(this, element);
                       }}>
            <Option value="">-</Option>
            <Option value={LabelingSchema.BINARY}>binary</Option>
            <Option value={LabelingSchema.MULTICLASS}>multi-class</Option>
            <Option value={LabelingSchema.MULTILABEL}>multi-label</Option>
        </Select>;
    }

    private userInput(element: DatasetElement): React.ReactElement {
        if (element.userInputType == UserInputType.LABEL) {
            return <div>
                <span className="short-field-label">Labeling schema:</span>
                {this.labelingSchemaSelect(element)}
                <span className="field-label">Labels:</span>
                <EditableTagGroup tags={element.labels} disable={this.props.readonly} updateFunc={(tags: Label[]) => {
                    element.labels = tags;
                    this.updateElement(this, element);
                }} binary={element.labelingSchema === LabelingSchema.BINARY}/>
            </div>;
        }
        return <span></span>;
    }

    private dataset(element: DatasetElement): React.ReactElement {
        return <div>
            <span className="short-field-label">Dataset name:</span>
            {this.datasetSelect(element)}
            <br/>
            <span className="short-field-label">User input type:</span>
            {this.userInputTYpeSelect(element)}
            {this.userInput(element)}
            <br/>
            <span className="short-field-label">Number of samples:</span>
            <Input field-name="numberOfSamples" style={{width: 120}}
                   disabled={this.props.readonly}
                   value={element.numberOfSamples}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <br/>
            <span className="short-field-label">Instruction:</span>
            <Input field-name="instruction"
                   disabled={this.props.readonly}
                   value={element.instruction}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <span className="field-label">Possible messages after each sample:</span>
            <TextArea rows={3}
                      field-name="afterEachSample"
                      disabled={this.props.readonly}
                      value={element.afterEachSample}
                      onChange={this.onValueChange.bind({root: this, element: element})}/>
            <span className="field-label">
                <Icon type={this.state.otherSettings ? 'down' : 'right'}/>
                <span style={{textDecoration: "underline", cursor: 'pointer'}} onClick={this.toggleOtherSettings}>Other settings</span>
                {this.otherSettings()}
            </span>
        </div>;
    }

    private toggleOtherSettings(): void {
        this.setState({...this.state, otherSettings: !this.state.otherSettings);
    }

    private otherSettings(): React.ReactElement {
        let activeSamplingBatchSize = null;
        if (this.state.activeSamplingMode == 'Batch') {
            activeSamplingBatchSize = <span>
                <span className="short-field-label">Batch size:</span>
                <Input field-name="act.batchSize" style={{width: '73%'}}/>
            </span>;
        }

        let onlineLearningBatchSize = null;
        if (this.state.onlineLearningMode == 'Batch') {
            onlineLearningBatchSize = <span>
                <span className="short-field-label">Batch size:</span>
                <Input field-name="on.batchSize" style={{width: '73%'}}/>
            </span>;
        }

        if (this.state.otherSettings) {
            return <div>
                <span className="field-label">Active sampling</span>
                <span className="short-field-label">Model name:</span>
                {this.models('73%')}
                <span className="short-field-label">Mode:</span>
                <Select style={{width: '73%'}}
                        value={this.state.activeSamplingMode}
                        onChange={v => {
                                this.setState({...this.state, activeSamplingMode: v});
                        }}>
                     <Option value={'ONE-BY-ONE'}>one by one</Option>
                     <Option value={'Batch'}>Batch</Option>
                </Select>
                {activeSamplingBatchSize}
                <span className="field-label">Online learning</span>
                <span className="short-field-label">Model name:</span>
                {this.models('73%')}
                <span className="short-field-label">Mode:</span>
                <Select style={{width: '73%'}}
                        value={this.state.onlineLearningMode}
                        onChange={v => {
                            this.setState({...this.state, onlineLearningMode: v});
                        }}>
                    <Option value={'ONE-BY-ONE'}>one by one</Option>
                    <Option value={'Batch'}>Batch</Option>
                </Select>
                {onlineLearningBatchSize}
            </div>
        }
        return null;
    }

    private modelPrediction(element: ModelPredictionElement): React.ReactElement {
        return (<div>
            <span className="field-label">Model name:</span>
            <Select value={element.modelName} style={{width: '100%'}}
                    field-name="modelName"
                    disabled={this.props.readonly}
                    onChange={v => {
                        element.modelName = v;
                        this.updateElement(this, element);
                    }}>
                <Option value="">-</Option>
                <Option value="SVM_binary">SVM_binary</Option>
                <Option value="LSTM_binary">LSTM_binary</Option>
                <Option value="BiLSTM_binary">BiLSTM_binary</Option>
                <Option value="CNN_binary">CNN_binary</Option>
                <Option value="SVM_multiclass">SVM_multiclass</Option>
                <Option value="LSTM_multiclass">LSTM_multiclass</Option>
                <Option value="BiLSTM_multiclass">BiLSTM_multiclass</Option>
                <Option value="CNN_multiclass">CNN_multiclass</Option>
            </Select>
            <span className="field-label">Introduction:</span>
            <Input field-name="introduction"
                   disabled={this.props.readonly}
                   value={element.introduction}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <span className="field-label">Message with model prediction:</span>
            <Input field-name="messageWithModelPrediction"
                   disabled={this.props.readonly}
                   value={element.messageWithModelPrediction}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <br/><br/>
            <span className="short-field-label" style={{width: '10rem'}}>Number of repetitions:</span>
            <Input field-name="numberOfRepetitions" style={{width: 120}}
                   disabled={this.props.readonly}
                   value={element.numberOfRepetitions}
                   onChange={this.onValueChange.bind({root: this, element: element})}/>
            <br/>
        </div>);
    }

    private onValueChange(event: any) {
        const target = event.target as HTMLElement;
        let fieldName: string = target.getAttribute("field-name") || '';

        let element = this.element;
        element[fieldName] = target.value;

        this.root.updateElement(this.root, element);
    }

    private updateElement(root: any, element: any) {
        const elements = root.state.conversationSchema;
        elements[element.id] = element;
        root.setState({
            ...this.state, conversationSchema: elements
        });
    }

    private icon(type: Type) {
        if (type == Type.DATASET) {
            return <Icon type="database" className="type-icon"/>
        } else if (type == Type.ASK_ABOUT_SOMETHING) {
            return <Icon type="question-circle" className="type-icon"/>
        } else if (type == Type.MODEL_PREDICTION) {
            return <Icon type="dot-chart" className="type-icon"/>
        }

        return <Icon type="message" className="type-icon"/>
    }

    private conversationSchema() {
        const elements: React.ReactElement[] = [];
        let counter = 0;
        for (let element of this.state.conversationSchema) {
            const e = (<Panel header={element.name} key={element.name + '_' + counter}
                              extra={this.icon(element.type)}>
                {this.createElement(element)}
                <Button type="danger" style={{float: 'right', margin: '0.5rem auto'}}
                        disabled={this.props.readonly}
                        onClick={(event) => {
                            event.preventDefault();
                            let elements = this.state.conversationSchema;
                            elements.splice(element.id, 1);
                            this.setState({...this.state, conversationSchema: elements});
                        }}>Delete</Button>
            </Panel>);
            elements.push(e);
            counter++;
        }
        const collapse = elements.length == 0 ? <div>Schema is empty</div> : (<Collapse>
            {elements}
        </Collapse>);
        return (<div>
            <h3>Conversation schema</h3>
            {collapse}
        </div>)
    }

    private addElement(): React.ReactElement {
        return (<div style={{display: 'inline-block'}}>
            <Button type="primary" size="large" onClick={this.showModal} disabled={this.props.readonly}>
                Add new schema element
            </Button>
            <Modal
                title="Add new schema element"
                visible={this.state.modalVisible}
                onOk={this.handleAdd}
                onCancel={this.handleCancel}>
                <p><strong>Component name:</strong></p>
                <Input placeholder="Name"
                       required={true}
                       className="new-comp-name"
                       value={this.state.newComponentName}
                       onChange={this.handleChangeNewComponent}/>
                <p><strong>Component type:</strong></p>
                <Radio.Group size="large" value={this.state.newComponentType} onChange={this.handleChange}>
                    <Radio.Button className="radio-type" value={Type.SIMPLE_MESSAGE}>Simple message</Radio.Button>
                    <Radio.Button className="radio-type" value={Type.ASK_ABOUT_SOMETHING}>Ask about
                        something</Radio.Button>
                    <Radio.Button className="radio-type" value={Type.DATASET}>Dataset samples</Radio.Button>
                    <Radio.Button className="radio-type" value={Type.MODEL_PREDICTION}>Model prediction</Radio.Button>
                </Radio.Group>
            </Modal>
        </div>);
    }

    private saveModal(): React.ReactElement {
        return <Modal
            title="Save"
            visible={this.state.saveModalVisible}
            onOk={this.handleCancel}
            onCancel={this.handleCancel}>
            <span>Configuration was saved correctly.</span>
        </Modal>
    }

    private save(): React.ReactElement {
        return <Button onClick={this.handleSave} className="next-step-button" disabled={this.props.readonly}
                       size="large" type="primary" style={{float: 'right'}}>Save</Button>
    }

    private handleSave(event: any) {
        event.preventDefault();
        botService.saveConfig({
            name: this.state.name,
            mlModelUrl: this.state.mlModelUrl,
            externalClientsConfigs: this.state.externalClientsConfigs,
            conversationSchema: this.state.conversationSchema
        });
        this.setState({...this.state, saveModalVisible: true, saved: true});
    }

    private showModal() {
        this.setState({...this.state, modalVisible: true});
    };

    private handleAdd() {
        if (this.state.newComponentName == undefined || this.state.newComponentType == undefined) {
            return;
        }

        const elements = this.state.conversationSchema;
        const idx = elements.length;

        let element: ConversationSchemaElement;
        if (this.state.newComponentType == Type.DATASET) {
            element = {
                id: idx,
                name: this.state.newComponentName,
                type: Type.DATASET,
                nextSteps: [],
                datasetId: -1,
                labels: []
            } as DatasetElement;
        } else if (this.state.newComponentType == Type.ASK_ABOUT_SOMETHING) {
            element = {
                id: idx,
                name: this.state.newComponentName,
                type: Type.ASK_ABOUT_SOMETHING,
                nextSteps: [],
                possibleMessages: '',
                waitForAnswer: true,
                validator: 'none'
            } as AskAboutSomethingElement;
        } else if (this.state.newComponentType == Type.MODEL_PREDICTION) {
            element = {
                id: idx,
                name: this.state.newComponentName,
                type: Type.MODEL_PREDICTION,
                nextSteps: [],
                modelName: ''
            } as ModelPredictionElement;
        } else {
            element = {
                id: idx,
                name: this.state.newComponentName,
                type: Type.SIMPLE_MESSAGE,
                nextSteps: [],
                possibleMessages: '',
                waitForAnswer: false
            } as SimpleMessageElement;
        }

        elements.push(element);
        this.setState({
            ...this.state, conversationSchema: elements, modalVisible: false,
            newComponentType: undefined, newComponentName: undefined
        });
    };

    private handleCancel() {
        this.setState({...this.state, modalVisible: false, saveModalVisible: false,
            newComponentType: undefined, newComponentName: undefined});
    };

    private handleChange(event: any) {
        this.setState({...this.state, newComponentType: event.target.value});
    }

    private handleChangeNewComponent(event: any) {
        this.setState({...this.state, newComponentName: event.target.value});
    }

    render(): React.ReactElement {
        return (
            <div>
                <div className="page-content" style={{width: '40rem'}}>
                    {this.mainSection()}
                    {this.conversationSchema()}
                    <div style={{marginTop: '1rem'}}>
                        {this.addElement()}
                        {this.saveModal()}
                        {this.save()}
                    </div>
                </div>
            </div>
        );
    }
}