import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatsbaseComponent } from './chatsbase/chatsbase.component';
import { LoginComponent } from './login/login.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { ConversationComponent } from './conversation/conversation.component';
import { NewConversationComponent } from './new-conversation/new-conversation.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';

const routes: Routes = [
  {
    path: 'home',
    component: ChatsbaseComponent,
    children: [
      { path: 'chat/:id', component: ConversationComponent },
      { path: 'new-dm/:id', component: NewConversationComponent }, 
      {path:"create-group", component: CreateGroupComponent},
    ],
  },
  { path: 'profile', component: ProfilePageComponent },
  {path:"forgot-password", component: ForgotPasswordComponent},
  
  { path: '', component: LoginComponent },
  

  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
