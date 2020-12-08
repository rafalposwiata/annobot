import * as React from "react";
import {Layout} from 'antd';
import {Route, Switch} from "react-router-dom";
import {DatasetList} from "../../dataset/component/DatasetList";
import {Sidebar} from "../../layout/component/Sidebar";
import {Configuration} from "../../configuration/component/Configuration";
import {DatasetAdd} from "../../dataset/component/DatasetAdd";
import {UserList} from "../../user/component/UserList";
import {Bot} from "../../bot/component/Bot";
import {Bots} from "../../bot/component/Bots";
import {DatasetDetail} from "../../dataset/component/DatasetDetail";

const {Header, Content, Footer, Sider} = Layout;


export class Dashboard extends React.Component<any, any> {


    render(): React.ReactElement {
        return (<Layout>
            <Sidebar/>
            <Layout>
                <Content style={{margin: '30px 30px 0'}}>
                    <div style={{background: '#fff', minHeight: 400}}>
                        <Switch>
                            {/*TODO refactor*/}
                            <Route path="/dashboard/bots/" exact={true} render={() => <Bots />}/>
                            <Route path="/dashboard/bots-new" render={(props) =>
                                <Bot botName="" readonly={false} editMode={false} {...props.match.params}/>}/>
                            <Route path="/dashboard/bots/:botName" render={(props) =>
                                <Bot editMode={true} readonly={false} {...props.match.params}/>}/>
                            <Route path="/dashboard/configuration" render={() => <Configuration/>}/>
                            <Route path="/dashboard/dataset/list" exact={true} render={() => <DatasetList/>}/>
                            <Route path="/dashboard/dataset/list/:datasetId"
                                   render={(props) => <DatasetDetail {...props.match.params}/>}/>
                            <Route path="/dashboard/dataset/add" render={(props) => <DatasetAdd {...props}/>}/>
                            <Route path="/dashboard/users" render={() => <UserList/>}/>
                        </Switch>
                    </div>
                </Content>
                {/*<Footer style={{textAlign: 'center'}}>Copyrights by Annobot</Footer>*/}
            </Layout>
        </Layout>);
    }
}