import * as React from "react";
import {Icon, Input, Tag, Tooltip} from 'antd';
import {Label} from "../../bot/model/bot_model";

export interface EditableTagGroupProps {
    tags: Label[];
    updateFunc: any;
    binary: boolean;
    disable: boolean;
}

export interface EditableTagGroupState {
    tags: Label[],
    inputVisible: boolean,
    inputValue: string
}

export class EditableTagGroup extends React.Component<EditableTagGroupProps, EditableTagGroupState> {

    private input: any;

    constructor(props: Readonly<EditableTagGroupProps>) {
        super(props);
        this.state = {
            tags: props.tags,
            inputVisible: false,
            inputValue: '',
        };

        this.showInput = this.showInput.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleInputConfirm = this.handleInputConfirm.bind(this);
    }

    componentWillUpdate(prevProps: EditableTagGroupProps, prevState: EditableTagGroupState) {
        if (this.props.tags !== prevState.tags) {
            this.props.updateFunc(prevState.tags);
        }
    }

    private showInput() {
        this.setState({inputVisible: true}, () => this.input.focus());
    };

    private handleClose(removedTag: any) {
        const tags = this.state.tags.filter(tag => tag !== removedTag);
        this.setState({...this.state, tags: tags});
    };

    private handleInputChange(e: any) {
        this.setState({inputValue: e.target.value});
    };

    private handleInputConfirm() {
        const {inputValue} = this.state;
        let {tags} = this.state;
        let label = this.stringToTag(inputValue);
        if (inputValue && tags.indexOf(label) === -1) {
            tags = [...tags, label];
        }
        this.setState({
            tags,
            inputVisible: false,
            inputValue: '',
        });
    };

    private stringToTag(value: string): Label {
        const openBracketidx = value.indexOf('[');
        const closeBracketidx = value.indexOf(']');
        if (openBracketidx > 0 && closeBracketidx > 0) {
            return {
                value: value.slice(0, openBracketidx - 1),
                abbr: value.slice(openBracketidx + 1, closeBracketidx)
            }
        }
        return {value: value};
    }

    private canAddNewTag() {
        return !this.props.disable && (!this.props.binary || this.state.tags.length < 2);
    }

    private tagToString(label: Label): string {
        return label.abbr ? `${label.value} [${label.abbr}]` : label.value;
    }

    render() {
        const {tags, inputVisible, inputValue} = this.state;
        return (
            <div>
                {tags.map((tag, index) => {
                    const isLongTag = tag.value.length > 20;
                    const tagElem = (
                        <Tag key={this.tagToString(tag)} color="blue" closable={!this.props.disable}  onClose={() => this.handleClose(tag)}>
                            {isLongTag ? `${this.tagToString(tag).slice(0, 20)}...` : this.tagToString(tag)}
                        </Tag>
                    );
                    return isLongTag ? (
                        <Tooltip title={this.tagToString(tag)} key={this.tagToString(tag)}>
                            {tagElem}
                        </Tooltip>
                    ) : (
                        tagElem
                    );
                })}
                {inputVisible && (
                    <Input
                        ref={ref => this.input = ref}
                        type="text"
                        size="small"
                        style={{width: 78}}
                        value={inputValue}
                        onChange={this.handleInputChange}
                        onBlur={this.handleInputConfirm}
                        onPressEnter={this.handleInputConfirm}
                    />
                )}
                {!inputVisible && this.canAddNewTag() && (
                    <Tag onClick={this.showInput} style={{background: '#fff', borderStyle: 'dashed'}}>
                        <Icon type="plus"/> New label
                    </Tag>
                )}
            </div>
        );
    }
}