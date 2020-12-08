import * as React from "react";
import {Icon, Layout, Menu} from 'antd';
import {Route} from "react-router-dom";

const { Header, Sider, Content } = Layout;


export class Sidebar extends React.Component<any, any> {

    state = {
        collapsed: false,
    };

    toggle = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    };

    render(): React.ReactElement {
        return (
            <Sider trigger={null} collapsible collapsed={this.state.collapsed}>
                <div className="logo"><Icon type="robot" style={{marginRight: '0.3rem'}}/>Annobot</div>
                <Menu theme="dark" mode="inline" className="side-menu">
                    <Menu.Item key="configuration">
                        <Route render={({history}) => <span className="nav-text" onClick={() => history.push("/dashboard/configuration")}><Icon type="profile" />Configuration</span>} />
                    </Menu.Item>
                    <Menu.Item key="datasets">
                        <Route render={({history}) => <span className="nav-text" onClick={() => history.push("/dashboard/dataset/list")}><Icon type="database" />Datasets</span>} />
                    </Menu.Item>
                    <Menu.Item key="bots">
                        <Route render={({history}) => <span className="nav-text" onClick={() => history.push("/dashboard/bots")}><Icon type="robot" />Bots</span>} />
                    </Menu.Item>
                    <Menu.Item key="conversations">
                        <span className="nav-text"><Icon type="message"/>Conversations</span>
                    </Menu.Item>
                </Menu>
            </Sider>);
    }
}