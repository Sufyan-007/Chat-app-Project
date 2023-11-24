import { Injectable, OnDestroy } from '@angular/core';
import { Conversation } from '../interface/conversation';
import {
  BehaviorSubject,
  Observable,
  ReplaySubject,
  Subject,
  Subscriber,
  lastValueFrom,
  map,
} from 'rxjs';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Messages } from '../interface/messages';
import { WebSocketService } from './web-socket.service';
import { User } from '../interface/user';

@Injectable({
  providedIn: 'root',
})
export class ConversationService implements OnDestroy {
  //Data
  conversations: Record<Conversation['id'], Conversation> = {};
  allMessages: Record<Conversation['id'], Messages[]> = {};
  result = new BehaviorSubject<Record<Conversation['id'], Conversation>>({});
  receivedMessages!: Subject<Messages>;
  conversationMessages = new BehaviorSubject<Messages[]>([]);

  //Lifecycle methods
  ngOnDestroy(): void {
    if (this.receivedMessages) this.receivedMessages.unsubscribe();
  }

  constructor(
    private http: HttpClient,
    private webSocketService: WebSocketService
  ) {
    this.connectToServer();
  }

  //Custom methods
  connectToServer(): void {
    this.receivedMessages = this.webSocketService.getRecievedMessage();
    this.receivedMessages.subscribe((message: Messages) => {
      if(this.allMessages[message.conversationId]!=undefined){
        this.allMessages[message.conversationId].push(message);
      }
      else{
        this.allMessages[message.conversationId]=[message]
      }
      this.conversationMessages.next(this.allMessages[message.conversationId]);
      if(this.conversations[message.conversationId]==undefined) {
        this.getConversationById(message.conversationId).unsubscribe();
      }else{
        this.conversations[message.conversationId].latestMessage=message.message;
      }
    });
  }

  getConversations(): Subject<Record<Conversation['id'], Conversation>> {
    if(this.result.closed){this.result = new BehaviorSubject<Record<Conversation['id'], Conversation>>(
      {}
    );}
    if (this.conversations) {
      const url = 'http://localhost:8080/conversations';
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: String(localStorage.getItem('token')),
      });
      this.http
        .get<Conversation[]>(url, { headers: headers })
        .subscribe((response) => {
          for (let conversation of response) {
            this.conversations[conversation.id] = conversation;
          }
          // this.conversations=this.conversations.concat(response)
          // console.log(this.conversations);
          this.result.next(this.conversations);
        });
    } else {
      this.result.next(this.conversations);
    }
    return this.result;
  }

  getConversationById(conversationId: number): Subject<Conversation> {
    const conv = new ReplaySubject<Conversation>();
    if (!this.conversations[conversationId]) {
      const url = 'http://localhost:8080/conversations/' + conversationId;
      // console.log('in Conversation : ' + url);
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: String(localStorage.getItem('token')),
      });
      this.http
        .get<Conversation>(url, { headers: headers })
        .subscribe((response) => {
          this.conversations[response.id] = response;
          this.result.next(this.conversations)
          conv.next(response);
        });
    } else {
      conv.next(this.conversations[conversationId]);
    }
    return conv;
  }
  

  refresgetConversations(): void {
    if (this.conversations) {
      this.result.next(this.conversations);
    }
  }

  getMessages(conversationId: number): Observable<Messages[]> {
    const url = `http://localhost:8080/conversations/messages/${conversationId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });

    this.http
      .get<Messages[]>(url, { headers: headers })
      .subscribe((messages) => {
        this.allMessages[conversationId] = messages;
        this.conversationMessages.next(this.allMessages[conversationId]);
      });
    return this.conversationMessages;
  }

  sendMessage(message: String, conversationId: number):Promise<Messages> {
    const url = `http://localhost:8080/sendmessage`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });
    const data = {
      message: message,
      conversationId: conversationId,
    };
    const response = this.http.post<Messages>(url, data, { headers });
    return lastValueFrom(response);
  }
  sendNewMessage(message: String, sentTo: string):Promise<Messages> {
    const url = `http://localhost:8080/sendmessage`;
    const headers = new HttpHeaders({
      Authorization: String(localStorage.getItem('token')),
    });
    const data = {
      message: message,
      sentTo: sentTo,
    };
    const response = this.http.post<Messages>(url, data, { headers });
    return lastValueFrom(response);
  }
  


  createGroup(conversationName:string|String,description:String|string,users:User[],groupIcon:File|null):Observable<Conversation> {
    const newConv={
      conversationName: conversationName,
      description:description,
      users: users
    }
    const body=JSON.stringify(newConv);
    const data =new FormData();
    data.append("body",body)
    if(groupIcon!=null){
      data.append("groupIcon",groupIcon)
    }

    const url = `http://localhost:8080/conversations`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });

    return this.http.post<Conversation>(url, data, { headers });
    
  }
}
