import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ConversationService } from '../service/conversation.service';
import { Observable } from 'rxjs';
import { Conversation } from '../interface/conversation';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.css']
})
export class ChatsComponent implements OnInit {
  activeConversationId!:number;

  @Output() selectConv= new EventEmitter();

  constructor(private conversationService: ConversationService, private router:Router){}

  conversations?: Observable<Conversation[]>;

  ngOnInit(): void {
    
    this.conversations=this.conversationService.getConversations()
  }

  open(conversation:Conversation){
    this.router.navigate([],{fragment:String(conversation.conversationName)})
    this.activeConversationId;
    this.selectConv.emit(conversation);
  }
}
