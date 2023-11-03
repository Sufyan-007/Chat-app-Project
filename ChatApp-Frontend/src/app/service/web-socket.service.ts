
import { Injectable } from '@angular/core';
import  * as SockJS from 'sockjs-client';
// import * as Stomp from 'stompjs';
import { Observable } from 'rxjs';
import * as Stomp from "stompjs";
// import * as SockJS from "so

@Injectable({
  providedIn: 'root'
})
export class WebSocketService{
  socket= new SockJS("http://localhost:8080/ws")
  stompClient= Stomp.over(this.socket)
  failCallback= (error:any) =>{
    console.log("Callback"+ error)
    setTimeout(this.connectToServer.bind(this),5000)
  }

  constructor() { 
    

  }
  connectToServer() {
    const username=localStorage.getItem("username")
    this.stompClient.connect({},()=>{

      this.stompClient.subscribe("/topic/chat/"+username,(data:any)=>{
        console.log(data);
      })
    },
    this.failCallback
    );
  }
}
