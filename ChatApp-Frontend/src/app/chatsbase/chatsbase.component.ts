import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Conversation } from '../interface/conversation';

@Component({
  selector: 'app-chatsbase',
  templateUrl: './chatsbase.component.html',
  styleUrls: ['./chatsbase.component.css']
})
export class ChatsbaseComponent implements OnInit{
  selectedConv!:Conversation;

  constructor(private router:Router){}

  ngOnInit(): void {
    if(localStorage.getItem("token") == null){
      console.log("Init")
      this.router.navigate(["/"]);
    }
    else{
      console.log("else")
    }

  }
  convSelect(event: Conversation) {
    this.selectedConv = event;
  }
    


}
