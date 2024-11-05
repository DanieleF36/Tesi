import React from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';

const players = {
    GK: 'Brennan',
    DF: ['Sperti', 'Saenz', 'Diaz', 'Kasereka'],
    MF: ['Robert', 'Rosas', 'Romero'],
    FW: ['Marinaro', 'Jeremias', 'Guarnieri'],
};

const Formation433 = () => {
    return (
        <Container fluid className="text-center mt-4">
            <h3>Formazione 4-3-3</h3>
            <Row className="justify-content-center mt-4">
                <Col xs={3}>
                    <Card className="bg-primary text-white">
                        <Card.Body>{players.GK}</Card.Body>
                    </Card>
                </Col>
            </Row>
            <Row className="justify-content-center mt-4">
                {players.DF.map((defender, index) => (
                    <Col xs={3} md={2} key={index}>
                        <Card className="bg-success text-white">
                            <Card.Body>{defender}</Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            <Row className="justify-content-center mt-4">
                {players.MF.map((midfielder, index) => (
                    <Col xs={4} md={3} key={index}>
                        <Card className="bg-warning text-dark">
                            <Card.Body>{midfielder}</Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            <Row className="justify-content-center mt-4">
                {players.FW.map((forward, index) => (
                    <Col xs={4} md={3} key={index}>
                        <Card className="bg-danger text-white">
                            <Card.Body>{forward}</Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default Formation433;
