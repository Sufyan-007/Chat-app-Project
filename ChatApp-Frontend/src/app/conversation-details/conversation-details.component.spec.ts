import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConversationDetailsComponent } from './conversation-details.component';

describe('ConversationDetailsComponent', () => {
  let component: ConversationDetailsComponent;
  let fixture: ComponentFixture<ConversationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConversationDetailsComponent]
    });
    fixture = TestBed.createComponent(ConversationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
