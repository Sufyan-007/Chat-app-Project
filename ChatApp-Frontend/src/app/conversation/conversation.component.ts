import { AfterViewChecked, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ConversationService } from '../service/conversation.service';
import { Conversation } from '../interface/conversation';
import { Messages } from '../interface/messages';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css']
})
export class ConversationComponent implements OnInit, OnChanges,AfterViewChecked {
  @Input() conversation!:Conversation;
  @ViewChild("chatbox") private chatbox!:ElementRef ;
  
  messages?: Messages[];


  constructor(private conversationService:ConversationService){}

  ngOnInit(): void {
    // this.messages=this.conversationService.getMessages(this.conversation.id); 
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(this.conversation){
       this.refreshConversation()
    }
  }
  ngAfterViewChecked(): void {
    
    if(this.chatbox){
      this.chatbox.nativeElement.scrollTop=this.chatbox.nativeElement.scrollHeight;
    }
  }
  
  


  messageInput:string |any;

  refreshConversation(){
    this.conversationService.getMessages(this.conversation.id).subscribe(messages =>this.messages=messages);
    
  }
  sendMessage(message:string){
    this.messageInput=""
    if(message!=="" ){  
      this.messageInput=""
      console.log(message)
      this.conversationService.sendMessage(message,this.conversation.id).then((message) => {
        message.self=true
        this.messages?.push(message)
      })
      this.refreshConversation()
    }
  }
}
