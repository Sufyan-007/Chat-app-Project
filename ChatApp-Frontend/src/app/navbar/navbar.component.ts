import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../interface/user';
import { ProfilePageComponent } from '../profile-page/profile-page.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor(
    private router: Router,
    private userService: UserService,
    private dialog: MatDialog
  ) {}
  user!: User;

  ngOnInit(): void {
    this.userService
      .findUser(localStorage.getItem('username') as string)
      .subscribe((user) => {
        this.user = user;
      });
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  openProfile() {
    if (this.user != null) {
      const ref=this.dialog.open(ProfilePageComponent, {
        data: {
          user: this.user,
        },
        width: '700px',
      });
      ref.afterClosed().subscribe((data) => {
        if (data!= null) {
          this.user = data;
        }
      })
    }
  }
}
