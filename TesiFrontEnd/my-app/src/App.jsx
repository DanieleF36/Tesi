import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';


import { React, useEffect, useState } from 'react';
import { Container, Row, Col, Button, Form } from 'react-bootstrap/'

import {Navigation} from './components/Navigation';
import Filters from './components/Filters';
import Chat from './components/Chat';
import Context from './components/Context'
import Option4Match from './components/Option4Match';
import MatchStats from './components/MatchStats';
import API from './API'
function App() {

  const [activeNPC, setActiveNPC] = useState({previus: undefined, present: undefined})
  //Context, Chat, Option4Match, 1stHalf, 2ndHalf
  const [pageToVisualizze, setPageToVisualizze] = useState("Context")
  const [messages, setMessages] = useState([]);
  const [npcs, setNpcs] = useState([])
  const [matchResult, setMatchResult] = useState(undefined)
  const [matchOptions, setMatchOptions] = useState(({
    changeGuarnieri: false,
    changeRomero: false,
    changeNatan: false,
    playBad: true,
    playGood: false,
    goodTactic: false,
    badTactic: true,
  }))
  //Se il giocatore è andato a parlare con il presidente
  let convocationAccepted = false

  const changeNPC = (newNpc)=>{setActiveNPC({previus: activeNPC.present, present: newNpc})}

  useEffect(()=>{
    API.NPCNames().then(res=>{
      setNpcs(...npcs, res)
    })
  }, [])
   
  useEffect(()=>{ 
    if(pageToVisualizze=="1stHalf" && !convocationAccepted)
      API.generateEvent({name: "allenatoreNonRispettaPresidente", 
                         description:"Il presidente aveva convocato l'allenatore ma lui non si è presentato. Adesso tutti i dirigenti sono arrabiati",
                         groupName:"dirigenti", 
                         idNPC: npcs.filter((npc)=> { return npc.name=="Elisa Depaoli(presidente)"})[0].id})
      .then()
    if(pageToVisualizze=="1stHalf")
      API.MatchOptions(matchOptions).then(res=>{
        setMatchResult(res)
      })
  }, [pageToVisualizze])

  useEffect(()=>{
    if(activeNPC.present != undefined && messages.length!=0 && messages.at(-1).sender == "You"){
      API.sendMessage(activeNPC.present.id, messages.at(-1)).then(res=>{
        setMessages([...messages, {sender: activeNPC.present.name, text: res}])
      })
    }
  }, [messages])

  useEffect(()=>{
    if(activeNPC.present!=undefined && activeNPC.present.name=="Elisa Depaoli")
      convocationAccepted = true
    if(activeNPC.previus!=undefined && messages.length!=0){
      API.endConversation(activeNPC.previus.id).catch(console.log("end converastion failed")).then(setMessages([]))
      
    }
  }, [activeNPC])

  return (
    <Container fluid className='App'>

      <Navigation setPage={setPageToVisualizze} actualPage={pageToVisualizze}/>

      <Row className="vh-100">
        <Col md={4} xl={3} bg="light" className="below-nav" id="left-sidebar">
          <Filters setActiveNPC={changeNPC} actualPage={pageToVisualizze} setPage={setPageToVisualizze} npcs={npcs}/>
        </Col>

        <Col md={8} xl={9} className="below-nav">
        {  
          pageToVisualizze=="Chat"?
            <>
              <h1 className="pb-3">Chat: <span className="notbold">{activeNPC.present?.name}</span></h1>
              <Row className="flex-grow-1">
                <Chat botName={activeNPC.present?.name} messages={messages} setMessages={setMessages} />
              </Row>
            </>:
          pageToVisualizze=="Context"?
            <>
              <Context/>
            </>:
          pageToVisualizze=="Option4Match"?
            <>
              <Option4Match setMatchOptions={setMatchOptions} matchOptions={matchOptions}/>
            </>:
          pageToVisualizze=="1stHalf"?
            <>
              <MatchStats half={"firts"}/>
            </>:
            <>
              <MatchStats half={"second"} result={matchResult}/>
            </>
        }     
        </Col>
      </Row>
    </Container>
  );
}

export default App;