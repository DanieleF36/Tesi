import {ListGroup} from 'react-bootstrap/';
import { useState } from 'react';
//import NAMES from '../names'

const Filters = (props) => {
  const [selected, setSelected] = useState('filter-calciatori');

  const search = (role) => {
    return props.npcs.filter((person)=>person.role == role)
  }

  const filters = {
    'filter-calciatori':{ label: 'Calciatori', id: 'filter-calciatori', filterFunction: "Giocatori"},
    'filter-dirigenti': { label: 'Dirigenti', id: 'filter-dirigenti', filterFunction: "Dirigenti"},
    'filter-st':        { label: 'Staff Tecnico', id: 'filter-st', filterFunction: "Staff Tecnico"},
    'filter-ss':        { label: 'Staff di Supporto', id: 'filter-ss', filterFunction: "Staff di Supporto"},
  };
  const filterArray = Object.entries(filters);
  return (
    <ListGroup as="ul" variant="flush" >
        {
          filterArray.map(([name, { label }]) => {
            return (
                <>
                  <ListGroup.Item as="li" key={name} onClick={() => setSelected(name)}
                  action active={selected === name ? true : false} >
                      {label}
                  </ListGroup.Item>
                  {selected == name ?
                  <>
                    <ListGroup >
                      {search(filterArray.find((f)=>f[0]==selected)[1].filterFunction).map((el)=>{
                        return(
                          <ListGroup.Item key={el.id} onClick={() => {props.setActiveNPC(el); props.setPage("Chat")}} action disabled={props.actualPage=="1stHalf" || props.actualPage=="optiOption4Matchons4Match"}>
                              {el.name}
                          </ListGroup.Item>
                      )})}
                      </ListGroup>
                    </>:
                    <></>
                  }
                </>
                
            );
          })
        }
    </ListGroup>
  )
}

export default Filters;
