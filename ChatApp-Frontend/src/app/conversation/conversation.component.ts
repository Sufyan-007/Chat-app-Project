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
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css'],
  host: {
    class: 'col h-100 ',
  },
})
export class ConversationComponent implements  AfterViewChecked {
  conversationId!: number;
  conversation!: Conversation;
  @ViewChild('chatbox') private chatbox!: ElementRef;
  showDetails = false;
  messages: Messages[] = [];
  autoscroll = false;

  constructor(
    private conversationService: ConversationService,
    private route: ActivatedRoute
  ) {
    this.route.paramMap.subscribe((param) => {
      if (param != null) {
        this.conversationId = param.get('id') as unknown as number;
        this.MyOnInit();
      }
    });
  }

  MyOnInit(): void {
    this.conversationService
      .getConversationById(this.conversationId)
      .subscribe((result) => {
        this.conversation = result;
        this.refreshConversation();
      });
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

  refreshConversation() {
    this.conversationService
      .getMessages(this.conversation.id)
      .pipe(
        map((messages) => {
          return messages.map((message) => {
            if (message.sender.username === localStorage.getItem('username')) {
              message.self = true;
            }
            return message;
          });
        })
      )
      .subscribe((messages) => {
        this.messages = messages;
      });
  }
  sendMessage(message: string) {
    this.messageInput = '';
    if (message.trim() !== '') {
      this.messageInput = '';
      this.autoscroll = true;
      this.conversationService.sendMessage(message, this.conversation.id);
    }
  }

  toggleDetails() {
    this.showDetails = !this.showDetails;
  }

  openFile() {
    document.getElementById('fileInput')!.click();
  }

  fileSelected(event: any) {
    const file = event.target.files[0];
    const allowedType = ['image/jpeg', 'image/png'];

    if (file.size <= 50 * 1024 * 1024) {
      this.conversationService.sendAttachment(this.conversation.id, file);
    } else {
      alert('File size too large (max: 50MB)');
    }
  }
}
