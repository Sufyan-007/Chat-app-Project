import { Component, ElementRef, OnInit } from '@angular/core';
import { User } from '../interface/user';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { Subject, Observable, switchMap, debounceTime } from 'rxjs';
import { ConversationService } from '../service/conversation.service';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css'],
  host: {
    class: 'col h-100 ',
  },
})
export class CreateGroupComponent implements OnInit {
  groupUsers = new Set<User>();
  groupName: String = '';

  searchResults: User[] = [];
  searchPipe = new Subject<string>();
  results: Observable<User[]> = this.searchPipe.pipe(
    switchMap((search) => this.userService.findUsers(search)),
    debounceTime(500)
  );

  ngOnInit(): void {
    console.log('Init');
  }

  constructor(
    private elementRef: ElementRef,
    private userService: UserService,
    private router: Router,
    private conversationService: ConversationService
  ) {
    this.results.subscribe((results) => {
      this.searchResults = results;
    });
  }

  search(event: any) {
    if (event.target.value) {
      this.searchPipe.next(event.target.value);
    } else {
      this.searchResults = [];
    }
  }

  addUser(user: User) {
    this.groupUsers.add(user);
  }

  createGroup(): void {
    this.conversationService
      .createGroup(this.groupName, [...this.groupUsers.values()])
      .subscribe((response) => {
        console.log(response);
        this.router.navigate(['/home/chat/' + response.id]);
      });
  }

  removeUser(user: User): void {
    this.groupUsers.delete(user);
  }
}
