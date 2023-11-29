import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ChatsbaseComponent } from './chatsbase/chatsbase.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ChatsComponent } from './chats-scrollbar/chats.component';
import { ConversationComponent } from './conversation/conversation.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { HttpCustomInterceptor } from './http-interceptor.interceptor';
import { NewConversationComponent } from './new-conversation/new-conversation.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import { LimitStringPipe } from './custom-pipes/limit-string.pipe';
import { ConversationDetailsComponent } from './conversation-details/conversation-details.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';



@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ChatsbaseComponent,
    LoginComponent,
    
    ChatsComponent,
    ConversationComponent,
    SearchBarComponent,
    NewConversationComponent,
    CreateGroupComponent,
    LimitStringPipe,
    ConversationDetailsComponent,
    ProfilePageComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    MatDialogModule,
    
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS, useClass:HttpCustomInterceptor, multi:true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
