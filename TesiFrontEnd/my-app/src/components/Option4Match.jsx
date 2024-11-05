import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Form, Row, Col} from 'react-bootstrap';
import Formation433 from './Formation433';

function Option4Match(props){
    const setMatchOptions = props.setMatchOptions
    const matchOptions = props.matchOptions

    const handleCheckboxChange = (event) => {
        const { name, checked } = event.target;
        if(name=="playGood")
            setMatchOptions((prevValues) => ({
                ...prevValues,
                [name]: checked,
                ["playBad"]: !matchOptions.playBad
            }));
        else if(name=="playBad")
            setMatchOptions((prevValues) => ({
                ...prevValues,
                [name]: checked,
                ["playGood"]: !matchOptions.playGood
            }));
        else if(name=="badTactic")
            setMatchOptions((prevValues) => ({
                ...prevValues,
                [name]: checked,
                ["goodTactic"]: !matchOptions.goodTactic
            }));
        else if(name=="goodTactic")
            setMatchOptions((prevValues) => ({
                ...prevValues,
                [name]: checked,
                ["badTactic"]: !matchOptions.badTactic
            }));
    
        else
            setMatchOptions((prevValues) => ({
                ...prevValues,
                [name]: checked,
            }));
    };

    const handleSubmit = (event) => {
        event.preventDefault(); 
    };

    return (
        <div className="container">
            <h3 className="text-center">Seleziona le Opzioni</h3>
            <Form onSubmit={handleSubmit}>
                <Form.Group>
                    <Form.Check
                        type="checkbox"
                        label="Cambia Guarnieri (Con questa opzione toglierai Guarnieri dalla partita nel secondo tempo)"
                        name="changeGuarnieri"
                        checked={matchOptions.changeGuarnieri}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Cambia Romero (Con questa opzione toglierai Romero dalla partita nel secondo tempo)"
                        name="changeRomero"
                        checked={matchOptions.changeRomero}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Cambia Natan (Con questa opzione farai entrare Natan dalla partita nel secondo tempo)"
                        name="changeNatan"
                        checked={matchOptions.changeNatan}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Gioca male"
                        name="playBad"
                        checked={matchOptions.playBad}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Gioca bene"
                        name="playGood"
                        checked={matchOptions.playGood}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Usa una pessima tattica"
                        name="badTactic"
                        checked={matchOptions.badTactic}
                        onChange={handleCheckboxChange}
                    />
                    <Form.Check
                        type="checkbox"
                        label="Usa una buona tattica"
                        name="goodTactic"
                        checked={matchOptions.goodTactic}
                        onChange={handleCheckboxChange}
                    />
                </Form.Group>
            </Form>
            <Formation433/>
        </div>
    )
}

export default Option4Match;