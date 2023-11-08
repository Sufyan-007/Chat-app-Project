import { Injectable, OnDestroy } from '@angular/core';
import { Conversation } from '../interface/conversation';
import {
  BehaviorSubject,
  Observable,
  Subject,
  Subscriber,
  lastValueFrom,
  map,
} from 'rxjs';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Messages } from '../interface/messages';
import { WebSocketService } from './web-socket.service';

@Injectable({
  providedIn: 'root',
})
export class ConversationService implements OnDestroy{

  //Data
  conversations: Record<Conversation["id"],Conversation> = {};
  allMessages: Record<Conversation["id"],Messages[]> = {};


  //Subjects
  result = new BehaviorSubject<Record<Conversation["id"],Conversation>>({});
  receivedMessages!:Subject<Messages> ;
  conversationMessages= new BehaviorSubject<Messages[]>([]);


  //Lifecycle methods
  ngOnDestroy(): void {
      if(this.receivedMessages) this.receivedMessages.unsubscribe();
  }

  constructor(private http: HttpClient,private webSocketService:WebSocketService) {
    this.connectToServer();
  }


  //Custom methods
  connectToServer(): void {
    this.receivedMessages=this.webSocketService.getRecievedMessage();
    this.receivedMessages.subscribe((message:Messages) =>{

      this.allMessages[message.conversationId].push(message)
      this.conversationMessages.next(this.allMessages[message.conversationId])
      
    })
  }


  getConversations(): Subject<Record<Conversation["id"],Conversation>> {
    this.result=new BehaviorSubject<Record<Conversation["id"],Conversation>>({})
    if (this.conversations) {
      const url = 'http://localhost:8080/conversations';
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: String(localStorage.getItem('token')),
      });
      this.http
        .get<Conversation[]>(url, { headers: headers })
        .subscribe((response) => {
          for(let conversation of response){
            this.conversations[conversation.id] = conversation
          }
          // this.conversations=this.conversations.concat(response)
          console.log(this.conversations)
          this.result.next(this.conversations);
        });
    } else {
      this.result.next(this.conversations);
    }
    return this.result;
  }

  
  refresgetConversations(): void {
    if (this.conversations) {
      this.result.next(this.conversations) ;

    
    }
  }


  getMessages(conversationId: number): Observable<Messages[]> {
    const url = `http://localhost:8080/conversations/${conversationId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });

    this.http.get<Messages[]>(url, { headers: headers }).subscribe((messages)=>{
      this.allMessages[conversationId]=messages
      this.conversationMessages.next(this.allMessages[conversationId])
    })
    return this.conversationMessages;
  }

  sendMessage(message: String, conversationId: number) {
    console.log('In service');
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
    // response.subscribe(data=>{
    //   console.log(data)
    // })
    return lastValueFrom(response);
  }
}
