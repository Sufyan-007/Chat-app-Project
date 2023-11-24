import { Component, ElementRef, OnInit } from '@angular/core';
import { User } from '../interface/user';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { Subject, Observable, switchMap, debounceTime } from 'rxjs';
import { ConversationService } from '../service/conversation.service';
import { icon } from '@fortawesome/fontawesome-svg-core';

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
  description=""
  groupIcon:File|null =null




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
      .createGroup(this.groupName,this.description, [...this.groupUsers.values()],this.groupIcon)
      .subscribe((response) => {
        console.log(response);
        this.router.navigate(['/home/chat/' + response.id]);
      });
  }

  removeUser(user: User): void {
    this.groupUsers.delete(user);
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    const allowedType = ['image/jpeg', 'image/png'];
    if (allowedType.includes(file.type)) {
      if (file.size <= 5 * 1024 * 1024) {
        this.groupIcon = file;
      } else {
        alert('File size too large (max: 3MB)');
      }
    } else {
      alert('Please select an image file');
    }
  }

  openFileInput() {
    // Trigger the hidden file input element
    document.getElementById('profilePicture')!.click();
  }

  getProfilePictureUrl() {
    // Return the URL for displaying the selected profile picture
    return this.groupIcon
      ? URL.createObjectURL(this.groupIcon)
      : '../../assets/Icons/person-circle.svg';
  }
}
