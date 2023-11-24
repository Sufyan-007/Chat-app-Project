import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Conversation } from '../interface/conversation';

@Component({
  selector: 'app-conversation-details',
  templateUrl: './conversation-details.component.html',
  styleUrls: ['./conversation-details.component.css']
})
export class ConversationDetailsComponent {

  @Output() closeEvent:EventEmitter<any>=new EventEmitter();
  @Input() conversation!:Conversation;

  close(){
    this.closeEvent.emit();
  }
}
