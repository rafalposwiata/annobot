import * as React from "react";
import {Avatar, Card, Icon, Tooltip} from "antd";
import {Link} from "react-router-dom";
import {botService} from '../service/BotService'

const {Meta} = Card;

export interface BotsState {
    bots: string[]
}

export class Bots extends React.Component<any, BotsState> {

    constructor(props: Readonly<any>) {
        super(props);
        this.state = {bots: []};
    }

    public componentDidMount(): void {
        botService.getChatbots().then(bots => {
            this.setState({...this.state, bots: bots});
        });
    }

    private bots(): React.ReactElement {
        let groups = [];
        let elements = [];
        let count = 1;
        const botsNumber = this.state.bots.length;
        for (let i = 0; i <= botsNumber; i++) {
            if (i == botsNumber) {
                elements.push(this.addCard());
            } else {
                elements.push(this.botCard(this.state.bots[i]));
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
            {groups}
        </div>;
    }

    private delete(botName: string) {
        botService.deleteConfig(botName).then(response => {
            const bots = this.state.bots.filter(bot => bot !== botName);
            this.setState({...this.state, bots: bots});
        });
    }

    private botCard(botName: string): React.ReactElement {
        return (<Card style={{width: 300, marginBottom: '2rem', marginRight: '2rem', display: 'inline-block'}}
                      actions={[
                          <Tooltip title="edit conversation schema">
                            <Link to={"/dashboard/bots/" + botName}><Icon type="edit" key="edit" /></Link>
                          </Tooltip>,
                          <Tooltip title={"chat with " + botName}>
                            <Link to={"/chat/" + botName} target="_blank"><Icon type="message" key="message" /></Link>
                          </Tooltip>,
                          <Tooltip title="delete">
                            <div onClick={() => this.delete(botName)}><Icon type="delete" key="delete" /></div>
                          </Tooltip>
                      ]}>
            <Meta
                avatar={
                    <Avatar style={{backgroundColor: '#2e5077'}} icon="user"/>
                }
                title={<Link to={"/dashboard/bots/" + botName}>{botName}</Link>}
                description=''
            />
        </Card>);
    }

    private addCard(): React.ReactElement {
        return (<Link to={"/dashboard/bots-new"}><Card style={{width: 300, marginBottom: '2rem', marginRight: '2rem', display: 'inline-block',
            paddingBottom: '3rem', paddingTop: '0.25rem', borderStyle: 'dashed'}}>
            <span style={{display: 'block', textAlign: 'center', position: 'relative', top: '1rem', color: 'black'}} >
                <Icon type="plus" key="plus" style={{marginRight: '0.2rem'}}/>add new</span>
        </Card>
        </Link>);
    }

    render(): React.ReactElement {
        return <div>
            <div className="page-header">Bots</div>
            <div className="page-content">
                {this.bots()}
            </div>
        </div>
    }
}