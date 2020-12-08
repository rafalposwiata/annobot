import * as React from "react";

export class Configuration extends React.Component<any, any> {

    render(): React.ReactElement {
        return (
            <div>
                <div className="page-header">Configuration</div>
                <div className="page-content">
                    <div>Admin email</div>
                    <div>Intent detection service url</div>
                    <div>Model service url</div>
                    <div>Data sample distibution strategy</div>
                </div>
            </div>
        );
    }
}