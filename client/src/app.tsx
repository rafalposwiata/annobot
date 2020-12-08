import * as React from "react";
import {HashRouter, Redirect, Route, Switch} from 'react-router-dom'
import {Chat} from "./chat/component/Chat"
import {Dashboard} from "./dashboard/component/Dashboard";
import {ImdbExperiment} from "./eperiment/component/ImdbExperiment";

export class Application extends React.Component {

    render(): React.ReactElement {
        return (
            <HashRouter>
                <Switch>
                    <Route path="/experiment" exact={true} render={() => <h1>Niepoprawny adres</h1>}/>
                    <Route path="/experiment/:userId" render={(props) => <ImdbExperiment {...props.match.params}/>}/>
                    <Route path="/chat/:botName" exact={true} render={(props) => <Chat {...props.match.params} userId={'guest'}/>}/>
                    <Route path="/chat/:botName/:userId" render={(props) => <Chat {...props.match.params}/>}/>
                    <Route path="/dashboard" render={() => <Dashboard/>}/>
                    <Route path="/" exact={true} render={(props) => <Chat {...props.match.params} botName={'Bob'} userId={'guest'}/>}/>
                    {/*<Redirect exact={true} from="/" to="/chat/bob"/>*/}
                </Switch>
            </HashRouter>
        );
    }
}
