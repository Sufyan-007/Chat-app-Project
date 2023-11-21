
import { Injectable } from '@angular/core';
import  * as SockJS from 'sockjs-client';
// import * as Stomp from 'stompjs';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import * as Stomp from "stompjs";
import { Messages } from '../interface/messages';
// import * as SockJS from "so

@Injectable({
  providedIn: 'root'
})
export class WebSocketService{
  socket= new SockJS("http://localhost:8080/ws")
  stompClient= Stomp.over(this.socket)

  failCallback= (error:any) =>{
    console.log("Callback "+ error)
    // setTimeout(this.connectToServer.bind(this),5000)
  }

  recievedMessage:Subject<Messages> = new Subject<Messages>();

  constructor() { 
    this.connectToServer()
    this.stompClient.debug=()=>{}
  }
  connectToServer() {
    
    if(this.stompClient.connected){
      this.stompClient.disconnect();
    }
    
    const username=localStorage.getItem("username")
    this.stompClient.connect({
      'Authorization': localStorage.getItem("token"),
    },()=>{
      
      this.stompClient.subscribe("/topic/chat/"+username,(data:any)=>{
        // console.log(data.body);
        this.recievedMessage.next(JSON.parse(data.body));
        
      }, {'Authorization': localStorage.getItem("token"),})
    },
    this.failCallback
    );
    
  }

  getRecievedMessage(){
    return this.recievedMessage
  }

}
