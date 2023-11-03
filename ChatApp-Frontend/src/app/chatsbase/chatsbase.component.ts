import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Conversation } from '../interface/conversation';
import { WebSocketService } from '../service/web-socket.service';

@Component({
  selector: 'app-chatsbase',
  templateUrl: './chatsbase.component.html',
  styleUrls: ['./chatsbase.component.css']
})
export class ChatsbaseComponent implements OnInit{
  selectedConv!:Conversation;

  constructor(private router:Router, private websocketService:WebSocketService){}

  ngOnInit(): void {
    if(localStorage.getItem("token") == null){
      console.log("Init")
      this.router.navigate(["/"]);
    }
    else{
      this.websocketService.connectToServer();
    }

  }
  convSelect(event: Conversation) {
    this.selectedConv = event;
  }
    


}
