class APIService {

    public url: string;

    constructor() {
        const host = window.location.hostname;
        if (host.indexOf("localhost") >= 0) {
            this.url = "http://localhost:8080/";
        } else {
            this.url = window.location.protocol + "//" + host + "/";
        }
    }

    public get(path: string, options?: any): Promise<any> {
        options = Object.assign({}, {headers: {'Content-Type': 'application/json'}}, options);
        return this.request(path, options);
    }

    public post(path: string, payload?: any, options?: any): Promise<any> {
        return this.methodWithBody('POST', path, payload, options);
    }

    public delete(path: string, payload?: any, options?: any): Promise<any> {
        return this.methodWithBody('DELETE', path, payload, options);
    }

    public methodWithBody(method: string, path: string, payload?: any, options?: any): Promise<any> {
        options = Object.assign({}, {
            method: method,
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}}, options);
        return this.request(path, options);
    }

    public postFile(path: string, payload: FormData, options?: any): Promise<any> {
        options = Object.assign({}, {method: "POST", body: payload}, options);
        return this.request(path, options);
    }

    private request(path: string, options?: any): Promise<any> {
        return fetch(`${this.url}${path}`, options).then(response => {
            if (!response.ok) {
                return Promise.reject(response)
            }
            return response.json()
        })
    }
}

export const api: APIService = new APIService();