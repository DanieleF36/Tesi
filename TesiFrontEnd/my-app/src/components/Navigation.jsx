import 'bootstrap-icons/font/bootstrap-icons.css';

import {Navbar, Form, Button, OverlayTrigger, Tooltip } from 'react-bootstrap';

const renderTooltip = (props) => (
    <Tooltip id="button-tooltip" {...props}>
        {
            props.actualPage=="Context"?
                <p>Cliccando si passerà alla scelta delle opzioni per la partita contro l'Inter</p>:
                props.actualPage=="Option4Match"?
                    <p>Cliccando si passerà al risultato del primo tempo</p>:
                    props.actualPage=="1stHalf"?
                        <p>Cliccando si passerà al risultato finale</p>:
                        <p>Cliccando si passerà alla fine della partita</p>
        }
        
    </Tooltip>
);

const Navigation = (props) => {
    
    const changePage=()=>{
        switch(props.actualPage){
            case "Context": props.setPage("Option4Match"); break; 
            case "Chat": props.setPage("Option4Match"); break;
            case "Option4Match": props.setPage("1stHalf"); break;
            case "1stHalf": props.setPage("2ndHalf"); break;
        }
    }

    return (
        <Navbar bg="primary" expand="sm" variant="dark" fixed="top" className="navbar-padding">
            <Navbar.Brand href="index.html">
            <i className="bi bi-football">Team Managment</i>
            </Navbar.Brand>
            <Form className="my-2 my-lg-0 mx-auto d-sm-block" action="#" role="search" aria-label="Quick search">
                <Form.Control className="mr-sm-2" type="search" placeholder="TODO" aria-label="Search query" />
            </Form>
            <OverlayTrigger
                placement="bottom" 
                delay={{ show: 250, hide: 400 }}
                overlay={renderTooltip({actualPage:props.actualPage})}>
                <Button variant="secondary" className="ml-md-auto" onClick={()=>{changePage()}}>
                    AVANTI
                </Button>
            </OverlayTrigger>
        </Navbar>
    );
}

export {Navigation};
