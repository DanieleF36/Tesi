import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Card, Row, Col } from 'react-bootstrap';

const MatchStats = (props) => {
    
    let stats = {
        score: '0 - 0',
        possession: {
            teamA: '45%',
            teamB: '55%',
        },
        shots: {
            teamA: 0,
            teamB: 0,
        },
        corners: {
            teamA: 1,
            teamB: 3,
        },
        yellowCards: {
            teamA: 1,
            teamB: 2,
        },
        redCards: {
            teamA: 0,
            teamB: 0,
        },
        scorers: {
            teamA: [],
            teamB: [],
        },
    };
    
    if(props.half=="second"){
        if(props.result.teamB == 3)
            stats = {
                score: '0 - 3',
                possession: {
                    teamA: '35%',
                    teamB: '65%',
                },
                shots: {
                    teamA: 0,
                    teamB: 7,
                },
                corners: {
                    teamA: 2,
                    teamB: 6,
                },
                yellowCards: {
                    teamA: 3,
                    teamB: 2,
                },
                redCards: {
                    teamA: 1,
                    teamB: 0,
                },
                scorers: {
                    teamA: [],
                    teamB: ["Le blanc(67)", "Toribo(75)", "Depaoli(89)"],
                },
            }
        else if(props.result.teamB == 1)
            stats = {
                score: '0 - 1',
                possession: {
                    teamA: '45%',
                    teamB: '55%',
                },
                shots: {
                    teamA: 0,
                    teamB: 4,
                },
                corners: {
                    teamA: 2,
                    teamB: 6,
                },
                yellowCards: {
                    teamA: 3,
                    teamB: 2,
                },
                redCards: {
                    teamA: 0,
                    teamB: 0,
                },
                scorers: {
                    teamA: [],
                    teamB: ["Le blanc(89)"],
                },
            };  
        else    if(props.result.teamA == 0)
                stats = {
                    score: '0 - 0',
                    possession: {
                        teamA: '47%',
                        teamB: '53%',
                    },
                    shots: {
                        teamA: 2,
                        teamB: 4,
                    },
                    corners: {
                        teamA: 4,
                        teamB: 6,
                    },
                    yellowCards: {
                        teamA: 2,
                        teamB: 2,
                    },
                    redCards: {
                        teamA: 0,
                        teamB: 0,
                    },
                    scorers: {
                        teamA: [],
                        teamB: [],
                    },
                };    
                else    
                    stats = {
                        score: '1 - 0',
                        possession: {
                            teamA: '52%',
                            teamB: '48%',
                        },
                        shots: {
                            teamA: 2,
                            teamB: 4,
                        },
                        corners: {
                            teamA: 4,
                            teamB: 6,
                        },
                        yellowCards: {
                            teamA: 2,
                            teamB: 2,
                        },
                        redCards: {
                            teamA: 0,
                            teamB: 0,
                        },
                        scorers: {
                            teamA: ["Jeremias(80)"],
                            teamB: [],
                        },
                    };    
    }

    return (
        <div className="container mt-4">
            <h3 className="text-center">Statistiche della Partita</h3>
            <Card className="mb-3">
                <Card.Body>
                    <Card.Title className="text-center">Risultato</Card.Title>
                    <Card.Text className="text-center" style={{ fontSize: '2rem' }}>
                        {stats.score}
                    </Card.Text>
                </Card.Body>
            </Card>

            <Row>
                <Col>
                    <Card>
                        <Card.Header>Possesso Palla</Card.Header>
                        <Card.Body>
                            <p><strong>Squadra A:</strong> {stats.possession.teamA}</p>
                            <p><strong>Squadra B:</strong> {stats.possession.teamB}</p>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card>
                        <Card.Header>Tiri in Porta</Card.Header>
                        <Card.Body>
                            <p><strong>Squadra A:</strong> {stats.shots.teamA}</p>
                            <p><strong>Squadra B:</strong> {stats.shots.teamB}</p>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="mt-3">
                <Col>
                    <Card>
                        <Card.Header>Calci d'Angolo</Card.Header>
                        <Card.Body>
                            <p><strong>Squadra A:</strong> {stats.corners.teamA}</p>
                            <p><strong>Squadra B:</strong> {stats.corners.teamB}</p>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card>
                        <Card.Header>Ammonizioni</Card.Header>
                        <Card.Body>
                            <p><strong>Squadra A:</strong> {stats.yellowCards.teamA}</p>
                            <p><strong>Squadra B:</strong> {stats.yellowCards.teamB}</p>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="mt-3">
                <Col>
                    <Card>
                        <Card.Header>Espulsioni</Card.Header>
                        <Card.Body>
                            <p><strong>Squadra A:</strong> {stats.redCards.teamA}</p>
                            <p><strong>Squadra B:</strong> {stats.redCards.teamB}</p>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Sezione per i Marcatori */}
            <Row className="mt-4">
                <Col>
                    <Card>
                        <Card.Header>Marcatori</Card.Header>
                        <Card.Body>
                            <Card.Title>Squadra A</Card.Title>
                            {stats.scorers.teamA.length > 0 ? (
                                stats.scorers.teamA.map((scorer, index) => (
                                    <p key={index}>
                                        {scorer.player} - {scorer.time}
                                    </p>
                                ))
                            ) : (
                                <p>Nessun marcatore</p>
                            )}

                            <Card.Title className="mt-3">Squadra B</Card.Title>
                            {stats.scorers.teamB.length > 0 ? (
                                stats.scorers.teamB.map((scorer, index) => (
                                    <p key={index}>
                                        {scorer.player} - {scorer.time}
                                    </p>
                                ))
                            ) : (
                                <p>Nessun marcatore</p>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default MatchStats;
