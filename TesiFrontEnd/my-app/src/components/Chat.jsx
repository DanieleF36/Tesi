import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, ListGroup } from 'react-bootstrap';

function Chat(props) {
  const {botName, messages, setMessages} = props
  const [inputText, setInputText] = useState('');
  
  const handleSendMessage = () => {
    if(messages.length == 0 || messages.at(-1).sender!="You"){
      if (inputText.trim() === '') 
        return;
      const newMessage = { sender: 'You', text: inputText }
      setMessages([...messages, newMessage])
      setInputText('')
    }
  };
  
  const handleInputChange = (e) => {
    setInputText(e.target.value);
  };
  return (
    <>
        <Row>
        {messages.map((msg, index) => (
            <p className={msg.sender == 'You'? 'right-text':'left-text'}> 
                <strong>{msg.sender === botName ? botName+": " : ''}</strong>
                {msg.text}
            </p>
        ))}
        </Row>
        <Row className="footer-row">
            <Col md={11}>
                <Form onSubmit={e=>{e.preventDefault(); handleSendMessage()}}>
                    <Form.Group controlId="messageInput">
                        <Form.Control
                        type="text"
                        placeholder="Scrivi un messaggio..."
                        value={inputText}
                        onChange={e=>handleInputChange(e)}
                        />
                    </Form.Group>
                </Form>
            </Col>
            <Col md={1}>
                <Button variant="primary" onClick={handleSendMessage} >
                        Invia
                </Button>
            </Col>
        </Row>
    </>
  );
}
export default Chat;