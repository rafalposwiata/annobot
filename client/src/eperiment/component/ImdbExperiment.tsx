import * as React from "react";
import {Button, Input, Radio} from 'antd';
import {experimentService} from "../service/ExperimentService";

const {TextArea} = Input;

export interface ImdbExperimentProps {
    userId: string;
}

export interface ImdbExperimentState {
    stepNumber: number;
    user: string;
    testVersion: number;
}

export class ImdbExperiment extends React.Component<ImdbExperimentProps, ImdbExperimentState> {

    private stepsVersions;

    constructor(props: Readonly<ImdbExperimentProps>) {
        super(props);
        this.state = {stepNumber: -1, user: props.userId, testVersion: Number(props.userId[1])};

        this.nextStep = this.nextStep.bind(this);
        this.stepsVersions = {
            0: [
                this.doccano_desktop(),
                this.label_studio_desktop(),
                this.annobot_desktop(),
                this.change_to_mobile(),
                this.doccano_mobile(),
                this.label_studio_mobile(),
                this.annobot_mobile(),
                this.finish()
            ], 1: [
                this.doccano_desktop(),
                this.annobot_desktop(),
                this.label_studio_desktop(),
                this.change_to_mobile(),
                this.doccano_mobile(),
                this.annobot_mobile(),
                this.label_studio_mobile(),
                this.finish()
            ], 2: [
                this.annobot_desktop(),
                this.doccano_desktop(),
                this.label_studio_desktop(),
                this.change_to_mobile(),
                this.annobot_mobile(),
                this.doccano_mobile(),
                this.label_studio_mobile(),
                this.finish()
            ], 3: [
                this.label_studio_desktop(),
                this.annobot_desktop(),
                this.doccano_desktop(),
                this.change_to_mobile(),
                this.label_studio_mobile(),
                this.annobot_mobile(),
                this.doccano_mobile(),
                this.finish()
            ], 4: [
                this.annobot_desktop(),
                this.label_studio_desktop(),
                this.doccano_desktop(),
                this.change_to_mobile(),
                this.annobot_mobile(),
                this.label_studio_mobile(),
                this.doccano_mobile(),
                this.finish()
            ], 5: [
                this.label_studio_desktop(),
                this.doccano_desktop(),
                this.annobot_desktop(),
                this.change_to_mobile(),
                this.label_studio_mobile(),
                this.doccano_mobile(),
                this.annobot_mobile(),
                this.finish()
            ],
        };
    }

    private instruction_step(): React.ReactElement {
        return (<span>
            W trakcie badania twoim celem będzie określenie czy recenzja filmu pobrana z portalu IMDB jest pozytywna
            czy negatywna. <strong>Nie musisz dokładnie czytać tych recenzji, chodzi o pierwsze wrażenie. </strong>
            W drugiej części badania będziesz
            potrzebował/a telefonu z podłączeniem do internetu. Całe badanie
            powinno zająć około 15 minut. <strong>Ważne żeby wykonać je bez przerywania. </strong>Jeśli jesteś gotowy/a przejdź dalej. Powodzenia!
        </span>)
    }

    private doccano_desktop(): React.ReactElement {
        return (<ol>
            <li>Wejdź na stronę <a href="https://opi-doccano.herokuapp.com/"
                                   target="_blank">https://opi-doccano.herokuapp.com/</a>.
            </li>
            <li>Zaloguj się do systemu podając następujące dane:
                <div>Username: <strong>{this.state.user}</strong></div>
                <div>Password: <strong>#Test1234</strong></div>
            </li>
            <li>Wybierz projekt <strong>imdb</strong></li>
            <li>Określ wydźwięk każdej z recenzji.</li>
            <li>Po wykonaniu zadania przejdź dalej.</li>
        </ol>);
    }

    private doccano_mobile(): React.ReactElement {
        return (<ol>
            <li>Używając <strong>telefonu</strong> wejdź na stronę <strong>https://opi-doccano.herokuapp.com/</strong>.
            </li>
            <li>Zaloguj się do systemu podając następujące dane:
                <div>Username: <strong>{this.state.user}</strong></div>
                <div>Password: <strong>#Test1234</strong></div>
            </li>
            <li>Wybierz projekt <strong>imdb MOBILE</strong></li>
            <li>Określ wydźwięk każdej z recenzji.</li>
            <li>Po wykonaniu zadania przejdź dalej.</li>
        </ol>);
    }

    private label_studio_desktop(): React.ReactElement {
        const url = `http://opi-${this.state.user}.herokuapp.com/`;
        return (<ol>
            <li>Wejdź na stronę <a href={url} target="_blank">{url}</a>.
            </li>
            <li>Określ wydźwięk każdej z recenzji.</li>
            <li>Po wykonaniu zadania przejdź dalej.</li>
        </ol>);
    }

    private label_studio_mobile(): React.ReactElement {
        const url = `http://mob-${this.state.user}.herokuapp.com/`;
        return (<ol>
            <li>Używając <strong>telefonu</strong> wejdź na stronę <strong>{url}</strong>.
            </li>
            <li>Określ wydźwięk każdej z recenzji.</li>
            <li>Po wykonaniu zadania przejdź dalej.</li>
        </ol>);
    }

    private annobot_desktop(): React.ReactElement {
        const url = `https://annobot.herokuapp.com/#/${this.state.user}`;
        return (<ol>
            <li>Wejdź na stronę <a href={url} target="_blank">{url}</a>.
            </li>
            <li>Postępuj zgodnie z wytycznymi.</li>
            <li>Po zakończeniu rozmowy przejdź dalej.</li>
        </ol>);
    }

    private annobot_mobile(): React.ReactElement {
        const url = `https://annobot.herokuapp.com/#/${this.state.user}`;
        return (<ol>
            <li>Używając <strong>telefonu</strong> wejdź na stronę <strong>{url}</strong>.
            </li>
            <li>Postępuj zgodnie z wytycznymi.</li>
            <li>Po zakończeniu rozmowy przejdź dalej.</li>
        </ol>);
    }

    private survey_1(): React.ReactElement {
        return (
            <div>
                <form>
                    <div>
                        <div>Praca, z którym z systemów była dla Ciebie najłatwiejsza? *</div>
                        <Radio.Group>
                            <Radio value={1}>Doccano</Radio>
                            <Radio value={2}>Label Studio</Radio>
                            <Radio value={3}>Annobot</Radio>
                        </Radio.Group>
                        <div style={{marginBottom: '1rem'}}>Dlaczego? *</div>
                        <TextArea rows={4} required={true}/>
                    </div>
                </form>
            </div>);
    }

    private survey_2(): React.ReactElement {
        return (
            <div>
                <form>
                    <div>
                        <div>Praca, z którym z systemów w <strong>wersji mobilnej</strong> była dla Ciebie
                            najłatwiejsza? *
                        </div>
                        <Radio.Group>
                            <Radio value={1}>Doccano</Radio>
                            <Radio value={2}>Label Studio</Radio>
                            <Radio value={3}>Annobot</Radio>
                        </Radio.Group>
                        <div style={{marginBottom: '1rem'}}>Dlaczego? *</div>
                        <TextArea rows={4} required={true}/>
                    </div>
                </form>
            </div>);
    }

    private change_to_mobile(): React.ReactElement {
        return (
            <h3>Przygotuj telefon z dostępem do internetu. Od teraz zadania będziesz wykonywał/a na telefonie!</h3>);
    }

    private finish(): React.ReactElement {
        return (<h2 style={{textAlign: 'center', marginBottom: '4rem'}}>Dziękuję!</h2>);
    }

    private step(): React.ReactElement {
        if (this.state.stepNumber == -1) {
            return this.instruction_step();
        }
        const listOfSteps = this.stepsVersions[this.state.testVersion];
        const idx = Math.min(listOfSteps.length - 1, this.state.stepNumber);

        return listOfSteps[idx];
    }

    private nextStep(): void {
        experimentService.sendInfo({
            userName: this.state.user, systemName: 'annobot',
            description: "step " + (this.state.stepNumber + 2)
        });
        this.setState(this.setState({...this.state, stepNumber: (this.state.stepNumber + 1)}));
    }

    private button(): React.ReactElement {
        if (this.state.stepNumber >= 7) {
            return <span></span>;
        }
        return (<div>
            <div className="page-number">{this.state.stepNumber + 2}</div>
            <Button className="next-step-button" size="large" type="primary" onClick={this.nextStep}>Dalej</Button>
        </div>);
    }

    render(): React.ReactElement {
        return (
            <div className="experiment">
                <div className="card">
                    <h1>Badanie użyteczności systemów</h1>
                    <div className="step-content">
                        {this.step()}
                    </div>
                    {this.button()}
                </div>
            </div>
        );
    }
}