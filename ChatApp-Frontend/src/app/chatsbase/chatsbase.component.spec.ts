import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatsbaseComponent } from './chatsbase.component';

describe('ChatsbaseComponent', () => {
  let component: ChatsbaseComponent;
  let fixture: ComponentFixture<ChatsbaseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatsbaseComponent]
    });
    fixture = TestBed.createComponent(ChatsbaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
