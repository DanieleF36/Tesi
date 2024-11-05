import NAMES from "./names"

const URL = "http://81.56.106.48:8080"

async function NPCNames() {
    const res = await fetch(URL+ "/npcs");
    const npcs = await res.json();
    
    return npcs.map(npc=>{return{id: npc.id, name:npc.name, role:npc.groupName}})
    /*
   return new Promise((resolve)=>{
    resolve(
        NAMES.map((p)=>{return {id: p.id, name: p.firstName+" "+p.lastName, role:p.role}})
    )
   })*/
}

async function sendMessage(idNpc, input) {
    
    const res = await fetch(URL+"/npcs/"+idNpc+"/conversation",{
        method: 'PUT',
        headers: {
           'Content-Type': 'application/json',
        },
        body: JSON.stringify({msg: input.text}),
    })
    const response = await res.text()
    return response
    /*
    return new Promise((resolve)=>{
        resolve("ok")
       })*/
}

async function endConversation(id) {
    const res = await fetch(URL+"/npcs/"+id+"/conversation", {method:"DELETE"})
    if(res.status==200)
        return 200
    else
        throw new Error("End conversation failed")
}

async function generateEvent(event) {
    const res = await fetch(URL+"/events",{
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(event),
    })
    return res.status
}

async function MatchOptions(options) {
    delete options.playGood
    delete options.goodTactic
    console.log(options)
    const res = await fetch(URL+"/match/0",{
        method:"PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(options)
    })
    const matchResult = res.json()
    return matchResult
}

const API = {NPCNames, sendMessage, endConversation, generateEvent, MatchOptions}

export default API