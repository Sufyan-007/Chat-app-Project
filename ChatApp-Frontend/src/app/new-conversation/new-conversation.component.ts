import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ConversationService } from '../service/conversation.service';
import { Conversation } from '../interface/conversation';
import { Messages } from '../interface/messages';
import { Observable, map } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../interface/user';

@Component({
  selector: 'app-new-conversation',
  templateUrl: './new-conversation.component.html',
  styleUrls: ['./new-conversation.component.css'],
  host:{
    class:'col h-100 '
  }
})

export class NewConversationComponent{
  conversation!: Conversation;
  user!: User;

  messages: Messages[]=[];
  autoscroll=false;

  constructor(private conversationService: ConversationService,private route:ActivatedRoute,private router:Router) {
    this.route.paramMap.subscribe((param)=>{
      if(param!=null ){
        const data=localStorage.getItem('newChat')
        if(data!=undefined){
          this.user= JSON.parse(  data as string );
          localStorage.removeItem('newChat');
        }
        else{
          this.router.navigate(['/home']);
        }
        console.log(this.user)
        this.MyOnInit();
      }

    })
    
  }

  MyOnInit(): void {
    this.conversation = {conversationName:this.user.username,iconUrl:this.user.profilePictureUrl} as Conversation;
    
  }


  messageInput: string | any;

  sendMessage(message: string) {
    this.messageInput = '';
    if (message !== '') {
      this.messageInput = '';
      this.autoscroll = true;
      this.conversationService.sendNewMessage(message, this.user.username).then((message:Messages) => {
        console.log(message)

        this.router.navigate(['/home/chat/'+message.conversationId]);
      })
    }
  }
}
