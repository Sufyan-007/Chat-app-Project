import { TestBed } from '@angular/core/testing';

import { HttpCustomInterceptor } from './http-interceptor.interceptor';

describe('HttpInterceptorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      HttpCustomInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: HttpCustomInterceptor = TestBed.inject(HttpCustomInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
