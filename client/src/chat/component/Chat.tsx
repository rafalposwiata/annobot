import * as React from "react";
import {Message} from "../model/chat_model";
import {chatService} from "../service/ChatService";
import {Icon} from "antd";
import {Label, UserInputType} from "../../bot/model/bot_model";

export interface ChatProps {
    botName: string;
    userId: string;
}

export interface ChatState {
    messages: Message[];
    conversationId?: string,
    userMessage?: string;
}

export class Chat extends React.Component<ChatProps, ChatState> {

    constructor(props: Readonly<ChatProps>) {
        super(props);
        this.state = {messages: []};

        this.handleChange = this.handleChange.bind(this);
        this.sendMessage = this.sendMessage.bind(this);
    }

    public componentDidMount(): void {
        chatService.initConversation(this.props.botName, this.props.userId, window.innerWidth < 1000).then(messages => {
            this.setState({...this.state, conversationId: messages[0].conversationId, messages: messages})
        });
    }

    public componentDidUpdate(): void {
        const objDiv = document.getElementsByClassName("conversation")[0];
        objDiv.scrollTop = objDiv.scrollHeight;
    }

    private handleChange(event: any) {
        this.setState({...this.state, userMessage: event.target.value});
    }

    private check(event: any) {
        const target = event.target as HTMLElement;
        let label: string = target.getAttribute("data-value") || '';

        const messages = this.state.messages;
        messages[messages.length-1].label = label;
        this.setState({...this.state, messages: messages});

        const message: Message = {
            text: label,
            conversationId: this.state.conversationId || '',
            userMessage: true
        };

        this.send(message);
    }

    private sendMessage(event: any) {
        event.preventDefault();

        const messages = this.state.messages;
        messages[messages.length-1].label = (this.state.userMessage || '').toLowerCase();
        this.setState({...this.state, messages: messages});

        const message: Message = {
            text: this.state.userMessage || '',
            conversationId: this.state.conversationId || '',
            userMessage: true
        };
        this.send(message);
    }

    private send(message: Message) {
        const messages = this.state.messages;
        messages.push(message);
        this.setState({...this.state, messages: messages, userMessage: ''});

        chatService.sendMessage(message).then(newMessages => {
            messages.push(...newMessages);
            this.setState({...this.state, messages: messages});
        })
    }

    private tagToString(label: Label): string {
        return label.abbr ? `${label.value} [${label.abbr}]` : label.value;
    }

    private conversation(): React.ReactElement {
        const messageElements = [];
        for (let message of this.state.messages) {
            if (message.userMessage) {
                messageElements.push(<li className="conversation-message me">{message.text}</li>)
            } else if (message.dataItem || message.botPrediction) {
                if (message.userInputType == UserInputType.LABEL) {
                    let labeling_section = <div
                        className={'label-' + message.label}>{message.label}</div>;
                    if (message.label == undefined) {
                        labeling_section = <div className="options">
                            {(message.labels || []).map(l =>
                                <button onClick={this.check.bind(this)} data-value={l.value}>{this.tagToString(l)}</button>
                            )}
                        </div>;
                    }

                    let element = <li className="conversation-message him-data-item">
                        <span className="quote">&ldquo;</span>
                        {message.text}
                        <span className="quote">&rdquo;</span>
                        {labeling_section}
                    </li>;
                    if (message.botPrediction) {
                        element = <li className="conversation-message him-data-item">
                            {message.text}
                            {labeling_section}
                        </li>;
                    }

                    messageElements.push(element);
                } else if (message.userInputType == UserInputType.IMAGE){

                } else {
                    const element = <li className="conversation-message him-data-item">
                        <span className="quote">&ldquo;</span>
                        {message.text}
                        <span className="quote">&rdquo;</span>
                    </li>;
                    messageElements.push(element);
                }
            } else {
                messageElements.push(<li className="conversation-message him">{message.text}</li>)
            }
        }

        return (<ul>
            {messageElements}
        </ul>);
    }

    render(): React.ReactElement {
        return (<div>
            <h1><Icon type="robot" style={{marginRight: '0.3rem'}}/>Annobot</h1>
            <div className="card">
                <div className="conversation">
                    {this.conversation()}
                </div>
                <div className="conversation-input">
                    <form onSubmit={this.sendMessage}>
                        <input type="text"
                               value={this.state.userMessage}
                               onChange={this.handleChange}
                               placeholder="Message"/>
                        <button type="submit">Send</button>
                    </form>
                </div>
            </div>
        </div>);
    }
}