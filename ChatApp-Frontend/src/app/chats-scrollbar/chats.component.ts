import { AfterContentChecked, Component, EventEmitter, OnChanges, OnDestroy, OnInit, Output } from '@angular/core';
import { ConversationService } from '../service/conversation.service';
import { Observable, Subject } from 'rxjs';
import { Conversation } from '../interface/conversation';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.css']
})
export class ChatsComponent implements OnInit,OnDestroy{
  activeConversationId!:number;

  @Output() selectConv= new EventEmitter();

  constructor(private conversationService: ConversationService, private router:Router){}

  conversations: Conversation[]=[];
  conversationSubject!: Subject<Record<Conversation["id"],Conversation>>;

  ngOnDestroy(): void {
      this.conversationSubject.unsubscribe();
  }

  ngOnInit(): void {
    
    this.conversationSubject= this.conversationService.getConversations()
    this.conversationSubject.subscribe((convs)=>{

      // Object.values(convs).forEach((conv)=>{
      //   this.conversations.push(conv);
      // })
      this.conversations = Object.values( convs);
    })
  }

  open(conversation:Conversation){
    this.router.navigate(["/home/chat/"+conversation.id])
    this.activeConversationId;
    this.selectConv.emit(conversation);
  }
}
