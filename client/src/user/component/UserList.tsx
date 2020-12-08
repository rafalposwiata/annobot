import * as React from "react";

export class UserList extends React.Component<any, any> {

    render(): React.ReactElement {
        return (
            <div>
                <h1>Users list</h1>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>age</th>
                        <th>conversation_id</th>
                        <th>channel</th>
                    </tr>
                    <tr>
                        <td>Bob</td>
                        <td>23</td>
                        <td>bfasb348bfa</td>
                        <td>Annobot</td>
                    </tr>
                    <tr>
                        <td>Mery</td>
                        <td>32</td>
                        <td>dsanifsann34</td>
                        <td>Facebook</td>
                    </tr>
                </table>
            </div>
        );
    }
}