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
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-conversation',
  templateUrl: './chat-bot.component.html',
  styleUrls: ['./chat-bot.component.css'],
  host: {
    class: 'col h-100 ',
  },
})
export class ChatBotComponent implements AfterViewChecked {
  @ViewChild('chatbox') private chatbox!: ElementRef;
  showDetails = false;
  messages: Messages[] = [];
  autoscroll = false;

  constructor(
    private conversationService: ConversationService,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {
    this.route.paramMap.subscribe((param) => {
      if (param != null) {
        this.MyOnInit();
      }
    });
  }

  MyOnInit(): void {
    this.autoscroll = true;
  }

  ngAfterViewChecked(): void {
    if (this.messages!.length > 0 && this.autoscroll) {
      this.scrollToBottom();
      // this.autoscroll = false;
    }
  }

  scrollToBottom(): void {
    this.chatbox.nativeElement.scrollTop =
      this.chatbox.nativeElement.scrollHeight;
  }

  messageInput: string | any = '';

  refreshConversation() {}
  sendMessage(message: string) {
    if (message.trim() !== '') {
      console.log(message);
      const m: Messages = {
        self: true,
        conversationId: -1,
        message: message,
        media: false,
        sender: {
          username: 'You',
          name: '',
          email: '',
          profilePictureUrl: '',
          bio: '',
        },
        sentAt: new Date().getTime(),
        updatedAt: new Date().getTime()
      };
      this.http.post<String>("http://localhost:8000/bot",{query:message}).subscribe((response)=>{
        console.log(response);
        const m: Messages = {
          self: false,
          conversationId: -1,
          message: response as string,
          media: false,
          sender: {
            username: 'ChatBot',
            name: '',
            email: '',
            profilePictureUrl: '../../assets/Icons/chat-bot-svgrepo-com.svg',
            bio: '',
          },
          sentAt: new Date().getTime(),
          updatedAt: new Date().getTime()
        };
        this.messages.push(m)
      })
      this.messages.push(m)
      this.messageInput = '';
      this.autoscroll = true;
    }
  }

  toggleDetails() {
    this.showDetails = !this.showDetails;
  }
}
